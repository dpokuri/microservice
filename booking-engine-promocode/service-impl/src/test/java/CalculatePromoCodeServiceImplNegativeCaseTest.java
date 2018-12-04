import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageFirstPromoCodeCannotCombineTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageInvalidChannelIdTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageInvalidStoreIdTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageNotActiveTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageNotValidUsageRulePeriodTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageNotValidUsageRuleTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsagePeriodNotValidTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsagePromoCodeNotAvailableTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.PromoCodeUsageUsedPromoCodeCannotCombineTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.UsedPromoCodeCannotCombineTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.UsedPromoCodeTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
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
public class CalculatePromoCodeServiceImplNegativeCaseTest {

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
        .plusDays(12).toDate();

    when(timeZoneProperties.getOffsetToDate()).thenReturn(0);

    PowerMockito.when(DateFormatterHelper.stringToDate("2010-01-08", 0)).thenReturn(new DateTime()
        .plusDays(201).toDate());
    PowerMockito.when(DateFormatterHelper.stringToDate("2000-02-08", 0)).thenReturn(new DateTime()
        .plusDays(200).toDate());

    PowerMockito.when(DateFormatterHelper.stringToDate("2100-01-01", 0)).thenReturn(new DateTime()
        .plusDays(210).toDate());

    PowerMockito.when(DateFormatterHelper.stringToDate("1990-02-09", 0)).thenReturn(new DateTime()
        .plusDays(199).toDate());

    PowerMockito.when(DateFormatterHelper.stringToDate("1990-02-10", 0)).thenReturn(defaultDate);

    PowerMockito.when(DateFormatterHelper.stringToDate("1990-02-11", 0))
        .thenReturn(defaultDateNotMatched);

