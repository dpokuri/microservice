import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageMaxCostLessThanActualCostTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageNoPromoCodeUsageExistedTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageSuccessfulCaseTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.UsedPromoCodeTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
import com.tl.booking.promo.code.service.impl.CalculatePromoCodeServiceImpl;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateFormatterHelper.class})
public class CalculatePromoCodeServiceImplPositiveCaseTest {

  @InjectMocks
  CalculatePromoCodeServiceImpl calculatePromoCodeService;

  @Mock
  PromoCodeObjectService promoCodeObjectService;

  @Mock
  PromoCodeUsageService promoCodeUsageService;

  @Mock
  TimeZoneProperties timeZoneProperties;

  private void mockingDateFormatterHelper() {
    Date defaultDate = new Date();
    Date defaultDateNotMatched = new DateTime()
        .plusDays(120).toDate();

    when(timeZoneProperties.getOffsetToDate()).thenReturn(0);

    PowerMockito.when(DateFormatterHelper.stringToDate("2010-01-08", 0)).thenReturn(new DateTime()
        .plusDays(2010).toDate());
    PowerMockito.when(DateFormatterHelper.stringToDate("2000-02-08", 0)).thenReturn(new DateTime()
        .plusDays(2000).toDate());

    PowerMockito.when(DateFormatterHelper.stringToDate("2100-01-01", 0)).thenReturn(new DateTime()
        .plusDays(2100).toDate());

    PowerMockito.when(DateFormatterHelper.stringToDate("1990-02-09", 0)).thenReturn(new DateTime()
        .plusDays(1990).toDate());

    PowerMockito.when(DateFormatterHelper.stringToDate("1990-02-10", 0)).thenReturn(defaultDate);

    PowerMockito.when(DateFormatterHelper.stringToDate("1990-02-11", 0))
        .thenReturn(defaultDateNotMatched);

    PowerMockito.when(DateFormatterHelper.getTodayDateTime()).thenReturn(CommonTestVariable
        .TODAY_DATE);
  }

