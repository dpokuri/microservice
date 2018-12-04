package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponseBuilder;

import java.util.Arrays;

public class InternalSubsidyTestVariable {

  public static String ID = "1";
  public static Integer page = 0;
  public static Integer limit = 20;
  public static String q = "";
  public static String countryId = "";
  public static String provinceId = "";
  public static String cityId = "";
  public static String areaId = "";
  public static String sort = "active";
  public static String method = "desc";
  public static Integer ACTIVE = 1;

  public static InternalSubsidyRequest REQUEST = new InternalSubsidyRequestBuilder()
      .withActive(ACTIVE)
      .build();
  public static InternalSubsidyActivateRequest REQUEST_ACTIVATE = new InternalSubsidyActivateRequestBuilder()
      .withActive(ACTIVE)
      .build();
  public static GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>> RESULT_PAGE = getResultPage();

  public static String REQUEST_ACTIVE = "{"
      + "  \"active\": " + ACTIVE
      + "}";

  private static InternalSubsidyResponse DATA = new InternalSubsidyResponseBuilder()
      .build();
  public static GatewayBaseResponse<InternalSubsidyResponse> RESULT = getResult();
  public static RestResponsePage<InternalSubsidyResponse> DATA_PAGE = getData();

  private static GatewayBaseResponse<InternalSubsidyResponse> getResult() {
    GatewayBaseResponse<InternalSubsidyResponse> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);

    return result;
  }

  private static RestResponsePage<InternalSubsidyResponse> getData() {
    RestResponsePage<InternalSubsidyResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(DATA));

    return data;
  }

  private static GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>> getResultPage() {
    GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA_PAGE);
    return result;
  }
}
