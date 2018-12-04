import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.BusinessLogicResponseRepository;
import com.tl.booking.promo.code.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.BusinessLogicResponseTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponseBuilder;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.impl.BusinessLogicResponseServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;

public class BusinessLogicResponseServiceImplTest extends BusinessLogicResponseTestVariable {

  @InjectMocks
  BusinessLogicResponseServiceImpl businessLogicResponseServiceImpl;
  @Mock
  BusinessLogicResponseRepository businessLogicResponseRepository;

  @Mock
  CacheService cacheService;

  @Test
  public void createBusinessLogicResponseTestSuccess() throws Exception {
    when(this.businessLogicResponseRepository
        .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(RESPONSE_CODE
            , CommonTestVariable.STORE_ID, 0)).thenReturn(null);

    when(businessLogicResponseRepository
        .save(BUSINESS_LOGIC_RESPONSE)).thenReturn(BUSINESS_LOGIC_RESPONSE);

    when(cacheService.createCache(CACHE_KEY_EN, BUSINESS_LOGIC_RESPONSE, 600)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY_ID, BUSINESS_LOGIC_RESPONSE, 600)).thenReturn(true);

    BusinessLogicResponse businessLogicResponse = businessLogicResponseServiceImpl
        .createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BUSINESS_LOGIC_RESPONSE)
        .blockingGet();

    verify(businessLogicResponseRepository)
        .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(RESPONSE_CODE
            , CommonTestVariable.STORE_ID, 0);

    verify(businessLogicResponseRepository).save(BUSINESS_LOGIC_RESPONSE);

    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY_EN);
    verify(cacheService).deleteCache(CACHE_KEY_ID);

    assertEquals(BUSINESS_LOGIC_RESPONSE, businessLogicResponse);
  }

  @Test
  public void createBusinessLogicResponseTestErrorExistBusinessLogicResponse() throws Exception {
    when(businessLogicResponseRepository
        .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(
            BUSINESS_LOGIC_RESPONSE.getResponseCode(),
            CommonTestVariable.STORE_ID, 0))
        .thenReturn(BUSINESS_LOGIC_RESPONSE);

    try {
      businessLogicResponseServiceImpl
          .createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
              BUSINESS_LOGIC_RESPONSE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(businessLogicResponseRepository)
          .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(RESPONSE_CODE,
              CommonTestVariable.STORE_ID, 0);

      assertEquals(ResponseCode.DUPLICATE_DATA.getCode(), be.getCode());
      assertEquals(ResponseCode.DUPLICATE_DATA.getMessage(), be.getMessage());
    }

  }

  @Test
  public void createBusinessLogicResponseTestSystemError() throws Exception {

    when(businessLogicResponseRepository
        .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(
            RESPONSE_CODE, CommonTestVariable.STORE_ID, 0))
        .thenReturn(null);

    when(businessLogicResponseRepository
        .save(BUSINESS_LOGIC_RESPONSE)).thenReturn(null);
    try {
      businessLogicResponseServiceImpl
          .createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
              BUSINESS_LOGIC_RESPONSE)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(businessLogicResponseRepository)
          .findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(RESPONSE_CODE,
              CommonTestVariable.STORE_ID, 0);
      verify(businessLogicResponseRepository).save(BUSINESS_LOGIC_RESPONSE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void updateBusinessLogicResponseExceptionTestSuccess() throws Exception {
    when(businessLogicResponseRepository.findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(BUSINESS_LOGIC_RESPONSE);

    when(businessLogicResponseRepository
        .save(BUSINESS_LOGIC_RESPONSE)).thenReturn(BUSINESS_LOGIC_RESPONSE);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY, BUSINESS_LOGIC_RESPONSE, 0)).thenReturn(true);

    BusinessLogicResponse businessLogicResponse = businessLogicResponseServiceImpl
        .updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BUSINESS_LOGIC_RESPONSE,
            ID).blockingGet();
    verify(businessLogicResponseRepository).findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0);
    verify(businessLogicResponseRepository).save(BUSINESS_LOGIC_RESPONSE);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY2);
    verify(cacheService).deleteCache(CACHE_KEY_ID);
    verify(cacheService).deleteCache(CACHE_KEY_EN);

    assertEquals(BUSINESS_LOGIC_RESPONSE, businessLogicResponse);
  }

  @Test
  public void updateBusinessLogicResponseTestErrordataNotExist() throws Exception {
    when(businessLogicResponseRepository.findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(
        CommonTestVariable.STORE_ID, ID, 0)).thenReturn(null);
    try {
      businessLogicResponseServiceImpl
          .updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
              BUSINESS_LOGIC_RESPONSE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(businessLogicResponseRepository).findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(
          CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void updateBusinessLogicResponseTestExceptionSystemError() throws Exception {
    when(businessLogicResponseRepository
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BUSINESS_LOGIC_RESPONSE);

    when(businessLogicResponseRepository
        .save(BUSINESS_LOGIC_RESPONSE)).thenReturn(null);

    try {
      businessLogicResponseServiceImpl
          .updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
              BUSINESS_LOGIC_RESPONSE, ID)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(businessLogicResponseRepository)
          .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(businessLogicResponseRepository).save(BUSINESS_LOGIC_RESPONSE);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }

  }

  @Test
  public void deleteBusinessLogicResponseTestSuccess() throws Exception {
    BUSINESS_LOGIC_RESPONSE.setIsDeleted(1);

    when(businessLogicResponseRepository
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BUSINESS_LOGIC_RESPONSE);

    when(businessLogicResponseRepository.softDeleted(BUSINESS_LOGIC_RESPONSE, ID)).thenReturn(true);

    when(cacheService.deleteCache(CACHE_KEY_FIND_ALL)).thenReturn(true);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);

    Boolean businessLogicResponse = businessLogicResponseServiceImpl
        .deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(businessLogicResponseRepository)
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(businessLogicResponseRepository).softDeleted(BUSINESS_LOGIC_RESPONSE, ID);
    verify(cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService).deleteCache(CACHE_KEY_EN);
    verify(cacheService).deleteCache(CACHE_KEY_ID);
    verify(cacheService).deleteCache(CACHE_KEY2);

    assertEquals(true, businessLogicResponse);
  }

  @Test
  public void deleteBusinessLogicResponseTestDataNotExist() throws Exception {

    when(businessLogicResponseRepository
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);
    try {
      businessLogicResponseServiceImpl
          .deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(businessLogicResponseRepository)
          .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void deleteBusinessLogicResponseTestExceptionSystemError() throws Exception {

    when(businessLogicResponseRepository
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BUSINESS_LOGIC_RESPONSE);

    when(businessLogicResponseRepository.softDeleted(BUSINESS_LOGIC_RESPONSE, ID))
        .thenReturn(false);

    try {
      businessLogicResponseServiceImpl
          .deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(businessLogicResponseRepository)
          .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
      verify(businessLogicResponseRepository).softDeleted(BUSINESS_LOGIC_RESPONSE, ID);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findBusinessLogicResponseByIdTestCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY2, BusinessLogicResponse.class))
        .thenReturn(BUSINESS_LOGIC_RESPONSE);

    BusinessLogicResponse businessLogicResponse = businessLogicResponseServiceImpl
        .findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY2, BusinessLogicResponse.class);

    assertEquals(BUSINESS_LOGIC_RESPONSE, businessLogicResponse);
  }

  @Test
  public void findBusinessLogicResponseByIdTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY2, BusinessLogicResponse.class))
        .thenReturn(null);
    when(businessLogicResponseRepository
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(BUSINESS_LOGIC_RESPONSE);
    when(cacheService.createCache(CACHE_KEY2, BUSINESS_LOGIC_RESPONSE, 0)).thenReturn(true);

    BusinessLogicResponse businessLogicResponse = businessLogicResponseServiceImpl
        .findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY2, BusinessLogicResponse.class);
    verify(businessLogicResponseRepository)
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);
    verify(cacheService).createCache(CACHE_KEY2, BUSINESS_LOGIC_RESPONSE, 0);

    assertEquals(BUSINESS_LOGIC_RESPONSE, businessLogicResponse);
  }

  @Test
  public void findBusinessLogicResponseByIdTestExceptionBusinessLogicResponseNotExist()
      throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY2, BusinessLogicResponse.class))
        .thenReturn(null);
    when(businessLogicResponseRepository
        .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0))
        .thenReturn(null);

    try {
      businessLogicResponseServiceImpl
          .findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (BusinessLogicException be) {
      verify(cacheService).findCacheByKey(CACHE_KEY2, BusinessLogicResponse.class);
      verify(businessLogicResponseRepository)
          .findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(CommonTestVariable.STORE_ID, ID, 0);

      assertEquals(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.BUSINESS_LOGIC_RESPONSE_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void findAllBusinessLogicResponseTestNonCacheResult() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_FIND_ALL, List.class))
        .thenReturn(null);
    when(businessLogicResponseRepository.findByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0))
        .thenReturn(BUSINESS_LOGIC_RESPONSE_LIST_IS_DELETED);
    when(cacheService.deleteCache(CACHE_KEY)).thenReturn(true);
    when(cacheService.createCache(CACHE_KEY_FIND_ALL, BUSINESS_LOGIC_RESPONSE_LIST_IS_DELETED, 0))
        .thenReturn(true);

    List<BusinessLogicResponse> businessLogicResponses = businessLogicResponseServiceImpl
        .findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    verify(cacheService).findCacheByKey(CACHE_KEY_FIND_ALL, List.class);
    verify(businessLogicResponseRepository)
        .findByStoreIdAndIsDeleted(CommonTestVariable.STORE_ID, 0);
    verify(this.cacheService).deleteCache(CACHE_KEY_FIND_ALL);
    verify(cacheService)
        .createCache(CACHE_KEY_FIND_ALL, BUSINESS_LOGIC_RESPONSE_LIST_IS_DELETED, 0);

    assertEquals(BUSINESS_LOGIC_RESPONSE_LIST_IS_DELETED, businessLogicResponses);
  }

  @Test
  public void findMessageByResponseCodeAndLanguageCacheOnlyTest() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_EN, String.class))
        .thenReturn(MESSAGE);

    String message = businessLogicResponseServiceImpl
        .findMessageByResponseCodeAndLanguage(CommonTestVariable.STORE_ID, RESPONSE_CODE, LANG);

    verify(cacheService).findCacheByKey(CACHE_KEY_EN, String.class);

    assertEquals(MESSAGE, message);
  }

  @Test
  public void findMessageByResponseCodeAndLanguageTest() throws Exception {
    when(cacheService.findCacheByKey(CACHE_KEY_ID, String.class))
        .thenReturn(null);
    when(cacheService.findCacheByKey(CACHE_KEY_EN, String.class))
        .thenReturn(null);
    when(businessLogicResponseRepository.findByResponseCodeAndStoreIdAndIsDeleted(RESPONSE_CODE,
        CommonTestVariable.STORE_ID, 0)).thenReturn(BUSINESS_LOGIC_RESPONSE);
    when(cacheService.deleteCache(CACHE_KEY_EN)).thenReturn(true);
    when(this.cacheService.createCache(CACHE_KEY, MESSAGE, 0))
        .thenReturn(true);

    String message = businessLogicResponseServiceImpl
        .findMessageByResponseCodeAndLanguage(CommonTestVariable.STORE_ID, RESPONSE_CODE, LANG);

    verify(businessLogicResponseRepository).findByResponseCodeAndStoreIdAndIsDeleted
        (RESPONSE_CODE, CommonTestVariable.STORE_ID, 0);
    verify(this.cacheService).deleteCache(CACHE_KEY_EN);
    verify(this.cacheService).createCache(CACHE_KEY_EN, MESSAGE, 600);
    verify(cacheService).findCacheByKey(CACHE_KEY_EN, String.class);

    assertEquals(MESSAGE, message);
  }

  @Test
  public void findBusinessLogicResponsePaginatedTestSuccess() throws Exception {
    when(businessLogicResponseRepository
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.STORE_ID, RESPONSE_CODE, PAGE,
            SIZE, BusinessLogicResponseColumn.ID.getValue(), SortDirection.ASC))
        .thenReturn(BUSINESS_LOGIC_RESPONSE_PAGE);

    Page<BusinessLogicResponse> businessLogicResponsePage = businessLogicResponseServiceImpl
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            RESPONSE_CODE, PAGE, SIZE, BusinessLogicResponseColumn.ID,
            SortDirection.ASC).blockingGet();

    verify(businessLogicResponseRepository)
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.STORE_ID, RESPONSE_CODE, PAGE,
            SIZE, BusinessLogicResponseColumn.ID.getValue(), SortDirection.ASC);

    assertEquals(BUSINESS_LOGIC_RESPONSE_PAGE, businessLogicResponsePage);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    RESPONSE_MESSAGES.add(RESPONSE_MESSAGE);

    BUSINESS_LOGIC_RESPONSE = new BusinessLogicResponseBuilder()
        .withResponseCode(RESPONSE_CODE)
        .withResponseMessage(RESPONSE_MESSAGES)
        .build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(businessLogicResponseRepository);
    verifyNoMoreInteractions(cacheService);
  }
}
