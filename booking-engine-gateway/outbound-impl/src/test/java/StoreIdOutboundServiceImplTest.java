import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.StoreIdOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.StoreIdTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;
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


public class StoreIdOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<StoreIdResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<StoreIdResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<StoreIdResponse>> RESULT_ACTIVE;

  RestResponsePage<StoreIdResponse> DATA;

  RestResponsePage<StoreIdResponse> DATA_PENDING;

  RestResponsePage<StoreIdResponse> DATA_ACTIVE;

  StoreIdResponse STORE_ID_RESPONSE_GENERATED;

  StoreIdResponse STORE_ID_RESPONSE_GENERATED_PENDING;

  StoreIdResponse STORE_ID_RESPONSE_GENERATED_ACTIVE;

  StoreIdResponse STORE_ID_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<StoreIdResponse> STORE_ID_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<StoreIdResponse> STORE_ID_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<StoreIdResponse> STORE_ID_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  StoreIdOutboundServiceImpl storeIdOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<StoreIdResponse>>> STORE_ID_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<StoreIdResponse>>> STORE_ID_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<StoreIdResponse>>> STORE_ID_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<StoreIdResponse>> STORE_ID_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<StoreIdResponse>>> STORE_ID_RESPONSE_LIST;


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
    this.QUERY.put("name", StoreIdTestVariable.NAME);
    this.QUERY.put("page", StoreIdTestVariable.PAGE.toString());
    this.QUERY.put("size", StoreIdTestVariable.SIZE.toString());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", StoreIdTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("page", StoreIdTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", StoreIdTestVariable.SIZE.toString());
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
    this.DATA.setContent(Arrays.asList(StoreIdTestVariable.STORE_ID_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findStoreIdsTest() throws Exception {
    when(this.promoCodeEndPointService.findAllStoreId(HEADER)).thenReturn
        (STORE_ID_RESPONSE_LIST);
    when(this.STORE_ID_RESPONSE_LIST.execute())
        .thenReturn(Response.success(StoreIdTestVariable.STORE_ID_RESPONSE_LIST));

    GatewayBaseResponse<List<StoreIdResponse>> promoCodeResponseGatewayBaseResponse = this.storeIdOutboundService
        .findAll(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getValue(), StoreIdTestVariable.STORE_ID_RESPONSE_LIST.getData().get(0).getValue());

    verify(this.promoCodeEndPointService).findAllStoreId(this.HEADER);
  }

  @Test
  public void findStoreIdsTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findAllStoreId(HEADER)).thenReturn
        (null);

    try {
      this.storeIdOutboundService
          .findAll(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findAllStoreId(this.HEADER);
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
