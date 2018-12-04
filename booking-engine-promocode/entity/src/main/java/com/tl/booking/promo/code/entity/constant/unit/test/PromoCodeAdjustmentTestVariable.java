package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.Cost;
import com.tl.booking.promo.code.entity.CostBuilder;
import com.tl.booking.promo.code.entity.PaymentMethod;
import com.tl.booking.promo.code.entity.PaymentMethodBuilder;
import com.tl.booking.promo.code.entity.PromoCodeAdjustmentDropdown;
import com.tl.booking.promo.code.entity.PromoCodeAdjustmentDropdownBuilder;
import com.tl.booking.promo.code.entity.PromoCodeCost;
import com.tl.booking.promo.code.entity.PromoCodeCostBuilder;
import com.tl.booking.promo.code.entity.PromoCodeDiscount;
import com.tl.booking.promo.code.entity.PromoCodeDiscountBuilder;
import com.tl.booking.promo.code.entity.PromoCodeDistribution;
import com.tl.booking.promo.code.entity.PromoCodeDistributionBuilder;
import com.tl.booking.promo.code.entity.PromoCodeGroupRule;
import com.tl.booking.promo.code.entity.PromoCodeGroupRuleBuilder;
import com.tl.booking.promo.code.entity.PromoCodePriceRange;
import com.tl.booking.promo.code.entity.PromoCodePriceRangeBuilder;
import com.tl.booking.promo.code.entity.PromoCodeRule;
import com.tl.booking.promo.code.entity.PromoCodeRuleBuilder;
import com.tl.booking.promo.code.entity.PromoCodeRuleValue;
import com.tl.booking.promo.code.entity.PromoCodeRuleValueBuilder;
import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.UsageRuleBuilder;
import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.CalculateType;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeType;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.CampaignBuilder;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustmentBuilder;
import com.tl.booking.promo.code.entity.dao.log.PromoCodeAdjustmentLog;
import com.tl.booking.promo.code.entity.dao.log.PromoCodeAdjustmentLogBuilder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class PromoCodeAdjustmentTestVariable {


  public static String CODE = "strings123";
  public static String CODE_OTHER = "strings12367";
  public static String CODE_EXIST_OTHER = "strings12367";
  public static String NAME = "string";
  public static String ID = "5a523cf15259791fb7a0e9cb";
  public static String USERNAME = "yonathan";
  public static String CHANNEL_ID = "1";
  public static String DESCRIPTION = "1";
  public static Long VERSION = 1L;
  public static Integer IS_DELETED = 0;
  public static Integer IS_DELETED_FOR_DELETED = 1;
  public static Double MAX_DISCOUNT = 10000000.00;
  public static Boolean PROMO_CODE_COMBINE = true;
  public static String BIN_NUMBER = "12123324dfger";
  public static Integer PAYMENT_ID = 0;

  public static String CACHE_KEY =
      CacheKey.PROMO_CODE_ADJUSTMENT + "-" + CommonTestVariable.STORE_ID + "-" + ID;

  public static String CACHE_KEY_FIND_ACTIVATE =
      CacheKey.PROMO_CODE_ADJUSTMENT + "-" + CommonTestVariable.STORE_ID + "-"
          + PromoCodeAdjustmentStatus.ACTIVE;

  public static String CHANNEL_ID_DISTRIBUTION_STRING = "string";
  public static String CHANNEL_ID_DISTRIBUTION_STRING_2 = "string";
  public static String PRODUCT_TYPE = "12345674321";


  public static Double MAX_VALUE = 10.0;
  public static String NAME_COST = "string";
  public static Double PERCENT = 1.0;
  public static Double VALUE_COST = 1.0;

  public static Double MAX_VALUE_DISCOUNT = 10.0;
  public static Double PERCENT_DISCOUNT = 1.0;
  public static Double VALUE_DISCOUNT = 1.0;

  public static Double MAX_VALUE_DISCOUNT_2 = 10.0;
  public static Double PERCENT_DISCOUNT_2 = 1.0;
  public static Double VALUE_DISCOUNT_2 = 1.0;

  public static Double MAX_PRICE_RANGE = 10.0;
  public static Double MIN_PRICE_RANGE = 1.0;

  public static Double MAX_PRICE_RANGE_2 = 20.0;
  public static Double MIN_PRICE_RANGE_2 = 10.1;


  public static String OPERATOR = "String";
  public static String VALUE_RULE = "String";
  public static String PARAM_RULE = "String";

  public static String NAME_GROUP_RULE = "String";

  public static Integer USAGE_COUNT = 100;
  public static final String PROMO_CODE_ADJUSTMENT_CREATE_REQUEST_BODY = "{"
      + " \"calculateType\": \"ORDER_DETAIL\","
      + "\"code\":\"" + CODE + "\","
      + "\"description\":\"" + DESCRIPTION + "\","
      + "\"maxDiscount\":" + MAX_DISCOUNT + ","
      + "\"name\":\"" + NAME + "\","
      + "\"promoCodeCombine\": true,"
      + "\"promoCodeType\":\"" + PromoCodeType.GENERAL.getCode() + "\","
      + "\"promoCodeDistributions\":" + "[" + "{"
      + "\"channelId\":[\"" + CHANNEL_ID_DISTRIBUTION_STRING + "\"],"
      + "\"storeId\":" + "\"" + CommonTestVariable.STORE_ID + "\""
      + "}" + "],"
      + "\"promoCodeGroupRules\":" + "[" + "{"
      + "\"name\":\"" + NAME_GROUP_RULE + "\","
      + "\"promoCodeCost\":"
      + "{" + "\"companyCost\":"
      + "["
      + "{"
      + "\"maxValue\":" + MAX_VALUE + ","
      + "\"name\":\"" + NAME_COST + "\","
      + "\"percent\":" + PERCENT + ","
      + "\"value\":" + VALUE_COST + "" + "}" + "],"
      + "\"partnerCost\":" + "[" + "{"
      + "\"maxValue\":" + MAX_VALUE + ","
      + "\"name\":\"" + NAME_COST + "\","
      + "\"percent\":" + PERCENT + ","
      + "\"value\":" + VALUE_COST + "}" + "]" + "},"
      + "\"promoCodePriceRanges\":" + "[" + "{"
      + "\"maxPrice\":" + MAX_PRICE_RANGE + ","
      + "\"minPrice\":" + MIN_PRICE_RANGE + ","
      + "\"promoCodeDiscount\":"
      + "{" + "\"maxValue\":" + MAX_VALUE_DISCOUNT + ","
      + "\"percent\":" + PERCENT_DISCOUNT + ","
      + "\"value\":" + VALUE_DISCOUNT + "" + "}" + "}" + "],"
      + "\"promoCodeRules\":"
      + "[" + "{"
      + "\"productType\":\"" + PRODUCT_TYPE + "\","
      + "\"param\":\"" + PARAM_RULE + "\","
      + "\"usedForCalculate\": true"
      + ","
      + "\"promoCodeRuleValue\":"
      + "{" + "\"operator\":\"" + OPERATOR + "\","
      + "\"value\":\"" + VALUE_RULE + "\""
      + "}" + "}" + "]"
      + "}" + "],"

      + "\"paymentMethods\": [\n"
      + "    {\n"
      + "      \"binNumbers\": [\n"
      + "        \"" + BIN_NUMBER + "\"\n"
      + "      ],\n"
      + "      \"paymentId\": 0\n"
      + "    }\n"
      + "  ],"

      + "\"usageRules\":"
      + "["
      + "{"
      + "\"usageCount\":" + USAGE_COUNT + ","
      + "\"usagePeriod\":\"" + UsagePeriod.DAILY.getCode() + "\", \"validatedBy\" : \"USERNAME\""
      + "}"
      + "], \"validAllOrderDetails\" : true"
      + "}";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static String PROMO_CODE_ADJUSTMENT_STATUS_DRAFT = "DRAFT";
  public static Set<String> BIN_NUMBERS = binNumbers();
  public static PaymentMethod PAYMENT_METHOD = new PaymentMethodBuilder()
      .withBinNumbers(BIN_NUMBERS)
      .withPaymentId(PAYMENT_ID)
      .build();

  public static List<PaymentMethod> PAYMENT_METHODS = Arrays.asList(PAYMENT_METHOD);


  public static Set<String> CHANNEL_ID_DISTRIBUTION = new HashSet<String>(
      Arrays.asList(CHANNEL_ID_DISTRIBUTION_STRING));
  public static Set<String> CHANNEL_ID_DISTRIBUTION_2 = new HashSet<String>(
      Arrays.asList(CHANNEL_ID_DISTRIBUTION_STRING_2));

  public static PromoCodeDistribution PROMO_CODE_DISTRIBUTION = new PromoCodeDistributionBuilder()
      .withChannelId(CHANNEL_ID_DISTRIBUTION)
      .withStoreId(CommonTestVariable.STORE_ID)
      .build();

  public static PromoCodeDistribution PROMO_CODE_DISTRIBUTION_2 = new PromoCodeDistributionBuilder()
      .withChannelId(CHANNEL_ID_DISTRIBUTION_2)
      .withStoreId(CommonTestVariable.STORE_ID)
      .build();

  public static Set<PromoCodeDistribution> PROMO_CODE_DISTRIBUTIONS = new HashSet<>(
      Arrays.asList(PROMO_CODE_DISTRIBUTION));


  public static Cost PARTNER_COST = new CostBuilder()
      .withMaxValue(MAX_VALUE)
      .withName(NAME_COST)
      .withPercent(PERCENT)
      .withValue(VALUE_COST)
      .build();

  public static Cost COMPANY_COST = new CostBuilder()
      .withMaxValue(MAX_VALUE)
      .withName(NAME_COST)
      .withPercent(PERCENT)
      .withValue(VALUE_COST)
      .build();

  public static List<Cost> PARTNER_COSTS = Arrays.asList(PARTNER_COST);
  public static List<Cost> COMPANY_COSTS = Arrays.asList(COMPANY_COST);

  public static PromoCodeCost PROMO_CODE_COST = new PromoCodeCostBuilder()
      .withCompanyCost(COMPANY_COSTS)
      .withPartnerCost(PARTNER_COSTS)
      .build();

  public static PromoCodeDiscount PROMO_CODE_DISCOUNT = new PromoCodeDiscountBuilder()
      .withMaxValue(MAX_VALUE_DISCOUNT)
      .withPercent(PERCENT_DISCOUNT)
      .withValue(VALUE_DISCOUNT)
      .build();

  public static PromoCodeDiscount PROMO_CODE_DISCOUNT_2 = new PromoCodeDiscountBuilder()
      .withMaxValue(MAX_VALUE_DISCOUNT_2)
      .withPercent(PERCENT_DISCOUNT_2)
      .withValue(VALUE_DISCOUNT_2)
      .build();


  public static PromoCodePriceRange PROMO_CODE_PRICE_RANGE = new PromoCodePriceRangeBuilder()
      .withMaxPrice(MAX_PRICE_RANGE)
      .withMinPrice(MIN_PRICE_RANGE)
      .withPromoCodeDiscount(PROMO_CODE_DISCOUNT)
      .build();

  public static PromoCodePriceRange PROMO_CODE_PRICE_RANGE_2 = new PromoCodePriceRangeBuilder()
      .withMaxPrice(MAX_PRICE_RANGE_2)
      .withMinPrice(MIN_PRICE_RANGE_2)
      .withPromoCodeDiscount(PROMO_CODE_DISCOUNT_2)
      .build();

  public static List<PromoCodePriceRange> PROMO_CODE_PRICE_RANGES = Arrays
      .asList(PROMO_CODE_PRICE_RANGE);

  public static PromoCodeRuleValue PROMO_CODE_RULE_VALUE = new PromoCodeRuleValueBuilder()
      .withOperator(OPERATOR)
      .withValue(VALUE_RULE)
      .build();

  public static PromoCodeRule PROMO_CODE_RULE = new PromoCodeRuleBuilder()
      .withParam(PARAM_RULE)
      .withPromoCodeRuleValue(PROMO_CODE_RULE_VALUE)
      .withUsedForCalculate(true)
      .withProductType(PRODUCT_TYPE)
      .build();

  public static List<PromoCodeRule> PROMO_CODE_RULES = Arrays.asList(PROMO_CODE_RULE);

  public static PromoCodeGroupRule PROMO_CODE_GROUP_RULE = new PromoCodeGroupRuleBuilder()
      .withName(NAME_GROUP_RULE)
      .withPromoCodeCost(PROMO_CODE_COST)
      .withPromoCodePriceRanges(PROMO_CODE_PRICE_RANGES)
      .withPromoCodeRules(PROMO_CODE_RULES)
      .build();

  public static List<PromoCodeGroupRule> PROMO_CODE_GROUP_RULES = Arrays
      .asList(PROMO_CODE_GROUP_RULE);

  public static UsageRule USAGE_RULE = new UsageRuleBuilder()
      .withUsageCount(USAGE_COUNT)
      .withUsagePeriod(UsagePeriod.DAILY)
      .withValidatedBy(ValidatedBy.USERNAME)
      .build();
  public static UsageRule USAGE_RULE_ZERO = new UsageRuleBuilder()
      .withUsageCount(0)
      .withUsagePeriod(UsagePeriod.DAILY)
      .build();

  public static List<UsageRule> USAGE_RULES_ZERO = Arrays.asList(USAGE_RULE_ZERO);
  public static List<UsageRule> USAGE_RULES = Arrays.asList(USAGE_RULE);
  public static List<UsageRule> USAGE_RULES_EMPTY = Arrays.asList();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withIsDeleted(0)
      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withCode(CODE)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT)
      .withIsDeleted(IS_DELETED)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES)
      .withValidAllOrderDetails(true)
      .build();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_ZERO_USAGE_RULE = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withIsDeleted(0)

      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withCode(CODE)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT)
      .withIsDeleted(IS_DELETED)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES_EMPTY)
      .withValidAllOrderDetails(true)
      .build();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_ZERO_RULE_VALUE = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withIsDeleted(0)

      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withCode(CODE)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT)
      .withIsDeleted(IS_DELETED)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES_ZERO)
      .withValidAllOrderDetails(true)
      .build();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_2 = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withIsDeleted(0)
      .withCode(CODE_OTHER)
      .withName(NAME)
      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withDescription(DESCRIPTION)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT)
      .withIsDeleted(IS_DELETED)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES)
      .withValidAllOrderDetails(true)
      .build();

  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_3 = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(USERNAME)
      .withUpdatedBy(USERNAME)
      .withChannelId(CHANNEL_ID)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withIsDeleted(0)

      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withCode(CODE_OTHER)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.DRAFT)
      .withIsDeleted(IS_DELETED)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES)
      .withValidAllOrderDetails(true)
      .build();


  public static List<PromoCodeAdjustment> PROMO_CODE_ADJUSTMENTS = Arrays
      .asList(PROMO_CODE_ADJUSTMENT, PROMO_CODE_ADJUSTMENT_2, PROMO_CODE_ADJUSTMENT_3);

  public static List<PromoCodeAdjustment> PROMO_CODE_ADJUSTMENTS_2 = Arrays
      .asList(PROMO_CODE_ADJUSTMENT);


  public static Page<PromoCodeAdjustment> PROMO_CODE_ADJUSTMENTS_PAGE = new PageImpl<>(
      PROMO_CODE_ADJUSTMENTS_2);

  public static List<PromoCodeAdjustment> PROMO_CODE_ADJUSTMENTS_NULLS = Arrays.asList();

  public static Page<PromoCodeAdjustment> PROMO_CODE_ADJUSTMENTS_PAGE_NULL = new PageImpl<>(
      PROMO_CODE_ADJUSTMENTS_NULLS);


  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_REQUEST = new PromoCodeAdjustmentBuilder()

      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCode(CODE)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withUsageRules(USAGE_RULES)
      .withPaymentMethods(PAYMENT_METHODS)
      .withValidAllOrderDetails(true)
      .build();


  public static PromoCodeAdjustment PROMO_CODE_ADJUSTMENT_CREATE_RESPONSE = new PromoCodeAdjustmentBuilder()
      .withId(ID)
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)

      .withCalculateType(CalculateType.ORDER_DETAIL)
      .withCode(CODE)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withIsDeleted(IS_DELETED)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withUsageRules(USAGE_RULES)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.INACTIVE)
      .withValidAllOrderDetails(true)
      .build();
  public static PromoCodeAdjustmentDropdown PROMO_CODE_ADJUSTMENT_DROPDOWN_1 = new PromoCodeAdjustmentDropdownBuilder()
      .withId(ID)
      .withName(NAME)
      .build();
  public static List<PromoCodeAdjustmentDropdown> PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST = Arrays
      .asList(PROMO_CODE_ADJUSTMENT_DROPDOWN_1);
  public static List<PromoCodeAdjustment> PROMO_CODE_ADJUSTMENT_RESOURCE_DROPDOWN = Arrays
      .asList(PROMO_CODE_ADJUSTMENT);
  public static PromoCodeAdjustmentLog PROMO_CODE_ADJUSTMENT_LOG = new PromoCodeAdjustmentLogBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)

      .withValue(PROMO_CODE_ADJUSTMENT)
      .build();

  public static Set<String> binNumbers() {
    Set<String> setString = new HashSet<>();
    setString.add(BIN_NUMBER);
    return setString;
  }

  public static Campaign CAMPAIGN_REQUEST_ACTIVE = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ID)
      .withCampaignStatus(CampaignStatus.ACTIVE)

      .build();

  public static Campaign CAMPAIGN_REQUEST = new CampaignBuilder()

      .withStoreId(CommonTestVariable.STORE_ID)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withIsDeleted(IS_DELETED)

      .withId(ID)
      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ID)
      .withCampaignStatus(CampaignStatus.INACTIVE)

      .build();

  public static List<Campaign> CAMPAIGN_LIST = Arrays.asList(CAMPAIGN_REQUEST);

}
