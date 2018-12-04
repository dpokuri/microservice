package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.BusinessLogicResponseRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.promo.code.entity.constant.enums.Language;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import com.tl.booking.promo.code.entity.dao.ResponseMessageResponse;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.BusinessLogicResponseService;
import com.tl.booking.promo.code.service.api.CacheService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class BusinessLogicResponseServiceImpl implements BusinessLogicResponseService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(BusinessLogicResponseServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  BusinessLogicResponseRepository businessLogicResponseRepository;

  @Override
  public String findMessageByResponseCodeAndLanguage(String storeId,
      String responseCode, String lang) {

    LOGGER.info("findCacheByKey request - storeId {}, responseCode {}, lang {}",
        storeId,
        responseCode, lang);

    String message = "";

    String cacheKey = this.generateCacheKeyLang(responseCode, storeId, lang);
    message = this.cacheService.findCacheByKey(cacheKey, String.class);
    if (!isExistMessage(message)) {
      BusinessLogicResponse businessLogicResponse = this.businessLogicResponseRepository
          .findByResponseCodeAndStoreIdAndIsDeleted(responseCode, storeId
              , 0);
      if (isExistBusinessLogicResponse(businessLogicResponse)) {
        Map<String, String> responseMap = new HashMap<>();
        for (ResponseMessageResponse responseMessage : businessLogicResponse.getResponseMessage()) {
          responseMap.put(responseMessage.getLang().getCode(), responseMessage.getContent());
        }

        message = responseMap.get(lang);
        this.cacheService.deleteCache(cacheKey);
        this.cacheService.createCache(cacheKey, message, 600);
      }
    }

    LOGGER.info("findCacheByKey response - message {}", message);

    return message;
  }

  @Override
  public Single<List<BusinessLogicResponse>> findBusinessLogicResponses(
      MandatoryRequest mandatoryRequest) {
    LOGGER.info("findBusinessLogicResponses Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<BusinessLogicResponse>>create(singleEmitter -> {

      String cacheKey = generateCacheKeyFindAll(mandatoryRequest.getStoreId());
      List<BusinessLogicResponse> businessLogicResponses = this.cacheService
          .findCacheByKey(cacheKey, List.class);

      if (!isExistBusinessLogicResponseList(businessLogicResponses)) {
        businessLogicResponses = this.businessLogicResponseRepository
            .findByStoreIdAndIsDeleted(mandatoryRequest.getStoreId(), 0);

        this.cacheService.deleteCache(cacheKey);
        this.cacheService.createCache(cacheKey, businessLogicResponses, 0);
      }

      LOGGER.info("findBusinessLogicResponses Response {}", businessLogicResponses);

      singleEmitter.onSuccess(businessLogicResponses);

    }).subscribeOn(Schedulers.io());

  }

  @Override
  public Single<Page<BusinessLogicResponse>> findBusinessLogicResponseFilterPaginated(
      MandatoryRequest mandatoryRequest, String responseCode,
      Integer page, Integer size, BusinessLogicResponseColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info(
        "findBusinessLogicResponseFilterPaginated Request mandatoryRequest, responseCode, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, responseCode, page, size, columnSort, sortDirection);

    return Single.<Page<BusinessLogicResponse>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<BusinessLogicResponse> businessLogicResponsePage = this.businessLogicResponseRepository
              .findBusinessLogicResponseFilterPaginated(mandatoryRequest.getStoreId(), responseCode,
                  page,
                  size, sort, sortDirection);

          LOGGER.info(
              "findBusinessLogicResponseFilterPaginated Response businessLogicResponsePage {} ",
              businessLogicResponsePage);

          singleEmitter.onSuccess(businessLogicResponsePage);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<BusinessLogicResponse> createBusinessLogicResponse(
      MandatoryRequest mandatoryRequest, BusinessLogicResponse businessLogicResponse) {
    LOGGER.info("createBusinessLogicResponse request {}, {}", mandatoryRequest,
        businessLogicResponse);
    return Single.<BusinessLogicResponse>create(
        singleEmitter -> {
          BusinessLogicResponse checkBusinessLogicResponse = this.businessLogicResponseRepository
              .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(
                  businessLogicResponse.getResponseCode()
                  , mandatoryRequest.getStoreId(), 0);

          if (isExistBusinessLogicResponse(checkBusinessLogicResponse)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                ResponseCode.DUPLICATE_DATA.getMessage());
          }

          BusinessLogicResponse createdBusinessLogicResponse = this.businessLogicResponseRepository
              .save(businessLogicResponse);

          if (!isExistBusinessLogicResponse(createdBusinessLogicResponse)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.deleteCacheBusinessLogicResponseFindAll(mandatoryRequest.getStoreId());
          String cacheKeyId = this.generateCacheKeyLang(createdBusinessLogicResponse.getResponseCode(),
              mandatoryRequest.getStoreId(), Language.EN.getCode());
          String cacheKeyEn = this.generateCacheKeyLang(createdBusinessLogicResponse.getResponseCode(),
              mandatoryRequest.getStoreId(), Language.ID.getCode());

          this.cacheService.deleteCache(cacheKeyId);
          this.cacheService.deleteCache(cacheKeyEn);

          LOGGER.info("createBusinessLogicResponse response {}", createdBusinessLogicResponse);
          singleEmitter.onSuccess(createdBusinessLogicResponse);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<BusinessLogicResponse> updateBusinessLogicResponse(
      MandatoryRequest mandatoryRequest, BusinessLogicResponse businessLogicResponseParam,
      String id) {
    LOGGER.info(
        "updateBusinessLogicResponse Request MandatoryRequest mandatoryRequest, businessLogicResponseParam, id {} ",
        mandatoryRequest, businessLogicResponseParam, id);

    return Single.<BusinessLogicResponse>create(singleEmitter -> {
      BusinessLogicResponse businessLogicResponse = this.businessLogicResponseRepository
          .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id,
              0);

      if (!isExistBusinessLogicResponse(businessLogicResponse)) {
        throw new BusinessLogicException(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getCode(),
            ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getMessage());
      }

      this.combineBusinessLogicResponseAndBusinessLogicResponseParam(businessLogicResponse,
          businessLogicResponseParam);

      BusinessLogicResponse updatedBusinessLogicResponse = this.businessLogicResponseRepository
          .save(businessLogicResponse);

      if (!isExistBusinessLogicResponse(updatedBusinessLogicResponse)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      String cacheKeyById = this.generateCacheKeyId(mandatoryRequest.getStoreId(), id);

      String cacheKeyId = this.generateCacheKeyLang(updatedBusinessLogicResponse.getResponseCode(),
          mandatoryRequest.getStoreId(), Language.EN.getCode());
      String cacheKeyEn = this.generateCacheKeyLang(updatedBusinessLogicResponse.getResponseCode(),
          mandatoryRequest.getStoreId(), Language.ID.getCode());

      this.deleteCacheBusinessLogicResponseFindAll(mandatoryRequest.getStoreId());
      this.cacheService.deleteCache(cacheKeyById);
      this.cacheService.deleteCache(cacheKeyId);
      this.cacheService.deleteCache(cacheKeyEn);

      LOGGER.info("updateBusinessLogicResponse Response BusinessLogicResponse {} ",
          updatedBusinessLogicResponse);

      singleEmitter.onSuccess(updatedBusinessLogicResponse);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Boolean> deleteBusinessLogicResponse(MandatoryRequest mandatoryRequest, String id) {

    LOGGER
        .info("deleteBusinessLogicResponse Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      BusinessLogicResponse businessLogicResponse = this.businessLogicResponseRepository
          .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id,
              0);

      if (!isExistBusinessLogicResponse(businessLogicResponse)) {
        throw new BusinessLogicException(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getCode(),
            ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getMessage());
      }

      businessLogicResponse.setIsDeleted(1);
      businessLogicResponse.setUpdatedBy(mandatoryRequest.getUsername());
      businessLogicResponse.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.businessLogicResponseRepository.softDeleted(businessLogicResponse, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheBusinessLogicResponseFindAll(mandatoryRequest.getStoreId());
      String cacheKeyById = this.generateCacheKeyId(mandatoryRequest.getStoreId(), id);
      String cacheKeyId = this.generateCacheKeyLang(businessLogicResponse.getResponseCode(),
          mandatoryRequest.getStoreId(), Language.EN.getCode());
      String cacheKeyEn = this.generateCacheKeyLang(businessLogicResponse.getResponseCode(),
          mandatoryRequest.getStoreId(), Language.ID.getCode());
      this.cacheService.deleteCache(cacheKeyId);
      this.cacheService.deleteCache(cacheKeyById);
      this.cacheService.deleteCache(cacheKeyEn);

      LOGGER.info("deleteBusinessLogicResponse Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<BusinessLogicResponse> findBusinessLogicResponseById(
      MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("findBusinessLogicResponseById Request mandatoryRequest, id {} ", mandatoryRequest,
        id);

    return Single.<BusinessLogicResponse>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          BusinessLogicResponse businessLogicResponse = this.cacheService
              .findCacheByKey(cacheKey, BusinessLogicResponse.class);

          if (!isExistBusinessLogicResponse(businessLogicResponse)) {
            businessLogicResponse = this.businessLogicResponseRepository
                .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(),
                    id, 0);

            if (!isExistBusinessLogicResponse(businessLogicResponse)) {
              throw new BusinessLogicException(
                  ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getCode(),
                  ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, businessLogicResponse, 0);
          }

          LOGGER.info("findBusinessLogicResponseById Response businessLogicResponse {} ",
              businessLogicResponse);

          singleEmitter.onSuccess(businessLogicResponse);
        }
    ).subscribeOn(Schedulers.io());
  }

  private void combineBusinessLogicResponseAndBusinessLogicResponseParam(
      BusinessLogicResponse businessLogicResponse,
      BusinessLogicResponse businessLogicResponseParam) {
    businessLogicResponse.setVersion(businessLogicResponse.getVersion());
    businessLogicResponse.setStoreId(businessLogicResponse.getStoreId());
    businessLogicResponse.setUsername(businessLogicResponse.getUsername());
    businessLogicResponse.setCreatedDate(businessLogicResponse.getCreatedDate());
    businessLogicResponse.setCreatedBy(businessLogicResponse.getCreatedBy());
    businessLogicResponse.setUpdatedBy(businessLogicResponse.getUsername());

    businessLogicResponse.setResponseCode(businessLogicResponseParam.getResponseCode());
    businessLogicResponse.setResponseMessage(businessLogicResponseParam.getResponseMessage());
  }

  private void deleteCacheBusinessLogicResponseFindAll(String storeId) {
    String cacheKeyFindAll = this.generateCacheKeyFindAll(storeId);
    this.cacheService.deleteCache(cacheKeyFindAll);
  }

  private String setColumnSort(BusinessLogicResponseColumn businessLogicResponseColumn) {
    String result = null;
    if (isNotNullColumnSort(businessLogicResponseColumn)) {
      result = businessLogicResponseColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(BusinessLogicResponseColumn businessLogicResponseColumn) {
    Boolean result = false;
    if (businessLogicResponseColumn != null) {
      result = true;
    }

    return result;
  }

  private boolean isExistBusinessLogicResponse(BusinessLogicResponse businessLogicResponse) {
    return businessLogicResponse != null;
  }

  private boolean isExistBusinessLogicResponseList(
      List<BusinessLogicResponse> businessLogicResponses) {
    return businessLogicResponses != null;
  }

  private boolean isExistMessage(String message) {
    return message != null;
  }

  private String generateCacheKey(String responseCode, String storeId) {
    return CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + responseCode + "-" + storeId;
  }

  private String generateCacheKeyLang(String responseCode, String storeId, String lang) {
    return CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + responseCode + "-" + storeId + "-" + lang;
  }

  private String generateCacheKeyId(String storeId, String id) {
    return CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindAll(String storeId) {
    return CacheKey.BUSINESS_LOGIC_RESPONSE + "-" + storeId;
  }


}
