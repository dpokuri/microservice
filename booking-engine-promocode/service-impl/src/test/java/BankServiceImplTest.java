import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.BankRepository;
import com.tl.booking.promo.code.entity.constant.enums.BankColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.BankTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.Bank;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.BankServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class BankServiceImplTest extends BankTestVariable {

  @InjectMocks
  BankServiceImpl bankService;

  @Mock
  BankRepository bankRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createBankTestSuccess() throws Exception {
    when(this.bankRepository
        .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID,
            BANK.getName(), 0)).thenReturn(null);

    when(bankRepository
        .save(BANK)).thenReturn(BANK);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, BANK, 0)).thenReturn(true);

    Bank bank = bankService
        .createBank(CommonTestVariable.MANDATORY_REQUEST, BANK).blockingGet();

    verify(bankRepository)
        .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID,
            BANK.getName(), 0);

    verify(bankRepository).save(BANK);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).createCache(CACHE_KEY, BANK, 0);

    assertEquals(BANK, bank);
  }

  @Test
  public void createBankTestErrorExistBank() throws Exception {
    when(bankRepository
        .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(BANK);

    try {
      bankService.createBank(CommonTestVariable.MANDATORY_REQUEST, BANK)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository)
          .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createBankTestSystemError() throws Exception {

    when(bankRepository
        .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(bankRepository
        .save(BANK)).thenReturn(null);
    try {
      bankService.createBank(CommonTestVariable.MANDATORY_REQUEST, BANK)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository)
          .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
      verify(bankRepository).save(BANK);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBankTestSuccess() throws Exception {
    when(bankRepository.findBankByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(BANK);

    when(bankRepository
        .save(BANK)).thenReturn(BANK);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, BANK, 0)).thenReturn(true);

    Bank bank = bankService
        .updateBank(CommonTestVariable.MANDATORY_REQUEST, BANK, ID).blockingGet();
    verify(bankRepository).findBankByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0);
    verify(bankRepository).save(BANK);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).createCache(CACHE_KEY, BANK, 0);

    assertEquals(BANK, bank);
  }

  @Test
  public void updateBankTestErrordataNotExist() throws Exception {
    when(bankRepository.findBankByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(null);
    try {
      bankService.updateBank(CommonTestVariable.MANDATORY_REQUEST, BANK, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository).findBankByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BANK_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BANK_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBankTestErrorNameIsExistInOtherDocument() throws Exception {
    when(bankRepository.findBankByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(BANK);
    when(bankRepository
        .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0))
        .thenReturn(BANK_2);

    try {
      bankService.updateBank(CommonTestVariable.MANDATORY_REQUEST, BANK_2, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository).findBankByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);
      verify(bankRepository)
          .findBankByStoreIdAndNameAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBankTestExceptionSystemError() throws Exception {
    when(bankRepository
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BANK);

    when(bankRepository
        .save(BANK)).thenReturn(null);

    try {
      bankService.updateBank(CommonTestVariable.MANDATORY_REQUEST, BANK, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository)
          .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(bankRepository).save(BANK);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void deleteBankTestSuccess() throws Exception {
    BANK.setIsDeleted(1);

    when(bankRepository
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BANK);

    when(bankRepository.softDeleted(BANK, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    Boolean bank = bankService
        .deleteBank(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(bankRepository)
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(bankRepository).softDeleted(BANK, ID);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY);

    assertEquals(true, bank);
  }

  @Test
  public void deleteBankTestDataNotExist() throws Exception {

    when(bankRepository
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      bankService.deleteBank(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository)
          .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BANK_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BANK_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deleteBankTestExceptionSystemError() throws Exception {

    when(bankRepository
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BANK);

    when(bankRepository.softDeleted(BANK, ID)).thenReturn(false);

    try {
      bankService.deleteBank(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(bankRepository)
          .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(bankRepository).softDeleted(BANK, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findAllBankTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_FIND_ALL, List.class))
        .thenReturn(null);
    when(bankRepository.findByIsDeletedAndStoreId(0, CommonTestVariable.STORE_ID))
        .thenReturn(BANK_LIST_IS_DELETED);
    when(cacheService.createCache(CACHE_KEY_FIND_ALL, BANK_LIST_IS_DELETED, 0)).thenReturn(true);

    List<Bank> bank = bankService
        .findBanks(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_FIND_ALL, List.class);
    verify(bankRepository).findByIsDeletedAndStoreId(0, CommonTestVariable.STORE_ID);
    verify(cacheService).createCache(CACHE_KEY_FIND_ALL, BANK_LIST_IS_DELETED, 0);

    assertEquals(BANK_LIST_IS_DELETED, bank);
  }

  @Test
  public void findBankByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Bank.class))
        .thenReturn(BANK);

    Bank bank = bankService
        .findBankById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Bank.class);

    assertEquals(BANK, bank);
  }

  @Test
  public void findBankByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Bank.class))
        .thenReturn(null);
    when(bankRepository
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BANK);
    when(cacheService.createCache(CACHE_KEY, BANK, 0)).thenReturn(true);

    Bank bank = bankService
        .findBankById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, Bank.class);
    verify(bankRepository)
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, BANK, 0);

    assertEquals(BANK, bank);
  }

  @Test
  public void findBankByIdTestExceptionBankNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, Bank.class))
        .thenReturn(null);
    when(bankRepository
        .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      bankService
          .findBankById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, Bank.class);
      verify(bankRepository)
          .findBankByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BANK_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BANK_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findBankPaginatedTestSuccess() throws Exception {
    when(bankRepository
        .findBankFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, BankColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(BANK_PAGE);

    Page<Bank> bankPage = bankService
        .findBankFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE, SIZE, BankColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(bankRepository)
        .findBankFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, BankColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(BANK_PAGE, bankPage);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(bankRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
