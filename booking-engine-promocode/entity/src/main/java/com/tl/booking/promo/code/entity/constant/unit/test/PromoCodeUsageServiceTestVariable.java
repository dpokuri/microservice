package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.CampaignPeriodBuilder;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.PromoCodeObjectBuilder;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.CampaignBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustmentBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsageBuilder;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.entity.dao.VariableBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;

public class PromoCodeUsageServiceTestVariable {

  public static String ID = "1234test";
  public static String PROMOCODE_ID = "IDCODE123";
  public static String PROMOCODE_ID_2 = "IDCODE123";
  public static String PROMOCODE_CODE = "CODE123";

  public static String STORE_ID_ALL = "*";
  public static String CHANNEL_ID_ALL = "*";
  public static String USERNAME_ALL = null;

  public static Date START_DATE_EXPIRED = new DateTime().minusDays(2).toDate();
  public static Date END_DATE_EXPIRED = new DateTime().minusDays(1).toDate();
  public static Date START_DATE = new DateTime().toDate();
  public static Date END_DATE = new DateTime().plusDays(2).toDate();
  public static Date START_DATE_1 = new DateTime().plusDays(10).toDate();
  public static Date END_DATE_1 = new DateTime().plusDays(11).toDate();
  public static Date TO_DAY = new Date();


  public static String CREDIT_CARD = "CreditCard";
  public static String VARIABLE_ID = "VARIABLEID";
  public static String CAMPAIGN_ID = "CAMPID";
  public static String CAMPAIGN_ID_EXIST = "CAMPIDEXIST";
  public static String ADJUSTMENT_ID = "ADJUSTID123";
  public static String PARAM = "PARAM123";
  public static String CAMPAIGN_CODE = "CAMPAIGNCODE123";

  public static PromoCodeUsage PROMO_CODE_USAGE = new PromoCodeUsageBuilder()
      .withPromoCode(PROMOCODE_CODE)
      .withPromoCodeId(PROMOCODE_ID)
      .withUsageCount(0)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .build();

  public static PromoCode PROMO_CODE = new PromoCodeBuilder()
      .withId(PROMOCODE_ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PROMOCODE_CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()
      .withId(ADJUSTMENT_ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withIsDeleted(0)
      .build();


  public static CampaignPeriod CAMPAIGN_PERIOD = new CampaignPeriodBuilder()
      .withStartDate(START_DATE)
      .withEndDate(END_DATE)
      .build();

  public static List<CampaignPeriod> CAMPAIGN_PERIODS = Arrays.asList(CAMPAIGN_PERIOD);

  public static Campaign CAMPAIGN = new CampaignBuilder()
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withId(CAMPAIGN_ID)
      .withCode(CAMPAIGN_CODE)

      .build();


  public static Variable VARIABLE = new VariableBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withId(VARIABLE_ID)
      .withParam(PARAM)
      .build();

  public static List<Variable> VARIABLE_LIST = Arrays.asList(VARIABLE, VARIABLE);

  public static Map<String, Variable> VARIABLE_MAPS = variableMap(VARIABLE_LIST);
  public static PromoCodeObject PROMO_CODE_OBJECT = new PromoCodeObjectBuilder()
      .withPromoCode(PROMO_CODE)
      .withPromoCodeAdjustment(PROMO_CODE_ADJUSTMENT)
      .withCampaign(CAMPAIGN)
      .withVariableMap(VARIABLE_MAPS)
      .build();
  public static PromoCodeObject PROMO_CODE_OBJECT_NOT_FOUND = new PromoCodeObjectBuilder()
      .withPromoCode(PROMO_CODE)
      .withPromoCodeAdjustment(PROMO_CODE_ADJUSTMENT)
      .withCampaign(CAMPAIGN)
      .withVariableMap(VARIABLE_MAPS)
      .build();
  public static PromoCodeObject PROMO_CODE_OBJECT_SET_CAMPAIGN = new PromoCodeObjectBuilder()
      .withCampaign(CAMPAIGN)
      .build();
  public static Date PROMOCODE_START_DATE = PROMO_CODE_OBJECT.getCampaign().getCampaignPeriods()
      .get(0).getStartDate();
  public static Date PROMOCODE_END_DATE = PROMO_CODE_OBJECT.getCampaign().getCampaignPeriods()
      .get(0).getEndDate();
  public static PromoCodeUsage PROMO_CODE_USAGE_NULL = new PromoCodeUsageBuilder().build();
  public static MandatoryRequest MANDATORY_REQUEST_2 = new MandatoryRequestBuilder()
      .withStoreId("*").withChannelId("*").withRequestId(CommonTestVariable.REQUEST_ID)
      .withServiceId(CommonTestVariable.SERVICE_ID).withUsername("*").build();

  private static Map<String, Variable> variableMap(List<Variable> VARIABLE_LIST) {
    Map<String, Variable> variableMap = new HashMap<>();

    for (Variable variable : VARIABLE_LIST) {
      variableMap.put(variable.getParam(), variable);
    }
    return variableMap;
  }

}
