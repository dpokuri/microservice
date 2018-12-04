package com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode;

import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.CampaignPeriodBuilder;
import com.tl.booking.promo.code.entity.Cost;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PromoCodeCost;
import com.tl.booking.promo.code.entity.PromoCodeDiscount;
import com.tl.booking.promo.code.entity.PromoCodeDistribution;
import com.tl.booking.promo.code.entity.PromoCodeDistributionBuilder;
import com.tl.booking.promo.code.entity.PromoCodeGroupRule;
import com.tl.booking.promo.code.entity.PromoCodeObject;
import com.tl.booking.promo.code.entity.PromoCodePriceRange;
import com.tl.booking.promo.code.entity.PromoCodeRule;
import com.tl.booking.promo.code.entity.PromoCodeRuleValueBuilder;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.DataType;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

public class UsedPromoCodeTestVariable {

  public static final Map<String, Variable> MAP_VARIABLES = getMapVariables();
  public static final List<Variable> VARIABLES = getVariables();
  public static final List<OrderDetail> ORDER_DETAILS = getOrderDetails();
  public static final PromoCodeDiscount PROMO_CODE_DISCOUNT = getPromoCodeDiscount();
  public static final PromoCodePriceRange PROMO_CODE_PRICE_RANGE = getPromoCodePriceRange();
  public static final List<PromoCodePriceRange> PROMO_CODE_PRICE_RANGES = getPromoCodePriceRanges();
  public static final List<PromoCodeRule> PROMO_CODE_RULES = getPromoCodeRules();
  public static final String PROMO_CODE_JSON_BODY = "{\"campaignId\": \"CAMPID\",\"code\": \"PROMOCODE\",\"maxQty\": 100}";
  public static Date START_DATE = new DateTime().toDate();
  public static Date END_DATE = new DateTime().plusDays(2).toDate();
  public static Date START_DATE_1 = new DateTime().plusDays(10).toDate();
  public static Date END_DATE_1 = new DateTime().plusDays(11).toDate();
  public static Date START_DATE_EXPIRED = new DateTime().minusDays(2).toDate();
  public static Date END_DATE_EXPIRED = new DateTime().minusDays(1).toDate();
  public static Double EXPECTED_DISCOUNT_AMOUNT = 12000.00;
  public static String ID = "PROMOCODEID";
  public static String CODE = "PROMOCODECANCOMBINE";
  public static final Set<String> USED_PROMO_CODES = getUsedPromoCodes();
  public static String VALUE = "value";
  public static String USERNAME = "development";
  public static Integer MAX_QTY = 100;
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static String CAMPAIGN_ID = "CAMPID";
  public static String CAMPAIGN_ID_EXIST = "CAMPIDEXIST";
  public static String CAMPAIGN_NAME = "CAMPNAME";
  public static String ADJUSTMENT_ID = "ADJID";
  public static String SORT = "CODE";
  public static String SORT_DIRECTION = "ASC";
  public static String CACHE_KEY = CacheKey.PROMO_CODE + "-" + ID;
  public static CampaignPeriod CAMPAIGN_PERIOD = new CampaignPeriodBuilder()
      .withStartDate(START_DATE).withEndDate(END_DATE).build();
  public static CampaignPeriod CAMPAIGN_PERIOD_1 = new CampaignPeriodBuilder()
      .withStartDate(START_DATE_1).withEndDate(END_DATE_1).build();
  public static CampaignPeriod CAMPAIGN_PERIOD_EXPIRED = new CampaignPeriodBuilder()
      .withStartDate(START_DATE_EXPIRED).withEndDate(END_DATE_EXPIRED).build();
  public static List<CampaignPeriod> CAMPAIGN_PERIODS = Arrays
      .asList(CAMPAIGN_PERIOD_EXPIRED, CAMPAIGN_PERIOD, CAMPAIGN_PERIOD_1);
  public static List<CampaignPeriod> CAMPAIGN_PERIODS_EXPIRED = Arrays
      .asList(CAMPAIGN_PERIOD_EXPIRED);
  public static PromoCodeCost PROMO_CODE_COST = getPromoCodeCost();
  public static Campaign CAMPAIGN = new CampaignBuilder()
      .withId(CAMPAIGN_ID)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withName(CAMPAIGN_NAME)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.ACTIVE)
      .build();
  public static Set<PromoCodeDistribution> PROMO_CODE_DISTRIBUTIONS = getPromoCodeDistributions();
  public static List<UsageRule> USAGE_RULES = getUsageRules();
  public static List<PromoCodeGroupRule> PROMO_CODE_GROUP_RULES = getPromoCodeGroupRules();
  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()
      .withId(ADJUSTMENT_ID)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withMaxDiscount(100000.00)
      .withName("NAMA")
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE)
      .withPromoCodeCombine(true)
      .withUsageRules(USAGE_RULES)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .build();
  public static Campaign CAMPAIGN_EXPIRED = new CampaignBuilder()
      .withId(CAMPAIGN_ID)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withName(CAMPAIGN_NAME)
      .withCampaignPeriods(CAMPAIGN_PERIODS_EXPIRED)
      .build();
  public static PromoCode PROMO_CODE = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withCode(CODE)
      .withMaxQty(MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static final PromoCodeObject PROMO_CODE_OBJECT = getPromoCodeObject();
  public static PromoCode PROMO_CODE_EXIST = new PromoCodeBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withCode(CODE)
      .withMaxQty(MAX_QTY)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .withCampaignId(CAMPAIGN_ID_EXIST)
      .build();
  public static List<PromoCode> PROMO_CODES = Arrays.asList(PROMO_CODE, PROMO_CODE_EXIST);
  public static PromoCode PROMO_CODE_REQUEST = new PromoCodeBuilder()
      .withCode(CODE)
      .withMaxQty(MAX_QTY)
      .withCampaignId(CAMPAIGN_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(0)
      .withUsageDate(START_DATE_1)
      .withStartDate(START_DATE)
      .withEndDate(END_DATE)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(0)
      .withStartDate(START_DATE_1)
      .withEndDate(START_DATE_1)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_GENERAL = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(0)
      .withUsageDate(START_DATE_1)
      .withStartDate(START_DATE)
      .withEndDate(END_DATE)
      .withStoreId("*")
      .withChannelId("*")
      .withUsername("*")
      .build();

  private static final List<Variable> getVariables() {
    Variable variable = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>"))
        .withDataType(DataType.STRING)
        .withParam("airline_name")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable2 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>"))
        .withDataType(DataType.STRING)
        .withParam("rute")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    return Arrays.asList(variable, variable2);
  }

  private static final Map<String, Variable> getMapVariables() {
    List<Variable> variables = getVariables();

    Map<String, Variable> variableMap = new HashMap<>();
    for (Variable variable : variables) {
      variableMap.put(variable.getParam(), variable);
    }
    return variableMap;
  }

  private static final OrderDetail getOrderDetail() {
    OrderDetail orderDetail = new OrderDetail();
    orderDetail.setAmount(20000.00);
    orderDetail.setDiscount(0.00);

    Map<String, String> orderAttribute = new HashMap<>();
    orderAttribute.put("airline_name", "airasia");
    orderAttribute.put("rute", "international");
    orderDetail.setOrderAttribute(orderAttribute);
    orderDetail.setReferenceId("string");

    return orderDetail;
  }

  private static final List<OrderDetail> getOrderDetails() {
    OrderDetail orderDetail = getOrderDetail();
    return Arrays.asList(orderDetail);
  }

  private static final Set<String> getUsedPromoCodes() {
    Set<String> usedPromoCodes = new HashSet<>();
    usedPromoCodes.add(CODE);
    return usedPromoCodes;
  }

  private static final PromoCodeObject getPromoCodeObject() {
    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setVariableMap(getMapVariables());
    promoCodeObject.setPromoCodeAdjustment(PROMO_CODE_ADJUSTMENT);
    promoCodeObject.setCampaign(CAMPAIGN);
    promoCodeObject.setPromoCode(PROMO_CODE);
    return promoCodeObject;
  }

  private static final PromoCodeDistribution getPromoCodeDistribution() {
    Set<String> channelIds = new HashSet<>();
    channelIds.add(CommonTestVariable.CHANNEL_ID);
    return new PromoCodeDistributionBuilder().withStoreId(CommonTestVariable.STORE_ID)
        .withChannelId(channelIds).build();
  }

  private static final Set<PromoCodeDistribution> getPromoCodeDistributions() {
    Set<PromoCodeDistribution> promoCodeDistributions = new HashSet<>();
    promoCodeDistributions.add(getPromoCodeDistribution());
    return promoCodeDistributions;
  }

  private static final List<UsageRule> getUsageRules() {
    UsageRule usageRule = new UsageRule();
    usageRule.setUsageCount(10);
    usageRule.setUsagePeriod(UsagePeriod.PERIOD);

    UsageRule usageRule1 = new UsageRule();
    usageRule1.setUsagePeriod(UsagePeriod.DAILY);
    usageRule1.setUsageCount(1);

    return Arrays.asList(usageRule, usageRule1);
  }

  private static final PromoCodeCost getPromoCodeCost() {
    PromoCodeCost promoCodeCost = new PromoCodeCost();
    Cost companyCost = new Cost();
    companyCost.setName("Marketing");
    companyCost.setPercent(10.00);
    companyCost.setValue(5000.00);
    companyCost.setMaxValue(100000.00);
    promoCodeCost.setCompanyCost(Arrays.asList(companyCost));

    Cost partnerCost = new Cost();
    partnerCost.setName("BCA");
    partnerCost.setPercent(10.00);
    partnerCost.setValue(5000.00);
    partnerCost.setMaxValue(100000.00);
    promoCodeCost.setPartnerCost(Arrays.asList(partnerCost));

    return promoCodeCost;
  }

  private static final List<PromoCodeGroupRule> getPromoCodeGroupRules() {
    return Arrays.asList(getPromoCodeGroupRule());
  }

  private static final PromoCodeGroupRule getPromoCodeGroupRule() {
    PromoCodeGroupRule promoCodeGroupRule = new PromoCodeGroupRule();
    promoCodeGroupRule.setName("Airasia International");
    promoCodeGroupRule.setPromoCodeCost(PROMO_CODE_COST);
    promoCodeGroupRule.setPromoCodePriceRanges(PROMO_CODE_PRICE_RANGES);
    promoCodeGroupRule.setPromoCodeRules(PROMO_CODE_RULES);
    return promoCodeGroupRule;
  }

  private static final PromoCodeDiscount getPromoCodeDiscount() {
    PromoCodeDiscount promoCodeDiscount = new PromoCodeDiscount();
    promoCodeDiscount.setMaxValue(100000.00);
    promoCodeDiscount.setPercent(10.00);
    promoCodeDiscount.setValue(10000.00);

    return promoCodeDiscount;
  }

  private static final List<PromoCodeRule> getPromoCodeRules() {
    PromoCodeRule promoCodeRule = new PromoCodeRule();
    promoCodeRule.setParam("airline_name");
    promoCodeRule.setPromoCodeRuleValue(
        new PromoCodeRuleValueBuilder().withOperator("=").withValue("airasia").build());

    PromoCodeRule promoCodeRule1 = new PromoCodeRule();
    promoCodeRule1.setParam("rute");
    promoCodeRule1.setPromoCodeRuleValue(
        new PromoCodeRuleValueBuilder().withOperator("=").withValue("international").build());

    return Arrays.asList(promoCodeRule, promoCodeRule1);
  }

  private static final PromoCodePriceRange getPromoCodePriceRange() {
    PromoCodePriceRange promoCodePriceRange = new PromoCodePriceRange();
    promoCodePriceRange.setMaxPrice(1000000.00);
    promoCodePriceRange.setMinPrice(0.00);
    promoCodePriceRange.setPromoCodeDiscount(PROMO_CODE_DISCOUNT);

    return promoCodePriceRange;
  }

  private static final List<PromoCodePriceRange> getPromoCodePriceRanges() {
    return Arrays.asList(getPromoCodePriceRange());
  }
}
