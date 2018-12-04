package com.tl.booking.gateway.entity.constant.unit.test;

import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.constant.enums.PromoCategoryColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionResponseBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PromoCategoryTestVariable {

  public static String ID = "123";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static PromoCategoryColumn COLUMN_SORT = PromoCategoryColumn.ID;
  public static SortDirection SORT_DIRECTION = SortDirection.DESC;

  public static String CATEGORY = "cate123";
  public static String PARENT_CATEGORY = "parentcate123";
  public static String GROUP = "group123";
  public static String LANG = "EN";


  public static String PRIVILEGES = "300,301,302,303";

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
    query.put("category", CATEGORY);
    query.put("parentCategory", PARENT_CATEGORY);
    query.put("page", PAGE.toString());
    query.put("size", SIZE.toString());
    query.put("columnSort", COLUMN_SORT.getName());
    query.put("sortDirection", SortDirection.DESC.getName());

    return query;
  }

  public static Map<String, String> QUERY = getQuery();

  private static List<Map<String,Object>> getListMapStringObject(){
    List<Map<String, Object>> resultList = new ArrayList<>();
    Map<String, Object> result = new HashMap<>();
    result.put("id", "1");
    result.put("category", "parent");
    result.put("description", "parent description");

    List<Map<String, Object>> resultListChild = new ArrayList<>();
    Map<String, Object> resultChild = new HashMap<>();
    resultChild.put("id", "2");
    resultChild.put("category", "child");
    resultChild.put("description", "child description");
    resultListChild.add(resultChild);
    result.put("subcategories", resultListChild);
    resultList.add(result);

    return resultList;
  }

  public static List<Map<String,Object>> LIST_MAP_RESULT = getListMapStringObject();

  private static GatewayBaseResponse<List<Map<String,Object>>> getMapResult(){
    GatewayBaseResponse<List<Map<String,Object>>> basePromoCategoryResponse = new GatewayBaseResponse<>();
    basePromoCategoryResponse.setCode("SUCCESS");
    basePromoCategoryResponse.setData(LIST_MAP_RESULT);

    return basePromoCategoryResponse;
  }

  public static GatewayBaseResponse<List<Map<String,Object>>> MAP_RESULT = getMapResult();

  public static TitleDescriptionResponse TITLE_DESCRIPTION_RESPONSE = new TitleDescriptionResponseBuilder()
      .withContent("content123")
      .withLang(Language.ID)
      .build();

  public static List<TitleDescriptionResponse> TITLE_DESCRIPTION_RESPONSES = Arrays.asList(TITLE_DESCRIPTION_RESPONSE);

  public static List<String> platform = Arrays.asList("web", "android");
  public static List<String> categories = Arrays.asList("cate1", "cate2");

  public static String START_DATE_STRING = "2019-10-10 00:00:00";
  public static String END_DATE_STRING = "2020-10-10 00:00:00";
  public static Date startDate = stringToDate(START_DATE_STRING);
  public static Date endDate = stringToDate(END_DATE_STRING);

  public static PromoCategoryResponse PROMO_CATEGORY_RESPONSE_GENERATED = new PromoCategoryResponseBuilder()
        .withId(ID)
        .build();

  private static GatewayBaseResponse<PromoCategoryResponse> getPromoCategoryResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<PromoCategoryResponse> basePromoCategoryResponse = new GatewayBaseResponse<>();
    basePromoCategoryResponse.setCode("SUCCESS");
    basePromoCategoryResponse.setData(PROMO_CATEGORY_RESPONSE_GENERATED);

    return basePromoCategoryResponse;
  }

  public static GatewayBaseResponse<PromoCategoryResponse> PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE = getPromoCategoryResponseGeneratedGatewayBaseResponse();

  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> RESULT = getResult();


  public static PromoCategoryRequest PROMO_CATEGORY_REQUEST = new PromoCategoryRequestBuilder()
      .build();


  private static RestResponsePage<PromoCategoryResponse> getData(){
    RestResponsePage<PromoCategoryResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(PROMO_CATEGORY_RESPONSE_GENERATED));

    return data;
  }

  public static RestResponsePage<PromoCategoryResponse> DATA = getData();



  static Date stringToDate(String date) {
    String pattern = "";
    if(date.length() == 19){
      pattern = "yyyy-MM-dd HH:mm:ss";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
      return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    } else if(date.length() == 10){
      pattern = "yyyy-MM-dd";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDate localDate = LocalDate.parse(date, formatter);

      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    return null;
  }

  public static String PROMO_CATEGORY_JSON = "{\n"
      + "  \"category\": \""+CATEGORY+"\",\n"
      + "  \"group\": \""+GROUP+"\",\n"
      + "  \"parentCategory\": \""+PARENT_CATEGORY+"\"\n"
      + "}";

  public static String PROMO_CATEGORY_JSON_METHOD_ARGUMENTS_NOT_VALID = "{\n"
      + "  \"category\": \"\",\n"
      + "  \"group\": \""+GROUP+"\",\n"
      + "  \"parentCategory\": \""+PARENT_CATEGORY+"\"\n"
      + "}";

}