  @Test
  public void calculatePromoCodeTestSuccessfulCase() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageSuccessfulCaseTestVariable
            .START_DATE_1);

    mockingDateFormatterHelper();

    when(this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageSuccessfulCaseTestVariable.CODE)).thenReturn(Single.just(
        PromoCodeUsageSuccessfulCaseTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageSuccessfulCaseTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageSuccessfulCaseTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageSuccessfulCaseTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageSuccessfulCaseTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageSuccessfulCaseTestVariable.START_DATE_1,
            PromoCodeUsageSuccessfulCaseTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageSuccessfulCaseTestVariable.PROMO_CODE_USAGE_DAILY));

    CalculationResult calculatePromoCode =
        this.calculatePromoCodeService.calculatePromoCode(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageSuccessfulCaseTestVariable.CODE,
            PromoCodeUsageSuccessfulCaseTestVariable.ORDER_DETAILS,
            PromoCodeUsageSuccessfulCaseTestVariable.USED_PROMO_CODES,
            PromoCodeUsageSuccessfulCaseTestVariable.TOTAL_PRICE,
            PromoCodeUsageSuccessfulCaseTestVariable.PAYMENT_DTO,
            "456456",
            IgnoreValidation.QUANTITY).blockingGet();

    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageSuccessfulCaseTestVariable.CODE);
    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageSuccessfulCaseTestVariable.PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageSuccessfulCaseTestVariable
                .PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageSuccessfulCaseTestVariable.START_DATE_1,
            PromoCodeUsageSuccessfulCaseTestVariable
                .PROMO_CODE_OBJECT);

    assertEquals(PromoCodeUsageSuccessfulCaseTestVariable.EXPECTED_DISCOUNT_AMOUNT,
        calculatePromoCode
            .getOrderDetails().get(0).getDiscount());
    assertEquals(PromoCodeUsageSuccessfulCaseTestVariable.ORDER_DETAILS.get(0).getAmount(),
        calculatePromoCode
            .getOrderDetails().get(0).getAmount());
  }

  @Test
  public void calculatePromoCodeTestMaxDiscountLessThanActualDiscount() {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable
            .START_DATE_1);

    mockingDateFormatterHelper();

    when(this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single
            .just(PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable
                    .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.START_DATE_1,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.PROMO_CODE_USAGE_DAILY));

    CalculationResult calculatePromoCode = this.calculatePromoCodeService
        .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.CODE,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.ORDER_DETAILS,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.USED_PROMO_CODES,
            PromoCodeUsageSuccessfulCaseTestVariable.TOTAL_PRICE,
            PromoCodeUsageSuccessfulCaseTestVariable.PAYMENT_DTO,
            "456456", IgnoreValidation.QUANTITY)
        .blockingGet();

    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.CODE);
    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable
                    .PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.START_DATE_1,
            PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable
                .PROMO_CODE_OBJECT);

    assertEquals((Double) 10.00, calculatePromoCode
        .getOrderDetails().get(0).getDiscount());
    assertEquals(PromoCodeUsageMaxDiscountLessThanActualDiscountTestVariable.ORDER_DETAILS.get(0)
        .getAmount(), calculatePromoCode
        .getOrderDetails().get(0).getAmount());

  }

  @Test
  public void calculatePromoCodeTestMaxCostLessThanActualCost() {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageMaxCostLessThanActualCostTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.CODE)).thenReturn(Single.just(
        PromoCodeUsageMaxCostLessThanActualCostTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsageMaxCostLessThanActualCostTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageMaxCostLessThanActualCostTestVariable
                    .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageMaxCostLessThanActualCostTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.START_DATE_1,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single
            .just(PromoCodeUsageMaxCostLessThanActualCostTestVariable.PROMO_CODE_USAGE_DAILY));

    CalculationResult calculatePromoCode = this.calculatePromoCodeService
        .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.CODE,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.ORDER_DETAILS,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.USED_PROMO_CODES,
            PromoCodeUsageSuccessfulCaseTestVariable.TOTAL_PRICE,
            PromoCodeUsageSuccessfulCaseTestVariable.PAYMENT_DTO,
            "456456", IgnoreValidation.QUANTITY)
        .blockingGet();

    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageMaxCostLessThanActualCostTestVariable.CODE);
    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageMaxCostLessThanActualCostTestVariable
                    .PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable.START_DATE_1,
            PromoCodeUsageMaxCostLessThanActualCostTestVariable
                .PROMO_CODE_OBJECT);

    assertEquals((Double) 10.00, calculatePromoCode
        .getOrderDetails().get(0).getPartnerCosts().get(0).getAmount());
    assertEquals((Double) 10.00, calculatePromoCode
        .getOrderDetails().get(0).getCompanyCosts().get(0).getAmount());
    assertEquals(
        PromoCodeUsageMaxCostLessThanActualCostTestVariable.ORDER_DETAILS.get(0).getAmount(),
        calculatePromoCode
            .getOrderDetails().get(0).getAmount());

  }

  @Test
  public void calculatePromoCodeTestNoPromoCodeUsageExisted() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageNoPromoCodeUsageExistedTestVariable
            .START_DATE_1);

    mockingDateFormatterHelper();
    when(this.promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.CODE)).thenReturn(Single.just(
        PromoCodeUsageNoPromoCodeUsageExistedTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageNoPromoCodeUsageExistedTestVariable
            .PROMO_CODE_USAGE_NO_COUNT));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageNoPromoCodeUsageExistedTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageNoPromoCodeUsageExistedTestVariable.PROMO_CODE_USAGE_GENERAL_NO_COUNT));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.START_DATE_1,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageNoPromoCodeUsageExistedTestVariable
            .PROMO_CODE_USAGE_DAILY_NO_COUNT));

    CalculationResult calculatePromoCode = this.calculatePromoCodeService
        .calculatePromoCode(CommonTestVariable
                .MANDATORY_REQUEST, PromoCodeUsageNoPromoCodeUsageExistedTestVariable.CODE,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.ORDER_DETAILS,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.USED_PROMO_CODES,
            PromoCodeUsageSuccessfulCaseTestVariable.TOTAL_PRICE,
            PromoCodeUsageSuccessfulCaseTestVariable.PAYMENT_DTO,
            "456456", IgnoreValidation.QUANTITY)
        .blockingGet();

    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageNoPromoCodeUsageExistedTestVariable.CODE);
    verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageNoPromoCodeUsageExistedTestVariable
                .PROMO_CODE_OBJECT);

    verify(this.promoCodeUsageService)
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable.START_DATE_1,
            PromoCodeUsageNoPromoCodeUsageExistedTestVariable
                .PROMO_CODE_OBJECT);

    assertEquals(PromoCodeUsageSuccessfulCaseTestVariable.EXPECTED_DISCOUNT_AMOUNT,
        calculatePromoCode
            .getOrderDetails().get(0).getDiscount());
    assertEquals(PromoCodeUsageSuccessfulCaseTestVariable.ORDER_DETAILS.get(0).getAmount(),
        calculatePromoCode
            .getOrderDetails().get(0).getAmount());
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    ReflectionTestUtils
        .setField(calculatePromoCodeService, "schedulerPromoCode", Schedulers.io
            ());
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeUsageService);
    verifyNoMoreInteractions(this.promoCodeObjectService);
  }
}
