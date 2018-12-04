package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelScrappingReportOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponseBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class HotelScrappingReportOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_EMAIL;

  Map<String, String> QUERY_EXPORT;

  GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> PAGE_RESULT;

  RestResponsePage<ScrappingReportResponse> DATA;

  GatewayBaseResponse<Boolean> RESULT;

  GatewayBaseResponse<String> RESULT_EXPORT;

  @InjectMocks
  HotelScrappingReportOutboundServiceImpl hotelScrappingReportOutboundService;

  @Mock
  HotelScrappingEndPointService hotelScrappingEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> SCRAPPING_REPORT_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<Boolean>> SCRAPPING_REPORT_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<String>> SCRAPPING_REPORT_RESPONSE_STRING;

  @Test
  public void getPriceReportDataTest() throws Exception {

    when(this.hotelScrappingEndPointService.getPriceReportData(HEADER, QUERY)).thenReturn(SCRAPPING_REPORT_RESPONSE_PAGE);
    when(this.SCRAPPING_REPORT_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.PAGE_RESULT));

    GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> reportResult = this.hotelScrappingReportOutboundService
        .getPriceReportData(CommonTestVariable.MANDATORY_REQUEST,
            Integer.parseInt(this.QUERY.get("page")),
            Integer.parseInt(this.QUERY.get("limit")),
            this.QUERY.get("regionId"),
            this.QUERY.get("checkInDate"),
            this.QUERY.get("scrappingDate"),
            this.QUERY.get("q"),
            this.QUERY.get("sort"),
            this.QUERY.get("method")
        ).blockingGet();

    assertEquals(this.PAGE_RESULT, reportResult);

    verify(this.hotelScrappingEndPointService).getPriceReportData(this.HEADER, QUERY);

  }

  @Test
  public void getPriceReportDataTestErrorRequest() throws Exception {

    when(this.hotelScrappingEndPointService.getPriceReportData(HEADER, QUERY)).thenReturn(null);

    try {
      this.hotelScrappingReportOutboundService
          .getPriceReportData(CommonTestVariable.MANDATORY_REQUEST,
              Integer.parseInt(this.QUERY.get("page")),
              Integer.parseInt(this.QUERY.get("limit")),
              this.QUERY.get("regionId"),
              this.QUERY.get("checkInDate"),
              this.QUERY.get("scrappingDate"),
              this.QUERY.get("q"),
              this.QUERY.get("sort"),
              this.QUERY.get("method")
          ).blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).getPriceReportData(HEADER, QUERY);
    }

  }

  @Test
  public void getEmailReportTest()throws Exception {

    when(this.hotelScrappingEndPointService.getEmailReport(HEADER, QUERY_EMAIL)).thenReturn(SCRAPPING_REPORT_RESPONSE);
    when(this.SCRAPPING_REPORT_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<Boolean> reportResult = this.hotelScrappingReportOutboundService
        .getEmailReport(CommonTestVariable
            .MANDATORY_REQUEST, "1", "1", "1", "1", "test@test.com").blockingGet();

    assertEquals(this.RESULT, reportResult);

    verify(this.hotelScrappingEndPointService).getEmailReport(this.HEADER, QUERY_EMAIL);

  }

  @Test
  public void getEmailReportTestErrorRequest()throws Exception {

    when(this.hotelScrappingEndPointService.getEmailReport(HEADER, QUERY_EMAIL)).thenReturn(null);

    try {
      this.hotelScrappingReportOutboundService
          .getEmailReport(CommonTestVariable
              .MANDATORY_REQUEST, "1", "1", "1", "1", "test@test.com").blockingGet();

    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).getEmailReport(this.HEADER, QUERY_EMAIL);
    }

  }

  @Test
  public void exportReportTest()throws Exception {

    when(this.hotelScrappingEndPointService.exportReport(HEADER, QUERY_EXPORT)).thenReturn(SCRAPPING_REPORT_RESPONSE_STRING);
    when(this.SCRAPPING_REPORT_RESPONSE_STRING.execute()).thenReturn(Response.success(this.RESULT_EXPORT));

    GatewayBaseResponse<String> exportReport = this.hotelScrappingReportOutboundService
        .exportReport(CommonTestVariable
            .MANDATORY_REQUEST, "1", "1", "1", "1").blockingGet();

    assertEquals(this.RESULT_EXPORT, exportReport);

    verify(this.hotelScrappingEndPointService).exportReport(this.HEADER, QUERY_EXPORT);

  }

  @Test
  public void exportReportTestErrorRequest()throws Exception {

    when(this.hotelScrappingEndPointService.exportReport(HEADER, QUERY_EXPORT)).thenReturn(null);

    try {
      this.hotelScrappingReportOutboundService
          .exportReport(CommonTestVariable
              .MANDATORY_REQUEST, "1", "1", "1", "1").blockingGet();

    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).exportReport(this.HEADER, QUERY_EXPORT);
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
    this.QUERY.put("page", "1");
    this.QUERY.put("limit", "1");
    this.QUERY.put("regionId", "1");
    this.QUERY.put("checkInDate", "1");
    this.QUERY.put("scrappingDate", "1");
    this.QUERY.put("q", "1");
    this.QUERY.put("sort", "desc");
    this.QUERY.put("method", "get");

    this.QUERY_EMAIL = new HashMap<>();
    this.QUERY_EMAIL.put("regionId", "1");
    this.QUERY_EMAIL.put("checkInDate", "1");
    this.QUERY_EMAIL.put("scrappingDate", "1");
    this.QUERY_EMAIL.put("q", "1");
    this.QUERY_EMAIL.put("email", "test@test.com");

    this.QUERY_EXPORT = new HashMap<>();
    this.QUERY_EXPORT.put("regionId", "1");
    this.QUERY_EXPORT.put("checkInDate", "1");
    this.QUERY_EXPORT.put("scrappingDate", "1");
    this.QUERY_EXPORT.put("q", "1");

    ScrappingReportResponse scrappingReportResponse = new ScrappingReportResponseBuilder()
        .withHotelId("1")
        .withHotelName("tes")
        .build();

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(scrappingReportResponse));

    this.PAGE_RESULT = new GatewayBaseResponse<>();
    this.PAGE_RESULT.setCode("SUCCCESS");
    this.PAGE_RESULT.setData(DATA);

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(true);

    this.RESULT_EXPORT = new GatewayBaseResponse<>();
    this.RESULT_EXPORT.setCode("SUCCCESS");
    this.RESULT_EXPORT.setData("");

  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelScrappingEndPointService);
  }

}