    PowerMockito.when(DateFormatterHelper.getTodayDateTime()).thenReturn(CommonTestVariable
        .TODAY_DATE);
  }

  @Test
  public void calculatePromoCodeTestFirstPromoCodeCannotCombine() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageFirstPromoCodeCannotCombineTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageFirstPromoCodeCannotCombineTestVariable
                    .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.START_DATE_1,
            PromoCodeUsageFirstPromoCodeCannotCombineTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single
            .just(PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService.calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
          PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.CODE,
          PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.ORDER_DETAILS,
          PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.USED_PROMO_CODES,
          PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.TOTAL_PRICE,
          PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.PAYMENT_DTO,
          "456456",
          IgnoreValidation.QUANTITY)
          .blockingGet();

    } catch (BusinessLogicException ble) {
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsageFirstPromoCodeCannotCombineTestVariable.CODE);

      assertEquals(ResponseCode.CANNOT_COMBINE.getCode(), ble.getCode());
      assertEquals(ResponseCode.CANNOT_COMBINE.getMessage(), ble.getMessage());
    }
  }

  @Test
  public void calculatePromoCodeTestUsedPromoCodeCannotCombine() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageUsedPromoCodeCannotCombineTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeCannotCombineTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeCannotCombineTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageUsedPromoCodeCannotCombineTestVariable
                    .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.START_DATE_1,
            PromoCodeUsageUsedPromoCodeCannotCombineTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single
            .just(PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService.calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
          PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.CODE,
          PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.ORDER_DETAILS,
          PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.USED_PROMO_CODES,
          PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.TOTAL_PRICE,
          PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PAYMENT_DTO,
          "456456",IgnoreValidation
              .QUANTITY)
          .blockingGet();

    } catch (BusinessLogicException ble) {
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.CODE);
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, UsedPromoCodeCannotCombineTestVariable.CODE);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsername(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
              (CommonTestVariable.MANDATORY_REQUEST,
                  PromoCodeUsageUsedPromoCodeCannotCombineTestVariable
                      .PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageUsedPromoCodeCannotCombineTestVariable.START_DATE_1,
              PromoCodeUsageUsedPromoCodeCannotCombineTestVariable
                  .PROMO_CODE_OBJECT);

      assertEquals(ResponseCode.USED_PROMO_CODE_CANNOT_COMBINE.getCode(), ble.getCode());
      assertEquals(ResponseCode.USED_PROMO_CODE_CANNOT_COMBINE.getMessage(), ble.getMessage());
    }
  }

  @Test
  public void calculatePromoCodeTestNotValidUsageRule() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageNotValidUsageRuleTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageNotValidUsageRuleTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (
                CommonTestVariable.MANDATORY_REQUEST,
                PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(
            PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_USAGE_GENERAL
        ));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNotValidUsageRuleTestVariable.START_DATE_1,
            PromoCodeUsageNotValidUsageRuleTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService.calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
          PromoCodeUsageNotValidUsageRuleTestVariable.CODE,
          PromoCodeUsageNotValidUsageRuleTestVariable.ORDER_DETAILS,
          PromoCodeUsageNotValidUsageRuleTestVariable.USED_PROMO_CODES,
          PromoCodeUsageNotValidUsageRuleTestVariable.TOTAL_PRICE,
          PromoCodeUsageNotValidUsageRuleTestVariable.PAYMENT_DTO,
          "456456",
          IgnoreValidation.NONE).blockingGet();

    } catch (BusinessLogicException ble) {
      assertEquals(ResponseCode.NOT_VALID_USAGE_RULE.getCode(), ble.getCode());
      assertEquals(ResponseCode.NOT_VALID_USAGE_RULE.getMessage(), ble.getMessage());

      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode
          (CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeUsageNotValidUsageRuleTestVariable.CODE);
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsername(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageNotValidUsageRuleTestVariable.PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
              (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageNotValidUsageRuleTestVariable
                  .PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageNotValidUsageRuleTestVariable.START_DATE_1,
              PromoCodeUsageNotValidUsageRuleTestVariable
                  .PROMO_CODE_OBJECT);
    }
  }

  @Test
  public void calculatePromoCodeTestNotValidUsageRulePeriod() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageNotValidUsageRulePeriodTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageNotValidUsageRulePeriodTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsageNotValidUsageRulePeriodTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNotValidUsageRulePeriodTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsageNotValidUsageRulePeriodTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageNotValidUsageRulePeriodTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageNotValidUsageRulePeriodTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNotValidUsageRulePeriodTestVariable.START_DATE_1,
            PromoCodeUsageNotValidUsageRulePeriodTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsageNotValidUsageRulePeriodTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService.calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
          PromoCodeUsageNotValidUsageRulePeriodTestVariable.CODE,
          PromoCodeUsageNotValidUsageRulePeriodTestVariable.ORDER_DETAILS,
          PromoCodeUsageNotValidUsageRulePeriodTestVariable.USED_PROMO_CODES,
          PromoCodeUsageNotValidUsageRulePeriodTestVariable.TOTAL_PRICE,
          PromoCodeUsageNotValidUsageRulePeriodTestVariable.PAYMENT_DTO,
          "456456",
          IgnoreValidation.NONE).blockingGet();

    } catch (BusinessLogicException ble) {
      assertEquals(ResponseCode.NOT_VALID_USAGE_RULE.getCode(), ble.getCode());
      assertEquals(ResponseCode.NOT_VALID_USAGE_RULE.getMessage(), ble.getMessage());

      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsageNotValidUsageRulePeriodTestVariable.CODE);

      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsername(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageNotValidUsageRulePeriodTestVariable.PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
              (CommonTestVariable.MANDATORY_REQUEST,
                  PromoCodeUsageNotValidUsageRulePeriodTestVariable
                      .PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageNotValidUsageRulePeriodTestVariable.START_DATE_1,
              PromoCodeUsageNotValidUsageRulePeriodTestVariable
                  .PROMO_CODE_OBJECT);
    }
  }

  @Test
  public void calculatePromoCodeTestPromoCodeNotAvailable() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsagePromoCodeNotAvailableTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsagePromoCodeNotAvailableTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsagePromoCodeNotAvailableTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsagePromoCodeNotAvailableTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsagePromoCodeNotAvailableTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsagePromoCodeNotAvailableTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsagePromoCodeNotAvailableTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsagePromoCodeNotAvailableTestVariable.START_DATE_1,
            PromoCodeUsagePromoCodeNotAvailableTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(
            Single.just(PromoCodeUsagePromoCodeNotAvailableTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService.calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
          PromoCodeUsagePromoCodeNotAvailableTestVariable.CODE,
          PromoCodeUsagePromoCodeNotAvailableTestVariable.ORDER_DETAILS,
          PromoCodeUsagePromoCodeNotAvailableTestVariable.USED_PROMO_CODES,
          PromoCodeUsagePromoCodeNotAvailableTestVariable.TOTAL_PRICE,
          PromoCodeUsagePromoCodeNotAvailableTestVariable.PAYMENT_DTO,
          "456456",IgnoreValidation
              .NONE).blockingGet();

    } catch (BusinessLogicException ble) {
      assertEquals(ResponseCode.PROMO_CODE_NOT_AVAILABLE.getCode(), ble.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_AVAILABLE.getMessage(), ble.getMessage());

      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsagePromoCodeNotAvailableTestVariable.CODE);
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsername(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsagePromoCodeNotAvailableTestVariable.PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
              (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsagePromoCodeNotAvailableTestVariable
                  .PROMO_CODE_OBJECT);

      verify(this.promoCodeUsageService)
          .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
              CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsagePromoCodeNotAvailableTestVariable.START_DATE_1,
              PromoCodeUsagePromoCodeNotAvailableTestVariable
                  .PROMO_CODE_OBJECT);
    }
  }

  @Test
  public void calculatePromoCodeTestPromoCodeNotActive() {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageNotActiveTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageNotActiveTestVariable.CODE)).thenReturn(Single.just(
        PromoCodeUsageNotActiveTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageNotActiveTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageNotActiveTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageNotActiveTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageNotActiveTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageNotActiveTestVariable.START_DATE_1,
            PromoCodeUsageNotActiveTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageNotActiveTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService
          .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageNotActiveTestVariable.CODE,
              PromoCodeUsageNotActiveTestVariable.ORDER_DETAILS,
              PromoCodeUsageNotActiveTestVariable.USED_PROMO_CODES,
              PromoCodeUsageNotActiveTestVariable.TOTAL_PRICE,
              PromoCodeUsageNotActiveTestVariable.PAYMENT_DTO,
              "456456",IgnoreValidation
                  .QUANTITY)
          .blockingGet();
    } catch (BusinessLogicException ble) {
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsageNotActiveTestVariable.CODE);
      assertEquals(ResponseCode.PROMO_CODE_NOT_ACTIVE.getCode(), ble.getCode());
      assertEquals(ResponseCode.PROMO_CODE_NOT_ACTIVE.getMessage(), ble.getMessage());
    }

  }

  @Test
  public void calculatePromoCodeTestStoreIdNotValid() {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageInvalidStoreIdTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageInvalidStoreIdTestVariable.CODE)).thenReturn(Single.just(
        PromoCodeUsageInvalidStoreIdTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageInvalidStoreIdTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageInvalidStoreIdTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageInvalidStoreIdTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageInvalidStoreIdTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageInvalidStoreIdTestVariable.START_DATE_1,
            PromoCodeUsageInvalidStoreIdTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageInvalidStoreIdTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService
          .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageInvalidStoreIdTestVariable.CODE,
              PromoCodeUsageInvalidStoreIdTestVariable.ORDER_DETAILS,
              PromoCodeUsageInvalidStoreIdTestVariable.USED_PROMO_CODES,
              PromoCodeUsageInvalidStoreIdTestVariable.TOTAL_PRICE,
              PromoCodeUsageInvalidStoreIdTestVariable.PAYMENT_DTO,
              "456456",IgnoreValidation
                  .QUANTITY)
          .blockingGet();
    } catch (BusinessLogicException ble) {
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsageInvalidStoreIdTestVariable.CODE);

      assertEquals(ResponseCode.INVALID_STORE_ID.getCode(), ble.getCode());
      assertEquals(ResponseCode.INVALID_STORE_ID.getMessage(), ble.getMessage());
    }

  }

  @Test
  public void calculatePromoCodeTestChannelIdNotValid() {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageInvalidChannelIdTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageInvalidChannelIdTestVariable.CODE))
        .thenReturn(Single.just(
            PromoCodeUsageInvalidChannelIdTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageInvalidChannelIdTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageInvalidChannelIdTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageInvalidChannelIdTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsageInvalidChannelIdTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageInvalidChannelIdTestVariable.START_DATE_1,
            PromoCodeUsageInvalidChannelIdTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsageInvalidChannelIdTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService
          .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageInvalidChannelIdTestVariable.CODE,
              PromoCodeUsageInvalidChannelIdTestVariable.ORDER_DETAILS,
              PromoCodeUsageInvalidChannelIdTestVariable.USED_PROMO_CODES,
              PromoCodeUsageInvalidChannelIdTestVariable.TOTAL_PRICE,
              PromoCodeUsageInvalidChannelIdTestVariable.PAYMENT_DTO,
              "456456",IgnoreValidation
                  .QUANTITY)
          .blockingGet();
    } catch (BusinessLogicException ble) {
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsageInvalidChannelIdTestVariable.CODE);

      assertEquals(ResponseCode.INVALID_CHANNEL_ID.getCode(), ble.getCode());
      assertEquals(ResponseCode.INVALID_CHANNEL_ID.getMessage(), ble.getMessage());
    }

  }

  @Test
  public void calculatePromoCodeTestPeriodNotValid() {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsagePeriodNotValidTestVariable
            .START_DATE_1);
    mockingDateFormatterHelper();

    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsagePeriodNotValidTestVariable.CODE)).thenReturn(Single.just(
        PromoCodeUsagePeriodNotValidTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsername(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsagePeriodNotValidTestVariable.PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsagePeriodNotValidTestVariable.PROMO_CODE_USAGE));

    when(this.promoCodeUsageService
        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsagePeriodNotValidTestVariable
                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
        PromoCodeUsagePeriodNotValidTestVariable.PROMO_CODE_USAGE_GENERAL));

    when(this.promoCodeUsageService
        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsagePeriodNotValidTestVariable.START_DATE_1,
            PromoCodeUsagePeriodNotValidTestVariable
                .PROMO_CODE_OBJECT))
        .thenReturn(Single.just(PromoCodeUsagePeriodNotValidTestVariable.PROMO_CODE_USAGE_DAILY));

    try {
      this.calculatePromoCodeService
          .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsagePeriodNotValidTestVariable.CODE,
              PromoCodeUsagePeriodNotValidTestVariable.ORDER_DETAILS,
              PromoCodeUsagePeriodNotValidTestVariable.USED_PROMO_CODES,
              PromoCodeUsagePeriodNotValidTestVariable.TOTAL_PRICE,
              PromoCodeUsagePeriodNotValidTestVariable.PAYMENT_DTO,
              "456456",IgnoreValidation
                  .QUANTITY)
          .blockingGet();
    } catch (BusinessLogicException ble) {
      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
          .MANDATORY_REQUEST, PromoCodeUsagePeriodNotValidTestVariable.CODE);

      assertEquals(ResponseCode.PERIOD_NOT_VALID.getCode(), ble.getCode());
      assertEquals(ResponseCode.PERIOD_NOT_VALID.getMessage(), ble.getMessage());
    }

  }

