import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.CardTypeOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.CardTypeTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CardTypeResponse;
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


public class CardTypeOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<CardTypeResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<CardTypeResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<CardTypeResponse>> RESULT_ACTIVE;

  RestResponsePage<CardTypeResponse> DATA;

  RestResponsePage<CardTypeResponse> DATA_PENDING;

  RestResponsePage<CardTypeResponse> DATA_ACTIVE;

  CardTypeResponse CARD_TYPE_RESPONSE_GENERATED;

  CardTypeResponse CARD_TYPE_RESPONSE_GENERATED_PENDING;

  CardTypeResponse CARD_TYPE_RESPONSE_GENERATED_ACTIVE;

  CardTypeResponse CARD_TYPE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<CardTypeResponse> CARD_TYPE_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<CardTypeResponse> CARD_TYPE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<CardTypeResponse> CARD_TYPE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  CardTypeOutboundServiceImpl productTypeOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<CardTypeResponse>>> CARD_TYPE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<CardTypeResponse>>> CARD_TYPE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<CardTypeResponse>>> CARD_TYPE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<CardTypeResponse>> CARD_TYPE_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<CardTypeResponse>>> CARD_TYPE_RESPONSE_LIST;


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
    this.QUERY.put("name", CardTypeTestVariable.NAME);
    this.QUERY.put("page", CardTypeTestVariable.PAGE.toString());
    this.QUERY.put("size", CardTypeTestVariable.SIZE.toString());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", CardTypeTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("page", CardTypeTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", CardTypeTestVariable.SIZE.toString());
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
    this.DATA.setContent(Arrays.asList(CardTypeTestVariable.CARD_TYPE_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findCardTypesTest() throws Exception {
    when(this.promoCodeEndPointService.findAllCardType(HEADER)).thenReturn
        (CARD_TYPE_RESPONSE_LIST);
    when(this.CARD_TYPE_RESPONSE_LIST.execute())
        .thenReturn(Response.success(CardTypeTestVariable.CARD_TYPE_RESPONSE_LIST));

    GatewayBaseResponse<List<CardTypeResponse>> promoCodeResponseGatewayBaseResponse = this.productTypeOutboundService
        .findAll(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getName(), CardTypeTestVariable.CARD_TYPE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getName());

    verify(this.promoCodeEndPointService).findAllCardType(this.HEADER);
  }

  @Test
  public void findCardTypesTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findAllCardType(HEADER)).thenReturn
        (null);

    try {
      this.productTypeOutboundService
          .findAll(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findAllCardType(this.HEADER);
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
