package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreIdTestVariable {


  public static String NAME = "nameaja";
  public static String ID = "12345edfewfef";
  public static String DESCRIPTION = "input123";
  public static String LABEL = "label123";
  public static String VALUE = "value123";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static StoreIdResponse STORE_ID_RESPONSE = new StoreIdResponseBuilder()
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

  private static RestResponsePage<StoreIdResponse> getData(){
    RestResponsePage<StoreIdResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(STORE_ID_RESPONSE));

    return data;
  }

  public static RestResponsePage<StoreIdResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<StoreIdResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<StoreIdResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<StoreIdResponse>> RESULT = getResult();


  private static GatewayBaseResponse<StoreIdResponse> getStoreIdResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<StoreIdResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(STORE_ID_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<StoreIdResponse> STORE_ID_RESPONSE_GENERATED_BASE_RESPONSE = getStoreIdResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();


  public static StoreIdResponse CAMPAIGN_DROPDOWN_RESPONSE = new StoreIdResponseBuilder()
      .withLabel(LABEL)
      .withValue(VALUE)
      .withId(ID)
      .build();

  public static List<StoreIdResponse> campaignDropdownResponseList = Arrays.asList(CAMPAIGN_DROPDOWN_RESPONSE);

  private static GatewayBaseResponse<List<StoreIdResponse>> getCampaignActiveResponse()
  {
    GatewayBaseResponse<List<StoreIdResponse>> baseResponse = new GatewayBaseResponse<>();
    baseResponse.setCode("SUCCESS");
    baseResponse.setData(campaignDropdownResponseList);

    return baseResponse;
  }

  public static GatewayBaseResponse<List<StoreIdResponse>> STORE_ID_RESPONSE_LIST = getCampaignActiveResponse();


  public static String STORE_ID_JSON = "{\n"
      + "  \"label\": \"" + LABEL + "\",\n"
      + "  \"value\": \"" + VALUE + "\"\n"
      + "}";

}
