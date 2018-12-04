import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.PaymentOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.PaymentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PaymentTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentResponse;
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


public class PaymentOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<PaymentResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<PaymentResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<PaymentResponse>> RESULT_ACTIVE;

  RestResponsePage<PaymentResponse> DATA;

  RestResponsePage<PaymentResponse> DATA_PENDING;

  RestResponsePage<PaymentResponse> DATA_ACTIVE;

  PaymentResponse PAYMENT_RESPONSE_GENERATED;

  PaymentResponse PAYMENT_RESPONSE_GENERATED_PENDING;

  PaymentResponse PAYMENT_RESPONSE_GENERATED_ACTIVE;

  PaymentResponse PAYMENT_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<PaymentResponse> PAYMENT_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<PaymentResponse> PAYMENT_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<PaymentResponse> PAYMENT_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  PaymentOutboundServiceImpl productTypeOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PaymentResponse>>> PAYMENT_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PaymentResponse>>> PAYMENT_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PaymentResponse>>> PAYMENT_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<PaymentResponse>> PAYMENT_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<PaymentResponse>>> PAYMENT_RESPONSE_LIST;


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
    this.QUERY.put("name", PaymentTestVariable.NAME);
    this.QUERY.put("page", PaymentTestVariable.PAGE.toString());
    this.QUERY.put("size", PaymentTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", PaymentColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", PaymentTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("page", PaymentTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", PaymentTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", PaymentColumn.ID.getName());
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
    this.DATA.setContent(Arrays.asList(PaymentTestVariable.PAYMENT_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findPaymentsTest() throws Exception {
    when(this.promoCodeEndPointService.findAllPayment(HEADER)).thenReturn
        (PAYMENT_RESPONSE_LIST);
    when(this.PAYMENT_RESPONSE_LIST.execute())
        .thenReturn(Response.success(PaymentTestVariable.PAYMENT_RESPONSE_LIST));

    GatewayBaseResponse<List<PaymentResponse>> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService
        .findAll(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getName(), PaymentTestVariable.PAYMENT_RESPONSE_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).findAllPayment(this.HEADER);
  }

  @Test
  public void findPaymentsTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findAllPayment(HEADER)).thenReturn
        (null);

    try {
      this.productTypeOutboundService
          .findAll(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findAllPayment(this.HEADER);
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
