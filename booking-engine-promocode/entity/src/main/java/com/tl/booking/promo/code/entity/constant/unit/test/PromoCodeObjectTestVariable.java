package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.PromoCodeObjectBuilder;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.CampaignBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustmentBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeBuilder;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.entity.dao.VariableBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromoCodeObjectTestVariable {

  public static String CODE = "test123";
  public static String ID = "ID123";
  public static String CAMPAIGN_ID = "CAMPAIGN_ID123";
  public static String PARAM = "PARAM_ID123";


  public static PromoCode PROMO_CODE = new PromoCodeBuilder()
      .withId(ID)
      .withCampaignId(CAMPAIGN_ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(PromoCodeTestVariable.USERNAME)
      .withUpdatedBy(PromoCodeTestVariable.USERNAME)
      .withCode(PromoCodeTestVariable.CODE)
      .withMaxQty(PromoCodeTestVariable.MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE)
      .withIsDeleted(0)
      .build();

  public static Campaign CAMPAIGN = new CampaignBuilder()
      .withCampaignStatus(CampaignStatus.ACTIVE)
      .withId(ID)
      .withPromoCodeAdjustmentId(PROMO_CODE_ADJUSTMENT.getId())
      .withCode(CODE)
      .withCampaignPeriods(new ArrayList<>())

      .build();


  public static Variable VARIABLE = new VariableBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withId(ID)
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
  public static PromoCodeObject PROMO_CODE_OBJECT_SET_CAMPAIGN = new PromoCodeObjectBuilder()
      .withCampaign(CAMPAIGN)
      .withPromoCodeAdjustment(PROMO_CODE_ADJUSTMENT)
      .withPromoCode(PROMO_CODE)
      .build();

  private static Map<String, Variable> variableMap(List<Variable> VARIABLE_LIST) {
    Map<String, Variable> variableMap = new HashMap<>();

    for (Variable variable : VARIABLE_LIST) {
      variableMap.put(variable.getParam(), variable);
    }
    return variableMap;
  }

}
