package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BankResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.BankResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeDTORequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeDTORequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequestBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankTestVariable {


  public static String NAME = "nameaja";
  public static String PARAM = "123456";
  public static String PARAM_2 = "1234567";
  public static String PARAM_3 = "1234568";
  public static String ID = "12345edfewfef";
  public static String INPUT_SOURCE = "input123";
  public static String DESCRIPTION = "input123";
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static BankResponse BANK_RESPONSE = new BankResponseBuilder()
      .withId(ID)
      .withName(NAME)
      .build();


  public static ProductTypeRequest BANK_REQUEST = new ProductTypeRequestBuilder()
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .build();

  public static ProductTypeDTORequest productTypeDTORequest = new ProductTypeDTORequestBuilder()
      .withId(ID)
      .withName(NAME)
      .build();


  public static ProductTypeRequest BANK_REQUEST_UPDATE = new ProductTypeRequestBuilder()
      .withName(NAME)
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

  private static RestResponsePage<BankResponse> getData(){
    RestResponsePage<BankResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(BANK_RESPONSE));

    return data;
  }

  public static RestResponsePage<BankResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<BankResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<BankResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<BankResponse>> RESULT = getResult();


  private static GatewayBaseResponse<BankResponse> getBankResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<BankResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(BANK_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<BankResponse> BANK_RESPONSE_GENERATED_BASE_RESPONSE = getBankResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();


  public static BankResponse CAMPAIGN_DROPDOWN_RESPONSE = new BankResponseBuilder()
      .withName(NAME)
      .withId(ID)
      .build();

  public static List<BankResponse> campaignDropdownResponseList = Arrays.asList(CAMPAIGN_DROPDOWN_RESPONSE);

  private static GatewayBaseResponse<List<BankResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<BankResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(campaignDropdownResponseList);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<BankResponse>> BANK_RESPONSE_LIST = getCampaignActiveResponse();


  public static String BANK_JSON = "{\n"
      + "  \"description\": \"" + DESCRIPTION + "\",\n"
      + "  \"name\": \"" + NAME + "\"\n"
      + "}";

}
