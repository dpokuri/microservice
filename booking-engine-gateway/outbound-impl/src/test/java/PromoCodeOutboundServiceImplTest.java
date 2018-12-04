import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.PromoCodeOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;


public class PromoCodeOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> RESULT_ACTIVE;

  RestResponsePage<PromoCodeResponse> DATA;

  RestResponsePage<PromoCodeResponse> DATA_PENDING;

  RestResponsePage<PromoCodeResponse> DATA_ACTIVE;

  PromoCodeRequest PROMO_CODE_REQUEST;

  PromoCodeRequest PROMO_CODE_WITHOUT_REQUEST;

  PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED;

  PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED_PENDING;

  PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED_ACTIVE;

  PromoCodeResponse PROMO_CODE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<PromoCodeResponse> PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<PromoCodeResponse> PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<PromoCodeResponse> PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  PromoCodeOutboundServiceImpl promoCodeOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> PROMO_CODE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> PROMO_CODE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> PROMO_CODE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<PromoCodeResponse>> PROMO_CODE_RESPONSE;


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
    this.QUERY.put("code", PromoCodeTestVariable.CODE);
    this.QUERY.put("campaignId", PromoCodeTestVariable.CAMPAIGN_ID);
    this.QUERY.put("page", PromoCodeTestVariable.PAGE.toString());
    this.QUERY.put("size", PromoCodeTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", PromoCodeColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("code", PromoCodeTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("campaignId", PromoCodeTestVariable.CAMPAIGN_ID);
    this.QUERY_UNACTIVATE.put("page", PromoCodeTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", PromoCodeTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", PromoCodeColumn.ID.getName());
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
    this.DATA.setContent(Arrays.asList(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

//  @Test
//  public void findPromoCodeFilterPaginatedTest() throws Exception {
//    when(this.promoCodeEndPointService.findPromoCodeFilterPaginated(this.HEADER, this.QUERY)).thenReturn(PROMO_CODE_RESPONSE_PAGE);
//    when(this.PROMO_CODE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));
//
//    GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> promoCodeResponse = this.promoCodeOutboundService
//        .findPromoCodeFilterPaginated(CommonTestVariable
//                .MANDATORY_REQUEST,
//            this.QUERY.get("code"), this.QUERY.get("campaignId"), 0,
//            10, PromoCodeColumn.valueOf(this.QUERY.get("columnSort")),
//            SortDirection.valueOf(this.QUERY.get("sortDirection")),PromoCodeTestVariable.PRIVILEGES).blockingGet();
//
//    assertEquals(10,promoCodeResponse.getData().getTotalPages());
//
//    verify(this.promoCodeEndPointService).findPromoCodeFilterPaginated(this.HEADER, this.QUERY);
//  }

//  @Test
//  public void findPromoCodeFilterPaginatedNotAllowedToRequestUnActiveTest() throws Exception {
//    when(this.promoCodeEndPointService.findPromoCodeFilterPaginated(this.HEADER, this.QUERY_UNACTIVATE)).thenReturn(PROMO_CODE_RESPONSE_PAGE);
//    when(this.PROMO_CODE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));
//
//    GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> promoCodeResponse = this.promoCodeOutboundService
//        .findPromoCodeFilterPaginated(CommonTestVariable
//                .MANDATORY_REQUEST,
//            this.QUERY.get("code"), this.QUERY.get("campaignId"), 0,
//            10, PromoCodeColumn.valueOf(this.QUERY.get("columnSort")),
//            SortDirection.valueOf(this.QUERY.get("sortDirection")),PromoCodeTestVariable.PRIVILEGES).blockingGet();
//
//    assertEquals(10,promoCodeResponse.getData().getTotalPages());
//
//    verify(this.promoCodeEndPointService).findPromoCodeFilterPaginated(this.HEADER, this.QUERY);
//  }


//    @Test
//  public void findPromoCodeFilterPaginatedNullTest() throws Exception {
//    when(this.promoCodeEndPointService.findPromoCodeFilterPaginated(this.HEADER, this
//        .QUERY_NULL))
//        .thenReturn(PROMO_CODE_RESPONSE_PAGE);
//
//    when(this.PROMO_CODE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
//        .RESULT));
//      GatewayBaseResponse<RestResponsePage<PromoCodeResponse>> promoCodeResponse = this.promoCodeOutboundService
//          .findPromoCodeFilterPaginated(CommonTestVariable
//                  .MANDATORY_REQUEST,
//              null, null, 0,
//              10, null,
//              null,PromoCodeTestVariable.PRIVILEGES).blockingGet();
//
//    assertEquals(10,promoCodeResponse.getData().getTotalPages());
//
//    verify(this.promoCodeEndPointService).findPromoCodeFilterPaginated(this.HEADER, this.QUERY_NULL);
//  }

//  @Test
//  public void findPromoCodeFilterPaginatedTestErrorRequest() throws Exception {
//    when(this.promoCodeEndPointService.findPromoCodeFilterPaginated(any(), any())).thenReturn(null);
//
//    try{
//      this.promoCodeOutboundService
//          .findPromoCodeFilterPaginated(CommonTestVariable
//                  .MANDATORY_REQUEST,
//              this.QUERY.get("code"), this.QUERY.get("campaignId"), 0,
//              10, PromoCodeColumn.valueOf(this.QUERY.get("columnSort")),
//              SortDirection.valueOf(this.QUERY.get("sortDirection")),PromoCodeTestVariable.PRIVILEGES).blockingGet();
//    }
//    catch (Exception e) {
//      verify(this.promoCodeEndPointService).findPromoCodeFilterPaginated(this.HEADER, this.QUERY);
//    }
//
//  }

  @Test
  public void createPromoCodeTest() throws Exception {
    when(this.promoCodeEndPointService.createPromoCode(HEADER, PromoCodeTestVariable.PROMO_CODE_REQUEST)).thenReturn
        (PROMO_CODE_RESPONSE);
    when(this.PROMO_CODE_RESPONSE.execute()).thenReturn(Response.success(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeOutboundService.createPromoCode
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).createPromoCode(this.HEADER, PromoCodeTestVariable.PROMO_CODE_REQUEST);
  }

  @Test
  public void createPromoCodeTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createPromoCode(HEADER, PromoCodeTestVariable.PROMO_CODE_REQUEST)).thenReturn
        (null);
    try {
      this.promoCodeOutboundService
          .createPromoCode(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createPromoCode(this.HEADER, PromoCodeTestVariable.PROMO_CODE_REQUEST);
    }
  }

  @Test
  public void updatePromoCodeTest() throws Exception {
    when(this.promoCodeEndPointService.updatePromoCode(HEADER, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID)).thenReturn
        (PROMO_CODE_RESPONSE);
    when(this.PROMO_CODE_RESPONSE.execute()).thenReturn(Response.success(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeOutboundService.updatePromoCode
        (CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updatePromoCode(HEADER, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID);
  }

  @Test
  public void updatePromoCodeTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updatePromoCode(HEADER, PROMO_CODE_WITHOUT_REQUEST, PromoCodeTestVariable.ID)).thenReturn
        (null);

    try {
      this.promoCodeOutboundService
          .updatePromoCode(CommonTestVariable
              .MANDATORY_REQUEST, PROMO_CODE_WITHOUT_REQUEST, PromoCodeTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updatePromoCode(this.HEADER, PROMO_CODE_WITHOUT_REQUEST, PromoCodeTestVariable.ID);
    }

  }

  @Test
  public void deletePromoCodeTest() throws Exception {
    when(this.promoCodeEndPointService.deletePromoCode(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.promoCodeOutboundService
        .deletePromoCode(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deletePromoCode(this.HEADER, PromoCodeTestVariable.ID);
  }

  @Test
  public void deletePromoCodeTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deletePromoCode(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (null);
    try {
      this.promoCodeOutboundService
          .deletePromoCode(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deletePromoCode(this.HEADER, PromoCodeTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeById(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (PROMO_CODE_RESPONSE);
    when(this.PROMO_CODE_RESPONSE.execute()).thenReturn(Response.success(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeOutboundService
        .findPromoCodeById(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).findPromoCodeById(this.HEADER, PromoCodeTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findPromoCodeById(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (null);

    try {
      this.promoCodeOutboundService
          .findPromoCodeById(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findPromoCodeById(this.HEADER, PromoCodeTestVariable.ID);
    }

  }

   @Test
  public void updatePromoCodeToActivatedTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusActivatePromoCode(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (this.PROMO_CODE_RESPONSE);
    when(this.PROMO_CODE_RESPONSE.execute()).thenReturn(Response.success(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeOutboundService
        .updateStatusActivatePromoCode(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();

     assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateStatusActivatePromoCode(this.HEADER, PromoCodeTestVariable.ID);
  }

  @Test
  public void updatePromoCodeToActivatedTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusActivatePromoCode(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (null);

    try{
      this.promoCodeOutboundService
          .updateStatusActivatePromoCode(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusActivatePromoCode(this.HEADER, PromoCodeTestVariable.ID);
    }
  }


  @Test
  public void updatePromoCodeToInActivatedTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusUnActivatePromoCode(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (this.PROMO_CODE_RESPONSE);
    when(this.PROMO_CODE_RESPONSE.execute()).thenReturn(Response.success(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<PromoCodeResponse> promoCodeResponseGatewayBaseResponse = this.promoCodeOutboundService
        .updateStatusUnActivatePromoCode(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateStatusUnActivatePromoCode(this.HEADER, PromoCodeTestVariable.ID);
  }


  @Test
  public void updatePromoCodeToInActivatedTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusUnActivatePromoCode(HEADER, PromoCodeTestVariable.ID)).thenReturn
        (null);

    try{
      this.promoCodeOutboundService
          .updateStatusUnActivatePromoCode(CommonTestVariable
              .MANDATORY_REQUEST, PromoCodeTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusUnActivatePromoCode(this.HEADER, PromoCodeTestVariable.ID);
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
