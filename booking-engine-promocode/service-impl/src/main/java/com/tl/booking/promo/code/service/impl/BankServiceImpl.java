package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.BankRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.BankColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Bank;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.BankService;
import com.tl.booking.promo.code.service.api.CacheService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class BankServiceImpl implements BankService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BankServiceImpl.class);

  @Autowired
  CacheService cacheService;

  @Autowired
  BankRepository bankRepository;

  @Override
  public Single<Bank> createBank(MandatoryRequest mandatoryRequest, Bank bank) {
    LOGGER.info("createBank request {}, {}", mandatoryRequest, bank);
    return Single.<Bank>create(
        singleEmitter -> {
          Bank checkBank = this.bankRepository
              .findBankByStoreIdAndNameAndIsDeleted(mandatoryRequest.getStoreId(),
                  bank.getName(), 0);

          if (isExistBank(checkBank)) {
            throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
                ResponseCode.DUPLICATE_DATA.getMessage());
          }

          Bank createdBank = this.bankRepository.save(bank);

          if (!isExistBank(createdBank)) {
            throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
                ResponseCode.SYSTEM_ERROR.getMessage());
          }

          this.deleteCacheBankFindAll(mandatoryRequest.getStoreId());
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), bank.getId());
          this.cacheService.createCache(cacheKey, bank, 0);

          LOGGER.info("createBank response {}", createdBank);
          singleEmitter.onSuccess(createdBank);
        }
    ).subscribeOn(Schedulers.io());
  }

  private void deleteCacheBankFindAll(String storeId) {
    String cacheKeyFindAll = this.generateCacheKeyFindAll(storeId);
    this.cacheService.deleteCache(cacheKeyFindAll);
  }

  private boolean isExistBank(Bank checkBank) {
    Boolean result = false;
    if (checkBank != null) {
      result = true;
    }

    return result;
  }

  @Override
  public Single<List<Bank>> findBanks(MandatoryRequest mandatoryRequest) {
    LOGGER.info("findBanks Request mandatoryRequest {} ", mandatoryRequest);

    return Single.<List<Bank>>create(singleEmitter -> {

      String cacheKey = this.generateCacheKeyFindAll(mandatoryRequest.getStoreId());
      List<Bank> banks = this.cacheService.findCacheByKey(cacheKey, List.class);

      if (!isExistListBank(banks)) {
        banks = this.bankRepository
            .findByIsDeletedAndStoreId(0, mandatoryRequest.getStoreId());

        this.cacheService.createCache(cacheKey, banks, 0);
      }

      LOGGER.info("findBanks Response bank {} ", banks);

      singleEmitter.onSuccess(banks);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistListBank(List<Bank> banks) {
    return banks != null;
  }

  @Override
  public Single<Bank> findBankById(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("findBankById Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Bank>create(
        singleEmitter -> {
          String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
          Bank bank = this.cacheService.findCacheByKey(cacheKey, Bank.class);

          if (!isExistBank(bank)) {
            bank = this.bankRepository
                .findBankByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

            if (!isExistBank(bank)) {
              throw new BusinessLogicException(ResponseCode.BANK_NOT_EXIST.getCode(),
                  ResponseCode.BANK_NOT_EXIST.getMessage());
            }

            this.cacheService.createCache(cacheKey, bank, 0);
          }

          LOGGER.info("findBankById Response bank {} ", bank);

          singleEmitter.onSuccess(bank);
        }
    ).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Page<Bank>> findBankFilterPaginated(MandatoryRequest mandatoryRequest, String name,
      Integer page, Integer size, BankColumn columnSort, SortDirection sortDirection) {

    LOGGER.info(
        "findBankFilterPaginated Request mandatoryRequest, name, page, size, columnSort, sortDirection {} ",
        mandatoryRequest, name, page, size, columnSort, sortDirection);

    return Single.<Page<Bank>>create(
        singleEmitter -> {

          String sort = setColumnSort(columnSort);

          Page<Bank> bankPage = this.bankRepository
              .findBankFilterPaginated(mandatoryRequest.getStoreId(), name, page,
                  size, sort, sortDirection);

          LOGGER.info("findBankFilterPaginated Response bankPage {} ", bankPage);

          singleEmitter.onSuccess(bankPage);
        }
    ).subscribeOn(Schedulers.io());
  }

  private String setColumnSort(BankColumn bankColumn) {
    String result = null;
    if (isNotNullColumnSort(bankColumn)) {
      result = bankColumn.getValue();
    }

    return result;
  }

  private Boolean isNotNullColumnSort(BankColumn bankColumn) {
    Boolean result = false;
    if (bankColumn != null) {
      result = true;
    }

    return result;
  }

  @Override
  public Single<Bank> updateBank(MandatoryRequest mandatoryRequest, Bank bankParam, String id) {
    LOGGER.info("updateBank Request MandatoryRequest mandatoryRequest, bankParam, id {} ",
        mandatoryRequest, bankParam, id);

    return Single.<Bank>create(singleEmitter -> {
      Bank bank = this.bankRepository
          .findBankByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistBank(bank)) {
        throw new BusinessLogicException(ResponseCode.BANK_NOT_EXIST.getCode(),
            ResponseCode.BANK_NOT_EXIST.getMessage());
      }

      this.checkStoreIdAndNameForOtherDocument(bank, bankParam);

      this.combineBankAndBankParam(bank, bankParam);

      Bank updatedBank = this.bankRepository.save(bank);

      if (!isExistBank(updatedBank)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheBankFindAll(mandatoryRequest.getStoreId());
      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), updatedBank.getId());
      this.cacheService.createCache(cacheKey, updatedBank, 0);

      LOGGER.info("updateBank Response Bank {} ", updatedBank);

      singleEmitter.onSuccess(updatedBank);

    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<Boolean> deleteBank(MandatoryRequest mandatoryRequest, String id) {

    LOGGER.info("deleteBank Request mandatoryRequest, id {} ", mandatoryRequest, id);

    return Single.<Boolean>create(singleEmitter -> {
      Bank bank = this.bankRepository
          .findBankByStoreIdAndIdAndIsDeleted(mandatoryRequest.getStoreId(), id, 0);

      if (!isExistBank(bank)) {
        throw new BusinessLogicException(ResponseCode.BANK_NOT_EXIST.getCode(),
            ResponseCode.BANK_NOT_EXIST.getMessage());
      }

      bank.setIsDeleted(1);
      bank.setUpdatedBy(mandatoryRequest.getUsername());
      bank.setUpdatedDate(DateFormatterHelper.getTodayDateTime());
      Boolean deleted = this.bankRepository.softDeleted(bank, id);

      if (!deleted) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      this.deleteCacheBankFindAll(mandatoryRequest.getStoreId());
      String cacheKey = this.generateCacheKey(mandatoryRequest.getStoreId(), id);
      this.cacheService.deleteCache(cacheKey);

      LOGGER.info("deleteBank Response Boolean {} ", deleted);

      singleEmitter.onSuccess(true);

    }).subscribeOn(Schedulers.io());
  }

  private void combineBankAndBankParam(Bank bank,
      Bank bankParam) {
    bank.setVersion(bank.getVersion());
    bank.setStoreId(bank.getStoreId());
    bank.setUsername(bank.getUsername());
    bank.setCreatedDate(bank.getCreatedDate());
    bank.setCreatedBy(bank.getCreatedBy());
    bank.setUpdatedBy(bank.getUsername());

    bank.setName(bankParam.getName());
  }

  private void checkStoreIdAndNameForOtherDocument(Bank bank,
      Bank bankParam) {

    if (isExistBank(bank) && !bank.getName()
        .equals(bankParam.getName())) {

      Boolean checkDuplicateStoreIdAndname = this.checkExistOtherStoreIdAndName(bankParam);
      if (checkDuplicateStoreIdAndname) {
        throw new BusinessLogicException(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(),
            ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage());
      }
    }
  }

  private Boolean checkExistOtherStoreIdAndName(Bank bankParam) {
    Boolean result = false;
    Bank bank = this.bankRepository
        .findBankByStoreIdAndNameAndIsDeleted(bankParam.getStoreId(),
            bankParam.getName(), 0);
    if (isExistBank(bank)) {
      result = true;
    }

    return result;
  }

  private String generateCacheKey(String storeId, String id) {
    return CacheKey.BANK + "-" + storeId + "-" + id;
  }

  private String generateCacheKeyFindAll(String storeId) {
    return CacheKey.BANK + "-" + storeId;
  }

}
