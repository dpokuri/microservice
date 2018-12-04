package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.ChannelIdRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.ChannelIdColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ChannelId;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.ChannelIdService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class ChannelIdServiceImpl implements ChannelIdService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelIdServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  ChannelIdRepository channelIdRepository;

  @Override
  public Single<ChannelId> createChannelId(MandatoryRequest mandatoryRequest, ChannelId channelId) {
    LOGGER.info("createChannelId request {}, {}", mandatoryRequest, channelId);
    return Single.<ChannelId>create(
        singleEmitter -> {

          ChannelId getChannelId = this.channelIdRepository
              .findByStoreIdAndValueAndIsDeleted(mandatoryRequest.getStoreId(),
                  channelId.getValue(), 0);

          if (isExistChannelId(getChannelId)) {
            throw new BusinessLogicException(ResponseCode.CHANNEL_ID_EXIST.getCode(),
                ResponseCode.CHANNEL_ID_EXIST.getMessage());
          }

          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), channelId.getId());

          ChannelId createdChannelId = this.channelIdRepository.save(channelId);

          if (!isExistChannelId(createdChannelId)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.cacheService.createCache(cacheKey, createdChannelId, 0);

          this.deleteCacheFind(mandatoryRequest.getStoreId());

          LOGGER.info("createChannelId response {}", createdChannelId);
          singleEmitter.onSuccess(createdChannelId);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<List<ChannelId>> findChannelIds(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findChannelIds Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<ChannelId>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFind(mandatoryRequest.getStoreId());
      List<ChannelId> channelIds = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistChannelIdList(channelIds)) {
        channelIds = this.channelIdRepository
            .findByStoreIdAndIsDeleted(mandatoryRequest.getStoreId(), 0);

        if (channelIds.isEmpty()) {
          throw new BusinessLogicException(ResponseCode.CHANNEL_ID_LIST_NOT_EXIST.getCode(),
              ResponseCode.CHANNEL_ID_LIST_NOT_EXIST.getMessage());
        }

        this.cacheService.createCache(cacheKey, channelIds, 0);
      }

      singleEmitter.onSuccess(channelIds);

      LOGGER.info("findChannelIds Response channelIds {} ", channelIds);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistChannelIdList(List<ChannelId> channelIds) {
    return channelIds != null;
  }


  @Override
  public Single<ChannelId> updateChannelId(MandatoryRequest mandatoryRequest,
      ChannelId channelIdParam, String id) {

    LOGGER.info("updateChannelId Request MandatoryRequest mandatoryRequest, channelIdParam, id {} ",
        mandatoryRequest, channelIdParam, id);

    return Single.<ChannelId>create(singleEmitter -> {
      ChannelId channelId = this.channelIdRepository
          .findByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistChannelId(channelId)) {
        throw new BusinessLogicException(ResponseCode.CHANNEL_ID_NOT_EXIST.getCode(),
            ResponseCode.CHANNEL_ID_NOT_EXIST.getMessage());
      }

      this.checkChannelIdAndValueForOtherDocument(mandatoryRequest, channelId, channelIdParam);

      this.combineChannelIdAndChannelIdParam(channelId, channelIdParam);

      ChannelId updatedChannelId = this.channelIdRepository.save(channelId);

      if (!isExistChannelId(updatedChannelId)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updatedChannelId.getId());
      this.cacheService.createCache(cacheKey, updatedChannelId, 0);

      this.deleteCacheFind(mandatoryRequest.getStoreId());

      LOGGER.info("updateChannelId Response ChannelId {} ", updatedChannelId);

      singleEmitter.onSuccess(updatedChannelId);

    }).subscribeOn(Schedulers.io());

  }

  private void checkChannelIdAndValueForOtherDocument(MandatoryRequest mandatoryRequest,
      ChannelId channelId,
      ChannelId channelIdParam) {

    if (isExistChannelId(channelId) && !channelId.getValue()
        .equals(channelIdParam.getValue())) {

      Boolean checkDuplicateChannelIdAndValue = this.checkExistOtherChannelIdAndValue
          (mandatoryRequest, channelIdParam);
      if (checkDuplicateChannelIdAndValue) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherChannelIdAndValue(MandatoryRequest mandatoryRequest,
      ChannelId channelIdParam) {
    Boolean result = false;
    ChannelId channelId = this.channelIdRepository
        .findByStoreIdAndValueAndIsDeleted(mandatoryRequest.getStoreId(), channelIdParam.getValue(),
            0);
    if (isExistChannelId(channelId)) {
      result = true;
    }

    return result;
  }


  private void combineChannelIdAndChannelIdParam(ChannelId channelId, ChannelId channelIdParam) {

    channelId.setVersion(channelId.getVersion());
    channelId.setChannelId(channelId.getChannelId());
    channelId.setUsername(channelId.getUsername());
    channelId.setCreatedDate(channelId.getCreatedDate());
    channelId.setCreatedBy(channelId.getCreatedBy());
    channelId.setUpdatedBy(channelIdParam.getUsername());

    channelId.setValue(channelIdParam.getValue());
    channelId.setLabel(channelIdParam.getLabel());

  }

  private boolean isExistChannelId(ChannelId channelId) {
    return channelId != null;
  }

  @Override
  public Single<Boolean> deleteChannelId(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deleteChannelId Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      ChannelId channelId = this.channelIdRepository
          .findByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistChannelId(channelId)) {
        throw new BusinessLogicException(ResponseCode.CHANNEL_ID_NOT_EXIST.getCode(),
            ResponseCode.CHANNEL_ID_NOT_EXIST.getMessage());
      }

      channelId.setIsDeleted(1);
      channelId.setUpdatedBy(mandatoryRequest.getUsername());
      channelId.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.channelIdRepository.softDeleted(channelId, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      this.deleteCacheFind(mandatoryRequest.getStoreId());

      LOGGER.info("deleteChannelId Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<ChannelId> findChannelIdById(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("findChannelIdById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<ChannelId>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          ChannelId channelId = this.cacheService.findCacheByKey(cacheKey, ChannelId.class);

          if (!isExistChannelId(channelId)) {
            channelId = this.channelIdRepository
                .findByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistChannelId(channelId)) {
              throw new BusinessLogicException(ResponseCode.CHANNEL_ID_NOT_EXIST.getCode(),
                  ResponseCode.CHANNEL_ID_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, channelId, 0);
          }

          LOGGER.info("findChannelIdById Response channelId {} ", channelId);

          singleEmitter.onSuccess(channelId);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Page<ChannelId>> findChannelIdFilterPaginated(MandatoryRequest mandatoryRequest,
      String value, Integer page, Integer size, ChannelIdColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info(
        "findChannelIdFilterPaginated Request mandatoryRequest, value, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, value, page, size, columnSort, sortDirection);

    return Single.<Page<ChannelId>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<ChannelId> productTypePage = this.channelIdRepository
              .findChannelIdFilterPaginated(mandatoryRequest.getChannelId(), value, page,
                  size, sort, sortDirection);

          LOGGER.info("findChannelIdFilterPaginated Response productTypePage {} ", productTypePage);

          singleEmitter.onSuccess(productTypePage);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(ChannelIdColumn channelIdColumn) {
    String result = null;
    if (isNotNullColumnSort(channelIdColumn)) {
      result = channelIdColumn.getValue();
    }

    return result;
  }

  private boolean isNotNullColumnSort(ChannelIdColumn channelIdColumn) {
    return channelIdColumn != null;
  }

  private void deleteCacheFind(String stoereId) {
    String cacheKey = this.generateCacheKeyFind(stoereId);
    this.cacheService.deleteCache(cacheKey);
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.CHANNEL_ID + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFind(String storeId) {
    return CacheKey.CHANNEL_ID + "-" + storeId;
  }

}
