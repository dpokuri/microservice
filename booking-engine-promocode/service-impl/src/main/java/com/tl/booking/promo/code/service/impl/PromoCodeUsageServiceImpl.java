package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepository;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import com.tl.booking.promo.code.entity.dao.SystemParameter;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.utility.PeriodHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
import com.tl.booking.promo.code.service.api.SystemParameterService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PromoCodeUsageServiceImpl implements PromoCodeUsageService {

  @Autowired
  private PromoCodeUsageRepository promoCodeUsageRepository;

  @Autowired
  private PromoCodeObjectService promoCodeObjectService;

  @Autowired
  private CacheService cacheService;

  @Autowired
  private SystemParameterService systemParameterService;

  @Autowired
  private TimeZoneProperties timeZoneProperties;

  @Override
  public Single<PromoCodeUsage> findPromoCodeUsageByPromoCodeObjectAndUsername(
      MandatoryRequest mandatoryRequest, PromoCodeObject promoCodeObject) {
    Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
    Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
    return this.getPromoCodeUsageByPromoCodeAndUsername(mandatoryRequest.getUsername(), startDate, endDate,
        promoCodeObject, "*").subscribeOn(Schedulers.io());

  }

  @Override
  public Single<PromoCodeUsage>
  findPromoCodeUsageByPromoCodeObjectAndCardNumber(
      MandatoryRequest mandatoryRequest, PromoCodeObject promoCodeObject, String cardNumber) {
    Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
    Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
    return this.getPromoCodeUsageByPromoCodeAndUsername("*", startDate, endDate,
        promoCodeObject, cardNumber).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<PromoCodeUsage>
  findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
      MandatoryRequest mandatoryRequest, Date todayDate, PromoCodeObject promoCodeObject) {
    return this.getPromoCodeUsageByPromoCodeAndUsername(mandatoryRequest.getUsername(), todayDate, todayDate,
        promoCodeObject, "*").subscribeOn(Schedulers.io());

  }

  @Override
  public Single<PromoCodeUsage>
  findPromoCodeUsageByPromoCodeObjectAndCardNameAndTodayDate(
      MandatoryRequest mandatoryRequest, Date todayDate, PromoCodeObject promoCodeObject, String
      cardNumber) {
    return this.getPromoCodeUsageByPromoCodeAndUsername("*", todayDate, todayDate,
        promoCodeObject, cardNumber).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Integer> findCurrentQuotaByPromoCode(MandatoryRequest mandatoryRequest,
      String code) {
    Single<PromoCodeObject> promoCodeObjectSingle = this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode
        (mandatoryRequest, code);

    return promoCodeObjectSingle
        .flatMap(promoCodeObject -> this.calculateQuotaFromMaxQuotaAndUsageGeneral
            (mandatoryRequest, promoCodeObject))
        .subscribeOn(Schedulers.io());
  }

  @Override
  public Single<PromoCodeObject> createPromoCodeUsageGeneral(MandatoryRequest mandatoryRequest,
      String code) {
    Single<PromoCodeObject> promoCodeObjectSingle = this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode
            (mandatoryRequest, code);

    return promoCodeObjectSingle
        .flatMap(promoCodeObject -> this.findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (mandatoryRequest, promoCodeObject)
        .map(promoCodeUsage -> {
          if(promoCodeUsage.getUsageCount() == null){
            Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
            Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

            PromoCodeUsage newPromoCodeUsage = new PromoCodeUsage();
            newPromoCodeUsage.setUsageCount(0);
            newPromoCodeUsage.setStartDate(startDate);
            newPromoCodeUsage.setEndDate(endDate);
            newPromoCodeUsage.setPromoCode(promoCodeObject.getPromoCode().getCode());
            newPromoCodeUsage.setPromoCodeId(promoCodeObject.getPromoCode().getId());
            newPromoCodeUsage.setUsername("*");
            newPromoCodeUsage.setCardNumber("*");
            this.promoCodeUsageRepository.save(newPromoCodeUsage);
          }
          return promoCodeObject;
        })).subscribeOn(Schedulers.io());
  }

  private boolean isExistSystemParameter(SystemParameter systemParameter) {
    return systemParameter != null;
  }

  @Override
  public Single<PromoCodeUsage>
  findPromoCodeUsageGeneralByPromoCodeObjectAndUsername(
      MandatoryRequest mandatoryRequest, PromoCodeObject promoCodeObject) {
    Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
    Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

    return this.getPromoCodeUsageByPromoCodeAndUsername("*", startDate,
        endDate,
        promoCodeObject, "*").subscribeOn(Schedulers.io());

  }

  private Single<Integer> calculateQuotaFromMaxQuotaAndUsageGeneral(MandatoryRequest
      mandatoryRequest, PromoCodeObject
      promoCodeObject) {
    return Single.<Integer>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      Integer expirySeconds = 5;
      SystemParameter systemParameter = this.systemParameterService.findSystemParameterByStoreId
          (mandatoryRequest.getStoreId(), "CHECK_QUOTA_CACHE_TIMEOUT");

      if(isExistSystemParameter(systemParameter)){
        expirySeconds = Integer.parseInt(systemParameter.getValue());
      }

      String cacheKey = this.generateCacheKey(promoCodeObject
          .getPromoCode().getId());
      Integer cacheCountQuota = this.cacheService.findCacheByKey(cacheKey,
          Integer.class);
      Integer currentQuota = cacheCountQuota;
      if(!isExistCurrentQuotaCache(cacheCountQuota)){
        PromoCodeUsage promoCodeUsageGeneral =
            this.promoCodeUsageRepository
                .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
                    promoCodeObject.getPromoCode().getId(),
                    startDate,
                    endDate,
                    "*",
                    "*"
                );
        currentQuota = promoCodeObject.getPromoCode().getMaxQty();
        if(isExistPromoCodeUsage(promoCodeUsageGeneral)){
          currentQuota =  currentQuota - promoCodeUsageGeneral
              .getUsageCount();
        }

        this.cacheService.createCache(cacheKey, currentQuota, expirySeconds);
      }

      singleEmitter.onSuccess(currentQuota);
    });
  }

  private boolean isExistCurrentQuotaCache(Integer cacheCountQuota) {
    return cacheCountQuota != null;
  }

  private Single<PromoCodeUsage> getPromoCodeUsageByPromoCodeAndUsername(String
      username, Date
      startDate, Date
      endDate,
      PromoCodeObject promoCodeObject, String cardNumber) {
    return Single.create(singleEmitter -> {
      PromoCodeUsage searchPromoCodeUsage = this.promoCodeUsageRepository
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              promoCodeObject.getPromoCode().getId(),
              startDate, endDate,
              cardNumber,
              username);

      if (!isExistPromoCodeUsage(searchPromoCodeUsage)) {
        searchPromoCodeUsage = new PromoCodeUsage();
      }

      singleEmitter.onSuccess(searchPromoCodeUsage);
    });
  }

  private String generateCacheKey(String id) {
    return CacheKey.CURRENT_QUOTA + "-" + id;
  }

  private Boolean isExistPromoCodeUsage(PromoCodeUsage promoCodeUsage) {
    return promoCodeUsage != null;
  }

}
