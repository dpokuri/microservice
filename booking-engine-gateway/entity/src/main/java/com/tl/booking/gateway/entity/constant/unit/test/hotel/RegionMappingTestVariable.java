package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponseBuilder;

import java.util.Arrays;

public class RegionMappingTestVariable {

  public static String ID = "1";
  public static Integer page = 1;
  public static Integer limit = 1;
  public static String q = "";
  public static String regionId = "1";
  public static String sort = "desc";
  public static String method = "";

  public static final String NAME ="Jakarta";
  public static final Integer ACTIVE = 1;

  private static RegionMappingResponse DATA = new RegionMappingResponseBuilder()
      .withName(NAME)
      .withId(ID)
      .withActive(ACTIVE)
      .build();

  private static RestResponsePage<RegionMappingResponse> getData(){
    RestResponsePage<RegionMappingResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(DATA));

    return data;
  }

  public static RestResponsePage<RegionMappingResponse> DATA_PAGE = getData();

  private static GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> getResultPage(){
    GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA_PAGE);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> RESULT_PAGE = getResultPage();

  private static GatewayBaseResponse<RegionMappingResponse> getResult(){
    GatewayBaseResponse<RegionMappingResponse> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RegionMappingResponse> RESULT = getResult();

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static RegionMappingRequest REQUEST = new RegionMappingRequestBuilder()
      .withName(NAME)
      .withActive(1)
      .build();


  public static final String REGION_MAPPING_REQUEST = "{"
      + "\"name\": \"" + NAME + "\","
      + "\"active\": \"" + ACTIVE + "\""
      + "}";
}