//  @Test
//  public void calculatePromoCodeTestOrderAttributeNotValid() {
//    PowerMockito.mockStatic(DateFormatterHelper.class);
//    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
//        PromoCodeUsageOrderAttributeNotValidTestVariable
//            .START_DATE_1);
//    mockingDateFormatterHelper();
//
//    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
//        .MANDATORY_REQUEST, PromoCodeUsageOrderAttributeNotValidTestVariable.CODE))
//        .thenReturn(Single.just(
//            PromoCodeUsageOrderAttributeNotValidTestVariable.PROMO_CODE_OBJECT));
//    when(this.promoCodeObjectService.findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
//        .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE)).thenReturn(Single.just
//        (UsedPromoCodeTestVariable.PROMO_CODE_OBJECT));
//    when(this.promoCodeUsageService
//        .findPromoCodeUsageByPromoCodeObjectAndUsername(
//            CommonTestVariable.MANDATORY_REQUEST,
//            PromoCodeUsageOrderAttributeNotValidTestVariable.PROMO_CODE_OBJECT))
//        .thenReturn(Single.just(PromoCodeUsageOrderAttributeNotValidTestVariable.PROMO_CODE_USAGE));
//
//    when(this.promoCodeUsageService
//        .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
//            (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageOrderAttributeNotValidTestVariable
//                .PROMO_CODE_OBJECT)).thenReturn(Single.just(
//        PromoCodeUsageOrderAttributeNotValidTestVariable.PROMO_CODE_USAGE_GENERAL));
//
//    when(this.promoCodeUsageService
//        .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
//            CommonTestVariable.MANDATORY_REQUEST,
//            PromoCodeUsageOrderAttributeNotValidTestVariable.START_DATE_1,
//            PromoCodeUsageOrderAttributeNotValidTestVariable
//                .PROMO_CODE_OBJECT))
//        .thenReturn(
//            Single.just(PromoCodeUsageOrderAttributeNotValidTestVariable.PROMO_CODE_USAGE_DAILY));
//
//    try {
//
//      CalculationResult calculatePromoCode = this.calculatePromoCodeService
//          .calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.CODE,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.ORDER_DETAILS,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.USED_PROMO_CODES,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.TOTAL_PRICE,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.PAYMENT_DTO,
//              "456456",
//              IgnoreValidation.QUANTITY).blockingGet();
//    } catch (BusinessLogicException ble) {
//      assertEquals(ResponseCode.PROMO_CODE_NOT_VALID_NO_DISCOUNT.getCode(), ble.getCode());
//      assertEquals(ResponseCode.PROMO_CODE_NOT_VALID_NO_DISCOUNT.getMessage(), ble.getMessage());
//
//      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
//          .MANDATORY_REQUEST, PromoCodeUsageOrderAttributeNotValidTestVariable.CODE);
//      verify(this.promoCodeObjectService).findPromoCodeObjectByStoreIdAndCode(CommonTestVariable
//          .MANDATORY_REQUEST, UsedPromoCodeTestVariable.CODE);
//
//      verify(this.promoCodeUsageService)
//          .findPromoCodeUsageByPromoCodeObjectAndUsername(
//              CommonTestVariable.MANDATORY_REQUEST,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.PROMO_CODE_OBJECT);
//
//      verify(this.promoCodeUsageService)
//          .findPromoCodeUsageGeneralByPromoCodeObjectAndUsername
//              (CommonTestVariable.MANDATORY_REQUEST,
//                  PromoCodeUsageOrderAttributeNotValidTestVariable
//                      .PROMO_CODE_OBJECT);
//
//      verify(this.promoCodeUsageService)
//          .findPromoCodeUsageByPromoCodeObjectAndUsernameAndTodayDate(
//              CommonTestVariable.MANDATORY_REQUEST,
//              PromoCodeUsageOrderAttributeNotValidTestVariable.START_DATE_1,
//              PromoCodeUsageOrderAttributeNotValidTestVariable
//                  .PROMO_CODE_OBJECT);
//    }
//  }

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
