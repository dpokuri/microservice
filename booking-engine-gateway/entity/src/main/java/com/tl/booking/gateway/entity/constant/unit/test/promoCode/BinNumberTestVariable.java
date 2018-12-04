package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinNumberTestVariable {


  public static String NAME = "nameaja";
  public static String PARAM = "123456";
  public static String PARAM_2 = "1234567";
  public static String PARAM_3 = "1234568";
  public static String ID = "12345edfewfef";
  public static String BANK_ID = "bank123";
  public static String BIN_NUMBER = "bin123";
  public static String CARD_TYPE = "card123";

  public static String DESCRIPTION = "input123";
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static BinNumberResponse BIN_NUMBER_RESPONSE = new BinNumberResponseBuilder()
      .withId(ID)
      .withBankId(BANK_ID)
      .withBinNumber(BIN_NUMBER)
      .withCardTypeId(CARD_TYPE)
      .withDescription(DESCRIPTION)
      .build();


  public static BinNumberRequest BIN_NUMBER_REQUEST = new BinNumberRequestBuilder()
      .withBankId(BANK_ID)
      .withBinNumber(BIN_NUMBER)
      .withCardTypeId(CARD_TYPE)
      .withDescription(DESCRIPTION)
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

  private static RestResponsePage<BinNumberResponse> getData(){
    RestResponsePage<BinNumberResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(BIN_NUMBER_RESPONSE));

    return data;
  }

  public static RestResponsePage<BinNumberResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<BinNumberResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<BinNumberResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<BinNumberResponse>> RESULT = getResult();


  private static GatewayBaseResponse<BinNumberResponse> getBinNumberResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<BinNumberResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(BIN_NUMBER_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<BinNumberResponse> BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE = getBinNumberResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();



  public static List<String> BIN_NUMBER_STRING_LIST = Arrays.asList(NAME);

  private static GatewayBaseResponse<List<String>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<String>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(BIN_NUMBER_STRING_LIST);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<String>> BIN_NUMBER_RESPONSE_LIST_DATA = getCampaignActiveResponse();


  public static String BIN_NUMBER_JSON = "{\n"
      + "  \"bankId\": \""+BANK_ID+"\",\n"
      + "  \"binNumber\": \""+BIN_NUMBER+"\",\n"
      + "  \"cardTypeId\": \""+CARD_TYPE+"\",\n"
      + "  \"description\": \""+DESCRIPTION+"\"\n"
      + "}";

}
