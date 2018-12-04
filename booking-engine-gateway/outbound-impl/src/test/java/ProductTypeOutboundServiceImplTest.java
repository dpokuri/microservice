import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.ProductTypeOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.ProductTypeTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionRequest;

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


public class ProductTypeOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<ProductTypeResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<ProductTypeResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<ProductTypeResponse>> RESULT_ACTIVE;

  RestResponsePage<ProductTypeResponse> DATA;

  RestResponsePage<ProductTypeResponse> DATA_PENDING;

  RestResponsePage<ProductTypeResponse> DATA_ACTIVE;

  ProductTypeRequest PRODUCT_TYPE_REQUEST;

  ProductTypeRequest PRODUCT_TYPE_WITHOUT_REQUEST;

  ProductTypeResponse PRODUCT_TYPE_RESPONSE_GENERATED;

  ProductTypeResponse PRODUCT_TYPE_RESPONSE_GENERATED_PENDING;

  ProductTypeResponse PRODUCT_TYPE_RESPONSE_GENERATED_ACTIVE;

  ProductTypeResponse PRODUCT_TYPE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<ProductTypeResponse> PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<ProductTypeResponse> PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<ProductTypeResponse> PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  ProductTypeOutboundServiceImpl productTypeOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> PRODUCT_TYPE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> PRODUCT_TYPE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> PRODUCT_TYPE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<ProductTypeResponse>> PRODUCT_TYPE_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<ProductTypeResponse>>> PRODUCT_TYPE_RESPONSE_LIST;


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
    this.QUERY.put("name", ProductTypeTestVariable.NAME);
    this.QUERY.put("page", ProductTypeTestVariable.PAGE.toString());
    this.QUERY.put("size", ProductTypeTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", ProductTypeColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", ProductTypeTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("page", ProductTypeTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", ProductTypeTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", ProductTypeColumn.ID.getName());
    this.QUERY_UNACTIVATE.put("sortDirection", SortDirection.ASC.getName());

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

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findProductTypeFilterPaginatedTest() throws Exception {
    when(this.promoCodeEndPointService.findProductTypeFilterPaginated(any(), any())).thenReturn(PRODUCT_TYPE_RESPONSE_PAGE);
    when(this.PRODUCT_TYPE_RESPONSE_PAGE.execute()).thenReturn(Response.success(ProductTypeTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<ProductTypeResponse>> promoCodeResponse = this.productTypeOutboundService
        .findProductTypeFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("name"),  ProductTypeTestVariable.PAGE,
            ProductTypeTestVariable.SIZE, ProductTypeColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),ProductTypeTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findProductTypeFilterPaginated(this.HEADER, this.QUERY);
  }


    @Test
  public void findProductTypeFilterPaginatedNullTest() throws Exception {
    when(this.promoCodeEndPointService.findProductTypeFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(PRODUCT_TYPE_RESPONSE_PAGE);

    when(this.PRODUCT_TYPE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
      GatewayBaseResponse<RestResponsePage<ProductTypeResponse>> promoCodeResponse = this.productTypeOutboundService
          .findProductTypeFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              null, 0,
              10, null,
              null,ProductTypeTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findProductTypeFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findProductTypeFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.findProductTypeFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.productTypeOutboundService
          .findProductTypeFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("name"),  0,
              10, ProductTypeColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),ProductTypeTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoCodeEndPointService).findProductTypeFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createVariableTest() throws Exception {
    when(this.promoCodeEndPointService.createProductType(HEADER, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST)).thenReturn
        (PRODUCT_TYPE_RESPONSE);
    when(this.PRODUCT_TYPE_RESPONSE.execute()).thenReturn(Response.success(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<ProductTypeResponse> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService.createProductType
        (CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).createProductType(this.HEADER, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST);
  }

  @Test
  public void createVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createProductType(HEADER, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST)).thenReturn
        (null);
    try {
      this.productTypeOutboundService
          .createProductType(CommonTestVariable
              .MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createProductType(this.HEADER, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST);
    }
  }

  @Test
  public void updateVariableTest() throws Exception {
    when(this.promoCodeEndPointService.updateProductType(HEADER, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.ID)).thenReturn
        (PRODUCT_TYPE_RESPONSE);
    when(this.PRODUCT_TYPE_RESPONSE.execute()).thenReturn(Response.success(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<ProductTypeResponse> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService.updateProductType
        (CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateProductType(HEADER, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.ID);
  }

  @Test
  public void updateVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updateProductType(HEADER, PRODUCT_TYPE_WITHOUT_REQUEST, ProductTypeTestVariable.ID)).thenReturn
        (null);

    try {
      this.productTypeOutboundService
          .updateProductType(CommonTestVariable
              .MANDATORY_REQUEST, PRODUCT_TYPE_WITHOUT_REQUEST, ProductTypeTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updateProductType(this.HEADER, PRODUCT_TYPE_WITHOUT_REQUEST, ProductTypeTestVariable.ID);
    }

  }

  @Test
  public void deleteVariableTest() throws Exception {
    when(this.promoCodeEndPointService.deleteProductType(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.productTypeOutboundService
        .deleteProductType(CommonTestVariable
            .MANDATORY_REQUEST, ProductTypeTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deleteProductType(this.HEADER, ProductTypeTestVariable.ID);
  }

  @Test
  public void deleteVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deleteVariable(HEADER, ProductTypeTestVariable.ID)).thenReturn
        (null);
    try {
      this.productTypeOutboundService
          .deleteProductType(CommonTestVariable
              .MANDATORY_REQUEST, ProductTypeTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deleteProductType(this.HEADER, ProductTypeTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findProductTypeById(HEADER, ProductTypeTestVariable.ID)).thenReturn
        (PRODUCT_TYPE_RESPONSE);
    when(this.PRODUCT_TYPE_RESPONSE.execute()).thenReturn(Response.success(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<ProductTypeResponse> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService
        .findProductTypeById(CommonTestVariable
            .MANDATORY_REQUEST, ProductTypeTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).findProductTypeById(this.HEADER, ProductTypeTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findProductTypeById(HEADER, ProductTypeTestVariable.ID)).thenReturn
        (null);

    try {
      this.productTypeOutboundService
          .findProductTypeById(CommonTestVariable
              .MANDATORY_REQUEST, ProductTypeTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findProductTypeById(this.HEADER, ProductTypeTestVariable.ID);
    }

  }

  @Test
  public void findProductTypesTest() throws Exception {
    when(this.promoCodeEndPointService.findProductTypes(HEADER)).thenReturn
        (PRODUCT_TYPE_RESPONSE_LIST);
    when(this.PRODUCT_TYPE_RESPONSE_LIST.execute())
        .thenReturn(Response.success(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_LIST_DATA));

    GatewayBaseResponse<List<ProductTypeResponse>> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService
        .findProductTypes(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getName(), ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).findProductTypes(this.HEADER);
  }

  @Test
  public void findProductTypesTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findProductTypes(HEADER)).thenReturn
        (null);

    try {
      this.productTypeOutboundService
          .findProductTypes(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findProductTypes(this.HEADER);
    }

  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeEndPointService);
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
