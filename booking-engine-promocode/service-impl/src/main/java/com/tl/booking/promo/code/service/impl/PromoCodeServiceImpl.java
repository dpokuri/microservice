package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.CampaignRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeRepository;
import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.PromoCodeAdjustmentService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
import com.tl.booking.promo.code.service.api.log.PromoCodeLogService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class PromoCodeServiceImpl implements PromoCodeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCodeServiceImpl.class);
  @Autowired
  PromoCodeRepository promoCodeRepository;
  @Autowired
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;
  @Autowired
  CampaignRepository campaignRepository;
  @Autowired
  CacheService cacheService;
  @Autowired
  PromoCodeLogService promoCodeLogService;

  @Autowired
  PromoCodeAdjustmentService promoCodeAdjustmentService;

  @Autowired
  PromoCodeUsageService promoCodeUsageService;

  @Autowired
  PromoCodeObjectService promoCodeObjectService;

  @Override
  public Single<PromoCode> createPromoCode(MandatoryRequest mandatoryRequest, PromoCode promoCode) {
    return Single.<PromoCode>create(singleEmitter -> {

      LOGGER.info("createPromoCode Request mandatoryRequest, promoCode {} ", mandatoryRequest,
          promoCode);

      if(promoCode.getCode().contains(" ")){
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_INVALID_CHARACTER.getCode(),
            ResponseCode.PROMO_CODE_INVALID_CHARACTER.getMessage());
      }

      Campaign campaign = this.checkCampaignId(promoCode.getCampaignId());
      this.checkAdjustmentId(campaign.getPromoCodeAdjustmentId());

      PromoCode checkPromoCode = this.promoCodeRepository
          .findPromoCodeByStoreIdAndCodeAndIsDeleted(promoCode.getStoreId(), promoCode.getCode(),
              0);

      if (isNotNullPromoCode(checkPromoCode)) {
        Campaign existPromoCodeCampaign = this.campaignRepository
            .findOne(checkPromoCode.getCampaignId());

        if (isValidPromoCodePeriod(existPromoCodeCampaign.getCampaignPeriods())) {
          throw new BusinessLogicException(ResponseCode.PROMO_CODE_EXIST.getCode(),
              ResponseCode.PROMO_CODE_EXIST.getMessage());
        }
      }

      promoCode.setIsDeleted(0);
      promoCode.setPromoCodeStatus(PromoCodeStatus.INACTIVE);

      PromoCode createdPromoCode = this.promoCodeRepository.save(promoCode);

      if (!isNotNullPromoCode(createdPromoCode)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }
      String cacheKey = this
          .generateCacheKeyByStoreIdAndCode(promoCode.getStoreId(), promoCode.getCode());
      this.cacheService.createCache(cacheKey, createdPromoCode, 0);
      this.deleteCacheFindByCampaignId(promoCode);

      LOGGER.info("createPromoCode Response promoCode {} ", createdPromoCode);

      singleEmitter.onSuccess(createdPromoCode);

    }).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<PromoCode> updateStatusActivedPromoCode(MandatoryRequest mandatoryRequest,
      String id) {

    return Single.<PromoCode>create(singleEmitter -> {
      LOGGER.info("updateStatusActivedPromoCode Request mandatoryRequest, id {} ", mandatoryRequest,
          id);

      PromoCode promoCode = this.promoCodeRepository
          .findPromoCodeByIdAndIsDeleted(id, 0);

      if (!isNotNullPromoCode(promoCode)) {
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
            ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(promoCode.getPromoCodeStatus(), PromoCodeStatus.INACTIVE)) {
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_STATUS_NOT_IN_ACTIVE.getCode(),
            ResponseCode.PROMO_CODE_STATUS_NOT_IN_ACTIVE.getMessage());
      }

      Campaign campaign = this.checkCampaignId(promoCode.getCampaignId());
      this.checkAdjustmentId(campaign.getPromoCodeAdjustmentId());

      promoCode.setPromoCodeStatus(PromoCodeStatus.ACTIVE);
      promoCode.setUpdatedBy(mandatoryRequest.getUsername());
      promoCode.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean updated = this.promoCodeRepository.updateStatus(promoCode);

      String cacheKey = this
          .generateCacheKeyByStoreIdAndCode(promoCode.getStoreId(), promoCode.getCode());
      String cacheKeyById = this.generateCacheKey(id);
      this.cacheService.deleteCache(cacheKey);
      this.cacheService.deleteCache(cacheKeyById);
      this.cacheService.createCache(cacheKey, promoCode, 0);
      this.deleteCacheFindByCampaignId(promoCode);

      LOGGER.info("updateStatusActivedPromoCode Response Boolean {} ", updated);

      singleEmitter.onSuccess(promoCode);
    })
    .flatMap(promoCode -> this.promoCodeLogService.createPromoCodeLog(mandatoryRequest, promoCode))
    .subscribeOn(Schedulers.io());

  }

  @Override
  public Single<PromoCode> updateStatusInActivedPromoCode(MandatoryRequest mandatoryRequest,
      String id) {

    LOGGER.info("updateStatusInActivedPromoCode Request mandatoryRequest, id {} ", mandatoryRequest,
        id);

    return Single.<PromoCode>create(singleEmitter -> {
      PromoCode promoCode = this.promoCodeRepository
          .findPromoCodeByIdAndIsDeleted(id, 0);

      if (!isNotNullPromoCode(promoCode)) {
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
            ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(promoCode.getPromoCodeStatus(), PromoCodeStatus.ACTIVE)) {
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_STATUS_NOT_ACTIVE.getCode(),
            ResponseCode.PROMO_CODE_STATUS_NOT_ACTIVE.getMessage());
      }

      promoCode.setPromoCodeStatus(PromoCodeStatus.INACTIVE);
      promoCode.setUpdatedBy(mandatoryRequest.getUsername());
      promoCode.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean updated = this.promoCodeRepository.updateStatus(promoCode);

      if (!updated) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKeyByStoreIdAndCode(promoCode.getStoreId(),
          promoCode.getCode());
      String cacheKeyById = this.generateCacheKey(id);
      this.cacheService.deleteCache(cacheKey);
      this.cacheService.deleteCache(cacheKeyById);
      this.cacheService.createCache(cacheKey, promoCode, 0);
      this.deleteCacheFindByCampaignId(promoCode);

      LOGGER.info("updateStatusInActivedPromoCode Response Boolean {} ", updated);

      singleEmitter.onSuccess(promoCode);

    })
    .flatMap(promoCode -> this.promoCodeLogService.createPromoCodeLog(mandatoryRequest, promoCode))
    .subscribeOn(Schedulers.io());

  }


  @Override
  public Single<PromoCode> updatePromoCode(MandatoryRequest mandatoryRequest,
      PromoCode promoCodeParam, String id) {

    return this.updateProccess(mandatoryRequest, promoCodeParam, id)
        .flatMap(updateProccess -> this.promoCodeLogService
            .createPromoCodeLog(mandatoryRequest, updateProccess)
        );
  }

  private Single<PromoCode> updateProccess(MandatoryRequest mandatoryRequest,
      PromoCode promoCodeParam, String id) {
    return Single.<PromoCode>create(singleEmitter -> {

      LOGGER.info("updatePromoCode Request mandatoryRequest, promoCodeParam, id {} ",
          mandatoryRequest, promoCodeParam, id);

      if(promoCodeParam.getCode().contains(" ")){
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_INVALID_CHARACTER.getCode(),
            ResponseCode.PROMO_CODE_INVALID_CHARACTER.getMessage());
      }

      Campaign campaign = this.checkCampaignId(promoCodeParam.getCampaignId());
      this.checkAdjustmentId(campaign.getPromoCodeAdjustmentId());

      PromoCode promoCode = this.promoCodeRepository
          .findPromoCodeByIdAndIsDeleted(id, 0);

      if (!isNotNullPromoCode(promoCode)) {
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
            ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
      }

      this.combinePromoCodeParamAndPromoCode(mandatoryRequest, promoCodeParam, promoCode);

      promoCode.setPromoCodeStatus(PromoCodeStatus.INACTIVE);

      PromoCode updatedPromoCode = this.promoCodeRepository.save(promoCode);

      if (!isNotNullPromoCode(updatedPromoCode)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKeyByStoreIdAndCode(updatedPromoCode.getStoreId(),
          updatedPromoCode.getCode());
      this.cacheService.deleteCache(cacheKey);
      String cacheKeyById = this.generateCacheKey(id);
      this.cacheService.deleteCache(cacheKeyById);
      this.cacheService.createCache(cacheKey, updatedPromoCode, 0);
      this.deleteCacheFindByCampaignId(promoCode);

      LOGGER.info("updatePromoCode Response PromoCode {} ", updatedPromoCode);

      singleEmitter.onSuccess(updatedPromoCode);

    }).subscribeOn(Schedulers.io());

  }


  @Override
  public Single<Boolean> deletePromoCode(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deletePromoCode Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      PromoCode promoCode = this.promoCodeRepository.findPromoCodeByIdAndIsDeleted(id, 0);

      if (!isNotNullPromoCode(promoCode)) {
        throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
            ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
      }

      promoCode.setIsDeleted(1);
      promoCode.setUpdatedBy(mandatoryRequest.getUsername());
      promoCode.setUpdatedDate(DateFormatterHelper.getTodayDate());
      Boolean deleted = this.promoCodeRepository.softDeleted(promoCode, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this
          .generateCacheKeyByStoreIdAndCode(promoCode.getStoreId(), promoCode.getCode());
      this.cacheService.deleteCache(cacheKey);
      String cacheKeyById = this.generateCacheKey(id);
      this.cacheService.deleteCache(cacheKeyById);
      this.deleteCacheFindByCampaignId(promoCode);

      LOGGER.info("deletePromoCode Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<PromoCode> findPromoCodeById(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("findPromoCodeById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<PromoCode>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(id);
          PromoCode promoCode = this.cacheService.findCacheByKey(cacheKey, PromoCode.class);

          if (!isNotNullPromoCode(promoCode)) {
            promoCode = this.promoCodeRepository.findPromoCodeByIdAndIsDeleted(id, 0);

            if (!isNotNullPromoCode(promoCode)) {
              throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
                  ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, promoCode, 0);
          }

          LOGGER.info("findPromoCodeById Response PromoCode {} ", promoCode);

          singleEmitter.onSuccess(promoCode);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<PromoCode> findPromoCodeByCode(MandatoryRequest mandatoryRequest, String code) {

    LOGGER.info("findPromoCodeByCode Request mandatoryRequest, code {} ", mandatoryRequest, code);

    return Single.<PromoCode>create(
        singleEmitter -> {
          String cacheKey = this
              .generateCacheKeyByStoreIdAndCode(mandatoryRequest.getStoreId(), code);
          PromoCode promoCode = this.cacheService.findCacheByKey(cacheKey, PromoCode.class);

          if (!isNotNullPromoCode(promoCode)) {
            promoCode = this.promoCodeRepository
                .findPromoCodeByStoreIdAndCodeAndIsDeleted(mandatoryRequest.getStoreId(), code, 0);

            if (!isNotNullPromoCode(promoCode)) {
              throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
                  ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, promoCode, 0);
          }

          LOGGER.info("findPromoCodeByCode Response PromoCode {} ", promoCode);

          singleEmitter.onSuccess(promoCode);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<PromoCode> findPromoCodeByCodeAndStatus(MandatoryRequest mandatoryRequest, String
      code, PromoCodeStatus promoCodeStatus) {

    LOGGER.info("findPromoCodeByCodeAndStatus Request mandatoryRequest, code, promoCodeStatus {} ",
        mandatoryRequest, code, promoCodeStatus);

    return Single.<PromoCode>create(
        singleEmitter -> {
          String cacheKey = this
              .generateCacheKeyByStoreIdAndCode(mandatoryRequest.getStoreId(), code);
          PromoCode promoCode = this.cacheService.findCacheByKey(cacheKey, PromoCode.class);

          if (!isNotNullPromoCode(promoCode)) {
            promoCode = this.promoCodeRepository
                .findPromoCodeByStoreIdAndCodeAndIsDeletedAndPromoCodeStatus
                    (mandatoryRequest.getStoreId(), code, 0, promoCodeStatus);

            if (!isNotNullPromoCode(promoCode)) {
              throw new BusinessLogicException(ResponseCode.PROMO_CODE_NOT_EXIST.getCode(),
                  ResponseCode.PROMO_CODE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, promoCode, 0);
          }

          LOGGER.info("findPromoCodeByCodeAndStatus Response PromoCode {} ", promoCode);

          singleEmitter.onSuccess(promoCode);
        }
    ).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<Page<PromoCode>> findPromoCodeFilterPaginated(MandatoryRequest mandatoryRequest,
      String code, String campaignId, Integer page, Integer size, PromoCodeColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info("findPromoCodeByCodeAndStatus Request MandatoryRequest mandatoryRequest,\n"
            + "      String code, String campaignId, Integer page, Integer size, PromoCodeColumn columnSort,\n"
            + "      SortDirection sortDirection {} ", mandatoryRequest, code, campaignId, page, size,
        columnSort, sortDirection);

    return Single.<Page<PromoCode>>create(
        singleEmitter -> {

          String sort = this.setColumnSort(columnSort);

          Page<PromoCode> promoCodes = this.promoCodeRepository
              .findPromoCodeFilterPaginated(mandatoryRequest.getStoreId(), code, campaignId, page,
                  size, sort, sortDirection);

          LOGGER.info("findPromoCodeByCodeAndStatus Response PromoCode {} ", promoCodes);

          singleEmitter.onSuccess(promoCodes);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Boolean> checkExistAndActivePromoCodeByCampaigns(MandatoryRequest mandatoryRequest, List<Campaign> campaigns, Boolean toDeleted) {

    return Single.<Boolean>create(singleEmitter -> {
      Boolean status = false;

      for (Campaign getCampaign : campaigns) {
          List<PromoCode> promoCodes = findPromoCodeByCampaignId(mandatoryRequest, getCampaign.getId());

          if(!promoCodes.isEmpty())
          {
            if(toDeleted)
            {
              throw new BusinessLogicException(ResponseCode.CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST.getCode(),ResponseCode.CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST.getMessage());
            }

            for (PromoCode getPromoCodes : promoCodes) {
              if(getPromoCodes.getPromoCodeStatus().equals(PromoCodeStatus.ACTIVE))
              {
                throw new BusinessLogicException(ResponseCode.CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE.getCode(),ResponseCode.CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE.getMessage());
              }
            }
          }
      }

      singleEmitter.onSuccess(status);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public List<PromoCode> findPromoCodeByCampaignId(MandatoryRequest mandatoryRequest, String campaignId)
  {
    String cacheKey = generateCacheKeybyCampaignId(mandatoryRequest.getStoreId(), campaignId);
    List<PromoCode> promoCodes = this.cacheService.findCacheByKey(cacheKey, List.class);

    if (!isExistPromoCodeListByCampaignid(promoCodes)) {
      promoCodes = this.promoCodeRepository.findAllByStoreIdAndCampaignIdAndIsDeleted(mandatoryRequest.getStoreId(), campaignId, 0);

      this.cacheService.createCache(cacheKey, promoCodes, 0);
    }

    return promoCodes;
  }

    private void combinePromoCodeParamAndPromoCode(MandatoryRequest mandatoryRequest,
      PromoCode promoCodeParam, PromoCode promoCode) {
    promoCode.setChannelId(promoCodeParam.getChannelId());
    promoCode.setVersion(promoCode.getVersion());
    promoCode.setStoreId(promoCode.getStoreId());
    promoCode.setCreatedDate(promoCode.getCreatedDate());
    promoCode.setCreatedBy(promoCode.getCreatedBy());
    promoCode.setUpdatedBy(mandatoryRequest.getUsername());

    promoCode.setCode(promoCodeParam.getCode());
    promoCode.setCampaignId(promoCodeParam.getCampaignId());
    promoCode.setMaxQty(promoCodeParam.getMaxQty());
  }

  private Boolean isNotNullPromoCode(PromoCode promoCode) {
    Boolean isNotNullPromoCode = false;
    if (promoCode != null) {
      isNotNullPromoCode = true;
    }

    return isNotNullPromoCode;
  }

  private String setColumnSort(PromoCodeColumn promoCodeColumn) {
    String result = null;
    if (isNotNullColumnSort(promoCodeColumn)) {
      result = promoCodeColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(PromoCodeColumn promoCodeColumn) {
    Boolean result = false;
    if (promoCodeColumn != null) {
      result = true;
    }

    return result;
  }

  private Boolean isCompareStatus(Object object1, Object object2) {
    Boolean status = false;
    if (object1.equals(object2)) {
      status = true;
    }

    return status;
  }


  private String generateCacheKey(String id) {
    return CacheKey.PROMO_CODE + "-" + id;
  }

  private String generateCacheKeyByStoreIdAndCode(String storeId, String code) {
    return CacheKey.PROMO_CODE + "-" + storeId + "-" + code;
  }

  private String generateCacheKeybyCampaignId(String storeId, String id) {
    return CacheKey.PROMO_CODE + "-campaign-" + storeId + "-" + id;
  }

  private Campaign checkCampaignId(String campaignId) {
    Campaign campaign = this.campaignRepository.findOne(campaignId);
    if (!isExistCampaign(campaign)) {
      throw new BusinessLogicException(ResponseCode.CAMPAIGN_NOT_EXIST.getCode(),
          ResponseCode.CAMPAIGN_NOT_EXIST.getMessage());
    }

    if (!campaign.getCampaignStatus()
        .equals(CampaignStatus.ACTIVE)) {
      throw new BusinessLogicException(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getCode(),
          ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getMessage());
    }

    return campaign;
  }

  private Boolean isExistCampaign(Campaign campaign) {
    Boolean existCampaign = false;
    if (campaign != null) {
      existCampaign = true;
    }

    return existCampaign;
  }

  private void checkAdjustmentId(String adjustmentId) {
    PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentById(adjustmentId);
    if (!isExistAdjustment(promoCodeAdjustment)) {
      throw new BusinessLogicException(ResponseCode.ADJUSTMENT_NOT_EXIST.getCode(),
          ResponseCode.ADJUSTMENT_NOT_EXIST.getMessage());
    }

    if (!promoCodeAdjustment.getPromoCodeAdjustmentStatus()
        .equals(PromoCodeAdjustmentStatus.ACTIVE)) {
      throw new BusinessLogicException(ResponseCode.ADJUSTMENT_NOT_ACTIVE.getCode(),
          ResponseCode.ADJUSTMENT_NOT_ACTIVE.getMessage());
    }
  }

  private Boolean isExistAdjustment(PromoCodeAdjustment promoCodeAdjustment) {
    Boolean existPromoCodeAdjustment = false;
    if (promoCodeAdjustment != null) {
      existPromoCodeAdjustment = true;
    }

    return existPromoCodeAdjustment;
  }

  private Boolean isValidPromoCodePeriod(List<CampaignPeriod> campaignPeriods) {
    Boolean validPromoCodePeriod = false;
    Date todayDate = new Date();

    for (CampaignPeriod campaignPeriod : campaignPeriods) {
      if (campaignPeriod.getStartDate().after(todayDate)) {
        validPromoCodePeriod = true;
        break;
      }
    }

    return validPromoCodePeriod;
  }

  private boolean isExistPromoCodeListByCampaignid(List<PromoCode> getCache) {
    return getCache != null;
  }

  private void deleteCacheFindByCampaignId(PromoCode promoCode)
  {
    String cacheKeyFinfByCampaignId = this.generateCacheKeybyCampaignId(promoCode.getStoreId(), promoCode.getCampaignId());
    this.cacheService.deleteCache(cacheKeyFinfByCampaignId);
  }

}
