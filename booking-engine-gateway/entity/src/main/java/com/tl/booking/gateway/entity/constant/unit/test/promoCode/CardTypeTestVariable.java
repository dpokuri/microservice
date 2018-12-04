package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CardTypeResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CardTypeResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardTypeTestVariable {


  public static String NAME = "nameaja";
  public static String PARAM = "123456";
  public static String PARAM_2 = "1234567";
  public static String PARAM_3 = "1234568";
  public static String ID = "12345edfewfef";
  public static String INPUT_SOURCE = "input123";
  public static String DESCRIPTION = "input123";
  public static String CARD_TYPE_ID = "paymentt123";
  public static Boolean BIN_NUMBER_TRUE = true;
  public static Boolean BIN_NUMBER_FALSE = false;
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static CardTypeResponse CARD_TYPE_RESPONSE = new CardTypeResponseBuilder()
      .withId(ID)
      .withName(NAME)

      .build();


  public static String NAME_2 = "anji";
  public static String CODE = "1234567";
  public static String ADJUSTMENT_ID = "1234567abcdfgt";
  public static String CAMPAIGN_ID = "campaign1234567";
  public static Integer IS_DELETED = 0;
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

  private static RestResponsePage<CardTypeResponse> getData(){
    RestResponsePage<CardTypeResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(CARD_TYPE_RESPONSE));

    return data;
  }

  public static RestResponsePage<CardTypeResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<CardTypeResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<CardTypeResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<CardTypeResponse>> RESULT = getResult();


  private static GatewayBaseResponse<CardTypeResponse> getCardTypeResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<CardTypeResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(CARD_TYPE_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<CardTypeResponse> CARD_TYPE_RESPONSE_GENERATED_BASE_RESPONSE = getCardTypeResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();


  public static CardTypeResponse CAMPAIGN_DROPDOWN_RESPONSE = new CardTypeResponseBuilder()
      .withName(NAME)
      .withId(ID)
      .build();

  public static List<CardTypeResponse> campaignDropdownResponseList = Arrays.asList(CAMPAIGN_DROPDOWN_RESPONSE);

  private static GatewayBaseResponse<List<CardTypeResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<CardTypeResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(campaignDropdownResponseList);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<CardTypeResponse>> CARD_TYPE_RESPONSE_LIST = getCampaignActiveResponse();


  public static String CARD_TYPE_JSON = "{\n"
      + "  \"description\": \"" + DESCRIPTION + "\",\n"
      + "  \"name\": \"" + NAME + "\"\n"
      + "}";

}
