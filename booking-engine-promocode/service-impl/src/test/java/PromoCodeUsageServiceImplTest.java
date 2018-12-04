import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepository;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageFirstPromoCodeCannotCombineTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeObjectTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeUsageServiceTestVariable;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import com.tl.booking.promo.code.entity.dao.SystemParameter;
import com.tl.booking.promo.code.entity.dao.SystemParameterBuilder;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.utility.PeriodHelper;
import com.tl.booking.promo.code.service.api.CacheService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.SystemParameterService;
import com.tl.booking.promo.code.service.impl.PromoCodeUsageServiceImpl;
import io.reactivex.Single;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PeriodHelper.class})
public class PromoCodeUsageServiceImplTest extends PromoCodeUsageServiceTestVariable {

  @InjectMocks
  PromoCodeUsageServiceImpl promoCodeUsageService;

  @Mock
  PromoCodeUsageRepository promoCodeUsageRepository;

  @Mock
  PromoCodeObjectService promoCodeObjectService;

  @Mock
  SystemParameterService systemParameterService;

  @Mock
  CacheService cacheService;

  @Mock
  TimeZoneProperties timeZoneProperties;

  private String CACHE_KEY_QUOTA = CacheKey.CURRENT_QUOTA + "-" + PromoCodeObjectTestVariable.ID;

