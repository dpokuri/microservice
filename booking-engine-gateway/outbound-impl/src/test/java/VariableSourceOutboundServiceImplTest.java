import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.VariableSourceOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.VariableSourceTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;
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


public class VariableSourceOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> KEY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<VariableSourceResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<VariableSourceResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<VariableSourceResponse>> RESULT_ACTIVE;

  RestResponsePage<VariableSourceResponse> DATA;

  RestResponsePage<VariableSourceResponse> DATA_PENDING;

  RestResponsePage<VariableSourceResponse> DATA_ACTIVE;

  VariableSourceResponse VARIABLE_SOURCE_RESPONSE_GENERATED;

  VariableSourceResponse VARIABLE_SOURCE_RESPONSE_GENERATED_PENDING;

  VariableSourceResponse VARIABLE_SOURCE_RESPONSE_GENERATED_ACTIVE;

  VariableSourceResponse VARIABLE_SOURCE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<VariableSourceResponse> VARIABLE_SOURCE_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<VariableSourceResponse> VARIABLE_SOURCE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<VariableSourceResponse> VARIABLE_SOURCE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  VariableSourceOutboundServiceImpl productTypeOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<VariableSourceResponse>>> VARIABLE_SOURCE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<VariableSourceResponse>>> VARIABLE_SOURCE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<VariableSourceResponse>>> VARIABLE_SOURCE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<VariableSourceResponse>> VARIABLE_SOURCE_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<VariableSourceResponse>>> VARIABLE_SOURCE_RESPONSE_LIST;


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
    this.QUERY.put("page", VariableSourceTestVariable.PAGE.toString());
    this.QUERY.put("size", VariableSourceTestVariable.SIZE.toString());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", VariableSourceTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("page", VariableSourceTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", VariableSourceTestVariable.SIZE.toString());
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
    this.DATA.setContent(Arrays.asList(VariableSourceTestVariable.VARIABLE_SOURCE_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

    this.KEY = new HashMap<>();
    this.KEY.put("key", VariableSourceTestVariable.KEY);


  }

  @Test
  public void findVariableSourcesTest() throws Exception {
    when(this.promoCodeEndPointService.findVariableSource(HEADER, VariableSourceTestVariable.SOURCE_TYPE,
        KEY)).thenReturn
        (VARIABLE_SOURCE_RESPONSE_LIST);
    when(this.VARIABLE_SOURCE_RESPONSE_LIST.execute())
        .thenReturn(Response.success(VariableSourceTestVariable.VARIABLE_SOURCE_RESPONSE_LIST));

    GatewayBaseResponse<List<VariableSourceResponse>> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService
        .findAll(CommonTestVariable
            .MANDATORY_REQUEST, VariableSourceTestVariable.SOURCE_TYPE, VariableSourceTestVariable.KEY).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getLabel(),
        VariableSourceTestVariable.VARIABLE_SOURCE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getLabel());

    verify(this.promoCodeEndPointService).findVariableSource(HEADER, VariableSourceTestVariable.SOURCE_TYPE,
        KEY);
  }

  @Test
  public void findVariableSourcesTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findVariableSource(HEADER, VariableSourceTestVariable.SOURCE_TYPE,
        KEY)).thenReturn
        (null);

    try {
      this.productTypeOutboundService
          .findAll(CommonTestVariable
              .MANDATORY_REQUEST, VariableSourceTestVariable.SOURCE_TYPE, VariableSourceTestVariable.KEY).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findVariableSource(HEADER, VariableSourceTestVariable.SOURCE_TYPE,
          KEY);
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
