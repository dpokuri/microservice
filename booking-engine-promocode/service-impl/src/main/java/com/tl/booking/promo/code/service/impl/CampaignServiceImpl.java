package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.CampaignRepository;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.entity.CampaignDropdown;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CampaignColumn;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.CampaignService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.log.CampaignLogService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class CampaignServiceImpl implements CampaignService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCodeServiceImpl.class);

  @Autowired
  CampaignRepository campaignRepository;
  @Autowired
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;
  @Autowired
  CampaignLogService campaignLogService;
  @Autowired
  CacheService cacheService;

  @Autowired
  PromoCodeService promoCodeService;

  @Override
  public Single<List<CampaignDropdown>> findCampaigns(MandatoryRequest mandatoryRequest) {

    LOGGER.info("findCampaigns Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<CampaignDropdown>>create(singleEmitter -> {

      String cacheKey = generateCacheKeyFindActivate(mandatoryRequest.getStoreId());
      List<CampaignDropdown> campaigns = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistCampaignList(campaigns)) {
        List<Campaign> campaignList = this.campaignRepository
            .findByStoreIdAndCampaignStatusAndIsDeleted(mandatoryRequest
                .getStoreId(), CampaignStatus.ACTIVE.getCode(), 0);

        if (campaignList.isEmpty()) {
          throw new BusinessLogicException(ResponseCode.CAMPAIGN_ACTIVE_IS_EMPTY.getCode(),
              ResponseCode.CAMPAIGN_ACTIVE_IS_EMPTY.getMessage());
        }

        campaigns = this.parsingCampaignActive(campaignList);

        this.cacheService.createCache(cacheKey, campaigns, 0);
      }

      LOGGER.info("findCampaigns Response campaigns {} ", campaigns);

      singleEmitter.onSuccess(campaigns);

    }).subscribeOn(Schedulers.io());
  }

  private List<CampaignDropdown> parsingCampaignActive(List<Campaign> campaignList) {
    List<CampaignDropdown> campaignDropdownList = new ArrayList<>();

    for (Campaign getCampaign : campaignList) {
      CampaignDropdown campaignDropdown = new CampaignDropdown();
      campaignDropdown.setId(getCampaign.getId());
      campaignDropdown.setName(getCampaign.getName());
      campaignDropdownList.add(campaignDropdown);
    }

    return campaignDropdownList;
  }

  private boolean isExistCampaignList(List<CampaignDropdown> campaigns) {
    return campaigns != null;
  }

  private boolean isExistCampaignListByAdjustmentid(List<Campaign> campaigns) {
    return campaigns != null;
  }

  @Override
  public Single<Campaign> findCampaignById(MandatoryRequest mandatoryRequest, String id) {
    return Single.<Campaign>create(singleEmitter -> {

      LOGGER.info("findCampaignById Request mandatoryRequest, id {} ", mandatoryRequest, id);

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      Campaign campaign = this.cacheService.findCacheByKey(cacheKey, Campaign.class);
      if (!isNotNullCampaign(campaign)) {
        campaign = this.campaignRepository
            .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

        if (!isExistCampaign(campaign)) {
          throw new BusinessLogicException(ResponseCode.NO_CAMPAIGN_EXISTED.getCode(),
              ResponseCode.NO_CAMPAIGN_EXISTED.getMessage());
        }

        this.cacheService.createCache(cacheKey, campaign, 0);
      }

      LOGGER.info("findCampaignById Response mandatoryRequest, id {} ", mandatoryRequest, id);

      singleEmitter.onSuccess(campaign);
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Campaign> createCampaign(MandatoryRequest mandatoryRequest, Campaign campaign) {

    LOGGER
        .info("createCampaign Request mandatoryRequest, campaign {} ", mandatoryRequest, campaign);

    return Single.<Campaign>create(singleEmitter -> {
      this.checkStoreIdAndName(campaign.getStoreId(), campaign.getName());

      this.checkPromoCodeAdjustmentStatus(campaign);

      campaign.setIsDeleted(0);
      campaign.setCampaignStatus(CampaignStatus.DRAFT);

      Campaign createdCampaign = this.campaignRepository
          .save(campaign);

      if (!isExistCampaign(createdCampaign)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), campaign.getId());
      this.cacheService.createCache(cacheKey, createdCampaign, 0);

      this.deleteCacheKeyFindActivate(mandatoryRequest);
      this.deleteCacheKeyFindByAdjustmentId(mandatoryRequest, campaign.getPromoCodeAdjustmentId());

      LOGGER.info("createCampaign Request campaign {} ", createdCampaign);

      singleEmitter.onSuccess(createdCampaign);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Campaign> updateCampaign(MandatoryRequest mandatoryRequest, Campaign campaignParam,
      String id) {

    return this.checkExistAndActivePromoCodeByCampaignId(mandatoryRequest, id, false)
        .flatMap(status -> this.updateProccess(mandatoryRequest, campaignParam, id))
        .flatMap(updateProccess -> this.campaignLogService
            .createCampaignLog(mandatoryRequest, updateProccess)
        );
  }

  private Single<Boolean> checkExistAndActivePromoCodeByCampaignId(MandatoryRequest mandatoryRequest, String id, Boolean toDeleted)
  {
    return Single.<Boolean>create(singleEmitter -> {

      Boolean status = false;

      List<PromoCode> promoCodes = this.promoCodeService.findPromoCodeByCampaignId(mandatoryRequest, id);

      if(!promoCodes.isEmpty()) {
        if (toDeleted) {
          throw new BusinessLogicException(ResponseCode.CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST.getCode(), ResponseCode.CANNOT_DELETE_CAUSE_PROMO_CODE_IS_EXIST.getMessage());
        }

        for (PromoCode getPromoCode : promoCodes) {
          if(getPromoCode.getPromoCodeStatus().equals(PromoCodeStatus.ACTIVE))
          {
            throw new BusinessLogicException(ResponseCode.CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE.getCode(),ResponseCode.CANNOT_UPDATE_CAUSE_PROMO_CODE_STATUS_ACTIVE.getMessage());
          }
        }
      }

      singleEmitter.onSuccess(status);
    }).subscribeOn(Schedulers.io());
  }

  private Single<Campaign> updateProccess(MandatoryRequest mandatoryRequest, Campaign campaignParam,
      String id) {
    return Single.<Campaign>create(singleEmitter -> {

      LOGGER.info("updateCampaign Request mandatoryRequest, campaignParam, id {} ", mandatoryRequest,
          campaignParam, id);

      this.checkPromoCodeAdjustmentStatus(campaignParam);

      Campaign campaign = this.campaignRepository
          .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistCampaign(campaign)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndNameForOtherDocument(campaign, campaignParam);

      this.combineCampaignAndCampaignParam(campaign, campaignParam);

      campaign.setCampaignStatus(CampaignStatus.DRAFT);

      Campaign updateCampaign = this.campaignRepository
          .save(campaign);

      if (!isExistCampaign(updateCampaign)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updateCampaign.getId());
      this.cacheService.deleteCache(cacheKey);
      this.cacheService.createCache(cacheKey, updateCampaign, 0);

      this.deleteCacheKeyFindActivate(mandatoryRequest);
      this.deleteCacheKeyFindByAdjustmentId(mandatoryRequest, campaign.getPromoCodeAdjustmentId());

      LOGGER.info("updateCampaign Response campaign {} ", updateCampaign);

      singleEmitter.onSuccess(updateCampaign);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Boolean> deleteCampaign(MandatoryRequest mandatoryRequest, String id) {
    return this.checkExistAndActivePromoCodeByCampaignId(mandatoryRequest, id, true)
        .flatMap(status -> this.deleteCampaignProcess(mandatoryRequest, id));
  }

  private Single<Boolean> deleteCampaignProcess(MandatoryRequest mandatoryRequest, String id)
  {
    return Single.<Boolean>create(singleEmitter -> {
      LOGGER.info("deleteCampaign Request mandatoryRequest, id {} ", mandatoryRequest, id);

      Campaign campaign = this.campaignRepository
          .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isNotNullCampaign(campaign)) {
        throw new BusinessLogicException(ResponseCode.CAMPAIGN_NOT_EXIST.getCode(),
            ResponseCode.CAMPAIGN_NOT_EXIST.getMessage());
      }

      campaign.setIsDeleted(1);
      campaign.setUpdatedBy(mandatoryRequest.getUsername());
      campaign.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      this.campaignRepository.softDeleted(campaign, id);

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      this.deleteCacheKeyFindActivate(mandatoryRequest);
      this.deleteCacheKeyFindByAdjustmentId(mandatoryRequest, campaign.getPromoCodeAdjustmentId());

      LOGGER.info("deleteCampaign Response Boolean {} ", true);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Page<Campaign>> findCampaignFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, CampaignStatus campaignStatus, Integer page, Integer size,
      CampaignColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info("findCampaignFilterPaginated Request mandatoryRequest,\n"
            + "      name, campaignStatus, page, size, columnSort,\n"
            + "     sortDirection {} ", mandatoryRequest, name, campaignStatus, page, size, columnSort,
        sortDirection);

    return Single.<Page<Campaign>>create(
        singleEmitter -> {

          String sort = this.setColumnSort(columnSort);

          String setCampaignStatus = this.setCampaignStatus(campaignStatus);

          Page<Campaign> campaignPage = this.campaignRepository
              .findCampaignFilterPaginated(mandatoryRequest.getStoreId(), name, setCampaignStatus,
                  page,
                  size, sort, sortDirection);

          LOGGER.info("findCampaignFilterPaginated Response campaignPage {} ", campaignPage);

          singleEmitter.onSuccess(campaignPage);
        }
    ).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<Campaign> updateStatusPendingCampaign(MandatoryRequest mandatoryRequest,
      String id) {

    LOGGER
        .info("updateStatusPendingCampaign Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Campaign>create(singleEmitter -> {
      Campaign campaign = this.campaignRepository
          .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isNotNullCampaign(campaign)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(campaign.getCampaignStatus(), CampaignStatus.DRAFT)) {
        throw new BusinessLogicException(ResponseCode.CAMPAIGN_STATUS_NOT_DRAFT.getCode(),
            ResponseCode.CAMPAIGN_STATUS_NOT_DRAFT.getMessage());
      }

      this.checkPromoCodeAdjustmentStatus(campaign);

      this.updateCampaignStatus(campaign, mandatoryRequest, id, CampaignStatus.PENDING);

      LOGGER.info("updateStatusPendingCampaign Response Boolean {} ", true);

      singleEmitter.onSuccess(campaign);

    })
    .flatMap(updateProccess -> this.campaignLogService.createCampaignLog(mandatoryRequest, updateProccess))
    .subscribeOn(Schedulers.io());

  }

  private void updateCampaignStatus(Campaign campaign, MandatoryRequest mandatoryRequest, String
      id, CampaignStatus campaignStatus) {
    campaign.setCampaignStatus(campaignStatus);
    campaign.setUpdatedBy(mandatoryRequest.getUsername());
    campaign.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
    this.campaignRepository.updateStatus(campaign);

    String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
    this.cacheService.createCache(cacheKey, campaign, 0);

    this.deleteCacheKeyFindActivate(mandatoryRequest);
    this.deleteCacheKeyFindByAdjustmentId(mandatoryRequest, campaign.getPromoCodeAdjustmentId());
  }

  @Override
  public Single<Campaign> updateStatusActiveCampaign(MandatoryRequest mandatoryRequest,
      String id) {

    return Single.<Campaign>create(singleEmitter -> {
      LOGGER
          .info("updateStatusActiveCampaign Request mandatoryRequest, id {} ", mandatoryRequest, id);

      Campaign campaign = this.campaignRepository
          .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isNotNullCampaign(campaign)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(campaign.getCampaignStatus(), CampaignStatus.PENDING)) {
        throw new BusinessLogicException(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getCode(),
            ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getMessage());
      }

      this.checkPromoCodeAdjustmentStatus(campaign);

      this.updateCampaignStatus(campaign, mandatoryRequest, id, CampaignStatus.ACTIVE);

      LOGGER.info("updateStatusActiveCampaign Response Boolean {} ", true);

      singleEmitter.onSuccess(campaign);

    })
    .flatMap(updateProccess -> this.campaignLogService.createCampaignLog(mandatoryRequest, updateProccess))
    .subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Campaign> updateStatusInactiveCampaign(MandatoryRequest mandatoryRequest, String
      id) {

    return this.checkExistAndActivePromoCodeByCampaignId(mandatoryRequest, id, false)
        .flatMap(status -> this.updateStatusInActiveProcess(mandatoryRequest, id))
        .flatMap(updateProccess -> this.campaignLogService.createCampaignLog(mandatoryRequest, updateProccess));
  }

  private Single<Campaign> updateStatusInActiveProcess(MandatoryRequest mandatoryRequest, String id) {
    return Single.<Campaign>create(singleEmitter -> {
      LOGGER
          .info("updateStatusClosedCampaign Request mandatoryRequest, id {} ", mandatoryRequest, id);

      Campaign campaign = this.campaignRepository
          .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isNotNullCampaign(campaign)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(campaign.getCampaignStatus(), CampaignStatus.ACTIVE)) {
        throw new BusinessLogicException(ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getCode(),
            ResponseCode.CAMPAIGN_STATUS_NOT_ACTIVE.getMessage());
      }

      this.updateCampaignStatus(campaign, mandatoryRequest, id, CampaignStatus.INACTIVE);

      LOGGER.info("updateStatusClosedCampaign Response Boolean {} ", true);

      singleEmitter.onSuccess(campaign);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Campaign> updateStatusRejectedCampaign(MandatoryRequest mandatoryRequest,
      String id) {

    LOGGER.info("updateStatusRejectedCampaign Request mandatoryRequest, id {} ", mandatoryRequest,
        id);

    return Single.<Campaign>create(singleEmitter -> {
      Campaign campaign = this.campaignRepository
          .findCampaignByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isNotNullCampaign(campaign)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(campaign.getCampaignStatus(), CampaignStatus.PENDING)) {
        throw new BusinessLogicException(ResponseCode.CAMPAIGN_STATUS_NOT_PENDING.getCode(),
            ResponseCode.CAMPAIGN_STATUS_NOT_PENDING.getMessage());
      }

      this.updateCampaignStatus(campaign, mandatoryRequest, id, CampaignStatus.DRAFT);

      LOGGER.info("updateStatusRejectedCampaign Response Boolean {} ", true);

      singleEmitter.onSuccess(campaign);

    })
    .flatMap(updateProccess -> this.campaignLogService.createCampaignLog(mandatoryRequest, updateProccess))
    .subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Boolean> checkExistAndActiveCampaignByAdjustmentId(MandatoryRequest mandatoryRequest,
      String adjustmentId, Boolean isDeleted) {


      return this.findCampaignByAdjustmentId(mandatoryRequest, adjustmentId)
          .flatMap(campaigns -> {

            Single<Boolean> getStatusCampaign = this.checkExistAndActiveCampaign(campaigns, isDeleted);
            Single<Boolean> getStatusPromoCode = this.promoCodeService
                .checkExistAndActivePromoCodeByCampaigns(mandatoryRequest, campaigns, isDeleted);

            return Single.zip(getStatusCampaign,getStatusPromoCode,
                (igetStatusCampaign, igetStatusPromoCode)->igetStatusCampaign && igetStatusPromoCode);

          }).subscribeOn(Schedulers.io());
  }

  private Single<List<Campaign>> findCampaignByAdjustmentId(MandatoryRequest mandatoryRequest, String adjustmentId)
  {
    return Single.<List<Campaign>>create(singleEmitter -> {

      String cacheKey = generateCacheKeybyAdjustmentId(mandatoryRequest.getStoreId(), adjustmentId);
      List<Campaign> campaigns = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistCampaignListByAdjustmentid(campaigns)) {

        campaigns = this.campaignRepository
            .findAllByStoreIdAndPromoCodeAdjustmentIdAndIsDeleted(mandatoryRequest.getStoreId(), adjustmentId, 0);

        this.cacheService.createCache(cacheKey, campaigns, 0);
      }

      singleEmitter.onSuccess(campaigns);

    }).subscribeOn(Schedulers.io());
  }


  private Single<Boolean> checkExistAndActiveCampaign(List<Campaign> campaigns, Boolean isDeleted)
  {
    return Single.<Boolean>create(singleEmitter -> {

      Boolean status = false;

        if(isDeleted && !campaigns.isEmpty())
        {
          throw new BusinessLogicException(ResponseCode.CANNOT_DELETE_CAUSE_CAMPAIGN_IS_EXIST.getCode(), ResponseCode.CANNOT_DELETE_CAUSE_CAMPAIGN_IS_EXIST.getMessage());
        }

        for (Campaign getCampaign : campaigns) {
          if(getCampaign.getCampaignStatus().equals(CampaignStatus.ACTIVE))
          {
            throw new BusinessLogicException(ResponseCode.CANNOT_UPDATE_CAUSE_CAMPAIGN_STATUS_ACTIVE.getCode(), ResponseCode.CANNOT_UPDATE_CAUSE_CAMPAIGN_STATUS_ACTIVE.getMessage());
          }
      }

      singleEmitter.onSuccess(status);

    }).subscribeOn(Schedulers.io());
  }

  private String setCampaignStatus(CampaignStatus campaignStatus) {
    String result = null;
    if (!isNotMatchCampaignStatus(campaignStatus)) {
      result = campaignStatus.getCode();
    }

    return result;
  }

  private Boolean isNotMatchCampaignStatus(CampaignStatus campaignStatus) {
    Boolean result = true;

    Boolean checkIsNotNull = this.isNotNullCampaignStatus(campaignStatus);
    if (checkIsNotNull) {
      for (CampaignStatus getCampaignStatus : CampaignStatus.values()) {
        if (getCampaignStatus.getCode().equals(campaignStatus.getCode())) {
          result = false;
          break;
        }
      }
    }

    return result;
  }

  private Boolean isNotNullCampaignStatus(CampaignStatus campaignStatus) {
    Boolean result = false;
    if (campaignStatus != null) {
      result = true;
    }

    return result;
  }

  private String setColumnSort(CampaignColumn campaignColumn) {
    String result = null;
    if (isNotNullColumnSort(campaignColumn)) {
      result = campaignColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(CampaignColumn campaignColumn) {
    Boolean result = false;
    if (campaignColumn != null) {
      result = true;
    }

    return result;
  }

  private void combineCampaignAndCampaignParam(Campaign campaign, Campaign campaignParam) {
    campaign.setVersion(campaign.getVersion());
    campaign.setStoreId(campaign.getStoreId());
    campaign.setUsername(campaign.getUsername());
    campaign.setCreatedDate(campaign.getCreatedDate());
    campaign.setCreatedBy(campaign.getCreatedBy());
    campaign.setUpdatedBy(campaignParam.getUsername());

    campaign.setName(campaignParam.getName());
    campaign.setCode(campaignParam.getCode());
    campaign.setPromoCodeAdjustmentId(campaignParam.getPromoCodeAdjustmentId());
    campaign.setCampaignPeriods(campaignParam.getCampaignPeriods());
  }

  private void checkStoreIdAndNameForOtherDocument(Campaign campaign, Campaign campaignParam) {

    if (isExistCampaign(campaign) && !campaign.getName()
        .equals(campaignParam.getName())) {

      Boolean checkDuplicateStoreIdAndName = this.checkExistOtherStoreIdAndName(campaignParam);
      if (checkDuplicateStoreIdAndName) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }

  }

  private Boolean checkExistOtherStoreIdAndName(Campaign campaignParam) {
    Boolean result = false;
    Campaign campaign = this.campaignRepository
        .findCampaignByIdAndNameAndIsDeleted(campaignParam.getStoreId(), campaignParam.getName(),
            0);
    if (isExistCampaign(campaign)) {
      result = true;
    }

    return result;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.CAMPAIGN + "-" + storeId + "-" + id;
  }

  private String generateCacheKeybyAdjustmentId(String storeId, String id) {
    return CacheKey.CAMPAIGN + "-adjustment-" + storeId + "-" + id;
  }

  private Boolean isExistCampaign(Campaign campaign) {
    if (campaign != null) {
      return true;
    }

    return false;
  }

  private void checkStoreIdAndName(String storeId, String name) {
    Campaign campaign = this.campaignRepository
        .findCampaignByStoreIdAndNameAndIsDeleted(storeId, name, 0);
    if (isExistCampaign(campaign)) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
          ResponseCode.DUPLICATE_DATA.getMessage());
    }
  }

  private void checkPromoCodeAdjustmentStatus(Campaign campaign) {
    PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByIdAndPromoCodeAdjustmentStatusAndIsDeleted(
            campaign.getPromoCodeAdjustmentId(), PromoCodeAdjustmentStatus.ACTIVE, 0);
    if (isNullPromoCodeAdjustment(promoCodeAdjustment)) {
      throw new BusinessLogicException(ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getCode(),
          ResponseCode.ADJUSTMENT_STATUS_ACTIVE_NOT_EXIST.getMessage());
    }
  }

  private Boolean isNullPromoCodeAdjustment(PromoCodeAdjustment promoCodeAdjustment) {
    Boolean result = false;
    if (promoCodeAdjustment == null) {
      result = true;
    }
    return result;
  }

  private Boolean isNotNullCampaign(Campaign campaign) {
    return campaign != null;
  }

  private Boolean isCompareStatus(Object object1, Object object2) {
    Boolean status = false;
    if (object1.equals(object2)) {
      status = true;
    }

    return status;
  }

  private String generateCacheKeyFindActivate(String storeId) {
    return CacheKey.CAMPAIGN + "-" + storeId + "-" + CampaignStatus.ACTIVE;
  }

  private void deleteCacheKeyFindActivate(MandatoryRequest mandatoryRequest) {
    String cacheKeyFindActivate = generateCacheKeyFindActivate(mandatoryRequest.getStoreId());
    this.cacheService.deleteCache(cacheKeyFindActivate);
  }

  private void deleteCacheKeyFindByAdjustmentId(MandatoryRequest mandatoryRequest, String adjustmentId) {
    String cacheKey = generateCacheKeybyAdjustmentId(mandatoryRequest.getStoreId(), adjustmentId);
    this.cacheService.deleteCache(cacheKey);
  }

}
