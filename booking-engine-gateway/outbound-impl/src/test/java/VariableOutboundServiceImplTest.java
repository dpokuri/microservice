import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.VariableOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.InputType;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.VariableTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;
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


public class VariableOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<VariableResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<VariableResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<VariableResponse>> RESULT_ACTIVE;

  RestResponsePage<VariableResponse> DATA;

  RestResponsePage<VariableResponse> DATA_PENDING;

  RestResponsePage<VariableResponse> DATA_ACTIVE;

  VariableRequest VARIABLE_REQUEST;

  VariableRequest VARIABLE_WITHOUT_REQUEST;

  VariableResponse VARIABLE_RESPONSE_GENERATED;

  VariableResponse VARIABLE_RESPONSE_GENERATED_PENDING;

  VariableResponse VARIABLE_RESPONSE_GENERATED_ACTIVE;

  VariableResponse VARIABLE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<VariableResponse> VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<VariableResponse> VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<VariableResponse> VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  VariableOutboundServiceImpl variableOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<VariableResponse>>> VARIABLE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<VariableResponse>>> VARIABLE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<VariableResponse>>> VARIABLE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<VariableResponse>> VARIABLE_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<VariableFindAllResponse>>> VARIABLE_ALL_RESPONSE;


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
    this.QUERY.put("param", VariableTestVariable.PARAM);
    this.QUERY.put("inputType", InputType.AUTOCOMPLETE.getName());
    this.QUERY.put("page", VariableTestVariable.PAGE.toString());
    this.QUERY.put("size", VariableTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", VariableColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("param", VariableTestVariable.PARAM);
    this.QUERY_UNACTIVATE.put("page", VariableTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", VariableTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", VariableColumn.ID.getName());
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
    this.DATA.setContent(Arrays.asList(VariableTestVariable.VARIABLE_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findVariableFilterPaginatedTest() throws Exception {
    when(this.promoCodeEndPointService.findVariableFilterPaginated(any(), any())).thenReturn(VARIABLE_RESPONSE_PAGE);
    when(this.VARIABLE_RESPONSE_PAGE.execute()).thenReturn(Response.success(VariableTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<VariableResponse>> promoCodeResponse = this.variableOutboundService
        .findVariableFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("param"), InputType.AUTOCOMPLETE.getName(), VariableTestVariable.PAGE,
            VariableTestVariable.SIZE, VariableColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),VariableTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findVariableFilterPaginated(this.HEADER, this.QUERY);
  }


    @Test
  public void findVariableFilterPaginatedNullTest() throws Exception {
    when(this.promoCodeEndPointService.findVariableFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(VARIABLE_RESPONSE_PAGE);

    when(this.VARIABLE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
      GatewayBaseResponse<RestResponsePage<VariableResponse>> promoCodeResponse = this.variableOutboundService
          .findVariableFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              null, null, 0,
              10, null,
              null,VariableTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findVariableFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findVariableFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.findVariableFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.variableOutboundService
          .findVariableFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("param"), InputType.AUTOCOMPLETE.getName(), 0,
              10, VariableColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),VariableTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoCodeEndPointService).findVariableFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createVariableTest() throws Exception {
    when(this.promoCodeEndPointService.createVariable(HEADER, VariableTestVariable.VARIABLE_REQUEST)).thenReturn
        (VARIABLE_RESPONSE);
    when(this.VARIABLE_RESPONSE.execute()).thenReturn(Response.success(VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<VariableResponse> promoCodeResponseGatewayBaseResponse = this.variableOutboundService.createVariable
        (CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.VARIABLE_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).createVariable(this.HEADER, VariableTestVariable.VARIABLE_REQUEST);
  }

  @Test
  public void createVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createVariable(HEADER, VariableTestVariable.VARIABLE_REQUEST)).thenReturn
        (null);
    try {
      this.variableOutboundService
          .createVariable(CommonTestVariable
              .MANDATORY_REQUEST, VariableTestVariable.VARIABLE_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createVariable(this.HEADER, VariableTestVariable.VARIABLE_REQUEST);
    }
  }

  @Test
  public void updateVariableTest() throws Exception {
    when(this.promoCodeEndPointService.updateVariable(HEADER, VariableTestVariable.VARIABLE_REQUEST, VariableTestVariable.ID)).thenReturn
        (VARIABLE_RESPONSE);
    when(this.VARIABLE_RESPONSE.execute()).thenReturn(Response.success(VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<VariableResponse> promoCodeResponseGatewayBaseResponse = this.variableOutboundService.updateVariable
        (CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.VARIABLE_REQUEST, VariableTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateVariable(HEADER, VariableTestVariable.VARIABLE_REQUEST, VariableTestVariable.ID);
  }

  @Test
  public void updateVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updateVariable(HEADER, VARIABLE_WITHOUT_REQUEST, VariableTestVariable.ID)).thenReturn
        (null);

    try {
      this.variableOutboundService
          .updateVariable(CommonTestVariable
              .MANDATORY_REQUEST, VARIABLE_WITHOUT_REQUEST, VariableTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updateVariable(this.HEADER, VARIABLE_WITHOUT_REQUEST, VariableTestVariable.ID);
    }

  }

  @Test
  public void deleteVariableTest() throws Exception {
    when(this.promoCodeEndPointService.deleteVariable(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.variableOutboundService
        .deleteVariable(CommonTestVariable
            .MANDATORY_REQUEST, VariableTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deleteVariable(this.HEADER, VariableTestVariable.ID);
  }

  @Test
  public void deleteVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deleteVariable(HEADER, VariableTestVariable.ID)).thenReturn
        (null);
    try {
      this.variableOutboundService
          .deleteVariable(CommonTestVariable
              .MANDATORY_REQUEST, VariableTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deleteVariable(this.HEADER, VariableTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findVariableById(HEADER, VariableTestVariable.ID)).thenReturn
        (VARIABLE_RESPONSE);
    when(this.VARIABLE_RESPONSE.execute()).thenReturn(Response.success(VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<VariableResponse> promoCodeResponseGatewayBaseResponse = this.variableOutboundService
        .findVariableById(CommonTestVariable
            .MANDATORY_REQUEST, VariableTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).findVariableById(this.HEADER, VariableTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findVariableById(HEADER, VariableTestVariable.ID)).thenReturn
        (null);

    try {
      this.variableOutboundService
          .findVariableById(CommonTestVariable
              .MANDATORY_REQUEST, VariableTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findVariableById(this.HEADER, VariableTestVariable.ID);
    }

  }


  @Test
  public void findAllTest() throws Exception {
    when(this.promoCodeEndPointService.findAllVariable(HEADER)).thenReturn
        (VARIABLE_ALL_RESPONSE);
    when(this.VARIABLE_ALL_RESPONSE.execute())
        .thenReturn(Response.success(VariableTestVariable.VARIABLE_ALL_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<List<VariableFindAllResponse>> promoCodeResponseGatewayBaseResponse = this.variableOutboundService
        .findAllVariable(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getLabel(), VariableTestVariable.VARIABLE_ALL_RESPONSE_GENERATED_BASE_RESPONSE.getData().get(0).getLabel());

    verify(this.promoCodeEndPointService).findAllVariable(this.HEADER);
  }

  @Test
  public void findAllTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findAllVariable(HEADER)).thenReturn
        (null);

    try {
      this.variableOutboundService
          .findAllVariable(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findAllVariable(this.HEADER);
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
