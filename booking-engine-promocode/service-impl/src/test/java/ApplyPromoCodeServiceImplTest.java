import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import com.tl.booking.promo.code.entity.constant.unit.test.ApplyPromoCode.PromoCodeUsageApplyPromoCodeTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.ApplyPromoCode.PromoCodeUsageUnApplyPromoCodeTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.VariableTestVariable;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.CalculatePromoCodeService;
import com.tl.booking.promo.code.service.api.DecrementPromoCodeUsageService;
import com.tl.booking.promo.code.service.api.IncrementPromoCodeUsageService;
import com.tl.booking.promo.code.service.impl.ApplyPromoCodeServiceImpl;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
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
public class ApplyPromoCodeServiceImplTest extends VariableTestVariable {

  @InjectMocks
  ApplyPromoCodeServiceImpl applyPromoCodeServiceImpl;

  @Mock
  private IncrementPromoCodeUsageService incrementPromoCodeUsageService;

  @Mock
  private DecrementPromoCodeUsageService decrementPromoCodeUsageService;

  @Mock
  private CalculatePromoCodeService calculatePromoCodeService;

  @Test
  public void applyPromo() throws Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageApplyPromoCodeTestVariable
            .START_DATE_1);

    when(this.calculatePromoCodeService.calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeUsageApplyPromoCodeTestVariable.CODE, PromoCodeUsageApplyPromoCodeTestVariable
            .ORDER_DETAILS, PromoCodeUsageApplyPromoCodeTestVariable.USED_PROMO_CODES,
        PromoCodeUsageApplyPromoCodeTestVariable.TOTAL_PRICE,
        PromoCodeUsageApplyPromoCodeTestVariable.PAYMENT_DTO,
        REFERENCE_ID, IgnoreValidation
            .QUANTITY))
        .thenReturn(Single.just(PromoCodeUsageApplyPromoCodeTestVariable.CALCULATION_RESULT));

    when(this.incrementPromoCodeUsageService.incrementPromoCodeUsage(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageApplyPromoCodeTestVariable
        .CALCULATION_RESULT, PromoCodeUsageApplyPromoCodeTestVariable
        .CARD_NUMBER,
        REFERENCE_ID)).thenReturn(Single.just
        (true));

    CalculationResult orderDetails = this.applyPromoCodeServiceImpl.applyPromoCode
        (CommonTestVariable
                .MANDATORY_REQUEST, PromoCodeUsageApplyPromoCodeTestVariable.CODE,
            PromoCodeUsageApplyPromoCodeTestVariable.ORDER_DETAILS,
            PromoCodeUsageApplyPromoCodeTestVariable.USED_PROMO_CODES,
            PromoCodeUsageApplyPromoCodeTestVariable.TOTAL_PRICE,
            PromoCodeUsageApplyPromoCodeTestVariable.PAYMENT_DTO,
            REFERENCE_ID, IgnoreValidation.QUANTITY)
        .blockingGet();

    verify(this.calculatePromoCodeService).calculatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeUsageApplyPromoCodeTestVariable.CODE, PromoCodeUsageApplyPromoCodeTestVariable
            .ORDER_DETAILS, PromoCodeUsageApplyPromoCodeTestVariable.USED_PROMO_CODES,
        PromoCodeUsageApplyPromoCodeTestVariable.TOTAL_PRICE,
        PromoCodeUsageApplyPromoCodeTestVariable.PAYMENT_DTO,
        REFERENCE_ID, IgnoreValidation.QUANTITY);

    verify(this.incrementPromoCodeUsageService).incrementPromoCodeUsage(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageApplyPromoCodeTestVariable
        .CALCULATION_RESULT, PromoCodeUsageApplyPromoCodeTestVariable.CARD_NUMBER,
        REFERENCE_ID);

    assertEquals(true, orderDetails.getOrderDetails().get(0).getPromoCodeRuleValid());

  }

  @Test
  public void unApplyPromo() throws Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageUnApplyPromoCodeTestVariable
            .START_DATE_1);

    when(this.decrementPromoCodeUsageService.decrementPromoCodeUsage(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeUsageUnApplyPromoCodeTestVariable.CODE,
        PromoCodeUsageUnApplyPromoCodeTestVariable.CARD_NUMBER,
        REFERENCE_ID
    )).thenReturn
        (Single
            .just(true));

    Boolean valid = this.applyPromoCodeServiceImpl.unApplyPromoCode
        (CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeUsageUnApplyPromoCodeTestVariable
            .CODE, PromoCodeUsageUnApplyPromoCodeTestVariable.CARD_NUMBER,
            REFERENCE_ID).blockingGet();

    verify(this.decrementPromoCodeUsageService).decrementPromoCodeUsage(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeUsageUnApplyPromoCodeTestVariable
        .CODE, PromoCodeUsageUnApplyPromoCodeTestVariable.CARD_NUMBER,
        REFERENCE_ID);

    assertEquals(true, valid);

  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    ReflectionTestUtils.setField(applyPromoCodeServiceImpl, "schedulerPromoCode", Schedulers.io
        ());
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(incrementPromoCodeUsageService);
    verifyNoMoreInteractions(decrementPromoCodeUsageService);
    verifyNoMoreInteractions(calculatePromoCodeService);
  }
}
