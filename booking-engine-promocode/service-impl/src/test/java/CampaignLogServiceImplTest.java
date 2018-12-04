import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.dao.api.log.CampaignLogRepository;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.unit.test.CampaignTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.impl.log.CampaignLogServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CampaignLogServiceImplTest extends CampaignTestVariable {

  @InjectMocks
  CampaignLogServiceImpl campaignService;

  @Mock
  CampaignLogRepository campaignRepository;

  @Test
  public void createPromoCodeLogTestSuccess() throws Exception {

    when(campaignRepository.save(CAMPAIGN_LOG)).thenReturn(CAMPAIGN_LOG);

    Campaign promoCode = campaignService
        .createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN).blockingGet();

    verify(campaignRepository).save(CAMPAIGN_LOG);

    assertEquals(CAMPAIGN, promoCode);
  }

  @Test
  public void createPromoCodeLogTestExceptionSystemError() throws Exception {

    when(campaignRepository.save(CAMPAIGN_LOG)).thenReturn(null);

    try {
      campaignService
          .createCampaignLog(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN).blockingGet();
    } catch (BusinessLogicException be) {
      verify(campaignRepository).save(CAMPAIGN_LOG);

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
    verifyNoMoreInteractions(campaignRepository);
  }
}
