package com.tl.booking.gateway.entity.constant.unit.test;

import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayDescription;
import com.tl.booking.gateway.entity.outbound.HolidayDescriptionBuilder;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayRequestBuilder;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.HolidayResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayTestVariable {

  public static String ID = "123";
  public static String START_DATE = "2011-01-01";
  public static String END_DATE = "2100-01-01";
  public static String LANG = "EN";
  public static String CONTENT = null;
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static HolidayColumn COLUMN_SORT = HolidayColumn.DATE;
  public static SortDirection SORT_DIRECTION = SortDirection.DESC;


  private static Map<String, String> getHeader(){
    Map<String, String> header = new HashMap<>();
    header.put("storeId", CommonTestVariable.STORE_ID);
    header.put("channelId", CommonTestVariable.CHANNEL_ID);
    header.put("requestId", CommonTestVariable.REQUEST_ID);
    header.put("serviceId", CommonTestVariable.SERVICE_ID);
    header.put("username", CommonTestVariable.USERNAME);

    return header;
  }
  public static Map<String, String> HEADER = getHeader();

  private static Map<String, String> getQuery(){
    Map<String, String> query = new HashMap<>();
    query.put("startDate", START_DATE);
    query.put("endDate", END_DATE);
    query.put("lang", LANG);
    query.put("content", CONTENT);
    query.put("page", PAGE.toString());
    query.put("size", SIZE.toString());
    query.put("columnSort", HolidayColumn.ID.getName());
    query.put("sortDirection", SortDirection.ASC.getName());

    return query;
  }

  public static Map<String, String> QUERY = getQuery();

  private static List<PrivilegeResponse> getPrivilegeResponse(){
    List<PrivilegeResponse> privilegeResponses = new ArrayList<>();
    privilegeResponses.add(
        new PrivilegeResponseBuilder()
            .withPrivilegeId("300")
            .withPrivilegeName("coba")
            .build());

    privilegeResponses.add(
        new PrivilegeResponseBuilder()
            .withPrivilegeId("301")
            .withPrivilegeName("coba1")
            .build());
    return privilegeResponses;
  }

  public static List<PrivilegeResponse> PRIVILEGE_RESPONSE = getPrivilegeResponse();


  public static HolidayDescription HOLIDAY_DESCRIPTION = new HolidayDescriptionBuilder()
      .withContent("blabla")
      .withLang(Language.EN)
      .build();

  public static HolidayResponse HOLIDAY_RESPONSE = new HolidayResponseBuilder()
      .withDate(new Date())
      .withDescriptions(Arrays.asList(HOLIDAY_DESCRIPTION))
      .build();

  public static HolidayResponse HOLIDAY_RESPONSE_GENERATED = new HolidayResponseBuilder()
        .withDate(new Date())
      .withDescriptions(Arrays.asList(HOLIDAY_DESCRIPTION))
      .withId(ID)
        .build();

  private static GatewayBaseResponse<HolidayResponse> getHolidayResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<HolidayResponse> baseHolidayResponse = new GatewayBaseResponse<>();
    baseHolidayResponse.setCode("SUCCESS");
    baseHolidayResponse.setData(HOLIDAY_RESPONSE_GENERATED);

    return baseHolidayResponse;
  }

  public static GatewayBaseResponse<HolidayResponse> HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE = getHolidayResponseGeneratedGatewayBaseResponse();

  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static RestResponsePage<HolidayResponse> getData(){
    RestResponsePage<HolidayResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(HOLIDAY_RESPONSE));

    return data;
  }

  public static RestResponsePage<HolidayResponse> DATA = getData();

  private static GatewayBaseResponse<RestResponsePage<HolidayResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<HolidayResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA);
    return result;
  }

  public static String PRIVILEGES = "300,301,302,303";

  public static GatewayBaseResponse<RestResponsePage<HolidayResponse>> RESULT = getResult();

  public static HolidayRequest HOLIDAY_REQUEST = new HolidayRequestBuilder()
        .withDate("2017-01-01")
        .withDescriptions(Arrays.asList(HOLIDAY_DESCRIPTION)).build();

  public static String HOLIDAY_JSON = "{\"date\":\"2017-01-01\","
      + "\"descriptions\":[{\"content\":\"blabla\",\"lang\":\"EN\"}]}";
}
