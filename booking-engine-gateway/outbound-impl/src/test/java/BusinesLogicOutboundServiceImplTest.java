import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.BusinessLogicOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BusinessLogicResponseTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;
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


public class BusinesLogicOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> RESULT_ACTIVE;

  RestResponsePage<BusinessLogicResponseResponse> DATA;

  RestResponsePage<BusinessLogicResponseResponse> DATA_PENDING;

  RestResponsePage<BusinessLogicResponseResponse> DATA_ACTIVE;

  BusinessLogicResponseRequest BUSINESS_LOGIC_REQUEST;

  BusinessLogicResponseRequest BUSINESS_LOGIC_WITHOUT_REQUEST;

  BusinessLogicResponseResponse BUSINESS_LOGIC_RESPONSE_GENERATED;

  BusinessLogicResponseResponse BUSINESS_LOGIC_RESPONSE_GENERATED_PENDING;

  BusinessLogicResponseResponse BUSINESS_LOGIC_RESPONSE_GENERATED_ACTIVE;

  BusinessLogicResponseResponse BUSINESS_LOGIC_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<BusinessLogicResponseResponse> BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<BusinessLogicResponseResponse> BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<BusinessLogicResponseResponse> BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  BusinessLogicOutboundServiceImpl businessLogicResponseOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> BUSINESS_LOGIC_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> BUSINESS_LOGIC_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> BUSINESS_LOGIC_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<BusinessLogicResponseResponse>> BUSINESS_LOGIC_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> BUSINESS_LOGIC_RESPONSE_LIST;


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
    this.QUERY.put("responseCode", BusinessLogicResponseTestVariable.CODE);
    this.QUERY.put("page", BusinessLogicResponseTestVariable.PAGE.toString());
    this.QUERY.put("size", BusinessLogicResponseTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", BusinessLogicResponseColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_NULL = new HashMap<>();
    this.QUERY_NULL.put("page", "0");
    this.QUERY_NULL.put("size", "10");

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findBusinessLogicResponseFilterPaginatedTest() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponseFilterPaginated(any(), any())).thenReturn(BUSINESS_LOGIC_RESPONSE_PAGE);
    when(this.BUSINESS_LOGIC_RESPONSE_PAGE.execute()).thenReturn(Response.success(BusinessLogicResponseTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> promoCodeResponse = this.businessLogicResponseOutboundService
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("responseCode"),  BusinessLogicResponseTestVariable.PAGE,
            BusinessLogicResponseTestVariable.SIZE, BusinessLogicResponseColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),BusinessLogicResponseTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findBusinessLogicResponseFilterPaginated(this.HEADER, this.QUERY);
  }


    @Test
  public void findBusinessLogicResponseFilterPaginatedNullTest() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponseFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(BUSINESS_LOGIC_RESPONSE_PAGE);

    when(this.BUSINESS_LOGIC_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
      GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>> promoCodeResponse = this.businessLogicResponseOutboundService
          .findBusinessLogicResponseFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              null, 0,
              10, null,
              null,BusinessLogicResponseTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findBusinessLogicResponseFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findBusinessLogicResponseFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponseFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.businessLogicResponseOutboundService
          .findBusinessLogicResponseFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("responseCode"),  0,
              10, BusinessLogicResponseColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),BusinessLogicResponseTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoCodeEndPointService).findBusinessLogicResponseFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createVariableTest() throws Exception {
    when(this.promoCodeEndPointService.createBusinessLogicResponse(HEADER, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST)).thenReturn
        (BUSINESS_LOGIC_RESPONSE);
    when(this.BUSINESS_LOGIC_RESPONSE.execute()).thenReturn(Response.success(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<BusinessLogicResponseResponse> promoCodeResponseGatewayBaseResponse = this.businessLogicResponseOutboundService.createBusinessLogicResponse
        (CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).createBusinessLogicResponse(this.HEADER, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST);
  }

  @Test
  public void createVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createBusinessLogicResponse(HEADER, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST)).thenReturn
        (null);
    try {
      this.businessLogicResponseOutboundService
          .createBusinessLogicResponse(CommonTestVariable
              .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createBusinessLogicResponse(this.HEADER, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST);
    }
  }

  @Test
  public void updateVariableTest() throws Exception {
    when(this.promoCodeEndPointService.updateBusinessLogicResponse(HEADER, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.ID)).thenReturn
        (BUSINESS_LOGIC_RESPONSE);
    when(this.BUSINESS_LOGIC_RESPONSE.execute()).thenReturn(Response.success(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<BusinessLogicResponseResponse> promoCodeResponseGatewayBaseResponse = this.businessLogicResponseOutboundService.updateBusinessLogicResponse
        (CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateBusinessLogicResponse(HEADER, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.ID);
  }

  @Test
  public void updateVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updateBusinessLogicResponse(HEADER, BUSINESS_LOGIC_WITHOUT_REQUEST, BusinessLogicResponseTestVariable.ID)).thenReturn
        (null);

    try {
      this.businessLogicResponseOutboundService
          .updateBusinessLogicResponse(CommonTestVariable
              .MANDATORY_REQUEST, BUSINESS_LOGIC_WITHOUT_REQUEST, BusinessLogicResponseTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updateBusinessLogicResponse(this.HEADER, BUSINESS_LOGIC_WITHOUT_REQUEST, BusinessLogicResponseTestVariable.ID);
    }

  }

  @Test
  public void deleteVariableTest() throws Exception {
    when(this.promoCodeEndPointService.deleteBusinessLogic(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.businessLogicResponseOutboundService
        .deleteBusinessLogicResponse(CommonTestVariable
            .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deleteBusinessLogic(this.HEADER, BusinessLogicResponseTestVariable.ID);
  }

  @Test
  public void deleteVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deleteVariable(HEADER, BusinessLogicResponseTestVariable.ID)).thenReturn
        (null);
    try {
      this.businessLogicResponseOutboundService
          .deleteBusinessLogicResponse(CommonTestVariable
              .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deleteBusinessLogic(this.HEADER, BusinessLogicResponseTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponseById(HEADER, BusinessLogicResponseTestVariable.ID)).thenReturn
        (BUSINESS_LOGIC_RESPONSE);
    when(this.BUSINESS_LOGIC_RESPONSE.execute()).thenReturn(Response.success(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<BusinessLogicResponseResponse> promoCodeResponseGatewayBaseResponse = this.businessLogicResponseOutboundService
        .findBusinessLogicResponseById(CommonTestVariable
            .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).findBusinessLogicResponseById(this.HEADER, BusinessLogicResponseTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponseById(HEADER, BusinessLogicResponseTestVariable.ID)).thenReturn
        (null);

    try {
      this.businessLogicResponseOutboundService
          .findBusinessLogicResponseById(CommonTestVariable
              .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findBusinessLogicResponseById(this.HEADER, BusinessLogicResponseTestVariable.ID);
    }

  }

  @Test
  public void findBusinessLogicResponsesTest() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponse(HEADER)).thenReturn
        (BUSINESS_LOGIC_RESPONSE_LIST);
    when(this.BUSINESS_LOGIC_RESPONSE_LIST.execute())
        .thenReturn(Response.success(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_LIST_DATA));

    GatewayBaseResponse<List<BusinessLogicResponseResponse>> promoCodeResponseGatewayBaseResponse = this.businessLogicResponseOutboundService
        .findBusinessLogicResponses(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getResponseMessage(), BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE.getData().getResponseMessage());

    verify(this.promoCodeEndPointService).findBusinessLogicResponse(this.HEADER);
  }

  @Test
  public void findBusinessLogicResponsesTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findBusinessLogicResponse(HEADER)).thenReturn
        (null);

    try {
      this.businessLogicResponseOutboundService
          .findBusinessLogicResponses(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findBusinessLogicResponse(this.HEADER);
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
