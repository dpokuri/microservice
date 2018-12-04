package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponseBuilder;

import java.util.Arrays;

public class PicRegionTestVariable {

  public static String ID = "1";
  public static Integer page = 1;
  public static Integer limit = 1;
  public static String q = "";
  public static String regionId = "1";
  public static String sort = "desc";
  public static String method = "";

  public static final String NAME = "name";
  public static final String EMAIL = "pic@tiket.com";
  public static final Integer ACTIVE = 1;


  private static PicRegionResponse DATA = new PicRegionResponseBuilder()
      .withName(NAME)
      .withId(ID)
      .withEmail(EMAIL)
      .build();

  private static RestResponsePage<PicRegionResponse> getData(){
    RestResponsePage<PicRegionResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(DATA));

    return data;
  }

  public static RestResponsePage<PicRegionResponse> DATA_PAGE = getData();

  private static GatewayBaseResponse<RestResponsePage<PicRegionResponse>> getResultPage(){
    GatewayBaseResponse<RestResponsePage<PicRegionResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA_PAGE);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<PicRegionResponse>> RESULT_PAGE = getResultPage();

  private static GatewayBaseResponse<PicRegionResponse> getResult(){
    GatewayBaseResponse<PicRegionResponse> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<PicRegionResponse> RESULT = getResult();

  public static PicRegionRequest REQUEST = new PicRegionRequestBuilder()
      .withName(NAME)
      .withEmail(EMAIL)
      .withActive(ACTIVE)
      .build();

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static String PIC_REGION_REQUEST = "{" +
      "\"name\": \"" + NAME + "\"," +
      "\"email\": \"" + EMAIL + "\"," +
      "\"active\": " + ACTIVE + "" +
      "}";

}
