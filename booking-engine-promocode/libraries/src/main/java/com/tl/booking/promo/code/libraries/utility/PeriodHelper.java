package com.tl.booking.promo.code.libraries.utility;

import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import java.util.Date;
import org.joda.time.DateTime;

public class PeriodHelper {

  private PeriodHelper() {
    // constructors class
  }

  public static Date getStartDate(PromoCodeObject promoCodeObject, Integer timezoneOffset) {
    Date startDate = null;
    for (CampaignPeriod campaignPeriod : promoCodeObject.getCampaign().getCampaignPeriods()) {
      DateTime startDateTime = new DateTime(campaignPeriod.getStartDate()).minusHours(timezoneOffset);
      DateTime endDateTime = new DateTime(campaignPeriod.getEndDate()).minusHours(timezoneOffset);
      if(startDateTime.isBeforeNow() && endDateTime.isAfterNow()){
        startDate = campaignPeriod.getStartDate();
        break;
      }
    }

    if(startDate == null){
      startDate = promoCodeObject.getCampaign().getCampaignPeriods().get(0).getStartDate();
    }

    return startDate;
  }

  public static Date getEndDate(PromoCodeObject promoCodeObject, Integer timezoneOffset) {
    Date endDate = null;
    for (CampaignPeriod campaignPeriod : promoCodeObject.getCampaign().getCampaignPeriods()) {
      DateTime startDateTime = new DateTime(campaignPeriod.getStartDate()).minusHours(timezoneOffset);
      DateTime endDateTime = new DateTime(campaignPeriod.getEndDate()).minusHours(timezoneOffset);
      if(startDateTime.isBeforeNow() && endDateTime.isAfterNow()){
        endDate = campaignPeriod.getEndDate();
        break;
      }
    }

    if(endDate == null){
      endDate = promoCodeObject.getCampaign().getCampaignPeriods().get(0).getEndDate();
    }

    return endDate;
  }

}
