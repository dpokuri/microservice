package com.tl.booking.gateway.entity.constant.unit.test.promoCode;

import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponseBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PhotoFileRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PhotoFileRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPagePeriodResponseBuilder;
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

public class PromoCodeTestVariable {
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
  public static String CODE = "code123";
  public static String CAMPAIGN_ID = "campaign123";

  public static String START_DATE = "2080-01-01 10:10:10";
  public static String END_DATE = "2090-01-01 10:10:10";
  public static String CATEGORIES = "CATE123";
  public static String TITLE = "title123";
  public static Integer PAGE = 0;
  public static Integer SIZE = 10;
  public static Integer MAX = 10;
  public static PromoPageColumn COLUMN_SORT = PromoPageColumn.ID;
  public static SortDirection SORT_DIRECTION = SortDirection.DESC;


  public static String PRIVILEGES = "320,321,322,323,324,325,326,"
      + "330,331,332,333,334,335,336,337,338,339,340,350,351,352,353,354,355,356,357,"
      + "358,359,360,361,362,363,364,365,366,370,371,372,373,374,375";

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


  public static String START_DATE_STRING = "2019-10-10 00:00:00";
  public static String END_DATE_STRING = "2020-10-10 00:00:00";
  public static Date startDate = stringToDate(START_DATE_STRING);
  public static Date endDate = stringToDate(END_DATE_STRING);

  public static PromoPagePeriodResponse promoPagePeriodResponse = new PromoPagePeriodResponseBuilder()
      .withEndDate(endDate)
      .withStartDate(startDate)
      .build();

  public static List<PromoPagePeriodResponse> promoPagePeriodResponses = Arrays.asList(promoPagePeriodResponse);

  public static PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED = new PromoCodeResponseBuilder()
      .withCode(CODE)
      .withId(ID)
      .withPromoCodeStatus(PromoCodeStatus.ACTIVE)
      .build();

  public static PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED_PENDING = new PromoCodeResponseBuilder()
      .withId(ID)
      .withCode(CODE)
      .build();

  public static PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED_ACTIVATED = new PromoCodeResponseBuilder()
      .withId(ID)
      .withCode(CODE)
      .build();

  public static PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED_WITH_SLUG = new PromoCodeResponseBuilder()
      .withId(ID)
      .withCode(CODE)
      .build();

  private static GatewayBaseResponse<PromoCodeResponse> getPromoCodeResponseGeneratedGatewayBaseResponse(){

    GatewayBaseResponse<PromoCodeResponse> basePromoCodeResponse = new GatewayBaseResponse<>();
    basePromoCodeResponse.setCode("SUCCESS");
    basePromoCodeResponse.setData(PROMO_CODE_RESPONSE_GENERATED);

    return basePromoCodeResponse;
  }

  public static GatewayBaseResponse<PromoCodeResponse> PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE = getPromoCodeResponseGeneratedGatewayBaseResponse();


  private static GatewayBaseResponse<Boolean> getGatewayBaseResponseBoolean(){
    GatewayBaseResponse<Boolean> booleanGatewayBaseResponse = new GatewayBaseResponse<>();
    booleanGatewayBaseResponse.setCode("SUCCESS");
    booleanGatewayBaseResponse.setData(true);

    return booleanGatewayBaseResponse;
  }

  public static GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN = getGatewayBaseResponseBoolean();

  private static GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> getResult(){
    GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> result = new GatewayBaseResponse<>();
    result.setCode("SUCCESS");
    result.setData(DATA);
    return result;
  }

  public static GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> RESULT = getResult();





  public static PromoPagePeriodRequest promoPagePeriodRequest = new PromoPagePeriodRequestBuilder()
      .withEndDate(END_DATE_STRING)
      .withStartDate(START_DATE_STRING)
      .build();

  public static List<PromoPagePeriodRequest> promoPagePeriodRequests = Arrays.asList(promoPagePeriodRequest);

  public static TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST = new TitleDescriptionRequestBuilder()
      .withContent(TITLE)
      .withLang(Language.ID)
      .build();

  public static List<TitleDescriptionRequest> TITLE_DESCRIPTION_REQUESTS = Arrays.asList(TITLE_DESCRIPTION_REQUEST);

  public static PromoCodeRequest PROMO_CODE_REQUEST = new PromoCodeRequestBuilder()
      .withCampaignId(CAMPAIGN_ID)
      .withCode(CODE)
      .withMaxQty(MAX)
      .build();

//  public static PromoPageRequest PROMO_CODE_REQUEST_WITH_SLUG = new PromoCodeRequestBuilder()
//      .withPlatform(platform)
//      .withSlug(SLUG)
//      .withTitle(TITLE_DESCRIPTION_REQUESTS)
//      .withPeriods(promoPagePeriodRequests)
//      .withCategoryIds(Arrays.asList("cate1"))
//      .withPrecedence(PRESEDENCE)
//      .withPromoCode("promocode")
//      .withPromoCodeCount(100)
//      .build();
//
//  public static PromoPageRequest PROMO_CODE_REQUEST_WITHOUT_SLUG = new
//      PromoCodeRequestBuilder()
//      .withPlatform(platform)
//      .withTitle(TITLE_DESCRIPTION_REQUESTS)
//      .withCategoryIds(categories)
//      .withPeriods(promoPagePeriodRequests)
//      .withPrecedence(PRESEDENCE)
//      .withPromoCode(PROMOCODE)
//      .withPromoCodeCount(PROMOCODE_COUNT)
//      .build();



  private static RestResponsePage<PromoCodeResponse> getData(){
    RestResponsePage<PromoCodeResponse> data = new RestResponsePage<>();
    data.setTotalPages(10);
    data.setContent(Arrays.asList(PROMO_CODE_RESPONSE_GENERATED));

    return data;
  }

  public static RestResponsePage<PromoCodeResponse> DATA = getData();



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



  public static String PROMO_CODE_JSON_BODY = "{\"campaignId\": \""+ CAMPAIGN_ID +"\",\"code\": \""+ CODE +"\",\"maxQty\": "+ MAX +"}";


}
