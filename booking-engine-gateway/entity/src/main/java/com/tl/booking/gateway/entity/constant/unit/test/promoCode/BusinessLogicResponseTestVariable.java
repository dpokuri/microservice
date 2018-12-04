package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.ResponseMessageRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ResponseMessageRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.ResponseMessageResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.ResponseMessageResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BusinessLogicResponseTestVariable {


  public static String CODE = "code123";
  public static String CONTENT = "123456";
  public static String ID = "12345edfewfef";
  public static String DESCRIPTION = "input123";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static ResponseMessageResponse RESPONSE_MESSAGE_RESPONSE = new ResponseMessageResponseBuilder()
      .withContent(CONTENT)
      .withLang(Language.ID)
      .build();

  public static List<ResponseMessageResponse> RESPONSE_MESSAGE_RESPONSE_LIST = Arrays.asList(RESPONSE_MESSAGE_RESPONSE);

  public static BusinessLogicResponseResponse BUSINESS_LOGIC_RESPONSE = new BusinessLogicResponseResponseBuilder()
      .withId(ID)
      .withResponseCode(CODE)
      .withResponseMessage(RESPONSE_MESSAGE_RESPONSE_LIST)

      .build();


  public static ResponseMessageRequest RESPONSE_MESSAGE_REQUEST = new ResponseMessageRequestBuilder()
      .withContent(CONTENT)
      .withLang(Language.ID)
      .build();

  public static List<ResponseMessageRequest> RESPONSE_MESSAGE_REQUEST_LIST = Arrays.asList(RESPONSE_MESSAGE_REQUEST);

  public static BusinessLogicResponseRequest BUSINESS_LOGIC_REQUEST = new BusinessLogicResponseRequestBuilder()
      .withResponseCode(CODE)
      .withResponseMessage(RESPONSE_MESSAGE_REQUEST_LIST)

      .build();






  public static String NAME_2 = "anji";
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

  private static RestResponsePage<BusinessLogicResponseResponse> getData(){
    RestResponsePage<BusinessLogicResponseResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(BUSINESS_LOGIC_RESPONSE));

    return data;
  }

  public static RestResponsePage<BusinessLogicResponseResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> RESULT = getResult();


  private static GatewayBaseResponse<BusinessLogicResponseResponse> getBusinessLogicResponseResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<BusinessLogicResponseResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(BUSINESS_LOGIC_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<BusinessLogicResponseResponse> BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE = getBusinessLogicResponseResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();


  public static List<BusinessLogicResponseResponse> campaignDropdownResponseList = Arrays.asList(BUSINESS_LOGIC_RESPONSE);

  private static GatewayBaseResponse<List<BusinessLogicResponseResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<BusinessLogicResponseResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(campaignDropdownResponseList);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<BusinessLogicResponseResponse>> BUSINESS_LOGIC_RESPONSE_LIST_DATA = getCampaignActiveResponse();


  public static String BUSINESS_LOGIC_JSON = "{\n"
      +"  \"responseCode\": \""+CODE+"\",\n"
      +"  \"responseMessage\": [\n"
      +"    {\n"
      +"      \"content\": \""+CONTENT+"\",\n"
      +"      \"lang\": \""+Language.ID.getCode()+"\"\n"
      +"    }"
      +"  ]\n"
      +"}";
}
