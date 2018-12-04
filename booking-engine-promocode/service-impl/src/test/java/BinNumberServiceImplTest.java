import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.BinNumberRepository;
import com.tl.booking.promo.code.entity.constant.enums.BankColumn;
import com.tl.booking.promo.code.entity.constant.enums.BinNumberColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.BinNumberTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.BinNumberServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class BinNumberServiceImplTest extends BinNumberTestVariable {

  @InjectMocks
  BinNumberServiceImpl binNumberService;

  @Mock
  BinNumberRepository binNumberRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void findAllBinNumberTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_FIND_ALL, List.class))
        .thenReturn(null);
    when(binNumberRepository
        .findBinNumbers(CommonTestVariable.STORE_ID, BIN_NUMBER, BANK_ID, CARD_TYPE_ID))
        .thenReturn(BIN_NUMBER_LIST_IS_DELETED);
    when(cacheService.createCache(CACHE_KEY_FIND_ALL, BIN_NUMBER_LIST_IS_DELETED, 0))
        .thenReturn(true);

    List<BinNumber> binNumber = binNumberService
        .findBinNumbers(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER, BANK_ID, CARD_TYPE_ID)
        .blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_FIND_ALL, List.class);
    verify(binNumberRepository)
        .findBinNumbers(CommonTestVariable.STORE_ID, BIN_NUMBER, BANK_ID, CARD_TYPE_ID);
    verify(cacheService).createCache(CACHE_KEY_FIND_ALL, BIN_NUMBER_LIST_IS_DELETED, 0);

    assertEquals(BIN_NUMBER_LIST_IS_DELETED, binNumber);
  }

  @Test
  public void createBinNumberTestSuccess() throws Exception {
    when(this.binNumberRepository
        .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
            CommonTestVariable.STORE_ID,
            BIN_NUMBER_BUILDER.getBinNumber(), 0)).thenReturn(null);

    when(binNumberRepository
        .save(BIN_NUMBER_BUILDER)).thenReturn(BIN_NUMBER_BUILDER);

    when(cacheService.createCache(CACHE_KEY, BIN_NUMBER_BUILDER, 0)).thenReturn(true);

    BinNumber binNumber = binNumberService
        .createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER).blockingGet();

    verify(binNumberRepository)
        .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
            CommonTestVariable.STORE_ID,
            BIN_NUMBER_BUILDER.getBinNumber(), 0);

    verify(binNumberRepository).save(BIN_NUMBER_BUILDER);

    verify(cacheService).deleteCache(CACHE_KEY2);
    verify(cacheService).deleteCache(CACHE_KEY);

    verify(cacheService).createCache(CACHE_KEY, BIN_NUMBER_BUILDER, 0);

    assertEquals(BIN_NUMBER_BUILDER, binNumber);
  }

  @Test
  public void createBinNumberTestErrorExistBank() throws Exception {
    when(binNumberRepository
        .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
            CommonTestVariable.STORE_ID,
            BIN_NUMBER_BUILDER.getBinNumber(), 0))
        .thenReturn(BIN_NUMBER_BUILDER);

    try {
      binNumberService.createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
              CommonTestVariable.STORE_ID,
              BIN_NUMBER_BUILDER.getBinNumber(), 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createBinNumberTestSystemError() throws Exception {

    when(binNumberRepository
        .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
            CommonTestVariable.STORE_ID,
            BIN_NUMBER_BUILDER.getBinNumber(), 0))
        .thenReturn(null);

    when(binNumberRepository
        .save(BIN_NUMBER_BUILDER)).thenReturn(null);
    try {
      binNumberService.createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
              CommonTestVariable.STORE_ID,
              BIN_NUMBER_BUILDER.getBinNumber(), 0);
      verify(binNumberRepository).save(BIN_NUMBER_BUILDER);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBinNumberTestSuccess() throws Exception {
    when(binNumberRepository.findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
        ID, 0)).thenReturn(BIN_NUMBER_BUILDER);

    when(binNumberRepository
        .save(BIN_NUMBER_BUILDER)).thenReturn(BIN_NUMBER_BUILDER);

    when(cacheService.createCache(CACHE_KEY, BIN_NUMBER_BUILDER, 0)).thenReturn(true);

    BinNumber binNumber = binNumberService
        .updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER, ID)
        .blockingGet();

    verify(binNumberRepository).findBinNumberByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0);
    verify(binNumberRepository).save(BIN_NUMBER_BUILDER);

    verify(cacheService).deleteCache(CACHE_KEY1);
    verify(cacheService).deleteCache(CACHE_KEY2);
    verify(cacheService).deleteCache(CACHE_KEY3);
    verify(cacheService).deleteCache(CACHE_KEY4);
    verify(cacheService).deleteCache(CACHE_KEY5);
    verify(cacheService).deleteCache(CACHE_KEY6);
    verify(cacheService).deleteCache(CACHE_KEY7);
    verify(cacheService).deleteCache(CACHE_KEY8);
    verify(cacheService).deleteCache(CACHE_KEY);

    verify(cacheService).createCache(CACHE_KEY, BIN_NUMBER_BUILDER, 0);

    assertEquals(BIN_NUMBER_BUILDER, binNumber);
  }

  @Test
  public void updateBinNumberTestErrordataNotExist() throws Exception {
    when(binNumberRepository.findBinNumberByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(null);
    try {
      binNumberService.updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository).findBinNumberByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BIN_NUMBER_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BIN_NUMBER_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBinNumberTestErrorNameIsExistInOtherDocument() throws Exception {
    when(binNumberRepository.findBinNumberByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(BIN_NUMBER_BUILDER);
    when(binNumberRepository
        .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
            CommonTestVariable.STORE_ID,
            BIN_NUMBER_2, 0))
        .thenReturn(BIN_NUMBER_BUILDER_2);

    try {
      binNumberService
          .updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER_2, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository).findBinNumberByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndBinNumberAndIsDeleted(
              CommonTestVariable.STORE_ID,
              BIN_NUMBER_2, 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBinNumberTestExceptionSystemError() throws Exception {
    when(binNumberRepository
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BIN_NUMBER_BUILDER);

    when(binNumberRepository
        .save(BIN_NUMBER_BUILDER)).thenReturn(null);

    try {
      binNumberService.updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_BUILDER, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(binNumberRepository).save(BIN_NUMBER_BUILDER);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void deleteBinNumberTestSuccess() throws Exception {
    BIN_NUMBER_BUILDER.setIsDeleted(1);

    when(binNumberRepository
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BIN_NUMBER_BUILDER);

    when(binNumberRepository.softDeleted(BIN_NUMBER_BUILDER, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY1)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY2)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY3)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY4)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY5)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY6)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY7)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY8)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    Boolean binNumber = binNumberService
        .deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(binNumberRepository)
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(binNumberRepository).softDeleted(BIN_NUMBER_BUILDER, ID);

    verify(cacheService).deleteCache(CACHE_KEY1);
    verify(cacheService).deleteCache(CACHE_KEY2);
    verify(cacheService).deleteCache(CACHE_KEY3);
    verify(cacheService).deleteCache(CACHE_KEY4);
    verify(cacheService).deleteCache(CACHE_KEY5);
    verify(cacheService).deleteCache(CACHE_KEY6);
    verify(cacheService).deleteCache(CACHE_KEY7);
    verify(cacheService).deleteCache(CACHE_KEY8);

    verify(cacheService).deleteCache(CACHE_KEY);

    assertEquals(true, binNumber);
  }

  @Test
  public void deleteBinNumberTestDataNotExist() throws Exception {

    when(binNumberRepository
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      binNumberService.deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BIN_NUMBER_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BIN_NUMBER_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deleteBinNumberTestExceptionSystemError() throws Exception {

    when(binNumberRepository
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BIN_NUMBER_BUILDER);

    when(binNumberRepository.softDeleted(BIN_NUMBER_BUILDER, ID)).thenReturn(false);

    try {
      binNumberService.deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(binNumberRepository).softDeleted(BIN_NUMBER_BUILDER, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findBinNumberByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, BinNumber.class))
        .thenReturn(BIN_NUMBER_BUILDER);

    BinNumber binNumber = binNumberService
        .findBinNumberById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, BinNumber.class);

    assertEquals(BIN_NUMBER_BUILDER, binNumber);
  }

  @Test
  public void findBinNumberByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, BinNumber.class))
        .thenReturn(null);
    when(binNumberRepository
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BIN_NUMBER_BUILDER);
    when(cacheService.createCache(CACHE_KEY, BIN_NUMBER_BUILDER, 0)).thenReturn(true);

    BinNumber binNumber = binNumberService
        .findBinNumberById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, BinNumber.class);
    verify(binNumberRepository)
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, BIN_NUMBER_BUILDER, 0);

    assertEquals(BIN_NUMBER_BUILDER, binNumber);
  }

  @Test
  public void findBinNumberByIdTestExceptionBankNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, BinNumber.class))
        .thenReturn(null);
    when(binNumberRepository
        .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      binNumberService
          .findBinNumberById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, BinNumber.class);
      verify(binNumberRepository)
          .findBinNumberByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BIN_NUMBER_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BIN_NUMBER_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findBinNumberPaginatedTestSuccess() throws Exception {
    when(binNumberRepository
        .findBinNumberFilterPaginated(CommonTestVariable.STORE_ID, BIN_NUMBER, BANK_ID,
            CARD_TYPE_ID, PAGE, SIZE, BankColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(BIN_NUMBER_PAGE);

    Page<BinNumber> binNumberPage = binNumberService
        .findBinNumberFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            BIN_NUMBER, BANK_ID, CARD_TYPE_ID, PAGE, SIZE, BinNumberColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(binNumberRepository)
        .findBinNumberFilterPaginated(CommonTestVariable.STORE_ID, BIN_NUMBER, BANK_ID,
            CARD_TYPE_ID,
            PAGE, SIZE, BinNumberColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(BIN_NUMBER_PAGE, binNumberPage);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(binNumberRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
