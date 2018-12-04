import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.ChannelIdRepository;
import com.tl.booking.promo.code.entity.constant.enums.ChannelIdColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.ChannelIdTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.ChannelId;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.ChannelIdServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class ChannelIdServiceImplTest extends ChannelIdTestVariable {

  @InjectMocks
  ChannelIdServiceImpl storeIdService;

  @Mock
  ChannelIdRepository storeIdRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createChannelIdTestSuccess() throws Exception {
    when(this.storeIdRepository
        .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID,
            CHANNEL_ID.getValue(), 0)).thenReturn(null);

    when(storeIdRepository
        .save(CHANNEL_ID)).thenReturn(CHANNEL_ID);

    when(cacheService.createCache(CACHE_KEY, CHANNEL_ID, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    ChannelId channelId = storeIdService
        .createChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID).blockingGet();

    verify(storeIdRepository)
        .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID,
            CHANNEL_ID.getValue(), 0);

    verify(storeIdRepository).save(CHANNEL_ID);
    verify(cacheService).createCache(CACHE_KEY, CHANNEL_ID, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(CHANNEL_ID, channelId);
  }


  @Test
  public void createChannelIdTestErrorExistChannelId() throws Exception {
    when(storeIdRepository
        .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(CHANNEL_ID);

    try {
      storeIdService.createChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);

      assertEquals(ResponseCode.CHANNEL_ID_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CHANNEL_ID_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createChannelIdTestSystemError() throws Exception {

    when(storeIdRepository
        .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0))
        .thenReturn(null);

    when(storeIdRepository
        .save(CHANNEL_ID)).thenReturn(null);
    try {
      storeIdService.createChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID, NAME, 0);
      verify(storeIdRepository).save(CHANNEL_ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestSuccess() throws Exception {
    when(storeIdRepository.findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID,
        ID, 0)).thenReturn(CHANNEL_ID);

    when(storeIdRepository
        .save(CHANNEL_ID)).thenReturn(CHANNEL_ID);

    when(cacheService.createCache(CACHE_KEY, CHANNEL_ID, 0)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    ChannelId channelId = storeIdService
        .updateChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID, ID).blockingGet();
    verify(storeIdRepository).findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(storeIdRepository).save(CHANNEL_ID);
    verify(cacheService).createCache(CACHE_KEY, CHANNEL_ID, 0);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(CHANNEL_ID, channelId);
  }


  @Test
  public void updateChannelIdTestErrordataNotExist() throws Exception {
    when(storeIdRepository.findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      storeIdService.updateChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository).findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CHANNEL_ID_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CHANNEL_ID_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateChannelIdTestErrorNameIsExistInOtherDocument() throws Exception {
    when(storeIdRepository.findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CHANNEL_ID);
    when(storeIdRepository
        .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0))
        .thenReturn(CHANNEL_ID_2);

    try {
      storeIdService.updateChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID_2, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository).findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(storeIdRepository)
          .findByStoreIdAndValueAndIsDeleted(CommonTestVariable.STORE_ID, NAME_2, 0);

      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getCode(), be.getCode());
      assertEquals(ResponseCode.NAME_EXIST_OTHER_RECORD.getMessage(), be.getMessage());
    }

  }


  @Test
  public void updateCampaignTestExceptionSystemError() throws Exception {
    when(storeIdRepository
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CHANNEL_ID);

    when(storeIdRepository
        .save(CHANNEL_ID)).thenReturn(null);

    try {
      storeIdService.updateChannelId(CommonTestVariable.MANDATORY_REQUEST, CHANNEL_ID, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(storeIdRepository).save(CHANNEL_ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }


  @Test
  public void deleteCampaignTestSuccess() throws Exception {
    CHANNEL_ID.setIsDeleted(1);

    when(storeIdRepository
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CHANNEL_ID);

    when(storeIdRepository.softDeleted(CHANNEL_ID, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY_FIND)).thenReturn(true);

    Boolean storeId = storeIdService
        .deleteChannelId(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(storeIdRepository)
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(storeIdRepository).softDeleted(CHANNEL_ID, ID);
    verify(cacheService).deleteCache(CACHE_KEY);
    verify(cacheService).deleteCache(CACHE_KEY_FIND);

    assertEquals(true, storeId);
  }

  @Test
  public void findChannelIdPaginatedTestSuccess() throws Exception {
    when(storeIdRepository
        .findChannelIdFilterPaginated(CommonTestVariable.CHANNEL_ID, NAME, PAGE,
            SIZE, ChannelIdColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(CHANNEL_ID_PAGE);

    Page<ChannelId> channelIdPage = storeIdService
        .findChannelIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE, SIZE, ChannelIdColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(storeIdRepository)
        .findChannelIdFilterPaginated(CommonTestVariable.CHANNEL_ID, NAME, PAGE,
            SIZE, ChannelIdColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(CHANNEL_ID_PAGE, channelIdPage);
  }

  @Test
  public void deleteChannelIdTestDataNotExist() throws Exception {

    when(storeIdRepository
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      storeIdService.deleteChannelId(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CHANNEL_ID_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CHANNEL_ID_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void deleteChannelIdTestExceptionSystemError() throws Exception {

    when(storeIdRepository
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CHANNEL_ID);

    when(storeIdRepository.softDeleted(CHANNEL_ID, ID)).thenReturn(false);

    try {
      storeIdService.deleteChannelId(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(storeIdRepository)
          .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(storeIdRepository).softDeleted(CHANNEL_ID, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findChannelIdByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, ChannelId.class))
        .thenReturn(CHANNEL_ID);

    ChannelId channelId = storeIdService
        .findChannelIdById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, ChannelId.class);

    assertEquals(CHANNEL_ID, channelId);
  }

  @Test
  public void findChannelIdByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, ChannelId.class))
        .thenReturn(null);
    when(storeIdRepository
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(CHANNEL_ID);
    when(cacheService.createCache(CACHE_KEY, CHANNEL_ID, 0)).thenReturn(true);

    ChannelId channelId = storeIdService
        .findChannelIdById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY, ChannelId.class);
    verify(storeIdRepository)
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY, CHANNEL_ID, 0);

    assertEquals(CHANNEL_ID, channelId);
  }

  @Test
  public void findChannelIdByIdTestExceptionCampaignNotExist() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY, ChannelId.class))
        .thenReturn(null);
    when(storeIdRepository
        .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      storeIdService
          .findChannelIdById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY, ChannelId.class);
      verify(storeIdRepository)
          .findByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.CHANNEL_ID_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CHANNEL_ID_NOT_EXIST.getMessage(), be.getMessage());
    }
  }


  @Test
  public void findChannelIdTestSuccessWithoutCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(null);
    when(storeIdRepository.findByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0))
        .thenReturn(CHANNEL_ID_LIST);
    when(this.cacheService.createCache(CACHE_KEY_FIND, CHANNEL_ID_LIST, 0)).thenReturn(true);

    List<ChannelId> storeId = this.storeIdService
        .findChannelIds(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);
    verify(storeIdRepository).findByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0);
    verify(this.cacheService).createCache(CACHE_KEY_FIND, CHANNEL_ID_LIST, 0);

    assertEquals(storeId, CHANNEL_ID_LIST);
  }

  @Test
  public void findChannelIdTestSuccessWithCache() throws Exception {

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(CHANNEL_ID_LIST);

    List<ChannelId> storeId = this.storeIdService
        .findChannelIds(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);

    assertEquals(storeId, CHANNEL_ID_LIST);
  }

  @Test
  public void findChannelIdTestExceptionDataNotExist() throws Exception {

    List<ChannelId> storeIdList = new ArrayList<>();

    when(this.cacheService.findCacheByKey(CACHE_KEY_FIND, List.class)).thenReturn(null);
    when(storeIdRepository.findByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0))
        .thenReturn(storeIdList);

    try {
      this.storeIdService.findChannelIds(CommonTestVariable.MANDATORY_REQUEST).blockingGet();
    } catch (BusinessLogicException be) {
      verify(this.cacheService).findCacheByKey(CACHE_KEY_FIND, List.class);
      verify(storeIdRepository).findByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0);
      assertEquals(ResponseCode.CHANNEL_ID_LIST_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.CHANNEL_ID_LIST_NOT_EXIST.getMessage(), be.getMessage());
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
