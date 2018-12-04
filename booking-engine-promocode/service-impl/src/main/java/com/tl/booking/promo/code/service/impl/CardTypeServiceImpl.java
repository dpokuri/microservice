package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.CardTypeRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CardTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.CardType;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.CardTypeService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class CardTypeServiceImpl implements CardTypeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CardTypeServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  CardTypeRepository cardTypeRepository;

  @Override
  public Single<List<CardType>> findCardTypes(MandatoryRequest mandatoryRequest, String bankId) {
    LOGGER.info("findCardTypes Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<CardType>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFindAll(mandatoryRequest.getStoreId(), bankId);
      List<CardType> cardTypes = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistListCardType(cardTypes)) {

        cardTypes = this.cardTypeRepository
            .findCardTypes(0, mandatoryRequest.getStoreId(), bankId);

        this.cacheService.createCache(cacheKey, cardTypes, 0);
      }

      LOGGER.info("findCardTypes Response cardTypes {} ", cardTypes);

      singleEmitter.onSuccess(cardTypes);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistListCardType(List<CardType> cardTypes) {
    return cardTypes != null;
  }

  @Override
  public Single<Page<CardType>> findCardTypeFilterPaginated(MandatoryRequest mandatoryRequest,
      String name,
      Integer page, Integer size, CardTypeColumn columnSort, SortDirection sortDirection) {

    LOGGER.info(
        "findCardTypeFilterPaginated Request mandatoryRequest, name, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, name, page, size, columnSort, sortDirection);

    return Single.<Page<CardType>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<CardType> cardTypePage = this.cardTypeRepository
              .findCardTypeFilterPaginated(mandatoryRequest.getStoreId(), name, page,
                  size, sort, sortDirection);

          LOGGER.info("findCardTypeFilterPaginated Response cardType {} ", cardTypePage);

          singleEmitter.onSuccess(cardTypePage);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(CardTypeColumn cardTypeColumn) {
    String result = null;
    if (isNotNullColumnSort(cardTypeColumn)) {
      result = cardTypeColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(CardTypeColumn cardTypeColumn) {
    Boolean result = false;
    if (cardTypeColumn != null) {
      result = true;
    }

    return result;
  }

  @Override
  public Single<CardType> findCardTypeById(MandatoryRequest mandatoryRequest, String id) {
    LOGGER.info("findCardTypeById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<CardType>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          CardType cardType = this.cacheService.findCacheByKey(cacheKey, CardType.class);

          if (!isExistCardType(cardType)) {
            cardType = this.cardTypeRepository
                .findCardTypeByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistCardType(cardType)) {
              throw new BusinessLogicException(ResponseCode.CARD_TYPE_NOT_EXIST.getCode(),
                  ResponseCode.CARD_TYPE_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, cardType, 0);
          }

          LOGGER.info("findCardTypeById Response cardType {} ", cardType);

          singleEmitter.onSuccess(cardType);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<CardType> updateCardType(MandatoryRequest mandatoryRequest, CardType cardTypeParam,
      String id) {
    LOGGER.info("updateCardType Request MandatoryRequest mandatoryRequest, cardTypeParam, id {} ",
        mandatoryRequest, cardTypeParam, id);

    return Single.<CardType>create(singleEmitter -> {
      CardType cardType = this.cardTypeRepository
          .findCardTypeByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistCardType(cardType)) {
        throw new BusinessLogicException(ResponseCode.CARD_TYPE_NOT_EXIST.getCode(),
            ResponseCode.CARD_TYPE_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndNameForOtherDocument(cardType, cardTypeParam);

      this.combineCardTypeAndCardTypeParam(cardType, cardTypeParam);

      CardType updatedCardType = this.cardTypeRepository.save(cardType);

      if (!isExistCardType(updatedCardType)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheCardTypeFindAll(mandatoryRequest.getStoreId(), cardType.getBankId());
      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updatedCardType.getId());
      this.cacheService.deleteCache(cacheKey);
      this.cacheService.createCache(cacheKey, updatedCardType, 0);

      LOGGER.info("updateCardType Response CardType {} ", updatedCardType);

      singleEmitter.onSuccess(updatedCardType);

    }).subscribeOn(Schedulers.io());
  }

  private void checkStoreIdAndNameForOtherDocument(CardType cardType,
      CardType cardTypeParam) {

    if (isExistCardType(cardType)
        && !cardType.getName().equals(cardTypeParam.getName())
        && !cardType.getBankId().equals(cardTypeParam.getBankId())) {

      Boolean checkDuplicateStoreIdAndname = this.checkExistOtherStoreIdAndName(cardTypeParam);
      if (checkDuplicateStoreIdAndname) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndName(CardType cardTypeParam) {
    Boolean result = false;
    CardType cardType = this.cardTypeRepository
        .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(cardTypeParam.getStoreId(),
            cardTypeParam.getName(), cardTypeParam.getBankId(), 0);
    if (isExistCardType(cardType)) {
      result = true;
    }

    return result;
  }

  private void combineCardTypeAndCardTypeParam(CardType cardType,
      CardType cardTypeParam) {
    cardType.setVersion(cardType.getVersion());
    cardType.setStoreId(cardType.getStoreId());
    cardType.setUsername(cardType.getUsername());
    cardType.setCreatedDate(cardType.getCreatedDate());
    cardType.setCreatedBy(cardType.getCreatedBy());
    cardType.setUpdatedBy(cardType.getUsername());

    cardType.setName(cardTypeParam.getName());
    cardType.setBankId(cardTypeParam.getBankId());
  }

  @Override
  public Single<Boolean> deleteCardType(MandatoryRequest mandatoryRequest, String id) {
    LOGGER.info("deleteCardType Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      CardType cardType = this.cardTypeRepository
          .findCardTypeByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistCardType(cardType)) {
        throw new BusinessLogicException(ResponseCode.CARD_TYPE_NOT_EXIST.getCode(),
            ResponseCode.CARD_TYPE_NOT_EXIST.getMessage());
      }

      cardType.setIsDeleted(1);
      cardType.setUpdatedBy(mandatoryRequest.getUsername());
      cardType.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.cardTypeRepository.softDeleted(cardType, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheCardTypeFindAll(mandatoryRequest.getStoreId(), cardType.getBankId());
      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      LOGGER.info("deleteCardType Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<CardType> createCardType(MandatoryRequest mandatoryRequest, CardType cardType) {
    LOGGER.info("createCardType request {}, {}", mandatoryRequest, cardType);
    return Single.<CardType>create(
        singleEmitter -> {
          CardType checkCardType = this.cardTypeRepository
              .findCardTypeByStoreIdAndNameAndBankIdAndIsDeleted(mandatoryRequest.getStoreId(),
                  cardType.getName(), cardType.getBankId(), 0);

          if (isExistCardType(checkCardType)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                ResponseCode.DUPLICATE_DATA.getMessage());
          }

          CardType createdCardType = this.cardTypeRepository.save(cardType);

          if (!isExistCardType(createdCardType)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.deleteCacheCardTypeFindAll(mandatoryRequest.getStoreId(), cardType.getBankId());
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), cardType.getId());
          this.cacheService.deleteCache(cacheKey);
          this.cacheService.createCache(cacheKey, cardType, 0);

          LOGGER.info("createCardType response {}", createdCardType);
          singleEmitter.onSuccess(createdCardType);
        }
    ).subscribeOn(Schedulers.io());
  }

  private boolean isExistCardType(CardType checkCardType) {
    Boolean result = false;
    if (checkCardType != null) {
      result = true;
    }

    return result;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.CARD_TYPE + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindAll(String storeId, String bankId) {
    return CacheKey.CARD_TYPE + "-" + storeId + "-" + bankId;
  }

  private void deleteCacheCardTypeFindAll(String storeId, String bankId) {
    String cacheKeyFindAll = this.generateCacheKeyFindAll(storeId, bankId);
    this.cacheService.deleteCache(cacheKeyFindAll);
  }
}
