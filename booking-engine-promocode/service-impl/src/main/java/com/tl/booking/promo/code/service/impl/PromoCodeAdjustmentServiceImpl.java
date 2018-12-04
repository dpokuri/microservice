package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.PromoCodeAdjustmentRepository;
import com.tl.booking.promo.code.entity.PromoCodeAdjustmentDropdown;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.CampaignService;
import com.tl.booking.promo.code.service.api.PromoCodeAdjustmentService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.log.PromoCodeAdjustmentLogService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PromoCodeAdjustmentServiceImpl implements PromoCodeAdjustmentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCodeServiceImpl.class);
  @Autowired
  PromoCodeAdjustmentRepository promoCodeAdjustmentRepository;
  @Autowired
  CacheService cacheService;

  @Autowired
  PromoCodeService promoCodeService;

  @Autowired
  CampaignService campaignService;

  @Autowired
  PromoCodeAdjustmentLogService promoCodeAdjustmentLogService;

  @Autowired
  private Scheduler scheduler;

  @Override
  public Single<PromoCodeAdjustment> createPromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustment) {

    LOGGER.info("createPromoCodeAdjustment Request mandatoryRequest, promoCodeAdjustment {} ",
        mandatoryRequest, promoCodeAdjustment);

    return Single.<PromoCodeAdjustment>create(singleEmitter -> {
      this.checkStoreIdAndCode(promoCodeAdjustment.getStoreId(), promoCodeAdjustment.getCode());

      promoCodeAdjustment.setIsDeleted(0);
      promoCodeAdjustment.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);

      this.checkMinCountArrayUsageRules(promoCodeAdjustment.getUsageRules());

      promoCodeAdjustment.setCreatedDate(promoCodeAdjustment.getCreatedDate());
      promoCodeAdjustment.setCreatedBy(promoCodeAdjustment.getCreatedBy());
      promoCodeAdjustment.setUpdatedDate(new Date());
      promoCodeAdjustment.setUpdatedBy(mandatoryRequest.getUsername());

      PromoCodeAdjustment createdPromoCodeAdjustment = this.promoCodeAdjustmentRepository
          .save(promoCodeAdjustment);

      if (!isExistPromoCodeAdjustment(createdPromoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), promoCodeAdjustment.getId());
      this.cacheService.createCache(cacheKey, createdPromoCodeAdjustment, 0);

      this.deleteCacheKeyFindActivate(mandatoryRequest);

      LOGGER.info("createPromoCodeAdjustment Response promoCodeAdjustment {} ",
          createdPromoCodeAdjustment);

      singleEmitter.onSuccess(createdPromoCodeAdjustment);

    }).subscribeOn(Schedulers.io());

  }

  private void checkMinCountArrayUsageRules(List<UsageRule> usageRules) {

    Integer usageCount = 0;
    for (UsageRule getUsageRule : usageRules) {
      if (getUsageRule.getUsageCount() <= 0) {
        throw new BusinessLogicException(ResponseCode.USAGE_COUNT_CANNOT_SET_ZERO.getCode(),
            ResponseCode.USAGE_COUNT_CANNOT_SET_ZERO.getMessage());
      }
      usageCount++;
    }

    if (usageCount.equals(0)) {
      throw new BusinessLogicException(ResponseCode.USAGE_RULES_MUST_BE_NOT_EMPTY.getCode(),
          ResponseCode.USAGE_RULES_MUST_BE_NOT_EMPTY.getMessage());
    }
  }


  @Override
  public Single<PromoCodeAdjustment> updatePromoCodeAdjustment(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustmentParam, String id) {

    LOGGER.info(
        "updatePromoCodeAdjustment Request mandatoryRequest, promoCodeAdjustmentParam, promoCodeAdjustment {} ",
        mandatoryRequest, promoCodeAdjustmentParam, id);

    return this.campaignService.checkExistAndActiveCampaignByAdjustmentId(mandatoryRequest, id, false)
        .flatMap(status -> this.updateProcess(mandatoryRequest, promoCodeAdjustmentParam, id))
        .flatMap(updateProccess -> this.promoCodeAdjustmentLogService
            .createPromoCodeAdjustmentLog(mandatoryRequest, updateProccess)
        );

  }

  private Single<PromoCodeAdjustment> updateProcess(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustmentParam, String id) {

    return Single.<PromoCodeAdjustment>create(singleEmitter -> {

      PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
          .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

      if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndCodeForOtherDocument(promoCodeAdjustment, promoCodeAdjustmentParam);

      this.checkMinCountArrayUsageRules(promoCodeAdjustment.getUsageRules());

      this.combinePromoCodeAdjustmentAndPromoCodeAdjustmentParam(promoCodeAdjustment,
          promoCodeAdjustmentParam);

      promoCodeAdjustment.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT);
      promoCodeAdjustment.setUpdatedDate(new Date());
      promoCodeAdjustment.setUpdatedBy(mandatoryRequest.getUsername());

      PromoCodeAdjustment updatePromoCodeAdjustment = this.promoCodeAdjustmentRepository
          .save(promoCodeAdjustment);

      if (!isExistPromoCodeAdjustment(updatePromoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.updateCache(mandatoryRequest, id, promoCodeAdjustment);
      this.deleteCacheKeyFindActivate(mandatoryRequest);

      LOGGER.info("updatePromoCodeAdjustment Response promoCodeAdjustment {} ",
          updatePromoCodeAdjustment);

      singleEmitter.onSuccess(updatePromoCodeAdjustment);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Boolean> deletePromoCodeAdjustment(MandatoryRequest mandatoryRequest, String id) {

    return this.campaignService.checkExistAndActiveCampaignByAdjustmentId(mandatoryRequest, id, true)
        .flatMap(status -> this.deleteProcess(mandatoryRequest, id));

  }

  private Single<Boolean> deleteProcess(MandatoryRequest mandatoryRequest, String id)
  {
    return Single.<Boolean>create(singleEmitter -> {
      LOGGER.info("deletePromoCodeAdjustment Request mandatoryRequest, id {} ", mandatoryRequest, id);

      PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
          .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

      if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      promoCodeAdjustment.setIsDeleted(1);
      promoCodeAdjustment.setUpdatedBy(mandatoryRequest.getUsername());
      promoCodeAdjustment.setUpdatedDate(DateFormatterHelper.getTodayDate());
      this.promoCodeAdjustmentRepository.softDeleted(promoCodeAdjustment, id);

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      this.deleteCacheKeyFindActivate(mandatoryRequest);

      LOGGER.info("deletePromoCodeAdjustment Response Boolean {} ", true);

      singleEmitter.onSuccess(true);


    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<PromoCodeAdjustment> findPromoCodeAdjustmentById(MandatoryRequest mandatoryRequest,
      String id) {

    LOGGER
        .info("findPromoCodeAdjustmentById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<PromoCodeAdjustment>create(
        singleEmitter -> {

          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          PromoCodeAdjustment promoCodeAdjustment = this.cacheService
              .findCacheByKey(cacheKey, PromoCodeAdjustment.class);

          if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
            promoCodeAdjustment = this.promoCodeAdjustmentRepository
                .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

            if (!isExistAdjustment(promoCodeAdjustment)) {
              throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
                  ResponseCode.DATA_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, promoCodeAdjustment, 0);
          }

          LOGGER.info("findPromoCodeAdjustmentById Response promoCodeAdjustment {} ",
              promoCodeAdjustment);

          singleEmitter.onSuccess(promoCodeAdjustment);

        }
    ).subscribeOn(Schedulers.io());


  }

  @Override
  public Single<Page<PromoCodeAdjustment>> findPromoCodeAdjustmentFilterPaginated(
      MandatoryRequest mandatoryRequest, String name,
      Boolean isPromoCodeCombine, Double maxDiscount, PromoCodeAdjustmentStatus
      promoCodeAdjustmentStatus,
      Integer page, Integer size, PromoCodeAdjustmentColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info("findPromoCodeAdjustmentFilterPaginated Request mandatoryRequest, name,\n"
            + "      isPromoCodeCombine, maxDiscount,  promoCodeAdjustmentStatus,\n"
            + "      page, size, columnSort, sortDirection) {} ",
        mandatoryRequest, name, isPromoCodeCombine, maxDiscount, promoCodeAdjustmentStatus, page,
        size, columnSort, sortDirection);

    return Single.<Page<PromoCodeAdjustment>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          String setPromoCodeAdjustmentStatus = this
              .setPromoCodeAdjustmentStatus(promoCodeAdjustmentStatus);

          Page<PromoCodeAdjustment> promoCodeAdjustments = this.promoCodeAdjustmentRepository
              .findPromoCodeAdjustmentFilterPaginated(mandatoryRequest.getStoreId(),
                  name, isPromoCodeCombine, maxDiscount, setPromoCodeAdjustmentStatus, page, size,
                  sort, sortDirection);

          LOGGER.info("findPromoCodeAdjustmentFilterPaginated Response promoCodeAdjustments {} ",
              promoCodeAdjustments);

          singleEmitter.onSuccess(promoCodeAdjustments);
        }
    ).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<PromoCodeAdjustment> updateStatusPendingPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id) {

    return this.updateProcessStatusPending(mandatoryRequest, id)
        .flatMap(updateProccess -> this.promoCodeAdjustmentLogService
            .createPromoCodeAdjustmentLog(mandatoryRequest, updateProccess));

  }

  private Single<PromoCodeAdjustment> updateProcessStatusPending(MandatoryRequest mandatoryRequest, String id)
  {

    return Single.<PromoCodeAdjustment>create(singleEmitter -> {
      LOGGER.info("updateStatusPendingPromoCodeAdjustment Request mandatoryRequest, id {} ",
          mandatoryRequest, id);

      PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
          .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

      if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(promoCodeAdjustment.getPromoCodeAdjustmentStatus(),
          PromoCodeAdjustmentStatus.DRAFT)) {
        throw new BusinessLogicException(
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_DRAFT.getCode(),
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_DRAFT.getMessage());
      }

      this.updatePromoCodeAdjustmentStatus(promoCodeAdjustment, mandatoryRequest, id, PromoCodeAdjustmentStatus.PENDING);

      LOGGER.info("updateStatusPendingPromoCodeAdjustment Response Boolean {} ", promoCodeAdjustment);

      singleEmitter.onSuccess(promoCodeAdjustment);

    }).subscribeOn(Schedulers.io());

}

  private void updatePromoCodeAdjustmentStatus(PromoCodeAdjustment promoCodeAdjustment,
      MandatoryRequest mandatoryRequest, String id, PromoCodeAdjustmentStatus promoCodeAdjustmentStatus) {
    promoCodeAdjustment.setPromoCodeAdjustmentStatus(promoCodeAdjustmentStatus);
    promoCodeAdjustment.setUpdatedBy(mandatoryRequest.getUsername());
    promoCodeAdjustment.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
    this.promoCodeAdjustmentRepository.updateStatus(promoCodeAdjustment);

    this.updateCache(mandatoryRequest, id, promoCodeAdjustment);
    this.deleteCacheKeyFindActivate(mandatoryRequest);
  }


  @Override
  public Single<PromoCodeAdjustment> updateStatusActivedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id) {

    return Single.<PromoCodeAdjustment>create(singleEmitter -> {
      LOGGER.info("updateStatusActivedPromoCodeAdjustment Request mandatoryRequest, id {} ",
          mandatoryRequest, id);

      PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
          .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

      if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(promoCodeAdjustment.getPromoCodeAdjustmentStatus(),
          PromoCodeAdjustmentStatus.PENDING) && !isCompareStatus(
          promoCodeAdjustment.getPromoCodeAdjustmentStatus(), PromoCodeAdjustmentStatus.INACTIVE)) {
        throw new BusinessLogicException(
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_CANNOT_UPDATE.getCode(),
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_CANNOT_UPDATE.getMessage());
      }

      this.updatePromoCodeAdjustmentStatus(promoCodeAdjustment, mandatoryRequest, id,
          PromoCodeAdjustmentStatus.ACTIVE);

      LOGGER.info("updateStatusActivedPromoCodeAdjustment Response promoCodeAdjustment {} ",
          promoCodeAdjustment);

      singleEmitter.onSuccess(promoCodeAdjustment);

    })
    .flatMap(updateProccess -> this.promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(mandatoryRequest, updateProccess))
    .subscribeOn(Schedulers.io());

  }


  @Override
  public Single<PromoCodeAdjustment> updateStatusInActivedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id) {

      return this.campaignService.checkExistAndActiveCampaignByAdjustmentId(mandatoryRequest, id, false)
          .flatMap(status -> this.updateStatusRejected(mandatoryRequest, id))
          .flatMap(updateProccess -> this.promoCodeAdjustmentLogService
              .createPromoCodeAdjustmentLog(mandatoryRequest, updateProccess)
          );
  }

  private Single<PromoCodeAdjustment> updateStatusRejected(MandatoryRequest mandatoryRequest, String id) {

    return Single.<PromoCodeAdjustment>create(singleEmitter -> {
      LOGGER.info("updateStatusInActivedPromoCodeAdjustment Request mandatoryRequest, id {} ",
          mandatoryRequest, id);

      PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
          .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

      if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(promoCodeAdjustment.getPromoCodeAdjustmentStatus(),
          PromoCodeAdjustmentStatus.ACTIVE)) {
        throw new BusinessLogicException(
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_IN_ACTIVE.getCode(),
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_IN_ACTIVE.getMessage());
      }

      this.updatePromoCodeAdjustmentStatus(promoCodeAdjustment, mandatoryRequest, id,
          PromoCodeAdjustmentStatus.INACTIVE);

      LOGGER.info("updateStatusInActivedPromoCodeAdjustment Response Boolean {} ", promoCodeAdjustment);

      singleEmitter.onSuccess(promoCodeAdjustment);

    }).subscribeOn(Schedulers.io());

  }

  public Single<List<PromoCodeAdjustmentDropdown>> findPromoCodeAdjustments(
      MandatoryRequest mandatoryRequest) {
    LOGGER.info("findPromoCodeAdjustments Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<PromoCodeAdjustmentDropdown>>create(singleEmitter -> {

      String cacheKey = generateCacheKeyFindActivate(mandatoryRequest.getStoreId());
      List<PromoCodeAdjustmentDropdown> promoCodeAdjustments = this.cacheService
          .findCacheByKey(cacheKey, List.class);

      if (!isExistPromoCodeAdjustmentList(promoCodeAdjustments)) {

        List<PromoCodeAdjustment> promoCodeAdjustmentList = this.promoCodeAdjustmentRepository
            .findAllByStoreIdAndPromoCodeAdjustmentStatusAndIsDeleted(mandatoryRequest
                .getStoreId(), PromoCodeAdjustmentStatus.ACTIVE, 0);

        if (promoCodeAdjustmentList.isEmpty()) {
          throw new BusinessLogicException(
              ResponseCode.PROMO_CODE_ADJUSTMENT_ACTIVE_IS_EMPTY.getCode(),
              ResponseCode.PROMO_CODE_ADJUSTMENT_ACTIVE_IS_EMPTY.getMessage());
        }

        promoCodeAdjustments = this.parsingPromoCodeAdjustmentActive(promoCodeAdjustmentList);

        this.cacheService.createCache(cacheKey, promoCodeAdjustments, 0);
      }

      LOGGER.info("findPromoCodeAdjustments Response {} ", promoCodeAdjustments);

      singleEmitter.onSuccess(promoCodeAdjustments);

    }).subscribeOn(Schedulers.io());

  }

  private List<PromoCodeAdjustmentDropdown> parsingPromoCodeAdjustmentActive(
      List<PromoCodeAdjustment> promoCodeAdjustmentList) {
    List<PromoCodeAdjustmentDropdown> promoCodeAdjustmentDropdownList = new ArrayList<>();

    for (PromoCodeAdjustment getPromoCodeAdjustment : promoCodeAdjustmentList) {
      PromoCodeAdjustmentDropdown promoCodeAdjustmentDropdown = new PromoCodeAdjustmentDropdown();
      promoCodeAdjustmentDropdown.setId(getPromoCodeAdjustment.getId());
      promoCodeAdjustmentDropdown.setName(getPromoCodeAdjustment.getName());
      promoCodeAdjustmentDropdownList.add(promoCodeAdjustmentDropdown);
    }

    return promoCodeAdjustmentDropdownList;
  }


  private boolean isExistPromoCodeAdjustmentList(
      List<PromoCodeAdjustmentDropdown> promoCodeAdjustments) {
    return promoCodeAdjustments != null;
  }

  @Override
  public Single<PromoCodeAdjustment> updateStatusRejectedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest, String id) {

    return Single.<PromoCodeAdjustment>create(singleEmitter -> {
      LOGGER.info("updateStatusRejectedPromoCodeAdjustment Request mandatoryRequest, id {} ",
          mandatoryRequest, id);

      PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
          .findPromoCodeAdjustmentByIdAndIsDeleted(id, 0);

      if (!isExistPromoCodeAdjustment(promoCodeAdjustment)) {
        throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage());
      }

      if (!isCompareStatus(promoCodeAdjustment.getPromoCodeAdjustmentStatus(),
          PromoCodeAdjustmentStatus.PENDING)) {
        throw new BusinessLogicException(
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_PENDING.getCode(),
            ResponseCode.PROMO_CODE_ADJUSTMENT_STATUS_NOT_PENDING.getMessage());
      }

      this.updatePromoCodeAdjustmentStatus(promoCodeAdjustment, mandatoryRequest, id,
          PromoCodeAdjustmentStatus.DRAFT);

      LOGGER.info("updateStatusRejectedPromoCodeAdjustment Response promoCodeAdjustment {} ",
          promoCodeAdjustment);

      singleEmitter.onSuccess(promoCodeAdjustment);

    })
    .flatMap(updateProccess -> this.promoCodeAdjustmentLogService.createPromoCodeAdjustmentLog(mandatoryRequest, updateProccess))
    .subscribeOn(Schedulers.io());

  }


  private String setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus promoCodeAdjustmentStatus) {
    String result = null;
    if (!isNotMatchPromoCodeAdjustmentStatus(promoCodeAdjustmentStatus)) {
      result = promoCodeAdjustmentStatus.getCode();
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

  private Boolean isNotMatchPromoCodeAdjustmentStatus(
      PromoCodeAdjustmentStatus promoCodeAdjustmentStatus) {
    Boolean result = true;

    Boolean checkIsNotNull = this.isNotNullPromoCodeAdjustmentStatus(promoCodeAdjustmentStatus);
    if (checkIsNotNull) {
      for (PromoCodeAdjustmentStatus promoCodeAdjustmentStatusData : PromoCodeAdjustmentStatus
          .values()) {
        if (promoCodeAdjustmentStatusData.getCode().equals(promoCodeAdjustmentStatus.getCode())) {
          result = false;
          break;
        }
      }
    }

    return result;
  }

  private Boolean isNotNullPromoCodeAdjustmentStatus(
      PromoCodeAdjustmentStatus promoCodeAdjustmentStatus) {
    Boolean result = false;
    if (promoCodeAdjustmentStatus != null) {
      result = true;
    }

    return result;
  }


  private String setColumnSort(PromoCodeAdjustmentColumn promoCodeAdjustmentColumn) {
    String result = null;
    if (isNotNullColumnSort(promoCodeAdjustmentColumn)) {
      result = promoCodeAdjustmentColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(PromoCodeAdjustmentColumn promoCodeAdjustmentColumn) {
    Boolean result = false;
    if (promoCodeAdjustmentColumn != null) {
      result = true;
    }

    return result;
  }

  private Boolean isExistPromoCodeAdjustment(PromoCodeAdjustment promoCodeAdjustment) {
    return promoCodeAdjustment != null;
  }

  private void checkStoreIdAndCode(String storeId, String code) {
    PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(storeId, code, 0);
    if (isExistAdjustment(promoCodeAdjustment)) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA_BY_CODE.getCode(),
          ResponseCode.DUPLICATE_DATA_BY_CODE.getMessage());
    }
  }

  private Boolean isExistAdjustment(PromoCodeAdjustment promoCodeAdjustment) {
    Boolean existPromoCodeAdjustment = false;
    if (promoCodeAdjustment != null) {
      existPromoCodeAdjustment = true;
    }

    return existPromoCodeAdjustment;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.PROMO_CODE_ADJUSTMENT + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindActivate(String storeId) {
    return CacheKey.PROMO_CODE_ADJUSTMENT + "-" + storeId + "-" + PromoCodeAdjustmentStatus.ACTIVE;
  }

  private void checkStoreIdAndCodeForOtherDocument(PromoCodeAdjustment promoCodeAdjustment,
      PromoCodeAdjustment promoCodeAdjustmentParam) {

    if (isExistAdjustment(promoCodeAdjustment) && !promoCodeAdjustment.getCode()
        .equals(promoCodeAdjustmentParam.getCode())) {

      Boolean checkDuplicateStoreIdAndCode = this
          .checkExistOtherStoreIdAndCode(promoCodeAdjustmentParam);
      if (checkDuplicateStoreIdAndCode) {
        throw new BusinessLogicException(ResponseCode.CODE_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.CODE_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndCode(PromoCodeAdjustment promoCodeAdjustmentParam) {
    Boolean result = false;
    PromoCodeAdjustment promoCodeAdjustment = this.promoCodeAdjustmentRepository
        .findPromoCodeAdjustmentByStoreIdAndCodeAndIsDeleted(promoCodeAdjustmentParam.getStoreId(),
            promoCodeAdjustmentParam.getCode(), 0);
    if (isExistPromoCodeAdjustment(promoCodeAdjustment)) {
      result = true;
    }

    return result;
  }

  private void combinePromoCodeAdjustmentAndPromoCodeAdjustmentParam(
      PromoCodeAdjustment promoCodeAdjustment, PromoCodeAdjustment promoCodeAdjustmentParam) {
    promoCodeAdjustment.setChannelId(promoCodeAdjustmentParam.getChannelId());
    promoCodeAdjustment.setStoreId(promoCodeAdjustmentParam.getStoreId());
    promoCodeAdjustment.setPromoCodeType(promoCodeAdjustmentParam.getPromoCodeType());
    promoCodeAdjustment.setPromoCodeCombine(promoCodeAdjustmentParam.isPromoCodeCombine());
    promoCodeAdjustment.setCode(promoCodeAdjustmentParam.getCode());
    promoCodeAdjustment.setName(promoCodeAdjustmentParam.getName());
    promoCodeAdjustment.setDescription(promoCodeAdjustmentParam.getDescription());
    promoCodeAdjustment.setUsageRules(promoCodeAdjustmentParam.getUsageRules());
    promoCodeAdjustment
        .setPromoCodeDistributions(promoCodeAdjustmentParam.getPromoCodeDistributions());
    promoCodeAdjustment.setMaxDiscount(promoCodeAdjustmentParam.getMaxDiscount());
    promoCodeAdjustment.setPromoCodeGroupRules(promoCodeAdjustmentParam.getPromoCodeGroupRules());
    promoCodeAdjustment
        .setPromoCodeAdjustmentStatus(promoCodeAdjustmentParam.getPromoCodeAdjustmentStatus());
    promoCodeAdjustment.setPromoCodeCost(promoCodeAdjustmentParam.getPromoCodeCost());
    promoCodeAdjustment.setPromoCodePriceRanges(promoCodeAdjustmentParam.getPromoCodePriceRanges());
    promoCodeAdjustment.setCalculateType(promoCodeAdjustmentParam.getCalculateType());
    promoCodeAdjustment.setValidAllOrderDetails(promoCodeAdjustmentParam.getValidAllOrderDetails
        ());
    promoCodeAdjustment.setPaymentMethods(promoCodeAdjustmentParam.getPaymentMethods());
  }

  private void deleteCacheKeyFindActivate(MandatoryRequest mandatoryRequest) {
    String cacheKeyFindActivate = generateCacheKeyFindActivate(mandatoryRequest.getStoreId());
    this.cacheService.deleteCache(cacheKeyFindActivate);
  }

  private void updateCache(MandatoryRequest mandatoryRequest, String id,
      PromoCodeAdjustment promoCodeAdjustment) {
    String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
    this.cacheService.deleteCache(cacheKey);
    this.cacheService.createCache(cacheKey, promoCodeAdjustment, 0);
  }

}
