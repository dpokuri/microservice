package com.tl.booking.gateway.entity.constant.unit.test.flightrme;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequestBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponse;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponseBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.DiscountRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.DiscountRequestBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.DiscountResponse;
import com.tl.booking.gateway.entity.outbound.flightrme.DiscountResponseBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.MarkupRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.MarkupRequestBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.MarkupResponse;
import com.tl.booking.gateway.entity.outbound.flightrme.MarkupResponseBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.PriceRangeRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.PriceRangeRequestBuilder;
import com.tl.booking.gateway.entity.outbound.flightrme.PriceRangeResponse;
import com.tl.booking.gateway.entity.outbound.flightrme.PriceRangeResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class AdjustmentRuleTestVariable {
  //public static String CODE = "strings123";
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
  public static Object VALUE_RULE = "String";
  public static String PARAM_RULE = "String";

  public static String NAME_GROUP_RULE = "String";

  public static Integer USAGE_COUNT = 100;

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static String ADJUSTMENT_RULE_STATUS_DRAFT = "DRAFT";
  public static String AIRLINE = "airline123";
  public static String AIRLINE_CLASS = "a,b,c,d,e";
  public static String FARE_CLASS = "FAREa,FAREb,FAREc,FAREd";

  public static String DESTINATION = "desti123";
  public static String DEP_START_DATE = "destiStartDate123";
  public static String DEP_END_DATE = "destiEndDate123";
  public static String ORIGIN_DESTINATION = "oriDesti123";
  public static Double START_PRICE = 0.00;
  public static Double END_PRICE = 10000.00;
  public static Double VALUE = 20000.00;
  public static String ORIGIN = "origin123";
  public static String END_DATE = "2020-10-10";
  public static String START_DATE = "2019-10-10";

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

  public static PriceRangeResponse PRICE_RANGE_RESPONSE = new PriceRangeResponseBuilder()
      .withStartPrice(START_PRICE)
      .withEndPrice(END_PRICE)
      .withPercent(PERCENT)
      .withValue(VALUE)
      .withMaxValue(MAX_VALUE)
      .build();

  public static List<PriceRangeResponse> PRICE_RANGE_RESPONSE_LIST = Arrays.asList(PRICE_RANGE_RESPONSE);

  public static PriceRangeRequest PRICE_RANGE_REQUEST = new PriceRangeRequestBuilder()
      .withStartPrice(START_PRICE)
      .withEndPrice(END_PRICE)
      .withPercent(PERCENT)
      .withValue(VALUE)
      .withMaxValue(MAX_VALUE)
      .build();

  public static List<PriceRangeRequest> PRICE_RANGE_REQUEST_LIST = Arrays.asList(PRICE_RANGE_REQUEST);

  public static MarkupRequest MARKUP_REQUEST = new MarkupRequestBuilder()
      .withPriceRanges(PRICE_RANGE_REQUEST_LIST)
      .build();

  public static DiscountRequest DISCOUNT_REQUEST = new DiscountRequestBuilder()
      .withPriceRanges(PRICE_RANGE_REQUEST_LIST)
      .build();

  public static MarkupResponse MARKUP_RESPONSE = new MarkupResponseBuilder()
      .withPriceRanges(PRICE_RANGE_RESPONSE_LIST)
      .build();

  public static DiscountResponse DISCOUNT_RESPONSE = new DiscountResponseBuilder()
      .withPriceRanges(PRICE_RANGE_RESPONSE_LIST)
      .build();

  public static AdjustmentRuleRequest ADJUSTMENT_RULE_REQUEST = new AdjustmentRuleRequestBuilder()
      .withAirline(AIRLINE)
      .withAirlineClass(AIRLINE_CLASS)
      .withOrigin(ORIGIN)
      .withDestination(DESTINATION)
      .withDepartureStartDate(DEP_START_DATE)
      .withDepartureEndDate(DEP_END_DATE)
      .withOriginDestination(ORIGIN_DESTINATION)
      .withMarkup(MARKUP_REQUEST)
      .withDiscount(DISCOUNT_REQUEST)
      .withFareClass(FARE_CLASS)

      .build();


  public static AdjustmentRuleResponse ADJUSTMENT_RULE_RESPONSE = new AdjustmentRuleResponseBuilder()
      .withId(ID)
      .withAirline(AIRLINE)
      .withAirlineClass(AIRLINE_CLASS)
      .withOrigin(ORIGIN)
      .withDestination(DESTINATION)
      .withDepartureStartDate(DEP_START_DATE)
      .withDepartureEndDate(DEP_END_DATE)
      .withOriginDestination(ORIGIN_DESTINATION)
      .withMarkup(MARKUP_RESPONSE)
      .withDiscount(DISCOUNT_RESPONSE)
      .withFareClass(FARE_CLASS)
      .withAdjustmentRuleStatus(AdjustmentRuleStatus.ACTIVE)

      .build();


  private static RestResponsePage<AdjustmentRuleResponse> getData(){
    RestResponsePage<AdjustmentRuleResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(ADJUSTMENT_RULE_RESPONSE));

    return data;
  }

  public static RestResponsePage<AdjustmentRuleResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> RESULT = getResult();


  private static GatewayBaseResponse<AdjustmentRuleResponse> getAdjustmentRuleResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<AdjustmentRuleResponse> baseAdjustmentRuleResponse = new GatewayBaseResponse<>();
    baseAdjustmentRuleResponse.setCode("SUCCESS");
    baseAdjustmentRuleResponse.setData(ADJUSTMENT_RULE_RESPONSE);

    return baseAdjustmentRuleResponse;
  }

  public static GatewayBaseResponse<AdjustmentRuleResponse> ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE = getAdjustmentRuleResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  public static AdjustmentRuleRequest ADJUSTMENT_RULE_REQUEST_CONTROLLER = new AdjustmentRuleRequestBuilder()
      .withAirline(AIRLINE)
      .withAirlineClass(AIRLINE_CLASS)
      .withOrigin(ORIGIN)
      .withDestination(DESTINATION)
      .withDepartureStartDate(START_DATE)
      .withDepartureEndDate(END_DATE)
      .withOriginDestination(ORIGIN_DESTINATION)
      .withMarkup(MARKUP_REQUEST)
      .withDiscount(DISCOUNT_REQUEST)
      .withFareClass(FARE_CLASS)

      .build();

  public static String ADJUSTMENT_RULE_REQUEST_BODY = "{\n"
      + "  \"airline\": \""+AIRLINE+"\",\n"
      + "  \"airlineClass\": \""+AIRLINE_CLASS+"\",\n"
      + "  \"departureEndDate\": \""+END_DATE+"\",\n"
      + "  \"departureStartDate\": \""+START_DATE+"\",\n"
      + "  \"destination\": \""+DESTINATION+"\",\n"
      + "  \"discount\": {\n"
      + "    \"priceRanges\": [\n"
      + "      {\n"
      + "        \"endPrice\": "+END_PRICE+",\n"
      + "        \"maxValue\": "+MAX_VALUE+",\n"
      + "        \"percent\": "+PERCENT+",\n"
      + "        \"startPrice\": "+START_PRICE+",\n"
      + "        \"value\": "+VALUE+"\n"
      + "      }\n"
      + "    ]\n"
      + "  },\n"
      + "  \"fareClass\": \""+FARE_CLASS+"\",\n"
      + "  \"markup\": {\n"
      + "    \"priceRanges\": [\n"
      + "      {\n"
      + "        \"endPrice\": "+END_PRICE+",\n"
      + "        \"maxValue\": "+MAX_VALUE+",\n"
      + "        \"percent\": "+PERCENT+",\n"
      + "        \"startPrice\": "+START_PRICE+",\n"
      + "        \"value\": "+VALUE+"\n"
      + "      }\n"
      + "    ]\n"
      + "  },\n"
      + "  \"origin\": \""+ORIGIN+"\",\n"
      + "  \"originDestination\": \""+ORIGIN_DESTINATION+"\"\n"
      + "}";
}
