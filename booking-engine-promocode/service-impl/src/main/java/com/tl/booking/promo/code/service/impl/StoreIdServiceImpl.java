package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.StoreIdRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.StoreIdColumn;
import com.tl.booking.promo.code.entity.dao.StoreId;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.StoreIdService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class StoreIdServiceImpl implements StoreIdService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreIdServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  StoreIdRepository storeIdRepository;

  @Override
  public Single<StoreId> createStoreId(MandatoryRequest mandatoryRequest, StoreId storeId) {
    LOGGER.info("createStoreId request {}, {}", mandatoryRequest, storeId);
    return Single.<StoreId>create(
        singleEmitter -> {

          StoreId getStoreId = this.storeIdRepository
              .findStoreIdByValueAndIsDeleted(storeId.getValue(), 0);

          if (isExistStoreId(getStoreId)) {
            throw new BusinessLogicException(ResponseCode.STORE_ID_EXIST.getCode(),
                ResponseCode.STORE_ID_EXIST.getMessage());
          }

          String cacheKey = this.generateCacheKey(storeId.getId());

          StoreId createdStoreId = this.storeIdRepository.save(storeId);

          if (!isExistStoreId(createdStoreId)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.cacheService.createCache(cacheKey, createdStoreId, 0);

          this.deleteCacheFind();

          LOGGER.info("createStoreId response {}", createdStoreId);
          singleEmitter.onSuccess(createdStoreId);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<List<StoreId>> findStoreIds(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findStoreIds Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<StoreId>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFind();
      List<StoreId> storeIds = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistStoreIdList(storeIds)) {
        storeIds = this.storeIdRepository.findByIsDeleted(0);

        if (storeIds.isEmpty()) {
          throw new BusinessLogicException(ResponseCode.STORE_ID_LIST_NOT_EXIST.getCode(),
              ResponseCode.STORE_ID_LIST_NOT_EXIST.getMessage());
        }

        this.cacheService.createCache(cacheKey, storeIds, 0);
      }

      singleEmitter.onSuccess(storeIds);

      LOGGER.info("findStoreIds Response storeIds {} ", storeIds);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistStoreIdList(List<StoreId> storeIds) {
    return storeIds != null;
  }


  @Override
  public Single<StoreId> updateStoreId(MandatoryRequest mandatoryRequest,
      StoreId storeIdParam, String id) {

    LOGGER.info("updateStoreId Request MandatoryRequest mandatoryRequest, storeIdParam, id {} ",
        mandatoryRequest, storeIdParam, id);

    return Single.<StoreId>create(singleEmitter -> {
      StoreId storeId = this.storeIdRepository
          .findStoreIdByIdAndIsDeleted(id, 0);

      if (!isExistStoreId(storeId)) {
        throw new BusinessLogicException(ResponseCode.STORE_ID_NOT_EXIST.getCode(),
            ResponseCode.STORE_ID_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndNameForOtherDocument(storeId, storeIdParam);

      this.combineStoreIdAndStoreIdParam(storeId, storeIdParam);

      StoreId updatedStoreId = this.storeIdRepository.save(storeId);

      if (!isExistStoreId(updatedStoreId)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(updatedStoreId.getId());
      this.cacheService.createCache(cacheKey, updatedStoreId, 0);

      this.deleteCacheFind();

      LOGGER.info("updateStoreId Response StoreId {} ", updatedStoreId);

      singleEmitter.onSuccess(updatedStoreId);

    }).subscribeOn(Schedulers.io());

  }

  private void checkStoreIdAndNameForOtherDocument(StoreId storeId,
      StoreId storeIdParam) {

    if (isExistStoreId(storeId) && !storeId.getValue()
        .equals(storeIdParam.getValue())) {

      Boolean checkDuplicateStoreIdAndname = this.checkExistOtherStoreIdAndName(storeIdParam);
      if (checkDuplicateStoreIdAndname) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndName(StoreId storeIdParam) {
    Boolean result = false;
    StoreId storeId = this.storeIdRepository
        .findStoreIdByValueAndIsDeleted(storeIdParam.getValue(), 0);
    if (isExistStoreId(storeId)) {
      result = true;
    }

    return result;
  }


  private void combineStoreIdAndStoreIdParam(StoreId storeId, StoreId storeIdParam) {

    storeId.setVersion(storeId.getVersion());
    storeId.setStoreId(storeId.getStoreId());
    storeId.setUsername(storeId.getUsername());
    storeId.setCreatedDate(storeId.getCreatedDate());
    storeId.setCreatedBy(storeId.getCreatedBy());
    storeId.setUpdatedBy(storeIdParam.getUsername());

    storeId.setValue(storeIdParam.getValue());
    storeId.setLabel(storeIdParam.getLabel());

  }

  private boolean isExistStoreId(StoreId storeId) {
    return storeId != null;
  }

  @Override
  public Single<Boolean> deleteStoreId(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deleteStoreId Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      StoreId storeId = this.storeIdRepository
          .findStoreIdByIdAndIsDeleted(id, 0);

      if (!isExistStoreId(storeId)) {
        throw new BusinessLogicException(ResponseCode.STORE_ID_NOT_EXIST.getCode(),
            ResponseCode.STORE_ID_NOT_EXIST.getMessage());
      }

      storeId.setIsDeleted(1);
      storeId.setUpdatedBy(mandatoryRequest.getUsername());
      storeId.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.storeIdRepository.softDeleted(storeId, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(id);
      this.cacheService.deleteCache(cacheKey);

      this.deleteCacheFind();

      LOGGER.info("deleteStoreId Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<StoreId> findStoreIdById(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("findStoreIdById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<StoreId>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(id);
          StoreId storeId = this.cacheService.findCacheByKey(cacheKey, StoreId.class);

          if (!isExistStoreId(storeId)) {
            storeId = this.storeIdRepository
                .findStoreIdByIdAndIsDeleted(id, 0);

            if (!isExistStoreId(storeId)) {
              throw new BusinessLogicException(ResponseCode.STORE_ID_NOT_EXIST.getCode(),
                  ResponseCode.STORE_ID_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, storeId, 0);
          }

          LOGGER.info("findStoreIdById Response storeId {} ", storeId);

          singleEmitter.onSuccess(storeId);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Page<StoreId>> findStoreIdFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, Integer page, Integer size, StoreIdColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info(
        "findStoreIdFilterPaginated Request mandatoryRequest, name, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, name, page, size, columnSort, sortDirection);

    return Single.<Page<StoreId>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<StoreId> productTypePage = this.storeIdRepository
              .findStoreIdFilterPaginated(mandatoryRequest.getStoreId(), name, page,
                  size, sort, sortDirection);

          LOGGER.info("findStoreIdFilterPaginated Response productTypePage {} ", productTypePage);

          singleEmitter.onSuccess(productTypePage);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(StoreIdColumn storeIdColumn) {
    String result = null;
    if (isNotNullColumnSort(storeIdColumn)) {
      result = storeIdColumn.getValue();
    }

    return result;
  }

  private boolean isNotNullColumnSort(StoreIdColumn storeIdColumn) {
    return storeIdColumn != null;
  }


  private void deleteCacheFind() {
    String cacheKey = this.generateCacheKeyFind();
    this.cacheService.deleteCache(cacheKey);
  }

  private String generateCacheKey(String id) {
    return CacheKey.STORE_ID + "-" + id;
  }

  private String generateCacheKeyFind() {
    return CacheKey.STORE_ID;
  }

}
