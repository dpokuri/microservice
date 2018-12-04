package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeType;
import com.tl.booking.gateway.entity.constant.enums.UsagePeriod;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CostRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CostRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.CostResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CostResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentMethodRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentMethodRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentMethodResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentMethodResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeCostRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeCostRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeCostResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeCostResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDiscountRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDiscountRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDiscountResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDiscountResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDistributionRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDistributionRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDistributionResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeDistributionResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeGroupRuleRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeGroupRuleRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeGroupRuleResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeGroupRuleResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodePriceRangeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodePriceRangeRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodePriceRangeResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodePriceRangeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleValueRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleValueRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleValueResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRuleValueResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.UsageRuleRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.UsageRuleRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.UsageRuleResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.UsageRuleResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  public static Integer MAX_DISCOUNT_INT = 10000000;
  public static Boolean PROMO_CODE_COMBINE = true;
  public static String BIN_NUMBER = "12123324dfger";
  public static Integer PAYMENT_ID = 0;

  public static String CHANNEL_ID_DISTRIBUTION_STRING = "string";
  public static String CHANNEL_ID_DISTRIBUTION_STRING_2 = "string";


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
  public static Object VALUE_RULE = "String";
  public static String PARAM_RULE = "String";

  public static String NAME_GROUP_RULE = "String";

  public static Integer USAGE_COUNT = 100;
  public static final String PROMO_CODE_ADJUSTMENT_CREATE_REQUEST_BODY = "{"
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
      + "[" + "{" + "\"param\":\"" + PARAM_RULE + "\","
      + "\"promoCodeRuleValue\":"
      + "{" + "\"operator\":\"" + OPERATOR + "\","
      + "\"value\":\"" + VALUE_RULE + "\"" + ","
      + "\"usedForCalculate\": true"

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
      + "\"usagePeriod\":\"" + UsagePeriod.DAILY.getCode() + "\","
      + "\"validatedBy\":\"" + USERNAME + "\""
      + "}"
      + "]"
      + "}";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static String PROMO_CODE_ADJUSTMENT_STATUS_DRAFT = "DRAFT";



  public static Set<String> BIN_NUMBERS = binNumbers();

  public static PaymentMethodResponse PAYMENT_METHOD_RESPONSE = new PaymentMethodResponseBuilder()
      .withBinNumbers(BIN_NUMBERS)
      .withPaymentId(PAYMENT_ID)
      .build();

  public static List<PaymentMethodResponse> PAYMENT_METHODS_RESPONSE = Arrays.asList(PAYMENT_METHOD_RESPONSE);


  public static Set<String> CHANNEL_ID_DISTRIBUTION = new HashSet<String>(
      Arrays.asList(CHANNEL_ID_DISTRIBUTION_STRING));
  public static Set<String> CHANNEL_ID_DISTRIBUTION_2 = new HashSet<String>(
      Arrays.asList(CHANNEL_ID_DISTRIBUTION_STRING_2));

  public static PromoCodeDistributionResponse PROMO_CODE_DISTRIBUTION = new PromoCodeDistributionResponseBuilder()
      .withChannelId(CHANNEL_ID_DISTRIBUTION)
      .withStoreId(CommonTestVariable.STORE_ID)
      .build();

  public static Set<PromoCodeDistributionResponse> PROMO_CODE_DISTRIBUTIONS = new HashSet<>(
      Arrays.asList(PROMO_CODE_DISTRIBUTION));


  public static CostResponse PARTNER_COST = new CostResponseBuilder()
      .withMaxValue(MAX_VALUE)
      .withName(NAME_COST)
      .withPercent(PERCENT)
      .withValue(VALUE_COST)
      .build();

  public static CostResponse COMPANY_COST = new CostResponseBuilder()
      .withMaxValue(MAX_VALUE)
      .withName(NAME_COST)
      .withPercent(PERCENT)
      .withValue(VALUE_COST)
      .build();

  public static List<CostResponse> PARTNER_COSTS = Arrays.asList(PARTNER_COST);
  public static List<CostResponse> COMPANY_COSTS = Arrays.asList(COMPANY_COST);

  public static PromoCodeCostResponse PROMO_CODE_COST = new PromoCodeCostResponseBuilder()
      .withCompanyCost(COMPANY_COSTS)
      .withPartnerCost(PARTNER_COSTS)
      .build();

  public static PromoCodeDiscountResponse PROMO_CODE_DISCOUNT = new PromoCodeDiscountResponseBuilder()
      .withMaxValue(MAX_VALUE_DISCOUNT)
      .withPercent(PERCENT_DISCOUNT)
      .withValue(VALUE_DISCOUNT)
      .build();

  public static PromoCodePriceRangeResponse PROMO_CODE_PRICE_RANGE = new PromoCodePriceRangeResponseBuilder()
      .withMaxPrice(MAX_PRICE_RANGE)
      .withMinPrice(MIN_PRICE_RANGE)
      .withPromoCodeDiscount(PROMO_CODE_DISCOUNT)
      .build();


  public static List<PromoCodePriceRangeResponse> PROMO_CODE_PRICE_RANGES = Arrays
      .asList(PROMO_CODE_PRICE_RANGE);

  public static PromoCodeRuleValueResponse PROMO_CODE_RULE_VALUE = new PromoCodeRuleValueResponseBuilder()
      .withOperator(OPERATOR)
      .withValue(VALUE_RULE)
      .build();

  public static PromoCodeRuleResponse PROMO_CODE_RULE = new PromoCodeRuleResponseBuilder()
      .withParam(PARAM_RULE)
      .withPromoCodeRuleValue(PROMO_CODE_RULE_VALUE)
      .build();

  public static List<PromoCodeRuleResponse> PROMO_CODE_RULES = Arrays.asList(PROMO_CODE_RULE);

  public static PromoCodeGroupRuleResponse PROMO_CODE_GROUP_RULE = new PromoCodeGroupRuleResponseBuilder()
      .withName(NAME_GROUP_RULE)
      .withPromoCodeCost(PROMO_CODE_COST)
      .withPromoCodePriceRanges(PROMO_CODE_PRICE_RANGES)
      .withPromoCodeRules(PROMO_CODE_RULES)
      .build();

  public static List<PromoCodeGroupRuleResponse> PROMO_CODE_GROUP_RULES = Arrays
      .asList(PROMO_CODE_GROUP_RULE);

  public static UsageRuleResponse USAGE_RULE = new UsageRuleResponseBuilder()
      .withUsageCount(USAGE_COUNT)
      .withUsagePeriod(UsagePeriod.DAILY)
      .build();
  public static UsageRuleResponse USAGE_RULE_ZERO = new UsageRuleResponseBuilder()
      .withUsageCount(0)
      .withUsagePeriod(UsagePeriod.DAILY)
      .build();

  public static List<UsageRuleResponse> USAGE_RULES_ZERO = Arrays.asList(USAGE_RULE_ZERO);
  public static List<UsageRuleResponse> USAGE_RULES = Arrays.asList(USAGE_RULE);
  public static List<UsageRuleResponse> USAGE_RULES_EMPTY = Arrays.asList();

  public static PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT_RESPONSE = new PromoCodeAdjustmentResponseBuilder()
      .withId(ID)
      .withName(NAME)

      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE)
      .build();


  public static PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT_CREATE_RESPONSE = new PromoCodeAdjustmentResponseBuilder()
      .withId(ID)
      .withName(NAME)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES)
      .withUsageRules(USAGE_RULES)
      .withPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.INACTIVE)
      .build();



  public static PromoCodeAdjustmentDropdownResponse PROMO_CODE_ADJUSTMENT_DROPDOWN_1 = new PromoCodeAdjustmentDropdownResponseBuilder()
      .withId(ID)
      .withName(NAME)
      .build();

  public static List<PromoCodeAdjustmentDropdownResponse> PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST = Arrays
      .asList(PROMO_CODE_ADJUSTMENT_DROPDOWN_1);


  private static GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST);

    return baseResponse;
  }


  public static GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>> PRO_DROPDOWN_RESPONSE_LIST = getCampaignActiveResponse();



  public static PaymentMethodRequest PAYMENT_METHOD_REQUEST = new PaymentMethodRequestBuilder()
      .withBinNumbers(BIN_NUMBERS)
      .withPaymentId(PAYMENT_ID)
      .build();

  public static List<PaymentMethodRequest> PAYMENT_METHODS_REQUEST = Arrays.asList(PAYMENT_METHOD_REQUEST);


  public static Set<String> CHANNEL_ID_DISTRIBUTION_REQUEST = new HashSet<String>(
      Arrays.asList(CHANNEL_ID_DISTRIBUTION_STRING));
  public static Set<String> CHANNEL_ID_DISTRIBUTION_2_REQUEST = new HashSet<String>(
      Arrays.asList(CHANNEL_ID_DISTRIBUTION_STRING_2));

  public static PromoCodeDistributionRequest PROMO_CODE_DISTRIBUTION_REQUEST = new PromoCodeDistributionRequestBuilder()
      .withChannelId(CHANNEL_ID_DISTRIBUTION_REQUEST)
      .withStoreId(CommonTestVariable.STORE_ID)
      .build();

  public static Set<PromoCodeDistributionRequest> PROMO_CODE_DISTRIBUTIONS_REQUEST = new HashSet<>(Arrays.asList(PROMO_CODE_DISTRIBUTION_REQUEST));


  public static CostRequest PARTNER_COST_REQUEST = new CostRequestBuilder()
      .withMaxValue(MAX_VALUE)
      .withName(NAME_COST)
      .withPercent(PERCENT)
      .withValue(VALUE_COST)
      .build();

  public static CostRequest COMPANY_COST_REQUEST = new CostRequestBuilder()
      .withMaxValue(MAX_VALUE)
      .withName(NAME_COST)
      .withPercent(PERCENT)
      .withValue(VALUE_COST)
      .build();

  public static List<CostRequest> PARTNER_COSTS_REQUEST = Arrays.asList(PARTNER_COST_REQUEST);
  public static List<CostRequest> COMPANY_COSTS_REQUEST = Arrays.asList(COMPANY_COST_REQUEST);

  public static PromoCodeCostRequest PROMO_CODE_COST_REQUEST = new PromoCodeCostRequestBuilder()
      .withCompanyCost(COMPANY_COSTS_REQUEST)
      .withPartnerCost(PARTNER_COSTS_REQUEST)
      .build();

  public static PromoCodeDiscountRequest PROMO_CODE_DISCOUNT_REQUEST = new PromoCodeDiscountRequestBuilder()
      .withMaxValue(MAX_VALUE_DISCOUNT)
      .withPercent(PERCENT_DISCOUNT)
      .withValue(VALUE_DISCOUNT)
      .build();

  public static PromoCodePriceRangeRequest PROMO_CODE_PRICE_RANGE_REQUEST = new PromoCodePriceRangeRequestBuilder()
      .withMaxPrice(MAX_PRICE_RANGE)
      .withMinPrice(MIN_PRICE_RANGE)
      .withPromoCodeDiscount(PROMO_CODE_DISCOUNT_REQUEST)
      .build();


  public static List<PromoCodePriceRangeRequest> PROMO_CODE_PRICE_RANGES_REQUEST = Arrays
      .asList(PROMO_CODE_PRICE_RANGE_REQUEST);

  public static PromoCodeRuleValueRequest PROMO_CODE_RULE_VALUE_REQUEST = new PromoCodeRuleValueRequestBuilder()
      .withOperator(OPERATOR)
      .withValue(VALUE_RULE)
      .build();

  public static PromoCodeRuleRequest PROMO_CODE_RULE_REQUEST = new PromoCodeRuleRequestBuilder()
      .withParam(PARAM_RULE)
      .withPromoCodeRuleValue(PROMO_CODE_RULE_VALUE_REQUEST)
      .build();

  public static List<PromoCodeRuleRequest> PROMO_CODE_RULES_REQUEST = Arrays.asList(PROMO_CODE_RULE_REQUEST);

  public static PromoCodeGroupRuleRequest PROMO_CODE_GROUP_RULE_REQUEST = new PromoCodeGroupRuleRequestBuilder()
      .withName(NAME_GROUP_RULE)
      .withPromoCodeCost(PROMO_CODE_COST_REQUEST)
      .withPromoCodePriceRanges(PROMO_CODE_PRICE_RANGES_REQUEST)
      .withPromoCodeRules(PROMO_CODE_RULES_REQUEST)
      .build();

  public static List<PromoCodeGroupRuleRequest> PROMO_CODE_GROUP_RULES_REQUEST = Arrays
      .asList(PROMO_CODE_GROUP_RULE_REQUEST);

  public static UsageRuleRequest USAGE_RULE_REQUEST = new UsageRuleRequestBuilder()
      .withUsageCount(USAGE_COUNT)
      .withUsagePeriod(UsagePeriod.DAILY)
      .build();
  public static UsageRuleRequest USAGE_RULE_ZERO_REQUEST = new UsageRuleRequestBuilder()
      .withUsageCount(0)
      .withUsagePeriod(UsagePeriod.DAILY)
      .build();

  public static List<UsageRuleRequest> USAGE_RULES_ZERO_REQUEST = Arrays.asList(USAGE_RULE_ZERO_REQUEST);
  public static List<UsageRuleRequest> USAGE_RULES_REQUEST = Arrays.asList(USAGE_RULE_REQUEST);
  public static List<UsageRuleRequest> USAGE_RULES_EMPTY_REQUEST = Arrays.asList();

  public static PromoCodeAdjustmentRequest PROMO_CODE_ADJUSTMENT_REQUEST = new PromoCodeAdjustmentRequestBuilder()
      .withName(NAME)

      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS_REQUEST)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES_REQUEST)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withUsageRules(USAGE_RULES_REQUEST)
      .build();


  public static PromoCodeAdjustmentRequest PROMO_CODE_ADJUSTMENT_CREATE_REQUEST = new PromoCodeAdjustmentRequestBuilder()
      .withCode(CODE)
      .withDescription(DESCRIPTION)
      .withName(NAME)
      .withMaxDiscount(MAX_DISCOUNT)
      .withPromoCodeCombine(PROMO_CODE_COMBINE)
      .withPromoCodeType(PromoCodeType.GENERAL)
      .withPromoCodeDistributions(PROMO_CODE_DISTRIBUTIONS_REQUEST)
      .withPromoCodeGroupRules(PROMO_CODE_GROUP_RULES_REQUEST)
      .withUsageRules(USAGE_RULES_REQUEST)
      .withPaymentMethods(PAYMENT_METHODS_REQUEST)
      .build();

  public static Set<String> binNumbers() {
    Set<String> setString = new HashSet<>();
    setString.add(BIN_NUMBER);
    return setString;
  }


  public static String NAME_2 = "anji";
  public static String ADJUSTMENT_ID = "1234567abcdfgt";
  public static String CAMPAIGN_ID = "campaign1234567";
  public static String NAME_OTHER = "anji";


  private static List<PrivilegeResponse> getPrivilegeResponse(){
    List<PrivilegeResponse> privilegeResponses = new ArrayList<>();
    privilegeResponses.add(
        new PrivilegeResponseBuilder()
            .withPrivilegeId("320")
            .withPrivilegeName("coba")
            .build());

    privilegeResponses.add(
        new PrivilegeResponseBuilder()
            .withPrivilegeId("321")
            .withPrivilegeName("coba1")
            .build());
    return privilegeResponses;
  }

  public static List<PrivilegeResponse> PRIVILEGE_RESPONSE = getPrivilegeResponse();


  public static String PRIVILEGES = "320,321,322,323,324,325,326,"
      + "330,331,332,333,334,335,336,337,338,339,340,350,351,352,353,354,355,356,357,"
      + "358,359,360,361,362,363,364,365,366,370,371,372,373,374,375";

  private static RestResponsePage<PromoCodeAdjustmentResponse> getDatass(){
    RestResponsePage<PromoCodeAdjustmentResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(PROMO_CODE_ADJUSTMENT_RESPONSE));

    return data;
  }

  public static RestResponsePage<PromoCodeAdjustmentResponse> DATA = getDatass();

  private static GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> getResultss(){
    GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> RESULT = getResultss();


  private static GatewayBaseResponse<PromoCodeAdjustmentResponse> getPromoCodeAdjustmentRequestGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<PromoCodeAdjustmentResponse> basePromoCodeRequest = new GatewayBaseResponse<>();
    basePromoCodeRequest.setCode("SUCCESS");
    basePromoCodeRequest.setData(PROMO_CODE_ADJUSTMENT_RESPONSE);

    return basePromoCodeRequest;
  }

  public static GatewayBaseResponse<PromoCodeAdjustmentResponse> PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE = getPromoCodeAdjustmentRequestGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBooleanss(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBooleanss();


}
