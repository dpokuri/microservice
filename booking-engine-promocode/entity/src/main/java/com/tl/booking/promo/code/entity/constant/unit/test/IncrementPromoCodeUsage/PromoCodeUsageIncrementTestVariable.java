package com.tl.booking.promo.code.entity.constant.unit.test.IncrementPromoCodeUsage;

import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.CalculationResultBuilder;
import com.tl.booking.promo.code.entity.CampaignPeriod;
import com.tl.booking.promo.code.entity.CampaignPeriodBuilder;
import com.tl.booking.promo.code.entity.Cost;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.CostAmountBuilder;
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
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.entity.constant.unit.test.CalculationPromoCode.UsedPromoCodeTestVariable;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

public class PromoCodeUsageIncrementTestVariable {

  public static final Map<String, Variable> MAP_VARIABLES = getMapVariables();
  public static final List<Variable> VARIABLES = getVariables();
  public static final Set<String> USED_PROMO_CODES = getUsedPromoCodes();
  public static final PromoCodeDiscount PROMO_CODE_DISCOUNT = getPromoCodeDiscount();
  public static final PromoCodePriceRange PROMO_CODE_PRICE_RANGE = getPromoCodePriceRange();
  public static final List<PromoCodePriceRange> PROMO_CODE_PRICE_RANGES = getPromoCodePriceRanges();
  public static final List<PromoCodeRule> PROMO_CODE_RULES = getPromoCodeRules();
  public static final String PROMO_CODE_JSON_BODY = "{\"campaignId\": \"CAMPID\",\"code\": \"PROMOCODE\",\"maxQty\": 100}";
  public static final String REFERENCE_ID = "123123434";
  public static final Double TOTAL_PRICE = 2000000.00;
  public static String CARD_NUMBER = "1231237889";
  public static Double MAX_COMPANY_COST = 10.00;
  public static Double MAX_PARTNER_COST = 10.00;
  public static Date START_DATE = new DateTime().minusHours(1).toDate();
  public static Date END_DATE = new DateTime().plusDays(2).toDate();
  public static Date START_DATE_1 = new DateTime().plusDays(10).toDate();
  public static Date END_DATE_1 = new DateTime().plusDays(11).toDate();
  public static Date START_DATE_EXPIRED = new DateTime().minusDays(2).toDate();
  public static Date END_DATE_EXPIRED = new DateTime().minusDays(1).toDate();
  public static Double EXPECTED_DISCOUNT_AMOUNT = 12000.00;
  public static Double DISCOUNT_AMOUNT = 10000.00;
  public static final List<OrderDetail> ORDER_DETAILS = getOrderDetails();
  public static Integer DAILY_USAGE_COUNT = 1;
  public static Integer PERIOD_USAGE_COUNT = 10;
  public static String ID = "PROMOCODEID";
  public static String CODE = "PROMOCODEINCREMENT";
  public static String VALUE = "value";
  public static String USERNAME = "development";
  public static Integer MAX_QTY = 100;
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static String PROMO_CODE_USAGE_ID = "8888";
  public static String CAMPAIGN_ID = "CAMPID";
  public static String CAMPAIGN_ID_EXIST = "CAMPIDEXIST";
  public static String CAMPAIGN_NAME = "CAMPNAME";
  public static String ADJUSTMENT_ID = "ADJID";
  public static String SORT = "CODE";
  public static String SORT_DIRECTION = "ASC";
  public static OrderDetail ORDER_DETAIL = getOrderDetail();
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
  public static Cost PARTNER_COST = getPartnerCost();
  public static Cost COMPANY_COST = getCompanyCost();
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
      .withPromoCodeCombine(false)
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
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_CARD_NUMBER = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(0)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername("*")
      .withCardNumber(CARD_NUMBER)
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(0)
      .withStartDate(START_DATE_1)
      .withEndDate(START_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY_CARD_NUMBER = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(0)
      .withStartDate(START_DATE_1)
      .withEndDate(START_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername("*")
      .withCardNumber(CARD_NUMBER)
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_GENERAL = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE)
      .withEndDate(END_DATE)
      .withStoreId("*")
      .withChannelId("*")
      .withUsername("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_GENERAL_FOR_INCREMENT = new PromoCodeUsageBuilder()
      .withPromoCode(CODE)
      .withPromoCodeId(ID)
      .withUsageCount(1)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId("*")
      .withChannelId("*")
      .withUsername("*")
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_GENERAL_FOR_INCREMENT_CARD_NUMBER = new
      PromoCodeUsageBuilder()
      .withPromoCode(CODE)
      .withPromoCodeId(ID)
      .withUsageCount(1)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId("*")
      .withChannelId("*")
      .withUsername("*")
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY_FOR_INCREMENT = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE_1)
      .withEndDate(START_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY_FOR_INCREMENT_EXIST_QTY = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY_FOR_INCREMENT_CARD_NUMBER = new
      PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE_1)
      .withEndDate(START_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CARD_NUMBER)
      .withCardNumber(CARD_NUMBER)
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_DAILY_FOR_INCREMENT_CARD_NUMBER_EXIST_QTY = new
      PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername("*")
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_FOR_INCREMENT = new PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .withCardNumber("*")
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static PromoCodeUsage PROMO_CODE_USAGE_FOR_INCREMENT_CARD_NUMBER = new
      PromoCodeUsageBuilder()
      .withPromoCode(PROMO_CODE.getCode())
      .withPromoCodeId(PROMO_CODE.getId())
      .withUsageCount(1)
      .withStartDate(START_DATE_EXPIRED)
      .withEndDate(END_DATE_1)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername("*")
      .withCardNumber(CARD_NUMBER)
      .withId(PROMO_CODE_USAGE_ID)
      .build();
  public static List<CostAmount> COMPANY_COST_AMOUNT = new ArrayList<>();
  public static List<CostAmount> PARTNER_COST_AMOUNT = new ArrayList<>();

  private static final List<Variable> getVariables() {
    Variable variable = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">="))
        .withDataType(DataType.STRING)
        .withParam("airline_name")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable2 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">="))
        .withDataType(DataType.STRING)
        .withParam("rute")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable3 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">=", ">"))
        .withDataType(DataType.INTEGER)
        .withParam("adult")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable4 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">=", ">", "<=", "<"))
        .withDataType(DataType.DOUBLE)
        .withParam("tax")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable5 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">=", ">", "<"))
        .withDataType(DataType.DATE)
        .withParam("departure_date")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable6 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "<>", "<=", ">=", ">", "<"))
        .withDataType(DataType.DATE)
        .withParam("return_date")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable7 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN"))
        .withDataType(DataType.STRING)
        .withParam("allowed_airline")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable8 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>"))
        .withDataType(DataType.STRING)
        .withParam("airline_code")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable9 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>"))
        .withDataType(DataType.DATE)
        .withParam("transit_date")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable10 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>"))
        .withDataType(DataType.DATE)
        .withParam("transit_date_exclude")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable11 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>", "<", "<="))
        .withDataType(DataType.INTEGER)
        .withParam("child")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable12 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>", "<", "<="))
        .withDataType(DataType.INTEGER)
        .withParam("infant")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable13 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>"))
        .withDataType(DataType.INTEGER)
        .withParam("airline_id")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    Variable variable14 = new VariableBuilder()
        .withAllowedArithmetics(Arrays.asList("=", "IN", "<>"))
        .withDataType(DataType.DOUBLE)
        .withParam("charge")
        .withInputSource("")
        .withInputType(InputType.TEXT)
        .build();

    return Arrays.asList(
        variable, variable2, variable3, variable4,
        variable5, variable6, variable7, variable8,
        variable9, variable10, variable11, variable12,
        variable13, variable14
    );
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
    orderDetail.setDiscount(DISCOUNT_AMOUNT);

    Map<String, String> orderAttributes = new HashMap<>();
    orderAttributes.put("asdasdqweqwe", "qweqweqwe");
    orderAttributes.put("airline_name", "airasia");
    orderAttributes.put("adult", "5");
    orderAttributes.put("tax", "20000.00");
    orderAttributes.put("charge", "10000.00");
    orderAttributes.put("departure_date", "2010-01-08");
    orderAttributes.put("return_date", "2000-02-08");
    orderAttributes.put("allowed_airline", "airasia");
    orderAttributes.put("airline_code", "B2222");
    orderAttributes.put("transit_date", "1990-02-10");
    orderAttributes.put("transit_date_exclude", "1990-02-10");
    orderAttributes.put("child", "2");
    orderAttributes.put("infant", "0");
    orderAttributes.put("airline_id", "1");
    orderDetail.setOrderAttribute(orderAttributes);
    orderDetail.setReferenceId("string");

    return orderDetail;
  }

  private static final List<OrderDetail> getOrderDetails() {
    OrderDetail orderDetail = getOrderDetail();
    return Arrays.asList(orderDetail);
  }

  private static final Set<String> getUsedPromoCodes() {
    Set<String> usedPromoCodes = new HashSet<>();
    usedPromoCodes.add(UsedPromoCodeTestVariable.CODE);
    return usedPromoCodes;
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



  private static List<CostAmount> getCompanyCostAmount() {
    List<CostAmount> companyCostAmount = new ArrayList<>();
    companyCostAmount.add(new CostAmountBuilder().withName("ggCompany").withAmount(20000.00).build
        ());

    return companyCostAmount;
  }

  private static List<CostAmount> getPartnerCostAmount() {
    List<CostAmount> partnerCostAmount = new ArrayList<>();
    partnerCostAmount.add(new CostAmountBuilder().withName("ggPartner").withAmount(10000.00).build
        ());

    return partnerCostAmount;
  }

  private static final List<UsageRule> getUsageRules() {
    UsageRule usageRule = new UsageRule();
    usageRule.setUsageCount(PERIOD_USAGE_COUNT);
    usageRule.setUsagePeriod(UsagePeriod.PERIOD);
    usageRule.setValidatedBy(ValidatedBy.USERNAME);

    UsageRule usageRule1 = new UsageRule();
    usageRule1.setUsagePeriod(UsagePeriod.DAILY);
    usageRule1.setUsageCount(DAILY_USAGE_COUNT);
    usageRule1.setValidatedBy(ValidatedBy.USERNAME);

    return Arrays.asList(usageRule, usageRule1);
  }

  private static final Cost getCompanyCost() {
    Cost companyCost = new Cost();
    companyCost.setName("Marketing");
    companyCost.setPercent(10.00);
    companyCost.setValue(5000.00);
    companyCost.setMaxValue(MAX_COMPANY_COST);

    return companyCost;
  }

  private static final Cost getPartnerCost() {
    Cost partnerCost = new Cost();
    partnerCost.setName("BCA");
    partnerCost.setPercent(10.00);
    partnerCost.setValue(5000.00);
    partnerCost.setMaxValue(MAX_PARTNER_COST);

    return partnerCost;
  }

  private static final PromoCodeCost getPromoCodeCost() {
    PromoCodeCost promoCodeCost = new PromoCodeCost();
    promoCodeCost.setCompanyCost(Arrays.asList(COMPANY_COST));
    promoCodeCost.setPartnerCost(Arrays.asList(PARTNER_COST));

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
    promoCodeDiscount.setMaxValue(10.00);
    promoCodeDiscount.setPercent(10.00);
    promoCodeDiscount.setValue(10000.00);

    return promoCodeDiscount;
  }

  public static final List<PromoCodeRule> getPromoCodeRules() {
    PromoCodeRule promoCodeRule = new PromoCodeRule();
    promoCodeRule.setParam("airline_name");
    promoCodeRule.setPromoCodeRuleValue(
        new PromoCodeRuleValueBuilder().withOperator("=").withValue("airasia").build());

    PromoCodeRule promoCodeRule1 = new PromoCodeRule();
    promoCodeRule1.setParam("rute");
    promoCodeRule1.setPromoCodeRuleValue(
        new PromoCodeRuleValueBuilder().withOperator("=").withValue("international").build());

    PromoCodeRule promoCodeRule2 = new PromoCodeRule();
    promoCodeRule2.setParam("adult");
    promoCodeRule2.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator(">=")
        .withValue("1").build());

    PromoCodeRule promoCodeRule3 = new PromoCodeRule();
    promoCodeRule3.setParam("tax");
    promoCodeRule3.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator(">=")
        .withValue("10000.00").build());

    PromoCodeRule promoCodeRule4 = new PromoCodeRule();
    promoCodeRule4.setParam("departure_date");
    promoCodeRule4.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<=")
        .withValue("2100-01-01").build());

    PromoCodeRule promoCodeRule5 = new PromoCodeRule();
    promoCodeRule5.setParam("return_date");
    promoCodeRule5.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator(">=")
        .withValue("1990-02-09").build());

    PromoCodeRule promoCodeRule6 = new PromoCodeRule();
    promoCodeRule6.setParam("allowed_airline");
    promoCodeRule6.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("IN")
        .withValue("airasia|lion|tiger").build());

    PromoCodeRule promoCodeRule7 = new PromoCodeRule();
    promoCodeRule7.setParam("airline_code");
    promoCodeRule7.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<>")
        .withValue("B22").build());

    PromoCodeRule promoCodeRule8 = new PromoCodeRule();
    promoCodeRule8.setParam("airline_name");
    promoCodeRule8.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<>")
        .withValue("liong").build());

    PromoCodeRule promoCodeRule9 = new PromoCodeRule();
    promoCodeRule9.setParam("departure_date");
    promoCodeRule9.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<")
        .withValue("2100-01-01").build());

    PromoCodeRule promoCodeRule10 = new PromoCodeRule();
    promoCodeRule10.setParam("return_date");
    promoCodeRule10.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator(">")
        .withValue("1990-02-09").build());

    PromoCodeRule promoCodeRule11 = new PromoCodeRule();
    promoCodeRule11.setParam("transit_date");
    promoCodeRule11.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("=")
        .withValue("1990-02-10").build());

    PromoCodeRule promoCodeRule12 = new PromoCodeRule();
    promoCodeRule12.setParam("transit_date_exclude");
    promoCodeRule12.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<>")
        .withValue("1990-02-11").build());

    PromoCodeRule promoCodeRule13 = new PromoCodeRule();
    promoCodeRule13.setParam("child");
    promoCodeRule13.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<>")
        .withValue("1").build());

    PromoCodeRule promoCodeRule14 = new PromoCodeRule();
    promoCodeRule14.setParam("infant");
    promoCodeRule14.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("=")
        .withValue("0").build());

    PromoCodeRule promoCodeRule15 = new PromoCodeRule();
    promoCodeRule15.setParam("airline_id");
    promoCodeRule15.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("IN")
        .withValue("1,2,3,4").build());

    PromoCodeRule promoCodeRule16 = new PromoCodeRule();
    promoCodeRule16.setParam("child");
    promoCodeRule16.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<")
        .withValue("10").build());

    PromoCodeRule promoCodeRule17 = new PromoCodeRule();
    promoCodeRule17.setParam("infant");
    promoCodeRule17.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<=")
        .withValue("0").build());

    PromoCodeRule promoCodeRule18 = new PromoCodeRule();
    promoCodeRule18.setParam("adult");
    promoCodeRule18.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator(">")
        .withValue("0").build());

    PromoCodeRule promoCodeRule19 = new PromoCodeRule();
    promoCodeRule19.setParam("tax");
    promoCodeRule19.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<=")
        .withValue("10000000000.00").build());

    PromoCodeRule promoCodeRule20 = new PromoCodeRule();
    promoCodeRule20.setParam("tax");
    promoCodeRule20.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<")
        .withValue("100000000000.00").build());

    PromoCodeRule promoCodeRule21 = new PromoCodeRule();
    promoCodeRule21.setParam("tax");
    promoCodeRule21.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator(">")
        .withValue("10.00").build());

    PromoCodeRule promoCodeRule22 = new PromoCodeRule();
    promoCodeRule22.setParam("tax");
    promoCodeRule22.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("<>")
        .withValue("12200.00").build());

    PromoCodeRule promoCodeRule23 = new PromoCodeRule();
    promoCodeRule23.setParam("charge");
    promoCodeRule23.setPromoCodeRuleValue(new PromoCodeRuleValueBuilder().withOperator("=")
        .withValue("10000.00").build());

    return Arrays.asList(
        promoCodeRule, promoCodeRule1, promoCodeRule2, promoCodeRule3,
        promoCodeRule4, promoCodeRule5, promoCodeRule6, promoCodeRule7, promoCodeRule8,
        promoCodeRule9, promoCodeRule10, promoCodeRule11, promoCodeRule12, promoCodeRule13,
        promoCodeRule14, promoCodeRule15, promoCodeRule16, promoCodeRule17,
        promoCodeRule18, promoCodeRule19, promoCodeRule20, promoCodeRule21,
        promoCodeRule22, promoCodeRule23
    );
  }

  public static final List<PromoCodeRule> getBlankOperatorPromoCodeRules() {
    PromoCodeRule promoCodeRule = new PromoCodeRule();
    promoCodeRule.setParam("airline_name");
    promoCodeRule.setPromoCodeRuleValue(
        new PromoCodeRuleValueBuilder().withOperator("").withValue("airasia").build());

    return Arrays.asList(
        promoCodeRule
    );
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

  private static final PromoCodeObject getPromoCodeObject() {
    PromoCodeObject promoCodeObject = new PromoCodeObject();
    promoCodeObject.setVariableMap(getMapVariables());
    promoCodeObject.setPromoCodeAdjustment(PROMO_CODE_ADJUSTMENT);
    promoCodeObject.setCampaign(CAMPAIGN);
    promoCodeObject.setPromoCode(PROMO_CODE);
    return promoCodeObject;
  }


  public static final CalculationResult CALCULATION_RESULT = new CalculationResultBuilder()
      .withOrderDetails(ORDER_DETAILS)
      .withPromoCodeObject(PROMO_CODE_OBJECT)
      .withTotalDiscount(DISCOUNT_AMOUNT)
      .withTotalPrice(2000000.00)
      .withUsedPromoCodes(USED_PROMO_CODES)
      .build();
}
