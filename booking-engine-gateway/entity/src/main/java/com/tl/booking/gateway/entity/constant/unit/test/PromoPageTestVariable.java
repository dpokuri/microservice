package com.tl.booking.gateway.entity.constant.unit.test;

import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PhotoFileRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PhotoFileRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionRequestBuilder;
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

public class PromoPageTestVariable {
  public static List<String> CATEGORIES_ARRAY = Arrays.asList("Mangga", "Melon");
  public static String PLATFORM = "platform123";
  public static List<String> PLATFORM_ARRAY = Arrays.asList(PLATFORM);

  public static String LANG = "lang123";
  public static String CONTENT = "content123";

  public static String PHOTO = "photo123";

  public static PhotoFileRequest PHOTO_FILE_REQUEST = new PhotoFileRequestBuilder()
      .withName(PHOTO)
      .withData(PHOTO)
      .build();

  public static Integer PRESEDENCE = 1;
  public static Integer PROMOCODE_COUNT = 1;
  public static String PROMOCODE = "promocode";


  public static String ID = "123";
  public static String SLUG = "slug123";
  public static String START_DATE = "2080-01-01 10:10:10";
  public static String END_DATE = "2090-01-01 10:10:10";
  public static String CATEGORIES = "CATE123";
  public static String TITLE = "title123";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static PromoPageColumn COLUMN_SORT = PromoPageColumn.ID;
  public static SortDirection SORT_DIRECTION = SortDirection.DESC;


  public static String PRIVILEGES = "300,301,302,303,304,305,306,";

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
    query.put("startDate", START_DATE);
    query.put("endDate", END_DATE);
    query.put("title", TITLE);
    query.put("categories", CATEGORIES);
    query.put("page", PAGE.toString());
    query.put("size", SIZE.toString());
    query.put("columnSort", COLUMN_SORT.getName());
    query.put("sortDirection", SortDirection.DESC.getName());

