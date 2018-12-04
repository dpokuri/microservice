import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepository;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.UsageRuleBuilder;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.IncrementPromoCodeUsage.PromoCodeUsageIncrementTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeUsageTestVariable;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.libraries.utility.CommonHelper;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import com.tl.booking.promo.code.service.impl.IncrementPromoCodeUsageServiceImpl;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
@PrepareForTest({DateFormatterHelper.class, CommonHelper.class})
public class IncrementPromoCodeUsageServiceImplTest {

  @InjectMocks
  IncrementPromoCodeUsageServiceImpl incrementPromoCodeUsageServiceImpl;

  @Mock
  private PromoCodeUsageRepository promoCodeUsageRepository;

  @Mock
  private PromoCodeUsageLogService promoCodeUsageLogService;

  @Mock
  TimeZoneProperties timeZoneProperties;

  @Test
  public void incrementPromoCodeUsageTestUpdateExistingQty() throws Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.mockStatic(CommonHelper.class);

    PowerMockito.when(CommonHelper.generateUsageRuleMap(Arrays.asList())).thenReturn(null);

    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageIncrementTestVariable
            .START_DATE_1);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
            PromoCodeUsageIncrementTestVariable.START_DATE,
            PromoCodeUsageIncrementTestVariable.END_DATE,
            "*",
            "*"
        ))
        .thenReturn(PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            anyObject(),
            anyObject(),
            anyObject(),
            anyObject(),
            anyObject()
        ))
        .thenReturn(PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE);

    when(this.promoCodeUsageRepository.updatePromoCodeUsageById
        (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
            PromoCodeUsageIncrementTestVariable.MAX_QTY))
        .thenReturn
        (new
        WriteResult(1, true, PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID));

    Boolean valid = this.incrementPromoCodeUsageServiceImpl
        .incrementPromoCodeUsage(CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeUsageIncrementTestVariable.CALCULATION_RESULT, PromoCodeUsageTestVariable
                .CARD_NUMBER, PromoCodeUsageIncrementTestVariable.REFERENCE_ID).blockingGet();

    verify(this.promoCodeUsageLogService).createPromoCodeUsageLog(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeUsageIncrementTestVariable.CODE,
        PromoCodeUsageIncrementTestVariable.DISCOUNT_AMOUNT, PromoCodeUsageIncrementTestVariable
            .PARTNER_COST_AMOUNT, PromoCodeUsageIncrementTestVariable.COMPANY_COST_AMOUNT,
        PromoCodeUsageIncrementTestVariable.TOTAL_PRICE, PromoCodeUsageIncrementTestVariable
            .REFERENCE_ID, 1, PromoCodeUsageIncrementTestVariable.ORDER_DETAILS);

    assertEquals(true, valid);
  }

  @Test
  public void incrementPromoCodeUsageTestUpdateExistingQtyFailedUpdatingDailyUsername() throws
      Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.mockStatic(CommonHelper.class);

    StringBuilder usageRuleCode = new StringBuilder();
    usageRuleCode.append(UsagePeriod.DAILY.getCode());
    usageRuleCode.append(ValidatedBy.USERNAME.getName());


    UsageRule usageRule = new UsageRuleBuilder()
        .withUsageCount(10).withUsagePeriod(UsagePeriod.DAILY).withValidatedBy(ValidatedBy
            .USERNAME).build();
    Map<String, UsageRule> usageRuleMap = new HashMap<>();
    usageRuleMap.put(usageRuleCode.toString(), usageRule);

    PowerMockito.when(CommonHelper.generateUsageRuleMap(Arrays.asList(usageRule)))
        .thenReturn
        (usageRuleMap);

    PromoCodeUsageIncrementTestVariable.CALCULATION_RESULT.getPromoCodeObject()
        .getPromoCodeAdjustment().setUsageRules(Arrays.asList(usageRule));

    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageIncrementTestVariable
            .START_DATE_1);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
            PromoCodeUsageIncrementTestVariable.START_DATE,
            PromoCodeUsageIncrementTestVariable.END_DATE,
            "*",
            "*"
        ))
        .thenReturn(PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
            PromoCodeUsageIncrementTestVariable.START_DATE_1,
            PromoCodeUsageIncrementTestVariable.START_DATE_1,
            "*",
            CommonTestVariable.USERNAME
        ))
        .thenReturn(PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_DAILY);

    when(this.promoCodeUsageRepository.updatePromoCodeUsageById
        (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
            PromoCodeUsageIncrementTestVariable.MAX_QTY))
        .thenReturn
            (new WriteResult(0, false, null));

    when(this.promoCodeUsageRepository.updatePromoCodeUsageById
        (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
            usageRule.getUsageCount()))
        .thenReturn
            (new WriteResult(0, false, null));


    try {
      Boolean valid = this.incrementPromoCodeUsageServiceImpl
          .incrementPromoCodeUsage(CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageIncrementTestVariable.CALCULATION_RESULT, PromoCodeUsageTestVariable
                  .CARD_NUMBER, PromoCodeUsageIncrementTestVariable.REFERENCE_ID).blockingGet();
    } catch (BusinessLogicException ble){
      assertEquals(ResponseCode.NOT_VALID_USAGE_RULE.getCode(), ble.getCode());

      verify(this.promoCodeUsageRepository)
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
              PromoCodeUsageIncrementTestVariable.START_DATE,
              PromoCodeUsageIncrementTestVariable.END_DATE,
              "*",
              "*"
          );

      verify(this.promoCodeUsageRepository)
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
              PromoCodeUsageIncrementTestVariable.START_DATE_1,
              PromoCodeUsageIncrementTestVariable.START_DATE_1,
              "*",
              CommonTestVariable.USERNAME
          );

      verify(this.promoCodeUsageRepository).updatePromoCodeUsageById
          (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
              usageRule.getUsageCount());
      verify(this.promoCodeUsageRepository).updatePromoCodeUsageById
          (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, -1,0);
    }
  }

  @Test
  public void incrementPromoCodeUsageTestUpdateExistingQtyFailedUpdatingDailyCardNumber() throws
      Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.mockStatic(CommonHelper.class);

    StringBuilder usageRuleCode = new StringBuilder();
    usageRuleCode.append(UsagePeriod.DAILY.getCode());
    usageRuleCode.append(ValidatedBy.CARD_NUMBER.getName());


    UsageRule usageRule = new UsageRuleBuilder()
        .withUsageCount(10).withUsagePeriod(UsagePeriod.DAILY).withValidatedBy(ValidatedBy
            .CARD_NUMBER).build();
    Map<String, UsageRule> usageRuleMap = new HashMap<>();
    usageRuleMap.put(usageRuleCode.toString(), usageRule);

    PowerMockito.when(CommonHelper.generateUsageRuleMap(Arrays.asList(usageRule)))
        .thenReturn
            (usageRuleMap);

    PromoCodeUsageIncrementTestVariable.CALCULATION_RESULT.getPromoCodeObject()
        .getPromoCodeAdjustment().setUsageRules(Arrays.asList(usageRule));

    PowerMockito.when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageIncrementTestVariable
            .START_DATE_1);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
            PromoCodeUsageIncrementTestVariable.START_DATE,
            PromoCodeUsageIncrementTestVariable.END_DATE,
            "*",
            "*"
        ))
        .thenReturn(PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
            PromoCodeUsageIncrementTestVariable.START_DATE_1,
            PromoCodeUsageIncrementTestVariable.START_DATE_1,
            PromoCodeUsageIncrementTestVariable.CARD_NUMBER,
            "*"
        ))
        .thenReturn(PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_DAILY_CARD_NUMBER);

    when(this.promoCodeUsageRepository.updatePromoCodeUsageById
        (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
            PromoCodeUsageIncrementTestVariable.MAX_QTY))
        .thenReturn
            (new WriteResult(0, false, null));

    when(this.promoCodeUsageRepository.updatePromoCodeUsageById
        (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
            usageRule.getUsageCount()))
        .thenReturn
            (new WriteResult(0, false, null));


    try {
      Boolean valid = this.incrementPromoCodeUsageServiceImpl
          .incrementPromoCodeUsage(CommonTestVariable.MANDATORY_REQUEST,
              PromoCodeUsageIncrementTestVariable.CALCULATION_RESULT, PromoCodeUsageIncrementTestVariable
                  .CARD_NUMBER, PromoCodeUsageIncrementTestVariable.REFERENCE_ID).blockingGet();
    } catch (BusinessLogicException ble){
      assertEquals(ResponseCode.NOT_VALID_USAGE_RULE.getCode(), ble.getCode());

      verify(this.promoCodeUsageRepository)
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
              PromoCodeUsageIncrementTestVariable.START_DATE,
              PromoCodeUsageIncrementTestVariable.END_DATE,
              "*",
              "*"
          );

      verify(this.promoCodeUsageRepository)
          .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
              PromoCodeUsageIncrementTestVariable.PROMO_CODE.getId(),
              PromoCodeUsageIncrementTestVariable.START_DATE_1,
              PromoCodeUsageIncrementTestVariable.START_DATE_1,
              PromoCodeUsageIncrementTestVariable.CARD_NUMBER,
              "*"
          );

      verify(this.promoCodeUsageRepository).updatePromoCodeUsageById
          (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, 1,
              usageRule.getUsageCount());
      verify(this.promoCodeUsageRepository).updatePromoCodeUsageById
          (PromoCodeUsageIncrementTestVariable.PROMO_CODE_USAGE_ID, -1,0);
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    ReflectionTestUtils
        .setField(incrementPromoCodeUsageServiceImpl, "schedulerPromoCode", Schedulers.io
            ());
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeUsageRepository);
    verifyNoMoreInteractions(promoCodeUsageLogService);
  }
}
