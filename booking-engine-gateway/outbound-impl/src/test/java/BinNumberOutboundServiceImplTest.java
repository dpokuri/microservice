import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.BinNumberOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BinNumberTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;
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


public class BinNumberOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;
  Map<String, String> QUERY_FIND;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<BinNumberResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<BinNumberResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<BinNumberResponse>> RESULT_ACTIVE;

  RestResponsePage<BinNumberResponse> DATA;

  RestResponsePage<BinNumberResponse> DATA_PENDING;

  RestResponsePage<BinNumberResponse> DATA_ACTIVE;

  BinNumberRequest BIN_NUMBER_REQUEST;

  BinNumberRequest BIN_NUMBER_WITHOUT_REQUEST;

  BinNumberResponse BIN_NUMBER_RESPONSE_GENERATED;

  BinNumberResponse BIN_NUMBER_RESPONSE_GENERATED_PENDING;

  BinNumberResponse BIN_NUMBER_RESPONSE_GENERATED_ACTIVE;

  BinNumberResponse BIN_NUMBER_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<BinNumberResponse> BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<BinNumberResponse> BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<BinNumberResponse> BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  BinNumberOutboundServiceImpl binNumberOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> BIN_NUMBER_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> BIN_NUMBER_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> BIN_NUMBER_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<BinNumberResponse>> BIN_NUMBER_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<List<String>>> BIN_NUMBER_RESPONSE_LIST;


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
    this.QUERY.put("binNumber", BinNumberTestVariable.NAME);
    this.QUERY.put("page", BinNumberTestVariable.PAGE.toString());
    this.QUERY.put("size", BinNumberTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", BinNumberColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());


    this.QUERY_FIND = new HashMap<>();
    this.QUERY_FIND.put("binNumber", BinNumberTestVariable.BIN_NUMBER);
    this.QUERY_FIND.put("bankId", BinNumberTestVariable.BANK_ID);
    this.QUERY_FIND.put("cardTypeId", BinNumberTestVariable.CARD_TYPE);

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", BinNumberTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("page", BinNumberTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", BinNumberTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", BinNumberColumn.ID.getName());
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
    this.DATA.setContent(Arrays.asList(BinNumberTestVariable.BIN_NUMBER_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findBinNumberFilterPaginatedTest() throws Exception {
    when(this.promoCodeEndPointService.findBinNumberFilterPaginated(any(), any())).thenReturn(BIN_NUMBER_RESPONSE_PAGE);
    when(this.BIN_NUMBER_RESPONSE_PAGE.execute()).thenReturn(Response.success(BinNumberTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<BinNumberResponse>> promoCodeResponse = this.binNumberOutboundService
        .findBinNumberFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("binNumber"),  BinNumberTestVariable.PAGE,
            BinNumberTestVariable.SIZE, BinNumberColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),BinNumberTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findBinNumberFilterPaginated(this.HEADER, this.QUERY);
  }


    @Test
  public void findBinNumberFilterPaginatedNullTest() throws Exception {
    when(this.promoCodeEndPointService.findBinNumberFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(BIN_NUMBER_RESPONSE_PAGE);

    when(this.BIN_NUMBER_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
      GatewayBaseResponse<RestResponsePage<BinNumberResponse>> promoCodeResponse = this.binNumberOutboundService
          .findBinNumberFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              null, 0,
              10, null,
              null,BinNumberTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findBinNumberFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findBinNumberFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.findBinNumberFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.binNumberOutboundService
          .findBinNumberFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("binNumber"),  0,
              10, BinNumberColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),BinNumberTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoCodeEndPointService).findBinNumberFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createVariableTest() throws Exception {
    when(this.promoCodeEndPointService.createBinNumber(HEADER, BinNumberTestVariable.BIN_NUMBER_REQUEST)).thenReturn
        (BIN_NUMBER_RESPONSE);
    when(this.BIN_NUMBER_RESPONSE.execute()).thenReturn(Response.success(BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<BinNumberResponse> promoCodeResponseGatewayBaseResponse = this.binNumberOutboundService.createBinNumber
        (CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).createBinNumber(this.HEADER, BinNumberTestVariable.BIN_NUMBER_REQUEST);
  }

  @Test
  public void createVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createBinNumber(HEADER, BinNumberTestVariable.BIN_NUMBER_REQUEST)).thenReturn
        (null);
    try {
      this.binNumberOutboundService
          .createBinNumber(CommonTestVariable
              .MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createBinNumber(this.HEADER, BinNumberTestVariable.BIN_NUMBER_REQUEST);
    }
  }

  @Test
  public void updateVariableTest() throws Exception {
    when(this.promoCodeEndPointService.updateBinNumber(HEADER, BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.ID)).thenReturn
        (BIN_NUMBER_RESPONSE);
    when(this.BIN_NUMBER_RESPONSE.execute()).thenReturn(Response.success(BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<BinNumberResponse> promoCodeResponseGatewayBaseResponse = this.binNumberOutboundService.updateBinNumber
        (CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).updateBinNumber(HEADER, BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.ID);
  }

  @Test
  public void updateVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updateBinNumber(HEADER, BIN_NUMBER_WITHOUT_REQUEST, BinNumberTestVariable.ID)).thenReturn
        (null);

    try {
      this.binNumberOutboundService
          .updateBinNumber(CommonTestVariable
              .MANDATORY_REQUEST, BIN_NUMBER_WITHOUT_REQUEST, BinNumberTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updateBinNumber(this.HEADER, BIN_NUMBER_WITHOUT_REQUEST, BinNumberTestVariable.ID);
    }

  }

  @Test
  public void deleteVariableTest() throws Exception {
    when(this.promoCodeEndPointService.deleteBinNumber(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.binNumberOutboundService
        .deleteBinNumber(CommonTestVariable
            .MANDATORY_REQUEST, BinNumberTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deleteBinNumber(this.HEADER, BinNumberTestVariable.ID);
  }

  @Test
  public void deleteVariableTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deleteVariable(HEADER, BinNumberTestVariable.ID)).thenReturn
        (null);
    try {
      this.binNumberOutboundService
          .deleteBinNumber(CommonTestVariable
              .MANDATORY_REQUEST, BinNumberTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deleteBinNumber(this.HEADER, BinNumberTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findBinNumberById(HEADER, BinNumberTestVariable.ID)).thenReturn
        (BIN_NUMBER_RESPONSE);
    when(this.BIN_NUMBER_RESPONSE.execute()).thenReturn(Response.success(BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<BinNumberResponse> promoCodeResponseGatewayBaseResponse = this.binNumberOutboundService
        .findBinNumberById(CommonTestVariable
            .MANDATORY_REQUEST, BinNumberTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getId(), BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.promoCodeEndPointService).findBinNumberById(this.HEADER, BinNumberTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findBinNumberById(HEADER, BinNumberTestVariable.ID)).thenReturn
        (null);

    try {
      this.binNumberOutboundService
          .findBinNumberById(CommonTestVariable
              .MANDATORY_REQUEST, BinNumberTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findBinNumberById(this.HEADER, BinNumberTestVariable.ID);
    }

  }

  @Test
  public void binNumberTest() throws Exception {
    when(this.promoCodeEndPointService.binNumber(HEADER,QUERY_FIND)).thenReturn
        (BIN_NUMBER_RESPONSE_LIST);
    when(this.BIN_NUMBER_RESPONSE_LIST.execute()).thenReturn(Response.success(BinNumberTestVariable.BIN_NUMBER_RESPONSE_LIST_DATA));

    GatewayBaseResponse<List<String>> promoCodeResponseGatewayBaseResponse = this.binNumberOutboundService
        .binNumber(CommonTestVariable
            .MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER, BinNumberTestVariable.BANK_ID, BinNumberTestVariable.CARD_TYPE).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0), BinNumberTestVariable.BIN_NUMBER_RESPONSE_LIST_DATA.getData().get(0));

    verify(this.promoCodeEndPointService).binNumber(this.HEADER, QUERY_FIND);
  }


  @Test
  public void binNumberTestError() throws Exception {
    when(this.promoCodeEndPointService.binNumber(any(),any())).thenReturn
        (null);
    try{
      this.binNumberOutboundService
          .binNumber(CommonTestVariable
              .MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER, BinNumberTestVariable.BANK_ID, BinNumberTestVariable.CARD_TYPE).blockingGet();
    }
    catch (Exception be) {

      verify(this.promoCodeEndPointService).binNumber(this.HEADER, QUERY_FIND);

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
