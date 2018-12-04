import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.StoreIdRepository;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.StoreIdColumn;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.StoreIdTestVariable;
import com.tl.booking.promo.code.entity.dao.StoreId;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.StoreIdServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class StoreIdServiceImplTest extends StoreIdTestVariable {

  @InjectMocks
  StoreIdServiceImpl storeIdService;

  @Mock
  StoreIdRepository storeIdRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createStoreIdTestSuccess() throws Exception {
    when(this.storeIdRepository
        .findStoreIdByValueAndIsDeleted(
            STORE_ID.getValue(), 0)).thenReturn(null);

    when(storeIdRepository
        .save(STORE_ID)).thenReturn(STORE_ID);

    when(cacheService.createCache(CACHE_KEY, STORE_ID, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    StoreId productType = storeIdService
        .createStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID).blockingGet();

    verify(storeIdRepository)
        .findStoreIdByValueAndIsDeleted(
            STORE_ID.getValue(), 0);

    verify(storeIdRepository).save(STORE_ID);
    verify(cacheService).createCache(CACHE_KEY, STORE_ID, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(STORE_ID, productType);
  }


  @Test
  public void createStoreIdTestErrorExistStoreId() throws Exception {
    when(storeIdRepository
        .findStoreIdByValueAndIsDeleted(NAME, 0))
        .thenReturn(STORE_ID);

    try {
      storeIdService.createStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findStoreIdByValueAndIsDeleted(NAME, 0);

      assertEquals(ResponseCode.STORE_ID_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.STORE_ID_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createStoreIdTestSystemError() throws Exception {

    when(storeIdRepository
        .findStoreIdByValueAndIsDeleted(NAME, 0))
        .thenReturn(null);

    when(storeIdRepository
        .save(STORE_ID)).thenReturn(null);
    try {
      storeIdService.createStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findStoreIdByValueAndIsDeleted(NAME, 0);
      verify(storeIdRepository).save(STORE_ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestSuccess() throws Exception {
    when(storeIdRepository.findStoreIdByIdAndIsDeleted(
        ID, 0)).thenReturn(STORE_ID);

    when(storeIdRepository
        .save(STORE_ID)).thenReturn(STORE_ID);

    when(cacheService.createCache(CACHE_KEY, STORE_ID, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    StoreId productType = storeIdService
        .updateStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID, ID).blockingGet();
    verify(storeIdRepository).findStoreIdByIdAndIsDeleted(ID, 0);
    verify(storeIdRepository).save(STORE_ID);
    verify(cacheService).createCache(CACHE_KEY, STORE_ID, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(STORE_ID, productType);
  }


  @Test
  public void updateStoreIdTestErrordataNotExist() throws Exception {
    when(storeIdRepository.findStoreIdByIdAndIsDeleted(ID, 0)).thenReturn(null);
    try {
      storeIdService.updateStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository).findStoreIdByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.STORE_ID_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.STORE_ID_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateStoreIdTestErrorNameIsExistInOtherDocument() throws Exception {
    when(storeIdRepository.findStoreIdByIdAndIsDeleted(ID, 0)).thenReturn(STORE_ID);
    when(storeIdRepository
        .findStoreIdByValueAndIsDeleted(NAME_2, 0))
        .thenReturn(STORE_ID_2);

    try {
      storeIdService.updateStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID_2, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository).findStoreIdByIdAndIsDeleted(ID, 0);
      verify(storeIdRepository)
          .findStoreIdByValueAndIsDeleted(NAME_2, 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestExceptionSystemError() throws Exception {
    when(storeIdRepository
        .findStoreIdByIdAndIsDeleted(ID, 0))
        .thenReturn(STORE_ID);

    when(storeIdRepository
        .save(STORE_ID)).thenReturn(null);

    try {
      storeIdService.updateStoreId(CommonTestVariable.MANDATORY_REQUEST, STORE_ID, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findStoreIdByIdAndIsDeleted(ID, 0);
      verify(storeIdRepository).save(STORE_ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void deleteCampaignTestSuccess() throws Exception {
    STORE_ID.setIsDeleted(1);

    when(storeIdRepository
        .findStoreIdByIdAndIsDeleted(ID, 0))
        .thenReturn(STORE_ID);

    when(storeIdRepository.softDeleted(STORE_ID, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    Boolean storeId = storeIdService
        .deleteStoreId(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(storeIdRepository)
        .findStoreIdByIdAndIsDeleted(ID, 0);
    verify(storeIdRepository).softDeleted(STORE_ID, ID);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(true, storeId);
  }

  @Test
  public void findStoreIdPaginatedTestSuccess() throws Exception {
    when(storeIdRepository
        .findStoreIdFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, StoreIdColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(STORE_ID_PAGE);

    Page<StoreId> productTypePage = storeIdService
        .findStoreIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE, SIZE, StoreIdColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(storeIdRepository)
        .findStoreIdFilterPaginated(CommonTestVariable.STORE_ID, NAME, PAGE,
            SIZE, StoreIdColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(STORE_ID_PAGE, productTypePage);
  }

  @Test
  public void deleteStoreIdTestDataNotExist() throws Exception {

    when(storeIdRepository
        .findStoreIdByIdAndIsDeleted(ID, 0))
        .thenReturn(null);
    try {
      storeIdService.deleteStoreId(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findStoreIdByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.STORE_ID_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.STORE_ID_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void deleteStoreIdTestExceptionSystemError() throws Exception {

    when(storeIdRepository
        .findStoreIdByIdAndIsDeleted(ID, 0))
        .thenReturn(STORE_ID);

    when(storeIdRepository.softDeleted(STORE_ID, ID)).thenReturn(false);

    try {
      storeIdService.deleteStoreId(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findStoreIdByIdAndIsDeleted(ID, 0);
      verify(storeIdRepository).softDeleted(STORE_ID, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findStoreIdByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, StoreId.class))
        .thenReturn(STORE_ID);

    StoreId productType = storeIdService
        .findStoreIdById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, StoreId.class);

    assertEquals(STORE_ID, productType);
  }

  @Test
  public void findStoreIdByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, StoreId.class))
        .thenReturn(null);
    when(storeIdRepository
        .findStoreIdByIdAndIsDeleted(ID, 0))
        .thenReturn(STORE_ID);
    when(cacheService.createCache(CACHE_KEY, STORE_ID, 0)).thenReturn(true);

    StoreId productType = storeIdService
        .findStoreIdById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, StoreId.class);
    verify(storeIdRepository)
        .findStoreIdByIdAndIsDeleted(ID, 0);
    verify(cacheService).createCache(CACHE_KEY, STORE_ID, 0);

    assertEquals(STORE_ID, productType);
  }

  @Test
  public void findStoreIdByIdTestExceptionCampaignNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, StoreId.class))
        .thenReturn(null);
    when(storeIdRepository
        .findStoreIdByIdAndIsDeleted(ID, 0))
        .thenReturn(null);

    try {
      storeIdService
          .findStoreIdById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, StoreId.class);
      verify(storeIdRepository)
          .findStoreIdByIdAndIsDeleted(ID, 0);

      assertEquals(ResponseCode.STORE_ID_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.STORE_ID_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findStoreIdTestSuccessWithoutCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(null);
    when(storeIdRepository.findByIsDeleted(0))
        .thenReturn(STORE_ID_LIST);
    when(this.cacheService.createCache(CACHE_KEY_FIND, STORE_ID_LIST, 0)).thenReturn(true);

    List<StoreId> storeId = this.storeIdService.findStoreIds(CommonTestVariable.MANDATORY_REQUEST)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);
    verify(storeIdRepository).findByIsDeleted(0);
    verify(this.cacheService).createCache(CACHE_KEY_FIND, STORE_ID_LIST, 0);

    assertEquals(storeId, STORE_ID_LIST);
  }

  @Test
  public void findStoreIdTestSuccessWithCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(STORE_ID_LIST);

    List<StoreId> storeId = this.storeIdService.findStoreIds(CommonTestVariable.MANDATORY_REQUEST)
        .blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);

    assertEquals(storeId, STORE_ID_LIST);
  }

  @Test
  public void findStoreIdTestExceptionDataNotExist() throws Exception {

    List<StoreId> storeIdList = new ArrayList<>();

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(null);
    when(storeIdRepository.findByIsDeleted(0))
        .thenReturn(storeIdList);

    try {
      this.storeIdService.findStoreIds(CommonTestVariable.MANDATORY_REQUEST).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);
      verify(storeIdRepository).findByIsDeleted(0);
      assertEquals(ResponseCode.STORE_ID_LIST_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.STORE_ID_LIST_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(storeIdRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
