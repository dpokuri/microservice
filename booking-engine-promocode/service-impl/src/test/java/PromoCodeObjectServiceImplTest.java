import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeObjectTestVariable;
import com.tl.booking.promo.code.service.api.CampaignService;
import com.tl.booking.promo.code.service.api.PromoCodeAdjustmentService;
import com.tl.booking.promo.code.service.api.PromoCodeService;
import com.tl.booking.promo.code.service.api.VariableService;
import com.tl.booking.promo.code.service.impl.PromoCodeObjectServiceImpl;
import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PromoCodeObjectServiceImplTest extends PromoCodeObjectTestVariable {

  @InjectMocks
  PromoCodeObjectServiceImpl promoCodeObjectService;

  @Mock
  PromoCodeService promoCodeService;

  @Mock
  CampaignService campaignService;

  @Mock
  PromoCodeAdjustmentService promoCodeAdjustmentService;

  @Mock
  VariableService variableService;

  @Test
  public void findPromoCodeObjectByStoreIdAndCodeTestSuccess() throws Exception {

    when(promoCodeService.findPromoCodeByCodeAndStatus(CommonTestVariable.MANDATORY_REQUEST, CODE,
        PromoCodeStatus.ACTIVE)).thenReturn(
        Single.just(PROMO_CODE));

    when(campaignService.findCampaignById(CommonTestVariable.MANDATORY_REQUEST,
        PROMO_CODE_OBJECT.getPromoCode().getCampaignId())).thenReturn(Single.just(CAMPAIGN));

    when(promoCodeAdjustmentService
        .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST,
            PROMO_CODE_OBJECT.getCampaign().getPromoCodeAdjustmentId()))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT));

    when(variableService.findVariablesMapped(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(VARIABLE_MAPS));

    PromoCodeObject promoCodeObject = promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST, CODE)
        .blockingGet();

    verify(promoCodeService)
        .findPromoCodeByCodeAndStatus(CommonTestVariable.MANDATORY_REQUEST, CODE,
            PromoCodeStatus.ACTIVE);

    verify(campaignService).findCampaignById(CommonTestVariable.MANDATORY_REQUEST,
        PROMO_CODE_OBJECT.getPromoCode().getCampaignId());

    verify(promoCodeAdjustmentService)
        .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST,
            PROMO_CODE_OBJECT.getCampaign().getPromoCodeAdjustmentId());

    assertEquals(PROMO_CODE_OBJECT, promoCodeObject);


  }


  @Test
  public void findPromoCodeObjectByCampaignIdMergeWithPromoCodeTestSuccess() throws Exception {

    when(campaignService.findCampaignById(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(CAMPAIGN));
    when(promoCodeAdjustmentService.findPromoCodeAdjustmentById(
        CommonTestVariable.MANDATORY_REQUEST,
        PROMO_CODE_OBJECT.getPromoCodeAdjustment().getId())).thenReturn
        (Single.just(PROMO_CODE_OBJECT.getPromoCodeAdjustment()));

    PromoCodeObject promoCodeObject = promoCodeObjectService
        .findPromoCodeObjectByCampaignIdMergeWithPromoCode(CommonTestVariable.MANDATORY_REQUEST, ID,
            PROMO_CODE).blockingGet();

    verify(campaignService).findCampaignById(CommonTestVariable.MANDATORY_REQUEST, ID);
    verify(promoCodeAdjustmentService).findPromoCodeAdjustmentById(CommonTestVariable
            .MANDATORY_REQUEST, PROMO_CODE_OBJECT.getPromoCodeAdjustment().getId());

    assertEquals(PROMO_CODE_OBJECT_SET_CAMPAIGN, promoCodeObject);

  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(promoCodeService);
  }
}