  @Test
  public void findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndUsernameTestSuccess()
      throws Exception {

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            START_DATE, END_DATE,"*",
            CommonTestVariable.USERNAME)).thenReturn
        (PROMO_CODE_USAGE);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_OBJECT).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            START_DATE, END_DATE,"*",
            CommonTestVariable.USERNAME);

    assertEquals(PROMO_CODE_USAGE, promoCodeUsage);

  }

  @Test
  public void findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndUsernameTestDataNotExist()
      throws Exception {
    PROMO_CODE_OBJECT_NOT_FOUND.getPromoCode().setId(PROMOCODE_ID_2);

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            START_DATE, END_DATE,"*",
            CommonTestVariable.USERNAME)).thenReturn(null);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_OBJECT_NOT_FOUND).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            START_DATE, END_DATE,"*",
            CommonTestVariable.USERNAME);

    assertEquals(PROMO_CODE_USAGE_NULL, promoCodeUsage);

  }

  @Test
  public void findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndUsernameAndTodayDateTestSuccess()
      throws Exception {

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            TO_DAY, TO_DAY,"*",
            CommonTestVariable.USERNAME)).thenReturn(PROMO_CODE_USAGE);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST, TO_DAY, PROMO_CODE_OBJECT).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            TO_DAY, TO_DAY,"*",
            CommonTestVariable.USERNAME);

    assertEquals(PROMO_CODE_USAGE, promoCodeUsage);
  }

  @Test
  public void findPromoCodeUsageGeneralByPromoCodeObjectAndStoreIdAndChannelIdAndUsernameTestSuccess()
      throws Exception {
    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            START_DATE, END_DATE,"*",
            "*")).thenReturn(PROMO_CODE_USAGE);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername(
            MANDATORY_REQUEST_2, PROMO_CODE_OBJECT).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMOCODE_ID,
            START_DATE, END_DATE,"*",
            "*");

    assertEquals(PROMO_CODE_USAGE, promoCodeUsage);
  }

  @Test
  public void findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndCardNumberTestSuccess()
      throws Exception {

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            START_DATE, END_DATE,
            CREDIT_CARD, "*")).thenReturn(PROMO_CODE_USAGE);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndCardNumber(
            MANDATORY_REQUEST_2, PROMO_CODE_OBJECT, CREDIT_CARD).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            START_DATE, END_DATE, 
            CREDIT_CARD, "*");

    assertEquals(PROMO_CODE_USAGE, promoCodeUsage);
  }


  @Test
  public void
  findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndCardNumberTestSuccessGetPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndStoreIdAndChannelIdAndCardNumberIsNull()
      throws Exception {

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            START_DATE, END_DATE, 
            CREDIT_CARD, "*")).thenReturn(null);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndCardNumber(
            MANDATORY_REQUEST_2, PROMO_CODE_OBJECT, CREDIT_CARD).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            START_DATE, END_DATE, 
            CREDIT_CARD, "*");

    assertEquals(PROMO_CODE_USAGE_NULL, promoCodeUsage);
  }


  @Test
  public void findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndCardNameAndTodayDateTEstSuccess()
      throws Exception {

    Date dateNow = new Date();

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            dateNow, dateNow, 
            CREDIT_CARD, "*")).thenReturn(PROMO_CODE_USAGE);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndCardNameAndTodayDate(
            MANDATORY_REQUEST_2, dateNow, PROMO_CODE_OBJECT, CREDIT_CARD).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            dateNow, dateNow, 
            CREDIT_CARD, "*");

    assertEquals(PROMO_CODE_USAGE, promoCodeUsage);
  }

  @Test
  public void
  findPromoCodeUsageByPromoCodeObjectAndStoreIdAndChannelIdAndCardNameAndTodayDateTEstSuccessNullDatabase()
      throws Exception {

    Date dateNow = new Date();

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            dateNow, dateNow, 
            CREDIT_CARD, "*")).thenReturn(PROMO_CODE_USAGE_NULL);

    PromoCodeUsage promoCodeUsage = promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndCardNameAndTodayDate(
            MANDATORY_REQUEST_2, dateNow, PROMO_CODE_OBJECT, CREDIT_CARD).blockingGet();

    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PROMO_CODE_OBJECT.getPromoCode().getId(),
            dateNow, dateNow, 
            CREDIT_CARD, "*");

    assertEquals(PROMO_CODE_USAGE_NULL, promoCodeUsage);
  }

  @Test
  public void findCurrentQuotaByPromoCodeCachedTest() throws Exception {
    PowerMockito.mockStatic(PeriodHelper.class);
    PowerMockito.when(PeriodHelper.getStartDate(PromoCodeObjectTestVariable.PROMO_CODE_OBJECT, 7))
        .thenReturn(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.START_DATE);
    PowerMockito.when(PeriodHelper.getEndDate(PromoCodeObjectTestVariable.PROMO_CODE_OBJECT, 7))
        .thenReturn(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.END_DATE);

    when(promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeObjectTestVariable.CODE)).thenReturn
        (Single.just(PromoCodeObjectTestVariable.PROMO_CODE_OBJECT));

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeObjectTestVariable.ID,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.START_DATE,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.END_DATE,
            "*", "*"
        )).thenReturn(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE);

    when(this.cacheService.findCacheByKey(CACHE_KEY_QUOTA, Integer.class)).thenReturn(90);

    SystemParameter systemParameter = new SystemParameterBuilder()
        .withValue("5")
        .withVariable("CHECK_QUOTA_CACHE_TIMEOUT")
        .build();
    when(this.systemParameterService.findSystemParameterByStoreId(CommonTestVariable.STORE_ID, "CHECK_QUOTA_CACHE_TIMEOUT"))
        .thenReturn
            (systemParameter);

    Integer count = this.promoCodeUsageService.findCurrentQuotaByPromoCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeObjectTestVariable.CODE).blockingGet();
    Integer expectedCount = 90;

    verify(promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeObjectTestVariable.CODE);

    verify(this.systemParameterService).findSystemParameterByStoreId(CommonTestVariable.STORE_ID,
        "CHECK_QUOTA_CACHE_TIMEOUT");
    verify(this.cacheService).findCacheByKey(CACHE_KEY_QUOTA, Integer.class);

    assertEquals(expectedCount,count);
  }

  @Test
  public void findCurrentQuotaByPromoCodeNoUsedTest() throws Exception {
    PowerMockito.mockStatic(PeriodHelper.class);
    PowerMockito.when(PeriodHelper.getStartDate(PromoCodeObjectTestVariable.PROMO_CODE_OBJECT, 7))
        .thenReturn(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.START_DATE);
    PowerMockito.when(PeriodHelper.getEndDate(PromoCodeObjectTestVariable.PROMO_CODE_OBJECT, 7))
        .thenReturn(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.END_DATE);

    when(promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeObjectTestVariable.CODE)).thenReturn
        (Single.just(PromoCodeObjectTestVariable.PROMO_CODE_OBJECT));

    when(this.cacheService.findCacheByKey(CACHE_KEY_QUOTA, Integer.class)).thenReturn(null);

    SystemParameter systemParameter = new SystemParameterBuilder()
        .withValue("5")
        .withVariable("CHECK_QUOTA_CACHE_TIMEOUT")
        .build();
    when(this.systemParameterService.findSystemParameterByStoreId(CommonTestVariable.STORE_ID, "CHECK_QUOTA_CACHE_TIMEOUT"))
        .thenReturn
        (systemParameter);

    when(promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeObjectTestVariable.ID,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.START_DATE,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.END_DATE,
            "*","*"
        )).thenReturn(null);

    Integer count = this.promoCodeUsageService.findCurrentQuotaByPromoCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeObjectTestVariable.CODE).blockingGet();
    Integer expectedCount = 100;

    verify(promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeObjectTestVariable.CODE);
    verify(promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeObjectTestVariable.ID,
            null,
            null,
            "*","*"
        );

    verify(this.systemParameterService).findSystemParameterByStoreId(CommonTestVariable.STORE_ID,
        "CHECK_QUOTA_CACHE_TIMEOUT");
    verify(this.cacheService).findCacheByKey(CACHE_KEY_QUOTA, Integer.class);
    verify(cacheService).createCache(CACHE_KEY_QUOTA, expectedCount, 5);

    assertEquals(expectedCount,count);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeUsageRepository);
    verifyNoMoreInteractions(cacheService);
    verifyNoMoreInteractions(promoCodeObjectService);
    verifyNoMoreInteractions(systemParameterService);
  }

}
