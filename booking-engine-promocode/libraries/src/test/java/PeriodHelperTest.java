import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.libraries.utility.PeriodHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PeriodHelperTest {

  @Test
  public void getStartDateTest() throws Exception {
    Date date = new DateTime().minusHours(5).toDate();
    Date endDate = new DateTime().plusHours(10).toDate();

    CampaignPeriod campaignPeriod = new CampaignPeriod();
    campaignPeriod.setStartDate(date);
    campaignPeriod.setEndDate(endDate);

    List<CampaignPeriod> campaignPeriods = new ArrayList<>();
    campaignPeriods.add(campaignPeriod);

    Campaign campaign = new Campaign();
    campaign.setCampaignPeriods(campaignPeriods);

    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setCampaign(campaign);

    Date startDate = PeriodHelper.getStartDate(promoCodeObject, 7);

    assertEquals(date, startDate);
  }

  @Test
  public void getStartDateNullTest() throws Exception {
    Date date = new DateTime().toDate();
    Date endDate = new DateTime().toDate();

    CampaignPeriod campaignPeriod = new CampaignPeriod();
    campaignPeriod.setStartDate(date);
    campaignPeriod.setEndDate(endDate);

    List<CampaignPeriod> campaignPeriods = new ArrayList<>();
    campaignPeriods.add(campaignPeriod);

    Campaign campaign = new Campaign();
    campaign.setCampaignPeriods(campaignPeriods);

    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setCampaign(campaign);

    Date startDate = PeriodHelper.getStartDate(promoCodeObject, 7);

    assertEquals(date, startDate);
  }

  @Test
  public void getEndDateTest() throws Exception {
    Date beforeDate = new DateTime().minusDays(3).toDate();
    Date afterDate = new DateTime().plusDays(2).toDate();

    CampaignPeriod campaignPeriod = new CampaignPeriod();
    campaignPeriod.setStartDate(beforeDate);
    campaignPeriod.setEndDate(afterDate);
    List<CampaignPeriod> campaignPeriods = new ArrayList<>();
    campaignPeriods.add(campaignPeriod);

    Campaign campaign = new Campaign();
    campaign.setCampaignPeriods(campaignPeriods);

    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setCampaign(campaign);

    Date endDate = PeriodHelper.getEndDate(promoCodeObject, 7);

    assertEquals(afterDate, endDate);
  }

  @Test
  public void getEndDateNullTest() throws Exception {
    Date date = new DateTime().toDate();
    Date endDate = new DateTime().toDate();

    CampaignPeriod campaignPeriod = new CampaignPeriod();
    campaignPeriod.setStartDate(date);
    campaignPeriod.setEndDate(endDate);

    List<CampaignPeriod> campaignPeriods = new ArrayList<>();
    campaignPeriods.add(campaignPeriod);

    Campaign campaign = new Campaign();
    campaign.setCampaignPeriods(campaignPeriods);

    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setCampaign(campaign);

    Date endDateFound = PeriodHelper.getEndDate(promoCodeObject, 7);

    assertEquals(endDate, endDateFound);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }
}