    return query;
  }

  public static Map<String, String> QUERY = getQuery();






  public static TitleDescriptionResponse TITLE_DESCRIPTION_RESPONSE = new TitleDescriptionResponseBuilder()
      .withContent("content123")
      .withLang(Language.ID)
      .build();

  public static List<TitleDescriptionResponse> TITLE_DESCRIPTION_RESPONSES = Arrays.asList(TITLE_DESCRIPTION_RESPONSE);

  public static List<String> platform = Arrays.asList(PLATFORM);
  public static List<String> categories = Arrays.asList("cate1", "cate2");

  public static String START_DATE_STRING = "2019-10-10 00:00:00";
  public static String END_DATE_STRING = "2020-10-10 00:00:00";
  public static Date startDate = stringToDate(START_DATE_STRING);
  public static Date endDate = stringToDate(END_DATE_STRING);

  public static PromoPagePeriodResponse promoPagePeriodResponse = new PromoPagePeriodResponseBuilder()
      .withEndDate(endDate)
      .withStartDate(startDate)
      .build();

  public static List<PromoPagePeriodResponse> promoPagePeriodResponses = Arrays.asList(promoPagePeriodResponse);

  public static PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED = new PromoPageResponseBuilder()
        .withTitle(TITLE_DESCRIPTION_RESPONSES)
        .withPlatform(platform)
        .withId(ID)
        .withPeriods(promoPagePeriodResponses)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withStatus(PromoPageStatus.DRAFT)
        .build();

  public static PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED_PENDING = new PromoPageResponseBuilder()
      .withTitle(TITLE_DESCRIPTION_RESPONSES)
      .withPlatform(platform)
      .withId(ID)
      .withPeriods(promoPagePeriodResponses)
      .withPrecedence(1)
      .withPromoCode("promocode")
      .withPromoCodeCount(100)
      .withStatus(PromoPageStatus.PENDING)
      .build();

  public static PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED_ACTIVATED = new PromoPageResponseBuilder()
      .withTitle(TITLE_DESCRIPTION_RESPONSES)
      .withPlatform(platform)
      .withId(ID)
      .withPeriods(promoPagePeriodResponses)
      .withPrecedence(1)
      .withPromoCode(PROMOCODE)
      .withPromoCodeCount(100)
      .withStatus(PromoPageStatus.ACTIVE)
      .build();

  public static PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED_WITH_SLUG = new PromoPageResponseBuilder()
      .withTitle(TITLE_DESCRIPTION_RESPONSES)
      .withPlatform(platform)
      .withSlug(SLUG)
      .withPeriods(promoPagePeriodResponses)
      .withPrecedence(1)
      .withPromoCode("promocode")
      .withPromoCodeCount(100)
      .withStatus(PromoPageStatus.DRAFT)
      .build();

  private static GatewayBaseResponse<PromoPageResponse> getPromoPageResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<PromoPageResponse> basePromoPageResponse = new GatewayBaseResponse<>();
    basePromoPageResponse.setCode("SUCCESS");
    basePromoPageResponse.setData(PROMO_PAGE_RESPONSE_GENERATED);

    return basePromoPageResponse;
  }

  private static GatewayBaseResponse<PromoPageResponse> getPromoPageResponseGeneratedGatewayBaseResponsePending(){

    GatewayBaseResponse<PromoPageResponse> basePromoPageResponse = new GatewayBaseResponse<>();
    basePromoPageResponse.setCode("SUCCESS");
    basePromoPageResponse.setData(PROMO_PAGE_RESPONSE_GENERATED_PENDING);

    return basePromoPageResponse;
  }

  private static GatewayBaseResponse<PromoPageResponse> getPromoPageResponseGeneratedGatewayBaseResponseActivated(){

    GatewayBaseResponse<PromoPageResponse> basePromoPageResponse = new GatewayBaseResponse<>();
    basePromoPageResponse.setCode("SUCCESS");
    basePromoPageResponse.setData(PROMO_PAGE_RESPONSE_GENERATED_ACTIVATED);

    return basePromoPageResponse;
  }

  public static GatewayBaseResponse<PromoPageResponse> PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE = getPromoPageResponseGeneratedGatewayBaseResponse();
  public static GatewayBaseResponse<PromoPageResponse> PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING = getPromoPageResponseGeneratedGatewayBaseResponsePending();

  public static GatewayBaseResponse<PromoPageResponse> PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVATED = getPromoPageResponseGeneratedGatewayBaseResponseActivated();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<RestResponsePage<PromoPageResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<PromoPageResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<PromoPageResponse>> RESULT = getResult();





  public static PromoPagePeriodRequest promoPagePeriodRequest = new PromoPagePeriodRequestBuilder()
      .withEndDate(END_DATE_STRING)
      .withStartDate(START_DATE_STRING)
      .build();

  public static List<PromoPagePeriodRequest> promoPagePeriodRequests = Arrays.asList(promoPagePeriodRequest);

  public static PhotoFileRequest photoFileRequest = new PhotoFileRequestBuilder()
      .withData(PHOTO)
      .withName(PHOTO)
      .build();

  public static TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST = new TitleDescriptionRequestBuilder()
      .withContent(TITLE)
      .withLang(Language.ID)
      .build();

  public static List<TitleDescriptionRequest> TITLE_DESCRIPTION_REQUESTS = Arrays.asList(TITLE_DESCRIPTION_REQUEST);

  public static PromoPageRequest PROMO_PAGE_REQUEST = new PromoPageRequestBuilder()
      .withPlatform(platform)
      .withTitle(TITLE_DESCRIPTION_REQUESTS)
      .withPeriods(promoPagePeriodRequests)
      .withPrecedence(1)
      .withCategoryIds(categories)
      .withSlug(SLUG)
      .withPromoCode(PROMOCODE)
      .withPromoCodeCount(PROMOCODE_COUNT)
      .build();

  public static PromoPageRequest PROMO_PAGE_REQUEST_WITH_SLUG = new PromoPageRequestBuilder()
      .withPlatform(platform)
      .withSlug(SLUG)
      .withTitle(TITLE_DESCRIPTION_REQUESTS)
      .withPeriods(promoPagePeriodRequests)
      .withCategoryIds(Arrays.asList("cate1"))
      .withPrecedence(PRESEDENCE)
      .withPromoCode("promocode")
      .withPromoCodeCount(100)
      .build();

  public static PromoPageRequest PROMO_PAGE_REQUEST_WITHOUT_SLUG = new
      PromoPageRequestBuilder()
      .withPlatform(platform)
      .withTitle(TITLE_DESCRIPTION_REQUESTS)
      .withCategoryIds(categories)
      .withPeriods(promoPagePeriodRequests)
      .withPrecedence(PRESEDENCE)
      .withPromoCode(PROMOCODE)
      .withPromoCodeCount(PROMOCODE_COUNT)
      .build();



  private static RestResponsePage<PromoPageResponse> getData(){
    RestResponsePage<PromoPageResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(PROMO_PAGE_RESPONSE_GENERATED));

    return data;
  }

  public static RestResponsePage<PromoPageResponse> DATA = getData();



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



  public static String PROMO_PAGE_JSON = "{\n"
      + "  \"categoryIds\": [\n"
      + "    \"cate1\"\n"
      + ",    \"cate2\"\n"
      + "  ],\n"
      + "  \"periods\": [\n"
      + "    {\n"
      + "      \"endDate\": \""+END_DATE_STRING+"\",\n"
      + "      \"startDate\": \""+START_DATE_STRING+"\"\n"
      + "    }\n"
      + "  ],\n"
      + "  \"photo\": {\n"
      + "    \"data\": \""+PHOTO+"\",\n"
      + "    \"name\": \""+PHOTO+"\"\n"
      + "  },\n"
      + "  \"platform\": [\n"
      + "    \""+PLATFORM+"\"\n"
      + "  ],\n"
      + "  \"precedence\": "+PRESEDENCE+",\n"
      + "  \"promoCode\": \""+PROMOCODE+"\",\n"
      + "  \"promoCodeCount\": "+PROMOCODE_COUNT+",\n"
      + "  \"slug\": \""+SLUG+"\",\n"
      + "  \"title\": [\n"
      + "    {\n"
      + "      \"content\": \""+TITLE+"\",\n"
      + "      \"lang\": \""+Language.ID.getCode()+"\"\n"
      + "    }\n"
      + "  ]\n"
      + "}";

  public static String PROMO_PAGE_JSON_ARGUMENT_NOT_VALID = "{\n"
      + "  \"categoryIds\": [\n"
      + "    \"cate1\"\n"
      + ",    \"cate2\"\n"
      + "  ],\n"
      + "  \"periods\": [\n"
      + "    {\n"
      + "      \"endDate\": \""+END_DATE_STRING+"\",\n"
      + "      \"startDate\": \""+START_DATE_STRING+"\"\n"
      + "    }\n"
      + "  ],\n"
      + "  \"photo\": {\n"
      + "    \"data\": \""+PHOTO+"\",\n"
      + "    \"name\": \""+PHOTO+"\"\n"
      + "  },\n"
      + "  \"platform\": [\n"
      + "    \""+PLATFORM+"\"\n"
      + "  ],\n"
      + "  \"precedence\": "+PRESEDENCE+",\n"
      + "  \"promocode\": \"\",\n"
      + "  \"promoCodeCount\": "+PROMOCODE_COUNT+",\n"
      + "  \"slug\": \""+SLUG+"\",\n"
      + "  \"title\": [\n"
      + "    {\n"
      + "      \"content\": \""+TITLE+"\",\n"
      + "      \"lang\": \""+Language.ID.getCode()+"\"\n"
      + "    }\n"
      + "  ]\n"
      + "}";


}
