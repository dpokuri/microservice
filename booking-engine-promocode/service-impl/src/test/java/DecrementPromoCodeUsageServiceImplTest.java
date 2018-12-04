import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepository;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.DecrementPromoCodeUsage.PromoCodeUsageDecrementTestVariable;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.utility.CommonHelper;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import com.tl.booking.promo.code.service.impl.DecrementPromoCodeUsageServiceImpl;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
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
public class DecrementPromoCodeUsageServiceImplTest {

  @InjectMocks
  DecrementPromoCodeUsageServiceImpl decrementPromoCodeUsageServiceImpl;

  @Mock
  private PromoCodeUsageRepository promoCodeUsageRepository;

  @Mock
  private PromoCodeUsageLogService promoCodeUsageLogService;

  @Mock
  private PromoCodeService promoCodeService;

  @Mock
  private PromoCodeObjectService promoCodeObjectService;

  @Mock
  TimeZoneProperties timeZoneProperties;

  @Test
  public void decrementPromoCodeUsageTestUpdateExistingQty() throws Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class);
    PowerMockito.mockStatic(CommonHelper.class);

    when(CommonHelper.generateUsageRuleMap(Arrays.asList())).thenReturn(null);

    when(DateFormatterHelper.getTodayDate()).thenReturn(
        PromoCodeUsageDecrementTestVariable
            .START_DATE_1);

    when(this.promoCodeUsageRepository
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageDecrementTestVariable.ID,
            PromoCodeUsageDecrementTestVariable.START_DATE,
            PromoCodeUsageDecrementTestVariable.END_DATE,
            "*",
            "*"
        )
    ).thenReturn(PromoCodeUsageDecrementTestVariable.PROMO_CODE_USAGE_GENERAL);

    when(this.promoCodeService.findPromoCodeByCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeUsageDecrementTestVariable.CODE))
        .thenReturn(Single.just(PromoCodeUsageDecrementTestVariable
            .PROMO_CODE));

    when(this.promoCodeObjectService.findPromoCodeObjectByCampaignIdMergeWithPromoCode
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageDecrementTestVariable.CAMPAIGN_ID,
            PromoCodeUsageDecrementTestVariable.PROMO_CODE)).thenReturn
        (Single.just(PromoCodeUsageDecrementTestVariable.PROMO_CODE_OBJECT));

    when(this.promoCodeUsageRepository.updatePromoCodeUsageById
        (PromoCodeUsageDecrementTestVariable.PROMO_CODE_USAGE_ID,-1,0)).thenReturn(new
        WriteResult(1, true,PromoCodeUsageDecrementTestVariable.PROMO_CODE_USAGE_ID));

    Boolean decrementUsage = this.decrementPromoCodeUsageServiceImpl
        .decrementPromoCodeUsage(
            CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageDecrementTestVariable.CODE,
            PromoCodeUsageDecrementTestVariable.CARD_NUMBER,
            "456456"
        ).blockingGet();

    verify(this.promoCodeUsageRepository)
        .findPromoCodeUsageByPromoCodeIdAndStartDateAndEndDateAndCardNumberAndUsername(
            PromoCodeUsageDecrementTestVariable.ID,
            PromoCodeUsageDecrementTestVariable.START_DATE,
            PromoCodeUsageDecrementTestVariable.END_DATE,
            "*",
            "*"
        );

    verify(this.promoCodeUsageRepository).updatePromoCodeUsageById
        (PromoCodeUsageDecrementTestVariable.PROMO_CODE_USAGE
            .getId(), -1, 0);

    verify(this.promoCodeService).findPromoCodeByCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeUsageDecrementTestVariable.CODE);

    verify(this.promoCodeObjectService).findPromoCodeObjectByCampaignIdMergeWithPromoCode
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageDecrementTestVariable.CAMPAIGN_ID,
            PromoCodeUsageDecrementTestVariable.PROMO_CODE);

    verify(this.promoCodeUsageLogService).createPromoCodeUsageLog
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeUsageDecrementTestVariable.CODE, 0.0, null,
            null,
            0.00,"456456", -1, null);

    assertEquals(true, decrementUsage);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    ReflectionTestUtils.setField(decrementPromoCodeUsageServiceImpl, "schedulerPromoCode", Schedulers.io
        ());
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeUsageRepository);
    verifyNoMoreInteractions(promoCodeUsageLogService);
    verifyNoMoreInteractions(promoCodeObjectService);
    verifyNoMoreInteractions(promoCodeService);
  }
}
