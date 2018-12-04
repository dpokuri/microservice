import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.PromoCategoryOutboundServiceImpl;
import com.tl.booking.gateway.outbound.impl.configuration.PromoListEndPointService;
import com.tl.booking.gateway.entity.constant.enums.PromoCategoryColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequestBuilder;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;


public class PromoCategoryOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> RESULT;

  RestResponsePage<PromoCategoryResponse> DATA;

  PromoCategoryRequest PROMO_CATEGORY_REQUEST;

  PromoCategoryResponse PROMO_CATEGORY_RESPONSE_GENERATED;

  GatewayBaseResponse<PromoCategoryResponse> PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  @InjectMocks
  PromoCategoryOutboundServiceImpl promoCategoryOutboundService;

  @Mock
  PromoListEndPointService promoListEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>> PROMO_CATEGORY_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<PromoCategoryResponse>> PROMO_CATEGORY_RESPONSE;


  @Mock
  Call<GatewayBaseResponse<Boolean>> CALL_TRUE;

  @Mock
  Call<GatewayBaseResponse<List<Map<String, Object>>>> CALL_MAP;


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
    this.QUERY.put("category", "test123");
    this.QUERY.put("page", "0");
    this.QUERY.put("size", "10");
    this.QUERY.put("columnSort", "ID");
    this.QUERY.put("sortDirection", "DESC");

    String ID = "123";


    PROMO_CATEGORY_RESPONSE_GENERATED = new PromoCategoryResponseBuilder()
        .build();

    PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE = new GatewayBaseResponse<>();
    PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE.setCode("SUCCESS");
    PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE.setData(PROMO_CATEGORY_RESPONSE_GENERATED);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

    this.PROMO_CATEGORY_REQUEST = new PromoCategoryRequestBuilder()
        .build();

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(PROMO_CATEGORY_RESPONSE_GENERATED));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

  }

  @Test
  public void findPromoCategoryFilterPaginatedTest() throws Exception {
    when(this.promoListEndPointService.findPromoCategoryFilterPaginated(this.HEADER, this.QUERY)).thenReturn(PROMO_CATEGORY_RESPONSE_PAGE);
    when(this.PROMO_CATEGORY_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> promoCategoryResponse = this.promoCategoryOutboundService
        .findPromoCategoryFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("category"),0,10, PromoCategoryColumn.valueOf(this.QUERY.get("columnSort")),
            SortDirection.valueOf(this.QUERY.get("sortDirection"))).blockingGet();

    assertEquals(10,promoCategoryResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoCategoryFilterPaginated(this.HEADER, this.QUERY);
  }


  @Test
  public void findPromoCategoryFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.findPromoCategoryFilterPaginated(this.HEADER, this.QUERY)).thenReturn(null);

    try{
      this.promoCategoryOutboundService
          .findPromoCategoryFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("category"),0,10, PromoCategoryColumn.valueOf(this.QUERY.get
          ("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection"))).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoListEndPointService).findPromoCategoryFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void findPromoCategoryFilterPaginatedBlankValueTest() throws Exception {
    Map<String, String> QUERY_NULL = new HashMap<>();
    QUERY_NULL = new HashMap<>();
    QUERY_NULL.put("page", "0");
    QUERY_NULL.put("size", "10");

    when(this.promoListEndPointService.findPromoCategoryFilterPaginated(this.HEADER, QUERY_NULL)).thenReturn(PROMO_CATEGORY_RESPONSE_PAGE);
    when(this.PROMO_CATEGORY_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>> promoCategoryResponse = this.promoCategoryOutboundService
        .findPromoCategoryFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            null,0,10, null, null).blockingGet();

    assertEquals(10,promoCategoryResponse.getData().getTotalPages());

    verify(this.promoListEndPointService).findPromoCategoryFilterPaginated(this.HEADER, QUERY_NULL);
  }

  @Test
  public void createPromoCategoryTest() throws Exception {
    when(this.promoListEndPointService.createPromoCategory(HEADER, PROMO_CATEGORY_REQUEST)).thenReturn
        (PROMO_CATEGORY_RESPONSE);
    when(this.PROMO_CATEGORY_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCategoryResponse> promoCategoryResponseGatewayBaseResponse = this.promoCategoryOutboundService.createPromoCategory
        (CommonTestVariable.MANDATORY_REQUEST, PROMO_CATEGORY_REQUEST).blockingGet();

    verify(this.promoListEndPointService).createPromoCategory(this.HEADER, PROMO_CATEGORY_REQUEST);
  }

  @Test
  public void createPromoCategoryTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.createPromoCategory(HEADER, PROMO_CATEGORY_REQUEST)).thenReturn
        (null);
    try {
      this.promoCategoryOutboundService
          .createPromoCategory(CommonTestVariable
              .MANDATORY_REQUEST, PROMO_CATEGORY_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoListEndPointService).createPromoCategory(this.HEADER, PROMO_CATEGORY_REQUEST);
    }
  }

  @Test
  public void updatePromoCategoryTest() throws Exception {
    when(this.promoListEndPointService.updatePromoCategory(HEADER, PROMO_CATEGORY_REQUEST, "123")).thenReturn
        (this.PROMO_CATEGORY_RESPONSE);
    when(this.PROMO_CATEGORY_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCategoryResponse> promoCategoryResponseGatewayBaseResponse = this.promoCategoryOutboundService
        .updatePromoCategory(CommonTestVariable
            .MANDATORY_REQUEST, PROMO_CATEGORY_REQUEST, "123").blockingGet();

    verify(this.promoListEndPointService).updatePromoCategory(this.HEADER, PROMO_CATEGORY_REQUEST, "123");
  }

  @Test
  public void updatePromoCategoryTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.updatePromoCategory(HEADER, PROMO_CATEGORY_REQUEST, "123")).thenReturn
        (null);

    try {
      this.promoCategoryOutboundService
          .updatePromoCategory(CommonTestVariable
              .MANDATORY_REQUEST, PROMO_CATEGORY_REQUEST, "123").blockingGet();
    }catch (Exception e) {
      verify(this.promoListEndPointService).updatePromoCategory(this.HEADER, PROMO_CATEGORY_REQUEST, "123");
    }

  }

  @Test
  public void deletePromoCategoryTest() throws Exception {
    when(this.promoListEndPointService.deletePromoCategory(this.HEADER, "123")).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.promoCategoryOutboundService
        .deletePromoCategory(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoListEndPointService).deletePromoCategory(this.HEADER, "123");
  }

  @Test
  public void deletePromoCategoryTestErrorRequest() throws Exception {
    when(this.promoListEndPointService.deletePromoCategory(HEADER, "123")).thenReturn
        (null);
    try {
      this.promoCategoryOutboundService
          .deletePromoCategory(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }catch (Exception e) {
      verify(this.promoListEndPointService).deletePromoCategory(this.HEADER, "123");
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoListEndPointService.findPromoCategoryById(HEADER, "123")).thenReturn
        (PROMO_CATEGORY_RESPONSE);
    when(this.PROMO_CATEGORY_RESPONSE.execute()).thenReturn(Response.success(this.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCategoryResponse> promoCategoryResponseGatewayBaseResponse = this.promoCategoryOutboundService
        .findPromoCategoryById(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    verify(this.promoListEndPointService).findPromoCategoryById(this.HEADER, "123");
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoListEndPointService.findPromoCategoryById(HEADER, "123")).thenReturn
        (null);

    try {
      this.promoCategoryOutboundService
          .findPromoCategoryById(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    } catch (Exception e) {
      verify(this.promoListEndPointService).findPromoCategoryById(this.HEADER, "123");
    }

  }

  @Test
  public void get() throws Exception {
    when(this.promoListEndPointService.findPromoCategoryById(HEADER, "123")).thenReturn
        (null);

    try {
      this.promoCategoryOutboundService
          .findPromoCategoryById(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    } catch (Exception e) {
      verify(this.promoListEndPointService).findPromoCategoryById(this.HEADER, "123");
    }

  }

  @Test
  public void getCategoriesTest() throws Exception {

    Map<String, String> queries = new HashMap<>();
    queries.put("lang", "EN");

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

    GatewayBaseResponse<List<Map<String, Object>>> gatewayBaseResponse = new GatewayBaseResponse<>();
    gatewayBaseResponse.setCode("SUCCCESS");
    gatewayBaseResponse.setData(resultList);

    when(this.promoListEndPointService.getCategories(HEADER, queries)).thenReturn
        (CALL_MAP);

    when(this.CALL_MAP.execute()).thenReturn(Response.success(gatewayBaseResponse));

    GatewayBaseResponse<List<Map<String, Object>>> resultGatewayBaseResponse =
        this.promoCategoryOutboundService.getCategories(CommonTestVariable.MANDATORY_REQUEST,
            "EN").blockingGet();

    assertEquals(1, resultGatewayBaseResponse.getData().size());

    verify(this.promoListEndPointService).getCategories(HEADER, queries);
  }

  @Test
  public void getCategoriesNotFoundTest() throws Exception {

    Map<String, String> queries = new HashMap<>();
    queries.put("lang", "EN");

    when(this.promoListEndPointService.getCategories(HEADER, queries)).thenReturn
        (null);

    try{
      GatewayBaseResponse<List<Map<String, Object>>> resultGatewayBaseResponse =
          this.promoCategoryOutboundService.getCategories(CommonTestVariable.MANDATORY_REQUEST,
              "EN").blockingGet();
    } catch (Exception e){
      verify(this.promoListEndPointService).getCategories(HEADER, queries);
    }
  }

  @Test
  public void getListCategoriesTest() throws Exception {

    Map<String, String> queries = new HashMap<>();
    queries.put("lang", "EN");

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

    GatewayBaseResponse<List<Map<String, Object>>> gatewayBaseResponse = new GatewayBaseResponse<>();
    gatewayBaseResponse.setCode("SUCCCESS");
    gatewayBaseResponse.setData(resultList);

    when(this.promoListEndPointService.getListCategories(HEADER, queries)).thenReturn
        (CALL_MAP);

    when(this.CALL_MAP.execute()).thenReturn(Response.success(gatewayBaseResponse));

    GatewayBaseResponse<List<Map<String, Object>>> resultGatewayBaseResponse =
        this.promoCategoryOutboundService.getListCategories(CommonTestVariable.MANDATORY_REQUEST,
            "EN").blockingGet();

    assertEquals(1, resultGatewayBaseResponse.getData().size());

    verify(this.promoListEndPointService).getListCategories(HEADER, queries);
  }

  @Test
  public void getListCategoriesNotFoundTest() throws Exception {

    Map<String, String> queries = new HashMap<>();
    queries.put("lang", "EN");

    when(this.promoListEndPointService.getListCategories(HEADER, queries)).thenReturn
        (null);

    try{
      GatewayBaseResponse<List<Map<String, Object>>> resultGatewayBaseResponse =
          this.promoCategoryOutboundService.getListCategories(CommonTestVariable.MANDATORY_REQUEST,
              "EN").blockingGet();
    } catch (Exception e){
      verify(this.promoListEndPointService).getListCategories(HEADER, queries);
    }
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoListEndPointService);
  }


}
