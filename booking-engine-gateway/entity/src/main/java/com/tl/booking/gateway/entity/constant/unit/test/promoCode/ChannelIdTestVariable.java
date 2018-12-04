package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.ChannelIdResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.ChannelIdResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChannelIdTestVariable {


  public static String NAME = "nameaja";
  public static String ID = "12345edfewfef";
  public static String DESCRIPTION = "input123";
  public static String LABEL = "Label123";
  public static String VALUE = "value123";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static ChannelIdResponse CHANNEL_ID_RESPONSE = new ChannelIdResponseBuilder()
      .withId(ID)
      .withLabel(LABEL)
      .withValue(VALUE)
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

  private static RestResponsePage<ChannelIdResponse> getData(){
    RestResponsePage<ChannelIdResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(CHANNEL_ID_RESPONSE));

    return data;
  }

  public static RestResponsePage<ChannelIdResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<ChannelIdResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<ChannelIdResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<ChannelIdResponse>> RESULT = getResult();


  private static GatewayBaseResponse<ChannelIdResponse> getChannelIdResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<ChannelIdResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(CHANNEL_ID_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<ChannelIdResponse> CHANNEL_ID_RESPONSE_GENERATED_BASE_RESPONSE = getChannelIdResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();



  public static List<ChannelIdResponse> campaignDropdownResponseList = Arrays.asList(CHANNEL_ID_RESPONSE);

  private static GatewayBaseResponse<List<ChannelIdResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<ChannelIdResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(campaignDropdownResponseList);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<ChannelIdResponse>> CHANNEL_ID_RESPONSE_LIST = getCampaignActiveResponse();


  public static String CHANNEL_ID_JSON = "{\n"
      + "  \"description\": \"" + DESCRIPTION + "\",\n"
      + "  \"name\": \"" + NAME + "\"\n"
      + "}";

}
