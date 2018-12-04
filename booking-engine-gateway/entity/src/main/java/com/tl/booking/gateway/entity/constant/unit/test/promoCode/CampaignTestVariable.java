package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignPeriodRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignPeriodRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignPeriodResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignPeriodResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CampaignTestVariable {

  public static String ID = "12345";
  public static String NAME = "yonathan";
  public static String NAME_2 = "anji";
  public static String CODE = "1234567";
  public static String ADJUSTMENT_ID = "1234567abcdfgt";
  public static String CAMPAIGN_ID = "campaign1234567";
  public static Integer IS_DELETED = 0;
  public static String NAME_OTHER = "anji";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;


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

  public static PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT = new PromoCodeAdjustmentResponseBuilder()
      .withId(ADJUSTMENT_ID)
      .build();

  public static CampaignPeriodResponse CAMPAIGN_PERIOD = new CampaignPeriodResponseBuilder()
      .withStartDate(new Date())
      .withEndDate(new Date())
      .build();

  public static List<CampaignPeriodResponse> CAMPAIGN_PERIODS = Arrays.asList(CAMPAIGN_PERIOD);

  public static String DATE_NOW = new Date().toString();

  public static CampaignPeriodRequest CAMPAIGN_PERIOD_REQUEST = new CampaignPeriodRequestBuilder()
      .withStartDate(DATE_NOW)
      .withEndDate(DATE_NOW)
      .build();

  public static List<CampaignPeriodRequest> CAMPAIGN_PERIODS_REQUEST = Arrays.asList(CAMPAIGN_PERIOD_REQUEST);

  public static CampaignRequest CAMPAIGN_REQUEST = new CampaignRequestBuilder()

      .withName(NAME)
      .withCode(CODE)
      .withPromoCodeAdjustmentId(ADJUSTMENT_ID)
      .withCampaignPeriods(CAMPAIGN_PERIODS_REQUEST)

      .build();

  public static CampaignResponse CAMPAIGN_RESPONSE = new CampaignResponseBuilder()
      .withId(ID)
      .withCode(CODE)
      .withName(NAME)
      .withCampaignPeriods(CAMPAIGN_PERIODS)
      .withCampaignStatus(CampaignStatus.ACTIVE)
      .build();


  private static RestResponsePage<CampaignResponse> getData(){
    RestResponsePage<CampaignResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(CAMPAIGN_RESPONSE));

    return data;
  }

  public static RestResponsePage<CampaignResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<CampaignResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<CampaignResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<CampaignResponse>> RESULT = getResult();


  private static GatewayBaseResponse<CampaignResponse> getCampaignResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<CampaignResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(CAMPAIGN_RESPONSE);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<CampaignResponse> CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE = getCampaignResponseGeneratedGatewayBaseResponse();


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

  public static String CAMPAIGN_REQUEST_BODY =
      "{\"campaignPeriods\":[{\"endDate\":\""+DATE_NOW+"\","
          + "\"startDate\":\""+DATE_NOW+"\"}],\"code\":\"" + CODE + "\",\"name\":\"" + NAME
          + "\",\"promoCodeAdjustmentId\":\"" + ADJUSTMENT_ID + "\"}";

}
