package com.tl.booking.promo.code.service.impl;

import com.mongodb.WriteResult;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepository;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.CommonHelper;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.libraries.utility.PeriodHelper;
import com.tl.booking.promo.code.service.api.DecrementPromoCodeUsageService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DecrementPromoCodeUsageServiceImpl implements DecrementPromoCodeUsageService {

  @Autowired
  private PromoCodeUsageRepository promoCodeUsageRepository;

  @Autowired
  private PromoCodeService promoCodeService;

  @Autowired
  private PromoCodeObjectService promoCodeObjectService;

  @Autowired
  private PromoCodeUsageLogService promoCodeUsageLogService;

  @Autowired
  private Scheduler schedulerPromoCode;

  @Autowired
  private TimeZoneProperties timeZoneProperties;

  @Override
  public Single<Boolean>
  decrementPromoCodeUsage(MandatoryRequest mandatoryRequest, String code,
      String cardNumber, String referenceId) {

    return this.promoCodeService
        .findPromoCodeByCode(mandatoryRequest, code)
        .flatMap(
            promoCode -> this
                .decrementPromoCodeUsageByCodeProcess(mandatoryRequest, promoCode, cardNumber, -1))
        .map(success -> {
          if(!success){
            throw new BusinessLogicException(ResponseCode.PROMO_CODE_NEVER_USED.getCode(),
                ResponseCode.PROMO_CODE_NEVER_USED.getMessage());
          }

          this.promoCodeUsageLogService
              .createPromoCodeUsageLog(mandatoryRequest,
                  code, 0.0, null, null, 0.00,
                  referenceId, -1, null);
          return success;
        }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementPromoCodeUsageByCodeProcess(MandatoryRequest mandatoryRequest,
      PromoCode promoCode, String cardNumber, Integer decrement) {

    return this.promoCodeObjectService
        .findPromoCodeObjectByCampaignIdMergeWithPromoCode(mandatoryRequest,
            promoCode.getCampaignId(),
            promoCode)
        .flatMap(
            promoCodeObject -> {
              Map<String, UsageRule> usageRulesMap = CommonHelper.generateUsageRuleMap
                  (promoCodeObject.getPromoCodeAdjustment().getUsageRules());

              Single<Boolean> decrementPromoCodeUsageGeneralRule = this
                  .decrementPromoCodeUsageGeneralRule(promoCodeObject,
                      decrement);

              Single<Boolean> decrementPromoCodeUsagePeriodRule = this
                  .decrementPromoCodeUsagePeriodRule(mandatoryRequest.getUsername(), promoCodeObject, usageRulesMap);

              Single<Boolean> decrementPromoCodeUsageDailyRule = this
                  .decrementPromoCodeUsageDailyRule(mandatoryRequest.getUsername(), promoCodeObject, usageRulesMap);

              Single<Boolean> decrementPromoCodeUsageDailyRuleByCardNumber = this
                  .decrementPromoCodeUsageDailyRuleByCardNumber(promoCodeObject,
                      cardNumber, usageRulesMap);

              Single<Boolean> decrementPromoCodeUsagePeriodRuleByCardNumber = this
                  .decrementPromoCodeUsagePeriodRuleByCardNumber(promoCodeObject,
                      cardNumber, usageRulesMap);

              return Single
                  .zip(decrementPromoCodeUsagePeriodRule,
                      decrementPromoCodeUsageGeneralRule,
                      decrementPromoCodeUsageDailyRule,
                      decrementPromoCodeUsageDailyRuleByCardNumber,
                      decrementPromoCodeUsagePeriodRuleByCardNumber,
                      (iPromoCodeUsagePeriodRule, iPromoCodeUsageGeneralRule, iPromoCodeUsageDailyRule,
                          iPromoCodeUsageDailyRuleByCardNumber, iPromoCodeUsagePeriodRuleByCardNumber)
                          ->
                          iPromoCodeUsagePeriodRule
                              && iPromoCodeUsageGeneralRule
                              && iPromoCodeUsageDailyRule
                              && iPromoCodeUsageDailyRuleByCardNumber
                              && iPromoCodeUsagePeriodRuleByCardNumber
                  ).map(validPromoCode -> {
                    if(!validPromoCode){
                      this.incrementUsageGeneral(promoCodeObject);
                      throw new BusinessLogicException(ResponseCode.PROMO_CODE_NEVER_USED.getCode
                          (), ResponseCode.PROMO_CODE_NEVER_USED.getMessage());
                    }
                    return true;
                  });
            }).subscribeOn(schedulerPromoCode);
  }


  private void incrementUsageGeneral(PromoCodeObject promoCodeObject) {
    Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
    Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

    this.incrementUsageProcess(startDate, endDate, "*", "*", promoCodeObject
        .getPromoCode().getId());
  }

  private void incrementUsageProcess(Date startDate, Date endDate, String cardNumber, String
      username,
      String promoCodeId) {
    PromoCodeUsage promoCodeUsage = this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            promoCodeId,
            startDate, endDate, cardNumber, username);
    if (isExistPromoCodeUsage(promoCodeUsage)) {
      this.promoCodeUsageRepository
          .updatePromoCodeUsageById(promoCodeUsage.getId(), 1, 0);
    }
  }

  private <T> List<T> mergeList(Object... objects) {
    List<T> list = new ArrayList<>();

    for (Object object : objects) {
      list.add((T) object);
    }

    return list;
  }

  private Single<Boolean> decrementPromoCodeUsagePeriodRule(String username,
      PromoCodeObject promoCodeObject, Map<String, UsageRule> usageRuleMap) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.PERIOD.getCode());
      usageRuleCode.append(ValidatedBy.USERNAME.getName());

      singleEmitter.onSuccess(this.decrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode
              .toString()), startDate,
          endDate,
          "*",
          username, promoCodeObject, -1));
    }).subscribeOn(schedulerPromoCode);

  }

  private Single<Boolean> decrementPromoCodeUsagePeriodRuleByCardNumber(
      PromoCodeObject promoCodeObject, String cardNumber, Map<String, UsageRule>
      usageRuleMap) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.PERIOD.getCode());
      usageRuleCode.append(ValidatedBy.CARD_NUMBER.getName());

      singleEmitter.onSuccess(this.decrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode
              .toString()), startDate,
          endDate,
          cardNumber,
          "*", promoCodeObject, -1));
    }).subscribeOn(schedulerPromoCode);

  }

  private Single<Boolean> decrementPromoCodeUsageGeneralRule(PromoCodeObject promoCodeObject, Integer decrement) {

    return Single.<Boolean>create(singleEmitter -> {
      Date startDate = PeriodHelper.getStartDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      Date endDate = PeriodHelper.getEndDate(promoCodeObject, timeZoneProperties.getOffsetToDate());
      PromoCodeUsage searchPromoCodeUsage = this.promoCodeUsageRepository
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              promoCodeObject.getPromoCode().getId(),
              startDate, endDate, "*", "*");
      if (isExistPromoCodeUsage(searchPromoCodeUsage)
          && isUsageCountGreaterThanZero(
          searchPromoCodeUsage)) {
        this.promoCodeUsageRepository
            .updatePromoCodeUsageById(searchPromoCodeUsage.getId(),decrement, 0);
      }
      singleEmitter.onSuccess(true);
    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementPromoCodeUsageDailyRule(String username,
      PromoCodeObject promoCodeObject, Map<String, UsageRule> usageRuleMap) {
    return Single.<Boolean>create(singleEmitter -> {
      Date todayDate = DateFormatterHelper.getTodayDate();

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.DAILY.getCode());
      usageRuleCode.append(ValidatedBy.USERNAME.getName());

      singleEmitter.onSuccess(this.decrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode.toString()), todayDate,
          todayDate,
          "*",
          username, promoCodeObject, -1));

    }).subscribeOn(schedulerPromoCode);
  }

  private Single<Boolean> decrementPromoCodeUsageDailyRuleByCardNumber(
      PromoCodeObject promoCodeObject, String cardNumber, Map<String, UsageRule> usageRuleMap) {
    return Single.<Boolean>create(singleEmitter -> {
      Date todayDate = DateFormatterHelper.getTodayDate();

      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(UsagePeriod.DAILY.getCode());
      usageRuleCode.append(ValidatedBy.CARD_NUMBER.getName());

      singleEmitter.onSuccess(this.decrementPromoCodeUsageProcess(usageRuleMap.get(usageRuleCode.toString()), todayDate,
          todayDate,
          cardNumber,
          "*", promoCodeObject, -1));

    }).subscribeOn(schedulerPromoCode);
  }

  private Boolean decrementPromoCodeUsageProcess(
      UsageRule usageRule,
      Date startDate,
      Date endDate,
      String cardNumber,
      String username,
      PromoCodeObject promoCodeObject,
      Integer decrement) {

    Boolean valid = true;
    if(isExistUsageRule(usageRule)) {
      PromoCodeUsage searchPromoCodeUsage = this.promoCodeUsageRepository
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              promoCodeObject.getPromoCode().getId(),
              startDate, endDate, cardNumber, username);

      valid = false;
      if (isExistPromoCodeUsage(searchPromoCodeUsage) && isUsageCountGreaterThanZero(
          searchPromoCodeUsage)) {
        WriteResult writeResult = this.promoCodeUsageRepository.updatePromoCodeUsageById
            (searchPromoCodeUsage.getId(), decrement, 0);
        valid = this.checkWriteResult(writeResult);
      }
    }
    return valid;
  }

  private Boolean checkWriteResult(WriteResult writeResult) {
    return writeResult.getN() > 0;
  }

  private boolean isExistUsageRule(UsageRule usageRule) {
    return usageRule != null;
  }


  private Boolean isUsageCountGreaterThanZero(PromoCodeUsage promoCodeUsage) {
    return promoCodeUsage.getUsageCount() > 0;
  }

  private Boolean isExistPromoCodeUsage(PromoCodeUsage promoCodeUsage) {
    return promoCodeUsage != null;
  }
}
