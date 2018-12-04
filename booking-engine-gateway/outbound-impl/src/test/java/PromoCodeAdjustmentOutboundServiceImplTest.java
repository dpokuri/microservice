import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.PromoCodeAdjustmentOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeAdjustmentTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;
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


public class PromoCodeAdjustmentOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> RESULT_ACTIVE;

  RestResponsePage<PromoCodeAdjustmentResponse> DATA;

  RestResponsePage<PromoCodeAdjustmentResponse> DATA_PENDING;

  RestResponsePage<PromoCodeAdjustmentResponse> DATA_ACTIVE;

  PromoCodeAdjustmentRequest PROMO_CODE_ADJUSTMENT_REQUEST;

  PromoCodeAdjustmentRequest PROMO_CODE_ADJUSTMENT_WITHOUT_REQUEST;

  PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT_RESPONSE_GENERATED;

  PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT_RESPONSE_GENERATED_PENDING;

  PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT_RESPONSE_GENERATED_ACTIVE;

  PromoCodeAdjustmentResponse PROMO_CODE_ADJUSTMENT_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<PromoCodeAdjustmentResponse> PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<PromoCodeAdjustmentResponse> PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<PromoCodeAdjustmentResponse> PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  PromoCodeAdjustmentOutboundServiceImpl promoCodeAdjustmentOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> PROMO_CODE_ADJUSTMENT_DROPDOWN_RESPONSE_DATA;

  @Mock
  Call<GatewayBaseResponse<PromoCodeAdjustmentResponse>> PROMO_CODE_ADJUSTMENT_RESPONSE;


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
    this.QUERY.put("name", PromoCodeAdjustmentTestVariable.CODE);
    this.QUERY.put("isPromoCodeCombine", String.valueOf(true));
    this.QUERY.put("page", PromoCodeAdjustmentTestVariable.PAGE.toString());
    this.QUERY.put("size", PromoCodeAdjustmentTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", PromoCodeAdjustmentColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", PromoCodeAdjustmentTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("campaignId", PromoCodeAdjustmentTestVariable.ID);
    this.QUERY_UNACTIVATE.put("page", PromoCodeAdjustmentTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", PromoCodeAdjustmentTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", PromoCodeAdjustmentColumn.ID.getName());
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
    this.DATA.setContent(Arrays.asList(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findPromoCodeAdjustmentFilterPaginatedTest() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeAdjustmentFilterPaginated(this.HEADER, this.QUERY)).thenReturn(PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> promoCodeResponse = this.promoCodeAdjustmentOutboundService
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("name"), true, PromoCodeAdjustmentTestVariable.PAGE,
            PromoCodeAdjustmentTestVariable.SIZE, PromoCodeAdjustmentColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),PromoCodeAdjustmentTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findPromoCodeAdjustmentFilterPaginated(this.HEADER, this.QUERY);
  }


  @Test
  public void findPromoCodeAdjustmentFilterPaginatedNullTest() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeAdjustmentFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE);

    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
      GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> promoCodeResponse = this.promoCodeAdjustmentOutboundService
          .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              null, null, 0,
              10, null,
              null,PromoCodeAdjustmentTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findPromoCodeAdjustmentFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findPromoCodeAdjustmentFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeAdjustmentFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.promoCodeAdjustmentOutboundService
          .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("name"), true, 0,
              10, PromoCodeAdjustmentColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),PromoCodeAdjustmentTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoCodeEndPointService).findPromoCodeAdjustmentFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createPromoCodeAdjustmentTest() throws Exception {
    when(this.promoCodeEndPointService.createPromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST)).thenReturn
        (PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService.createPromoCodeAdjustment
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getName(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).createPromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST);
  }



  @Test
  public void createPromoCodeAdjustmentTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createPromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST)).thenReturn
        (null);
    try {
      this.promoCodeAdjustmentOutboundService
          .createPromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createPromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST);
    }
  }

  @Test
  public void updatePromoCodeAdjustmentTest() throws Exception {
    when(this.promoCodeEndPointService.updatePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService.updatePromoCodeAdjustment
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getName(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).updatePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST, PromoCodeAdjustmentTestVariable.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updatePromoCodeAdjustment(HEADER, PROMO_CODE_ADJUSTMENT_WITHOUT_REQUEST, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);

    try {
      this.promoCodeAdjustmentOutboundService
          .updatePromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PROMO_CODE_ADJUSTMENT_WITHOUT_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updatePromoCodeAdjustment(this.HEADER, PROMO_CODE_ADJUSTMENT_WITHOUT_REQUEST, PromoCodeAdjustmentTestVariable.ID);
    }

  }

  @Test
  public void deletePromoCodeAdjustmentTest() throws Exception {
    when(this.promoCodeEndPointService.deletePromoCodeAdjustment(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.promoCodeAdjustmentOutboundService
        .deletePromoCodeAdjustment(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deletePromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
  }

  @Test
  public void deletePromoCodeAdjustmentTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deletePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);
    try {
      this.promoCodeAdjustmentOutboundService
          .deletePromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deletePromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeAdjustmentById(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService
        .findPromoCodeAdjustmentById(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getName(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).findPromoCodeAdjustmentById(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeAdjustmentById(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);

    try {
      this.promoCodeAdjustmentOutboundService
          .findPromoCodeAdjustmentById(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findPromoCodeAdjustmentById(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
    }

  }

   @Test
  public void updatePromoCodeAdjustmentToActivatedTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusActivatePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (this.PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService
        .updateStatusActivatePromoCodeAdjustment(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

     assertEquals(promoCodeResponseGatewayBaseResponse.getData().getName(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).updateStatusActivatePromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentToActivatedTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusActivatePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);

    try{
      this.promoCodeAdjustmentOutboundService
          .updateStatusActivatePromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusActivatePromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
    }
  }


  @Test
  public void updatePromoCodeAdjustmentToInActivatedTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusUnActivatePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (this.PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService
        .updateStatusUnActivatePromoCodeAdjustment(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateStatusUnActivatePromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
  }


  @Test
  public void updatePromoCodeAdjustmentToInActivatedTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusUnActivatePromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);

    try{
      this.promoCodeAdjustmentOutboundService
          .updateStatusUnActivatePromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusUnActivatePromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
    }
  }


  @Test
  public void updatePromoCodeAdjustmentToPendingTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusPendingPromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (this.PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService
        .updateStatusPendingPromoCodeAdjustment(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateStatusPendingPromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentTopendingTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusPendingPromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);

    try{
      this.promoCodeAdjustmentOutboundService
          .updateStatusPendingPromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusPendingPromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
    }
  }



  @Test
  public void updatePromoCodeAdjustmentToRejectTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusRejectedPromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (this.PROMO_CODE_ADJUSTMENT_RESPONSE);
    when(this.PROMO_CODE_ADJUSTMENT_RESPONSE.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeAdjustmentResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService
        .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateStatusRejectedPromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentToRejectTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusRejectedPromoCodeAdjustment(HEADER, PromoCodeAdjustmentTestVariable.ID)).thenReturn
        (null);

    try{
      this.promoCodeAdjustmentOutboundService
          .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusRejectedPromoCodeAdjustment(this.HEADER, PromoCodeAdjustmentTestVariable.ID);
    }
  }

  @Test
  public void findPromoCodeAdjustmentActive() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeAdjustmentActivate(HEADER)).thenReturn
        (this.PROMO_CODE_ADJUSTMENT_DROPDOWN_RESPONSE_DATA);
    when(this.PROMO_CODE_ADJUSTMENT_DROPDOWN_RESPONSE_DATA.execute()).thenReturn(Response.success(PromoCodeAdjustmentTestVariable.PRO_DROPDOWN_RESPONSE_LIST));

    GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>> promoCodeResponseGatewayBaseResponse = this.promoCodeAdjustmentOutboundService
        .findPromoCodeAdjustmentActivate(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getId(), PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).findPromoCodeAdjustmentActivate(this.HEADER);
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
