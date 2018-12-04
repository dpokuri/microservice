import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.PromoPageOutboundServiceImpl;
import com.tl.booking.gateway.outbound.impl.configuration.PromoListEndPointService;
import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;


public class PromoPageOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_PENDING;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<PromoPageResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<PromoPageResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<PromoPageResponse>> RESULT_ACTIVE;

  RestResponsePage<PromoPageResponse> DATA;

  RestResponsePage<PromoPageResponse> DATA_PENDING;

  RestResponsePage<PromoPageResponse> DATA_ACTIVE;

  PromoPageRequest PROMO_PAGE_REQUEST;

  PromoPageRequest PROMO_PAGE_WITHOUT_REQUEST;

  PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED;

  PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED_PENDING;

  PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED_ACTIVE;

  PromoPageResponse PROMO_PAGE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<PromoPageResponse> PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<PromoPageResponse> PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<PromoPageResponse> PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  String SLUG = "123";

  @InjectMocks
  PromoPageOutboundServiceImpl promoPageOutboundService;

  @Mock
  PromoListEndPointService promoListEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> PROMO_PAGE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> PROMO_PAGE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> PROMO_PAGE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<PromoPageResponse>> PROMO_PAGE_RESPONSE;


  @Mock
  Call<GatewayBaseResponse<Boolean>> CALL_TRUE;


  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.HEADER = new HashMap<>();
    this.HEADER.put("storeId", CommonTestVariable.STORE_ID);
    this.HEADER.put("channelId", CommonTestVariable.CHANNEL_ID);
    this.HEADER.put("requestId", CommonTestVariable.REQUEST_ID);
    this.HEADER.put("serviceId", CommonTestVariable.SERVICE_ID);
    this.HEADER.put("username", CommonTestVariable.USERNAME);

    this.QUERY = new HashMap<>();
    this.QUERY.put("title", "test123");
    this.QUERY.put("categories", "cate123");
    this.QUERY.put("status", "DRAFT");
    this.QUERY.put("precedence", "1");
    this.QUERY.put("page", "0");
    this.QUERY.put("size", "10");
    this.QUERY.put("columnSort", "ID");
    this.QUERY.put("sortDirection", "DESC");

    this.QUERY_PENDING = new HashMap<>();
    this.QUERY_PENDING.put("title", "test123");
    this.QUERY_PENDING.put("categories", "cate123");
    this.QUERY_PENDING.put("status", "PENDING");
    this.QUERY_PENDING.put("precedence", "1");
    this.QUERY_PENDING.put("page", "0");
    this.QUERY_PENDING.put("size", "10");
    this.QUERY_PENDING.put("columnSort", "ID");
    this.QUERY_PENDING.put("sortDirection", "DESC");

    this.QUERY_ACTIVE = new HashMap<>();
    this.QUERY_ACTIVE.put("title", "test123");
    this.QUERY_ACTIVE.put("categories", "cate123");
    this.QUERY_ACTIVE.put("status", "ACTIVE");
    this.QUERY_ACTIVE.put("precedence", "1");
    this.QUERY_ACTIVE.put("page", "0");
    this.QUERY_ACTIVE.put("size", "10");
    this.QUERY_ACTIVE.put("columnSort", "ID");
    this.QUERY_ACTIVE.put("sortDirection", "DESC");

    this.QUERY_NULL = new HashMap<>();
    this.QUERY_NULL.put("page", "0");
    this.QUERY_NULL.put("size", "10");

    String ID = "123";

    TitleDescriptionResponse TITLE_DESCRIPTION_RESPONSE = new TitleDescriptionResponseBuilder()
        .withContent("content123")
        .withLang(Language.ID)
        .build();

    List<TitleDescriptionResponse> TITLE_DESCRIPTION_RESPONSES = Arrays.asList(TITLE_DESCRIPTION_RESPONSE);

    List<String> platform = Arrays.asList("web", "android");
    List<String> categories = Arrays.asList("cate1", "cate2");

    String START_DATE_STRING = "2019-10-10 00:00:00";
    String END_DATE_STRING = "2020-10-10 00:00:00";
    Date startDate = stringToDate(START_DATE_STRING);
    Date endDate = stringToDate(END_DATE_STRING);

    PromoPagePeriodResponse promoPagePeriodResponse = new PromoPagePeriodResponseBuilder()
        .withEndDate(endDate)
        .withStartDate(startDate)
        .build();

    List<PromoPagePeriodResponse> promoPagePeriodResponses = Arrays.asList(promoPagePeriodResponse);

    PROMO_PAGE_RESPONSE_GENERATED = new PromoPageResponseBuilder()
        .withTitle(TITLE_DESCRIPTION_RESPONSES)
        .withPlatform(platform)
        .withId(ID)
        .withPeriods(promoPagePeriodResponses)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withStatus(PromoPageStatus.DRAFT)
        .build();

    PROMO_PAGE_RESPONSE_GENERATED_PENDING = new PromoPageResponseBuilder()
        .withTitle(TITLE_DESCRIPTION_RESPONSES)
        .withPlatform(platform)
        .withId(ID)
        .withPeriods(promoPagePeriodResponses)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withStatus(PromoPageStatus.PENDING)
        .build();

    PROMO_PAGE_RESPONSE_GENERATED_ACTIVE = new PromoPageResponseBuilder()
        .withTitle(TITLE_DESCRIPTION_RESPONSES)
        .withPlatform(platform)
        .withId(ID)
        .withPeriods(promoPagePeriodResponses)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withStatus(PromoPageStatus.ACTIVE)
        .build();

    PROMO_PAGE_RESPONSE_GENERATED_NULL = new PromoPageResponseBuilder()
        .withTitle(TITLE_DESCRIPTION_RESPONSES)
        .withPlatform(platform)
        .withId(ID)
        .withPeriods(promoPagePeriodResponses)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withStatus(PromoPageStatus.ACTIVE)
        .build();


    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE = new GatewayBaseResponse<>();
    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE.setCode("SUCCESS");
    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE.setData(PROMO_PAGE_RESPONSE_GENERATED);

    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING = new GatewayBaseResponse<>();
    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING.setCode("SUCCESS");
    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING.setData(PROMO_PAGE_RESPONSE_GENERATED_PENDING);

    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE = new GatewayBaseResponse<>();
    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE.setCode("SUCCESS");
    PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE.setData
        (PROMO_PAGE_RESPONSE_GENERATED_ACTIVE);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(PROMO_PAGE_RESPONSE_GENERATED));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);


    PromoPagePeriodRequest promoPagePeriodRequest = new PromoPagePeriodRequestBuilder()
        .withEndDate(END_DATE_STRING)
        .withStartDate(START_DATE_STRING)
        .build();

    List<PromoPagePeriodRequest> promoPagePeriodRequests = Arrays.asList(promoPagePeriodRequest);

    PhotoFileRequest photoFileRequest = new PhotoFileRequestBuilder()
        .withData("data")
        .withName("name")
        .build();

    TITLE_DESCRIPTION_REQUEST = new TitleDescriptionRequestBuilder()
        .withContent("content123")
        .withLang(Language.ID)
        .build();

    List<TitleDescriptionRequest> TITLE_DESCRIPTION_REQUESTS = Arrays.asList(TITLE_DESCRIPTION_REQUEST);

    this.PROMO_PAGE_REQUEST = new PromoPageRequestBuilder()
        .withPlatform(platform)
        .withTitle(TITLE_DESCRIPTION_REQUESTS)
        .withPeriods(promoPagePeriodRequests)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withSlug(SLUG)
        .build();

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(PROMO_PAGE_RESPONSE_GENERATED));

    this.DATA_PENDING = new RestResponsePage<>();
    this.DATA_PENDING.setTotalPages(10);
    this.DATA_PENDING.setContent(Arrays.asList(PROMO_PAGE_RESPONSE_GENERATED_PENDING));

    this.DATA_ACTIVE = new RestResponsePage<>();
    this.DATA_ACTIVE.setTotalPages(10);
    this.DATA_ACTIVE.setContent(Arrays.asList(PROMO_PAGE_RESPONSE_GENERATED_ACTIVE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    this.RESULT_PENDING = new GatewayBaseResponse<>();
    this.RESULT_PENDING.setCode("SUCCCESS");
    this.RESULT_PENDING.setData(this.DATA_PENDING);

    this.RESULT_ACTIVE = new GatewayBaseResponse<>();
    this.RESULT_ACTIVE.setCode("SUCCCESS");
    this.RESULT_ACTIVE.setData(this.DATA_ACTIVE);


    PROMO_PAGE_WITHOUT_REQUEST = new PromoPageRequestBuilder()
        .withTitle(TITLE_DESCRIPTION_REQUESTS)
        .withPlatform(platform)
        .withCategoryIds(categories)
        .withPeriods(promoPagePeriodRequests)
        .withPrecedence(1)
        .withPromoCode("promocode")
        .withPromoCodeCount(100)
        .withSlug(SLUG)
        .build();

  }

  @Test
  public void findPromoPageFilterPaginatedTest() throws Exception {
    when(this.promoListEndPointService.findPromoPageFilterPaginated(this.HEADER, this.QUERY)).thenReturn(PROMO_PAGE_RESPONSE_PAGE);
    when(this.PROMO_PAGE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RestResponsePage<PromoPageResponse>> promoPageResponse = this.promoPageOutboundService
        .findPromoPageFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("title"), this.QUERY.get("categories"), PromoPageStatus.DRAFT, 1,0,
            10, PromoPageColumn.valueOf(this.QUERY.get("columnSort")),
            SortDirection.valueOf(this.QUERY.get("sortDirection")),"300,301,302,303,304,305,"
                + "306,307,308,309,310,311,312,313").blockingGet();

    assertEquals(10,promoPageResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoPageFilterPaginated(this.HEADER, this.QUERY);
  }

  @Test
  public void findPromoPageFilterPaginatedNotAllowedToRequestApprovalTest() throws Exception {
    when(this.promoListEndPointService.findPromoPageFilterPaginated(this.HEADER, this.QUERY)).thenReturn(PROMO_PAGE_RESPONSE_PAGE);
    when(this.PROMO_PAGE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RestResponsePage<PromoPageResponse>> promoPageResponse = this.promoPageOutboundService
        .findPromoPageFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("title"), this.QUERY.get("categories"), PromoPageStatus.DRAFT, 1,0,
            10, PromoPageColumn.valueOf(this.QUERY.get("columnSort")),
            SortDirection.valueOf(this.QUERY.get("sortDirection")), "").blockingGet();

    assertEquals(10,promoPageResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoPageFilterPaginated(this.HEADER, this.QUERY);
  }

  @Test
  public void findPromoPageFilterPaginatedPendingTest() throws Exception {
    when(this.promoListEndPointService.findPromoPageFilterPaginated(this.HEADER, this
        .QUERY_PENDING))
        .thenReturn(PROMO_PAGE_RESPONSE_PAGE_PENDING);
    when(this.PROMO_PAGE_RESPONSE_PAGE_PENDING.execute()).thenReturn(Response.success(this
        .RESULT_PENDING));

    GatewayBaseResponse<RestResponsePage<PromoPageResponse>> promoPageResponse = this.promoPageOutboundService
        .findPromoPageFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("title"), this.QUERY_PENDING.get("categories"), PromoPageStatus.PENDING, 1,0,
            10, PromoPageColumn.valueOf(this.QUERY_PENDING.get("columnSort")),
            SortDirection.valueOf(this.QUERY_PENDING.get("sortDirection")),"300,301,302,303,304,305,"
                + "306,307,308,309,310,311,312,313").blockingGet();

    assertEquals(10,promoPageResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoPageFilterPaginated(this.HEADER, this.QUERY_PENDING);
  }

  @Test
  public void findPromoPageFilterPaginatedActiveTest() throws Exception {
    when(this.promoListEndPointService.findPromoPageFilterPaginated(this.HEADER, this
        .QUERY_ACTIVE))
        .thenReturn(PROMO_PAGE_RESPONSE_PAGE_ACTIVE);
    when(this.PROMO_PAGE_RESPONSE_PAGE_ACTIVE.execute()).thenReturn(Response.success(this
        .RESULT_ACTIVE));

    GatewayBaseResponse<RestResponsePage<PromoPageResponse>> promoPageResponse = this.promoPageOutboundService
        .findPromoPageFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("title"), this.QUERY_ACTIVE.get("categories"), PromoPageStatus.ACTIVE,
            1,0,
            10, PromoPageColumn.valueOf(this.QUERY_ACTIVE.get("columnSort")),
            SortDirection.valueOf(this.QUERY_ACTIVE.get("sortDirection")),"300,301,302,303,304,305,"
                + "306,307,308,309,310,311,312,313").blockingGet();

    assertEquals(10,promoPageResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoPageFilterPaginated(this.HEADER, this.QUERY_ACTIVE);
  }

  @Test
  public void findPromoPageFilterPaginatedNullTest() throws Exception {
    when(this.promoListEndPointService.findPromoPageFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(PROMO_PAGE_RESPONSE_PAGE_ACTIVE);
    when(this.PROMO_PAGE_RESPONSE_PAGE_ACTIVE.execute()).thenReturn(Response.success(this
        .RESULT_ACTIVE));

    GatewayBaseResponse<RestResponsePage<PromoPageResponse>> promoPageResponse = this.promoPageOutboundService
        .findPromoPageFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            null, null, null,
            null,0,
            10, null,
            null,"300,301,302,303,304,305,"
                + "306,307,308,309,310,311,312,313").blockingGet();

    assertEquals(10,promoPageResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoPageFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findPromoPageFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.findPromoPageFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.promoPageOutboundService
          .findPromoPageFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("title"), this.QUERY.get("categories"), PromoPageStatus.DRAFT, 1,0,10, PromoPageColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")), "300,301,302,303,304,305,"
                  + "306,307,308,309,310,311,312,313").blockingGet();
    }
    catch (Exception e) {
      verify(this.promoListEndPointService).findPromoPageFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createPromoPageTest() throws Exception {
    when(this.promoListEndPointService.createPromoPage(HEADER, PROMO_PAGE_REQUEST)).thenReturn
        (PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService.createPromoPage
        (CommonTestVariable.MANDATORY_REQUEST, PROMO_PAGE_REQUEST).blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).createPromoPage(this.HEADER, PROMO_PAGE_REQUEST);
  }

  @Test
  public void createPromoPageTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.createPromoPage(HEADER, PROMO_PAGE_REQUEST)).thenReturn
        (null);
    try {
      this.promoPageOutboundService
          .createPromoPage(CommonTestVariable
              .MANDATORY_REQUEST, PROMO_PAGE_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoListEndPointService).createPromoPage(this.HEADER, PROMO_PAGE_REQUEST);
    }
  }

  @Test
  public void updatePromoPageTest() throws Exception {
    when(this.promoListEndPointService.updatePromoPage(HEADER, PROMO_PAGE_WITHOUT_REQUEST, "123")).thenReturn
        (this.PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
        .updatePromoPage(CommonTestVariable
            .MANDATORY_REQUEST, PROMO_PAGE_WITHOUT_REQUEST, "123").blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).updatePromoPage(this.HEADER, PROMO_PAGE_WITHOUT_REQUEST, "123");
  }

  @Test
  public void updatePromoPageTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.updatePromoPage(HEADER, PROMO_PAGE_WITHOUT_REQUEST, "123")).thenReturn
        (null);

    try {
      GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
          .updatePromoPage(CommonTestVariable
              .MANDATORY_REQUEST, PROMO_PAGE_WITHOUT_REQUEST, "123").blockingGet();
    }catch (Exception e) {
      verify(this.promoListEndPointService).updatePromoPage(this.HEADER, PROMO_PAGE_WITHOUT_REQUEST, "123");
    }

  }

  @Test
  public void deletePromoPageTest() throws Exception {
    when(this.promoListEndPointService.deletePromoPage(this.HEADER, "123")).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.promoPageOutboundService
        .deletePromoPage(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoListEndPointService).deletePromoPage(this.HEADER, "123");
  }

  @Test
  public void deletePromoPageTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.deletePromoPage(HEADER, "123")).thenReturn
        (null);
    try {
      this.promoPageOutboundService
          .deletePromoPage(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }catch (Exception e) {
      verify(this.promoListEndPointService).deletePromoPage(this.HEADER, "123");
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoListEndPointService.findPromoPageBySlug(HEADER, "123")).thenReturn
        (PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
        .findPromoPageBySlug(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).findPromoPageBySlug(this.HEADER, "123");
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoListEndPointService.findPromoPageBySlug(HEADER, "123")).thenReturn
        (null);

    try {
      this.promoPageOutboundService
          .findPromoPageBySlug(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    } catch (Exception e) {
      verify(this.promoListEndPointService).findPromoPageBySlug(this.HEADER, "123");
    }

  }

  @Test
  public void updatePromoPageToPendingTest() throws Exception {
    when(this.promoListEndPointService.updateStatusPendingPromoPage(HEADER, "123")).thenReturn
        (this.PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
        .updateStatusPendingPromoPage(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).updateStatusPendingPromoPage(this.HEADER, "123");
  }

  @Test
  public void updatePromoPageToPendingTestExceptionError() throws Exception {
    when(this.promoListEndPointService.updateStatusPendingPromoPage(HEADER, "123")).thenReturn
        (null);

    try{
      this.promoPageOutboundService
          .updateStatusPendingPromoPage(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoListEndPointService).updateStatusPendingPromoPage(this.HEADER, "123");
    }
  }

  @Test
  public void updatePromoPageToActivatedTest() throws Exception {
    when(this.promoListEndPointService.updateStatusActivatedPromoPage(HEADER, "123")).thenReturn
        (this.PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
        .updateStatusActivatedPromoPage(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).updateStatusActivatedPromoPage(this.HEADER, "123");
  }

  @Test
  public void updatePromoPageToActivatedTestExceptionError() throws Exception {
    when(this.promoListEndPointService.updateStatusActivatedPromoPage(HEADER, "123")).thenReturn
        (null);

    try{
      this.promoPageOutboundService
          .updateStatusActivatedPromoPage(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoListEndPointService).updateStatusActivatedPromoPage(this.HEADER, "123");
    }
  }


  @Test
  public void updatePromoPageToInActivatedTest() throws Exception {
    when(this.promoListEndPointService.updateStatusInActivatedPromoPage(HEADER, "123")).thenReturn
        (this.PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
        .updateStatusInActivatedPromoPage(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).updateStatusInActivatedPromoPage(this.HEADER, "123");
  }



  @Test
  public void updatePromoPageToInActivatedTestExceptionError() throws Exception {
    when(this.promoListEndPointService.updateStatusInActivatedPromoPage(HEADER, "123")).thenReturn
        (null);

    try{
      this.promoPageOutboundService
          .updateStatusInActivatedPromoPage(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoListEndPointService).updateStatusInActivatedPromoPage(this.HEADER, "123");
    }
  }

  @Test
  public void updatePromoPageToRejectedTest() throws Exception {
    when(this.promoListEndPointService.updateStatusRejectedPromoPage(HEADER, "123")).thenReturn
        (this.PROMO_PAGE_RESPONSE);
    when(this.PROMO_PAGE_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoPageResponse> promoPageResponseGatewayBaseResponse = this.promoPageOutboundService
        .updateStatusRejectedPromoPage(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(this.TITLE_DESCRIPTION_REQUEST.getLang(), promoPageResponseGatewayBaseResponse.getData().getTitle().get(0).getLang());

    verify(this.promoListEndPointService).updateStatusRejectedPromoPage(this.HEADER, "123");
  }



  @Test
  public void updatePromoPageToRejectedTestExceptionError() throws Exception {
    when(this.promoListEndPointService.updateStatusRejectedPromoPage(HEADER, "123")).thenReturn
        (null);

    try{
      this.promoPageOutboundService
          .updateStatusRejectedPromoPage(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoListEndPointService).updateStatusRejectedPromoPage(this.HEADER, "123");
    }
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoListEndPointService);
  }

  private static Date stringToDate(String date) {
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

}
