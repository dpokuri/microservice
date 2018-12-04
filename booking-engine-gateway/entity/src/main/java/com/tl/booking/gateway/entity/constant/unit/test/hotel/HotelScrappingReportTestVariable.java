package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponseBuilder;

import java.util.Arrays;

public class HotelScrappingReportTestVariable {

  public static Integer page = 1;
  public static Integer limit = 1;
  public static String q = "";
  public static String regionId = "1";
  public static String sort = "desc";
  public static String method = "";
  public static String hotelID = "1";

  public static final String EMAIL = "email@tiket.com";

  private static ScrappingReportResponse DATA = new ScrappingReportResponseBuilder()
      .withHotelName("")
      .withHotelId("1")
      .build();

  private static RestResponsePage<ScrappingReportResponse> getData(){
    RestResponsePage<ScrappingReportResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(DATA));

    return data;
  }

  public static RestResponsePage<ScrappingReportResponse> DATA_PAGE = getData();

  private static GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> getResultPage(){
    GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA_PAGE);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> RESULT_PAGE = getResultPage();

  private static GatewayBaseResponse<Boolean> getResultBoolean(){
    GatewayBaseResponse<Boolean> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(true);
    return result;
  }

  public static GatewayBaseResponse<Boolean> RESULT_BOOLEAN = new GatewayBaseResponse<>();

  private static GatewayBaseResponse<String> getResultString(){
    GatewayBaseResponse<String> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData("");
    return result;
  }

  public static GatewayBaseResponse<String> RESULT_STRING = getResultString();

}
