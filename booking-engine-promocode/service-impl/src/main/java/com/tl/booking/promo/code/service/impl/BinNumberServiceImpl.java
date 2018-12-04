package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.BinNumberRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.BinNumberColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.BinNumberService;
import com.tl.booking.promo.code.service.api.CacheService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class BinNumberServiceImpl implements BinNumberService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BinNumberServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  BinNumberRepository binNumberRepository;

  @Override
  public Single<List<BinNumber>> findBinNumbers(MandatoryRequest mandatoryRequest, String binNumber,
      String bankId, String cardTypeId) {
    LOGGER.info("findBinNumbers Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<BinNumber>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFindAll(mandatoryRequest.getStoreId(),
          binNumber, bankId, cardTypeId);
      List<BinNumber> binNumbers = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistListBinNumber(binNumbers)) {
        binNumbers = this.binNumberRepository
            .findBinNumbers(mandatoryRequest.getStoreId(), binNumber, bankId, cardTypeId);

        this.cacheService.createCache(cacheKey, binNumbers, 0);
      }

      LOGGER.info("findBinNumbers Response binNumber {} ", binNumbers);

      singleEmitter.onSuccess(binNumbers);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistListBinNumber(List<BinNumber> binNumbers) {
    return binNumbers != null;
  }

  @Override
  public Single<BinNumber> createBinNumber(MandatoryRequest mandatoryRequest, BinNumber binNumber) {

    LOGGER.info("createBinNumber request {}, {}", mandatoryRequest, binNumber);

    return Single.<BinNumber>create(
        singleEmitter -> {

          BinNumber checkBinNumber = this.binNumberRepository
              .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
                  mandatoryRequest.getStoreId(),
                  binNumber.getBinNumber(), 0);

          if (isExistBinNumber(checkBinNumber)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                ResponseCode.DUPLICATE_DATA.getMessage());
          }

          BinNumber createdBinNumber = this.binNumberRepository.save(binNumber);

          if (!isExistBinNumber(createdBinNumber)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.deleteCacheBinNumberFindAll(mandatoryRequest.getStoreId(), null, null, null);
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), binNumber.getId());
          this.cacheService.deleteCache(cacheKey);
          this.cacheService.createCache(cacheKey, binNumber, 0);

          LOGGER.info("createBinNumber response {}", createdBinNumber);

          singleEmitter.onSuccess(createdBinNumber);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<BinNumber> updateBinNumber(MandatoryRequest mandatoryRequest,
      BinNumber binNumberParam,
      String id) {

    LOGGER.info("updateBinNumber Request MandatoryRequest mandatoryRequest, binNumberParam, id {} ",
        mandatoryRequest, binNumberParam, id);

    return Single.<BinNumber>create(singleEmitter -> {
      BinNumber binNumber = this.binNumberRepository
          .findBinNumberByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistBinNumber(binNumber)) {
        throw new BusinessLogicException(ResponseCode.BIN_NUMBER_NOT_EXIST.getCode(),
            ResponseCode.BIN_NUMBER_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndBinNumberForOtherDocument(binNumber,
          binNumberParam);

      this.combineBinNumberAndBinNumberParam(binNumber, binNumberParam);

      BinNumber updatedBinNumber = this.binNumberRepository.save(binNumber);

      if (!isExistBinNumber(updatedBinNumber)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheBinNumberFindAllCombine(mandatoryRequest.getStoreId(),
          binNumber.getBinNumber(),
          binNumber.getBankId(), binNumber.getCardTypeId());
      String cacheKeyById = this
          .generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKeyById);

      String cacheKey = this
          .generateCacheKey(mandatoryRequest.getStoreId(), updatedBinNumber.getId());
      this.cacheService.createCache(cacheKey, updatedBinNumber, 0);

      LOGGER.info("updateBinNumber Response BinNumber {} ", updatedBinNumber);

      singleEmitter.onSuccess(updatedBinNumber);

    }).subscribeOn(Schedulers.io());
  }

  private void deleteCacheBinNumberFindAll(String storeId, String binNumber, String bankId,
      String cardTypeId) {
    String cacheKeyFindAll = this.generateCacheKeyFindAll(storeId, binNumber, bankId, cardTypeId);
    this.cacheService.deleteCache(cacheKeyFindAll);
  }

  private void deleteCacheBinNumberFindAllCombine(String storeId, String binNumber, String bankId,
      String cardTypeId) {
    String cacheKey1 = this.generateCacheKeyFindAll(storeId, binNumber, bankId, cardTypeId);
    String cacheKey2 = this.generateCacheKeyFindAll(storeId, null, null, null);
    String cacheKey3 = this.generateCacheKeyFindAll(storeId, binNumber, null, null);
    String cacheKey4 = this.generateCacheKeyFindAll(storeId, null, bankId, null);
    String cacheKey5 = this.generateCacheKeyFindAll(storeId, null, null, cardTypeId);
    String cacheKey6 = this.generateCacheKeyFindAll(storeId, binNumber, bankId, null);
    String cacheKey7 = this.generateCacheKeyFindAll(storeId, binNumber, null, cardTypeId);
    String cacheKey8 = this.generateCacheKeyFindAll(storeId, null, bankId, cardTypeId);

    List<String> cacheKeyArrays = Arrays.asList(cacheKey1, cacheKey2, cacheKey3, cacheKey4,
        cacheKey5, cacheKey6, cacheKey7, cacheKey8);

    for (String cache : cacheKeyArrays) {
      this.cacheService.deleteCache(cache);
    }
  }

  private void combineBinNumberAndBinNumberParam(BinNumber binNumber,
      BinNumber binNumberParam) {
    binNumber.setVersion(binNumber.getVersion());
    binNumber.setStoreId(binNumber.getStoreId());
    binNumber.setUsername(binNumber.getUsername());
    binNumber.setCreatedDate(binNumber.getCreatedDate());
    binNumber.setCreatedBy(binNumber.getCreatedBy());
    binNumber.setUpdatedBy(binNumber.getUsername());

    binNumber.setBinNumber(binNumberParam.getBinNumber());
    binNumber.setBankId(binNumberParam.getBankId());
    binNumber.setCardTypeId(binNumberParam.getCardTypeId());
    binNumber.setDescription(binNumberParam.getDescription());
  }

  private void checkStoreIdAndBinNumberForOtherDocument(BinNumber binNumber,
      BinNumber binNumberParam) {

    if (isExistBinNumber(binNumber)
        && !binNumber.getBinNumber().equals(binNumberParam.getBinNumber())) {

      Boolean checkDuplicateStoreIdAndname =
          this.checkExistOtherStoreIdAndBinNumber(binNumberParam);

      if (checkDuplicateStoreIdAndname) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndBinNumber(
      BinNumber binNumberParam) {
    Boolean result = false;
    BinNumber binNumber = this.binNumberRepository
        .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
            binNumberParam.getStoreId(), binNumberParam.getBinNumber(), 0);
    if (isExistBinNumber(binNumber)) {
      result = true;
    }

    return result;
  }

  private boolean isExistBinNumber(BinNumber checkBinNumber) {
    Boolean result = false;
    if (checkBinNumber != null) {
      result = true;
    }

    return result;
  }

  @Override
  public Single<Boolean> deleteBinNumber(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deleteBinNumber Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      BinNumber binNumber = this.binNumberRepository
          .findBinNumberByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistBinNumber(binNumber)) {
        throw new BusinessLogicException(ResponseCode.BIN_NUMBER_NOT_EXIST.getCode(),
            ResponseCode.BIN_NUMBER_NOT_EXIST.getMessage());
      }

      binNumber.setIsDeleted(1);
      binNumber.setUpdatedBy(mandatoryRequest.getUsername());
      binNumber.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.binNumberRepository.softDeleted(binNumber, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheBinNumberFindAllCombine(mandatoryRequest.getStoreId(),
          binNumber.getBinNumber(),
          binNumber.getBankId(), binNumber.getCardTypeId());

      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      LOGGER.info("deleteBinNumber Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<BinNumber> findBinNumberById(MandatoryRequest mandatoryRequest, String id) {
    LOGGER.info("findBinNumberById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<BinNumber>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          BinNumber binNumber = this.cacheService.findCacheByKey(cacheKey, BinNumber.class);

          if (!isExistBinNumber(binNumber)) {
            binNumber = this.binNumberRepository
                .findBinNumberByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistBinNumber(binNumber)) {
              throw new BusinessLogicException(ResponseCode.BIN_NUMBER_NOT_EXIST.getCode(),
                  ResponseCode.BIN_NUMBER_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, binNumber, 0);
          }

          LOGGER.info("findBinNumberById Response binNumber {} ", binNumber);

          singleEmitter.onSuccess(binNumber);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Page<BinNumber>> findBinNumberFilterPaginated(MandatoryRequest mandatoryRequest,
      String binNumber, String bankId, String cardTypeId, Integer page, Integer size,
      BinNumberColumn columnSort,
      SortDirection sortDirection) {

    LOGGER.info("findBinNumberFilterPaginated Request mandatoryRequest, name, bankId, cardTypeId, "
            + "page, size, columnSort, sortDirection {} ",
        mandatoryRequest, binNumber, bankId, cardTypeId, page, size, columnSort, sortDirection);

    return Single.<Page<BinNumber>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<BinNumber> binNumberPage = this.binNumberRepository
              .findBinNumberFilterPaginated(mandatoryRequest.getStoreId(), binNumber, bankId,
                  cardTypeId, page, size, sort, sortDirection);

          LOGGER.info("findBinNumberFilterPaginated Response binNumberPage {} ", binNumberPage);

          singleEmitter.onSuccess(binNumberPage);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(BinNumberColumn binNumberColumn) {
    String result = null;
    if (isNotNullColumnSort(binNumberColumn)) {
      result = binNumberColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(BinNumberColumn binNumberColumn) {
    Boolean result = false;
    if (binNumberColumn != null) {
      result = true;
    }

    return result;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.BIN_NUMBER + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindAll(String storeId, String binNumber, String bankId,
      String cardTypeId) {
    return CacheKey.BIN_NUMBER + "-" + storeId + "-" + binNumber + "-" + bankId + "-" + cardTypeId;
  }
}
