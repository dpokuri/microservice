package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelMappingOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponseBuilder;

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

public class HotelMappingOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> PAGE_RESULT;

  RestResponsePage<HotelMappingResponse> DATA;

  GatewayBaseResponse<HotelMappingResponse> RESULT;

  GatewayBaseResponse BASE_RESPONSE;

  HotelMappingRequest HOTEL_MAPPING_REQUEST;

  @InjectMocks
  HotelMappingOutboundServiceImpl hotelMappingOutboundService;

  @Mock
  HotelScrappingEndPointService hotelScrappingEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> HOTEL_MAPPING_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<HotelMappingResponse>> HOTEL_MAPPING_RESPONSE;

  @Mock
  Call<GatewayBaseResponse> RESPONSE;

  @Test
  public void findHotelMappingsByStoreIdTest() throws Exception {

    when(this.hotelScrappingEndPointService.findHotelMappingsByStoreId(HEADER, QUERY)).thenReturn(HOTEL_MAPPING_RESPONSE_PAGE);
    when(this.HOTEL_MAPPING_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.PAGE_RESULT));

    GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> hotelMappingResponse = this.hotelMappingOutboundService
        .findHotelMappingsByStoreId(CommonTestVariable
            .MANDATORY_REQUEST,
            Integer.parseInt(this.QUERY.get("page")),
            Integer.parseInt(this.QUERY.get("limit")),
            this.QUERY.get("q"),
            this.QUERY.get("regionId"),
            this.QUERY.get("sort"),
            this.QUERY.get("method")
            ).blockingGet();

    assertEquals(this.PAGE_RESULT, hotelMappingResponse);

    verify(this.hotelScrappingEndPointService).findHotelMappingsByStoreId(this.HEADER, QUERY);

  }

  @Test
  public void findHotelMappingsByStoreIdTestErrorRequest() throws Exception {

    when(this.hotelScrappingEndPointService.findHotelMappingsByStoreId(HEADER, QUERY)).thenReturn(null);

    try {
      this.hotelMappingOutboundService
          .findHotelMappingsByStoreId(CommonTestVariable
                  .MANDATORY_REQUEST,
              Integer.parseInt(this.QUERY.get("page")),
              Integer.parseInt(this.QUERY.get("limit")),
              this.QUERY.get("q"),
              this.QUERY.get("regionId"),
              this.QUERY.get("sort"),
              this.QUERY.get("method")
          ).blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findHotelMappingsByStoreId(HEADER, QUERY);
    }

  }

  @Test
  public void findHotelMappingByStoreIdAndIdTest()throws Exception {

    when(this.hotelScrappingEndPointService.findHotelMappingByStoreIdAndId(HEADER,"1")).thenReturn(HOTEL_MAPPING_RESPONSE);
    when(this.HOTEL_MAPPING_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<HotelMappingResponse> hotelMappingResult = this.hotelMappingOutboundService
        .findHotelMappingByStoreIdAndId(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.RESULT, hotelMappingResult);

    verify(this.hotelScrappingEndPointService).findHotelMappingByStoreIdAndId(this.HEADER, "1");

  }

  @Test
  public void findHotelMappingByStoreIdAndIdTestErrorRequest()throws Exception {

    when(this.hotelScrappingEndPointService.findHotelMappingByStoreIdAndId(HEADER,"1")).thenReturn(null);

    try {
      this.hotelMappingOutboundService
          .findHotelMappingByStoreIdAndId(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findHotelMappingByStoreIdAndId(this.HEADER, "1");
    }

  }

  @Test
  public void createHotelOtaTest() throws Exception {
    when(this.hotelScrappingEndPointService.createHotelMapping(HEADER, HOTEL_MAPPING_REQUEST)).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse hotelMappingResult = this.hotelMappingOutboundService
        .createHotelMapping(CommonTestVariable
            .MANDATORY_REQUEST, HOTEL_MAPPING_REQUEST).blockingGet();

    assertEquals(this.RESULT, hotelMappingResult);

    verify(this.hotelScrappingEndPointService).createHotelMapping(this.HEADER, HOTEL_MAPPING_REQUEST);
  }

  @Test
  public void createHotelOtaTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.createHotelMapping(HEADER, HOTEL_MAPPING_REQUEST)).thenReturn
        (null);
    try {
      this.hotelMappingOutboundService
          .createHotelMapping(CommonTestVariable
              .MANDATORY_REQUEST, HOTEL_MAPPING_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).createHotelMapping(this.HEADER, HOTEL_MAPPING_REQUEST);
    }
  }

  @Test
  public void updateHotelMappingTest() throws Exception {
    when(this.hotelScrappingEndPointService.updateHotelMapping(HEADER, "1", HOTEL_MAPPING_REQUEST)).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse hotelMappingResult = this.hotelMappingOutboundService
        .updateHotelMapping(CommonTestVariable
            .MANDATORY_REQUEST, "1", HOTEL_MAPPING_REQUEST).blockingGet();

    assertEquals(this.RESULT, hotelMappingResult);

    verify(this.hotelScrappingEndPointService).updateHotelMapping(this.HEADER, "1", HOTEL_MAPPING_REQUEST);
  }

  @Test
  public void updateHotelMappingTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.updateHotelMapping(HEADER, "1", HOTEL_MAPPING_REQUEST)).thenReturn
        (null);
    try {
      this.hotelMappingOutboundService
          .updateHotelMapping(CommonTestVariable
              .MANDATORY_REQUEST, "1", HOTEL_MAPPING_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).updateHotelMapping(this.HEADER, "1", HOTEL_MAPPING_REQUEST);
    }
  }

  @Test
  public void deleteHotelOtaTest() throws Exception {
    when(this.hotelScrappingEndPointService.deleteHotelMapping(HEADER, "1")).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.BASE_RESPONSE));

    GatewayBaseResponse hotelMappingResult = this.hotelMappingOutboundService
        .deleteHotelMapping(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.BASE_RESPONSE, hotelMappingResult);

    verify(this.hotelScrappingEndPointService).deleteHotelMapping(this.HEADER, "1");
  }

  @Test
  public void deleteHotelOtaTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.deleteHotelOta(HEADER, "1")).thenReturn
        (null);
    try {
      this.hotelMappingOutboundService
          .deleteHotelMapping(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).deleteHotelMapping(this.HEADER, "1");
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
    this.QUERY.put("q", "tes");
    this.QUERY.put("regionId", "1");
    this.QUERY.put("sort", "desc");
    this.QUERY.put("method", "get");

    HotelMappingResponse hotelMappingResponse = new HotelMappingResponseBuilder()
        .withId("1")
        .withName("Tiket.com")
        .withActive(1)
        .build();

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(hotelMappingResponse));

    this.PAGE_RESULT = new GatewayBaseResponse<>();
    this.PAGE_RESULT.setCode("SUCCCESS");
    this.PAGE_RESULT.setData(DATA);

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(hotelMappingResponse);


    this.HOTEL_MAPPING_REQUEST = new HotelMappingRequestBuilder()
        .withName("Tiket.com")
        .withActive(1)
        .build();

    this.BASE_RESPONSE = new GatewayBaseResponse();
    this.BASE_RESPONSE.setCode("SUCCCESS");
    this.BASE_RESPONSE.setData(null);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelScrappingEndPointService);
  }

}
