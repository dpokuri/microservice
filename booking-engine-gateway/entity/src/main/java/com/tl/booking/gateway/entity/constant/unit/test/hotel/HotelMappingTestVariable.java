package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponseBuilder;

import java.util.Arrays;

public class HotelMappingTestVariable {

  public static String ID = "1";
  public static Integer page = 1;
  public static Integer limit = 1;
  public static String q = "";
  public static String regionId = "1";
  public static String sort = "desc";
  public static String method = "";

  public static final String NAME = "hotel";
  public static final Integer ACTIVE = 1;

  private static HotelMappingResponse DATA = new HotelMappingResponseBuilder()
      .withName(NAME)
      .withId(ID)
      .withRegionId(regionId)
      .build();

  private static RestResponsePage<HotelMappingResponse> getData(){
    RestResponsePage<HotelMappingResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(DATA));

    return data;
  }

  public static RestResponsePage<HotelMappingResponse> DATA_PAGE = getData();

  private static GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> getResultPage(){
    GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA_PAGE);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> RESULT_PAGE = getResultPage();

  private static GatewayBaseResponse<HotelMappingResponse> getResult(){
    GatewayBaseResponse<HotelMappingResponse> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<HotelMappingResponse> RESULT = getResult();

  public static HotelMappingRequest REQUEST = new HotelMappingRequestBuilder()
      .withName(NAME)
      .withActive(ACTIVE)
      .withRegionId(regionId)
      .build();

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static String HOTEL_MAPPING_REQUEST = "{"
      + "\"name\": \"" + NAME + "\","
      + "\"regionId\": \"" + regionId + "\","
      + "\"active\": \"" + ACTIVE + "\""
      + "}";
}
