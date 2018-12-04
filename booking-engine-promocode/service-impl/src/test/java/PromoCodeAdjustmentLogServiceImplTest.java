import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.log.PromoCodeAdjustmentLogRepository;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeAdjustmentTestVariable;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.impl.log.PromoCodeAdjustmentLogServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PromoCodeAdjustmentLogServiceImplTest extends PromoCodeAdjustmentTestVariable {

  @InjectMocks
  PromoCodeAdjustmentLogServiceImpl promoCodeService;

  @Mock
  PromoCodeAdjustmentLogRepository promoCodeRepository;

  @Test
  public void createPromoCodeLogTestSuccess() throws Exception {

    when(promoCodeRepository.save(PROMO_CODE_ADJUSTMENT_LOG)).thenReturn(PROMO_CODE_ADJUSTMENT_LOG);

    PromoCodeAdjustment promoCode = promoCodeService
        .createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT)
        .blockingGet();

    verify(promoCodeRepository).save(PROMO_CODE_ADJUSTMENT_LOG);

    assertEquals(PROMO_CODE_ADJUSTMENT, promoCode);
  }

  @Test
  public void createPromoCodeLogTestExceptionSystemError() throws Exception {

    when(promoCodeRepository.save(PROMO_CODE_ADJUSTMENT_LOG)).thenReturn(null);

    try {
      promoCodeService
          .createPromoCodeAdjustmentLog(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT)
          .blockingGet();
    } catch (BusinessLogicException be) {
      verify(promoCodeRepository).save(PROMO_CODE_ADJUSTMENT_LOG);

      assertEquals(ResponseCode.SYSTEM_ERROR.getCode(), be.getCode());
      assertEquals(ResponseCode.SYSTEM_ERROR.getMessage(), be.getMessage());
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeRepository);
  }
}
