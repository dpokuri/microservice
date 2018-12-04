import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.HolidayOutboundServiceImpl;
import com.tl.booking.gateway.outbound.impl.configuration.CalendarEndPointService;
import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.Language;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayDescription;
import com.tl.booking.gateway.entity.outbound.HolidayDescriptionBuilder;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayRequestBuilder;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.HolidayResponseBuilder;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;


public class HolidayOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<HolidayResponse>> RESULT;

  RestResponsePage<HolidayResponse> DATA;

  HolidayRequest HOLIDAY_REQUEST;

  HolidayResponse HOLIDAY_RESPONSE_GENERATED;

  GatewayBaseResponse<HolidayResponse> HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  @InjectMocks
  HolidayOutboundServiceImpl holidayOutboundService;

  @Mock
  CalendarEndPointService gatewayEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> HOLIDAY_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<HolidayResponse>> HOLIDAY_RESPONSE;


  @Mock
  Call<GatewayBaseResponse<Boolean>> CALL_TRUE;


  @Test
  public void findHolidayFilterPaginatedTest() throws Exception {
    when(this.gatewayEndPointService.findHolidayFilterPaginated(HEADER, QUERY)).thenReturn(HOLIDAY_RESPONSE_PAGE);
    when(this.HOLIDAY_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RestResponsePage<HolidayResponse>> holidayResponse = this.holidayOutboundService
        .findHolidayFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST,
        this.QUERY.get("startDate"), this.QUERY.get("endDate"), this.QUERY.get("lang"), this
            .QUERY.get("content"),0,10, HolidayColumn
            .valueOf(this.QUERY.get("columnSort")),
            SortDirection.valueOf(this.QUERY.get("sortDirection"))).blockingGet();

    assertEquals(10,holidayResponse.getData().getTotalPages());

    verify(this.gatewayEndPointService).findHolidayFilterPaginated(this.HEADER, this.QUERY);
  }

  @Test
  public void findHolidayFilterPaginatedBlankValueTest() throws Exception {
    when(this.gatewayEndPointService.findHolidayFilterPaginated(HEADER, QUERY_NULL)).thenReturn
        (HOLIDAY_RESPONSE_PAGE);
    when(this.HOLIDAY_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RestResponsePage<HolidayResponse>> holidayResponse = this.holidayOutboundService
        .findHolidayFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            null, null, null, null,0,10, null,null).blockingGet();

    assertEquals(10,holidayResponse.getData().getTotalPages());

    verify(this.gatewayEndPointService).findHolidayFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findHolidayFilterPaginatedTestErrorRequest() throws Exception {
    when(this.gatewayEndPointService.findHolidayFilterPaginated(HEADER, QUERY)).thenReturn(null)
;
    try {
      this.holidayOutboundService
          .findHolidayFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("startDate"), this.QUERY.get("endDate"), this.QUERY.get("lang"), this
                  .QUERY.get("content"), 0, 10, HolidayColumn
                  .valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection"))).blockingGet();
    }catch (Exception e){
      verify(this.gatewayEndPointService).findHolidayFilterPaginated(this.HEADER, this.QUERY);
    }
  }

  @Test
  public void createHolidayTest() throws Exception {
    when(this.gatewayEndPointService.createHoliday(HEADER, HOLIDAY_REQUEST)).thenReturn
        (HOLIDAY_RESPONSE);
    when(this.HOLIDAY_RESPONSE.execute()).thenReturn(Response.success(this.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<HolidayResponse> holidayResponse = this.holidayOutboundService
        .createHoliday(CommonTestVariable
                .MANDATORY_REQUEST, HOLIDAY_REQUEST).blockingGet();

    assertEquals("EN", holidayResponse.getData().getDescriptions().get(0).getLang().getCode());

    verify(this.gatewayEndPointService).createHoliday(this.HEADER, HOLIDAY_REQUEST);
  }

  @Test
  public void createHolidayTestErrorRequest() throws Exception {
    when(this.gatewayEndPointService.createHoliday(HEADER, HOLIDAY_REQUEST)).thenReturn
        (null);
    try {
      this.holidayOutboundService
          .createHoliday(CommonTestVariable
              .MANDATORY_REQUEST, HOLIDAY_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.gatewayEndPointService).createHoliday(this.HEADER, HOLIDAY_REQUEST);
    }
  }

  @Test
  public void updateHolidayTest() throws Exception {
    when(this.gatewayEndPointService.updateHoliday(HEADER, HOLIDAY_REQUEST, "123")).thenReturn
        (HOLIDAY_RESPONSE);
    when(this.HOLIDAY_RESPONSE.execute()).thenReturn(Response.success(this.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<HolidayResponse> holidayResponse = this.holidayOutboundService
        .updateHoliday(CommonTestVariable
            .MANDATORY_REQUEST, HOLIDAY_REQUEST, "123").blockingGet();

    assertEquals("EN", holidayResponse.getData().getDescriptions().get(0).getLang().getCode());

    verify(this.gatewayEndPointService).updateHoliday(this.HEADER, HOLIDAY_REQUEST, "123");
  }

  @Test
  public void updateHolidayTestErrorRequest() throws Exception {
    when(this.gatewayEndPointService.updateHoliday(HEADER, HOLIDAY_REQUEST, "123")).thenReturn
        (null);

    try {
      GatewayBaseResponse<HolidayResponse> holidayResponse = this.holidayOutboundService
          .updateHoliday(CommonTestVariable
              .MANDATORY_REQUEST, HOLIDAY_REQUEST, "123").blockingGet();
    }catch (Exception e) {
      verify(this.gatewayEndPointService).updateHoliday(this.HEADER, HOLIDAY_REQUEST, "123");
    }

  }

  @Test
  public void deleteHolidayTest() throws Exception {
    when(this.gatewayEndPointService.deleteHoliday(HEADER, "123")).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.holidayOutboundService
        .deleteHoliday(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.gatewayEndPointService).deleteHoliday(this.HEADER, "123");
  }

  @Test
  public void deleteHolidayTestErrorRequest() throws Exception {
    when(this.gatewayEndPointService.deleteHoliday(HEADER, "123")).thenReturn
        (null);
    try {
      this.holidayOutboundService
          .deleteHoliday(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    }catch (Exception e) {
      verify(this.gatewayEndPointService).deleteHoliday(this.HEADER, "123");
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.gatewayEndPointService.findHolidayById(HEADER, "123")).thenReturn
        (HOLIDAY_RESPONSE);
    when(this.HOLIDAY_RESPONSE.execute()).thenReturn(Response.success(this.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<HolidayResponse> holidayResponse = this.holidayOutboundService
        .findHolidayById(CommonTestVariable
            .MANDATORY_REQUEST, "123").blockingGet();

    assertEquals("EN", holidayResponse.getData().getDescriptions().get(0).getLang().getCode());

    verify(this.gatewayEndPointService).findHolidayById(this.HEADER, "123");
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.gatewayEndPointService.findHolidayById(HEADER, "123")).thenReturn
        (null);

    try {
      this.holidayOutboundService
          .findHolidayById(CommonTestVariable
              .MANDATORY_REQUEST, "123").blockingGet();
    } catch (Exception e) {
      verify(this.gatewayEndPointService).findHolidayById(this.HEADER, "123");
    }

  }


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
    this.QUERY.put("startDate", "2011-01-01");
    this.QUERY.put("endDate", "2100-01-01");
    this.QUERY.put("lang", "EN");
    this.QUERY.put("content", "");
    this.QUERY.put("page", "0");
    this.QUERY.put("size", "10");
    this.QUERY.put("columnSort", "ID");
    this.QUERY.put("sortDirection", "DESC");

    this.QUERY_NULL = new HashMap<>();
    this.QUERY_NULL.put("page", "0");
    this.QUERY_NULL.put("size", "10");

    HolidayDescription holidayDescription = new HolidayDescriptionBuilder()
        .withContent("blabla")
        .withLang(Language.EN)
        .build();

    HolidayResponse holidayResponse = new HolidayResponseBuilder()
        .withDate(new Date())
        .withDescriptions(Arrays.asList(holidayDescription))
        .build();

    HOLIDAY_RESPONSE_GENERATED = new HolidayResponseBuilder()
        .withDate(new Date())
        .withDescriptions(Arrays.asList(holidayDescription))
        .withId("123")
        .build();

    HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE = new GatewayBaseResponse<>();
    HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE.setCode("SUCCESS");
    HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE.setData(HOLIDAY_RESPONSE_GENERATED);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(holidayResponse));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    this.HOLIDAY_REQUEST = new HolidayRequestBuilder()
        .withDate("2017-01-01")
        .withDescriptions(Arrays.asList(holidayDescription))
        .build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.gatewayEndPointService);
  }
}
