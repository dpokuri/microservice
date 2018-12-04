package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.constant.enums.DataType;
import com.tl.booking.gateway.entity.constant.enums.InputType;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariableTestVariable {


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

  public static VariableResponse VARIABLE_RESPONSE = new VariableResponseBuilder()
      .withId(ID)
      .withName(NAME)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)
      .build();


  public static VariableRequest VARIABLE_REQUEST = new VariableRequestBuilder()
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)

      .build();

  public static List<String> PRO_DTO_REQ = Arrays.asList(NAME);

  public static VariableRequest VARIABLE_REQUEST_UPDATE = new VariableRequestBuilder()
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withProductTypes(PRO_DTO_REQ)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)

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

  private static RestResponsePage<VariableResponse> getData(){
    RestResponsePage<VariableResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(VARIABLE_RESPONSE));

    return data;
  }

  public static RestResponsePage<VariableResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<VariableResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<VariableResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<VariableResponse>> RESULT = getResult();


  private static GatewayBaseResponse<VariableResponse> getVariableResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<VariableResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(VARIABLE_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<VariableResponse> VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE = getVariableResponseGeneratedGatewayBaseResponse();


  public static VariableFindAllResponse VARIABLE_ALL_RESPONSE_DATA = new VariableFindAllResponseBuilder()
      .withId(ID)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)
      .build();


  private static List<VariableFindAllResponse> VARIABLE_FIND_ALL_DATA = Arrays.asList(VARIABLE_ALL_RESPONSE_DATA);

  private static GatewayBaseResponse<List<VariableFindAllResponse>> getVariableAllResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<List<VariableFindAllResponse>> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(VARIABLE_FIND_ALL_DATA);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<List<VariableFindAllResponse>> VARIABLE_ALL_RESPONSE_GENERATED_BASE_RESPONSE = getVariableAllResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();


  public static CampaignDropdownResponse CAMPAIGN_DROPDOWN_RESPONSE = new CampaignDropdownResponseBuilder()
      .withName(NAME)
      .withId(ID)
      .build();

  public static List<CampaignDropdownResponse> campaignDropdownResponseList = Arrays.asList(CAMPAIGN_DROPDOWN_RESPONSE);

  private static GatewayBaseResponse<List<CampaignDropdownResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<CampaignDropdownResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(campaignDropdownResponseList);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<CampaignDropdownResponse>> CAMPAIGN_DROPDOWN_RESPONSE_LIST = getCampaignActiveResponse();

  public static String VARIABLE_JSON_BODY = ""
      + "{"
      + "\"allowedArithmetics\": [ \"test1\", \"test2\"], "
      + "\"dataType\": \"" + DataType.STRING.getName() + "\", "
      + "\"description\": \"" + DESCRIPTION + "\", "
      + "\"inputSource\": \"" + INPUT_SOURCE + "\", "
      + "\"inputType\": \"" + InputType.DROPDOWN.getValue() + "\", "
      + "\"param\": \"" + PARAM + "\","
      + "\"name\": \"" + NAME + "\","
      + "\"productTypes\": [ { \"id\": \"" + ID + "\", \"name\": \"" + NAME + "\" }  ]"
      + "}";

}
