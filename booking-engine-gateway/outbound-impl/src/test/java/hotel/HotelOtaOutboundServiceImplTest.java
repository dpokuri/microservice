package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelOtaOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponseBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class HotelOtaOutboundServiceImplTest {

  Map<String, String> HEADER;

  GatewayBaseResponse<List<OtaResponse>> LIST_RESULT;

  GatewayBaseResponse<OtaResponse> RESULT;

  GatewayBaseResponse BASE_RESPONSE;

  OtaRequest OTA_REQUEST;

  @InjectMocks
  HotelOtaOutboundServiceImpl hotelOtaOutboundService;

  @Mock
  HotelScrappingEndPointService hotelScrappingEndPointService;

  @Mock
  Call<GatewayBaseResponse<List<OtaResponse>>> LIST_OTA_RESPONSE;

  @Mock
  Call<GatewayBaseResponse<OtaResponse>> OTA_RESPONSE;

  @Mock
  Call<GatewayBaseResponse> RESPONSE;

  @Test
  public void findHotelOtaTest() throws Exception {

    when(this.hotelScrappingEndPointService.findHotelOta(HEADER)).thenReturn(LIST_OTA_RESPONSE);
    when(this.LIST_OTA_RESPONSE.execute()).thenReturn(Response.success(this.LIST_RESULT));

    GatewayBaseResponse<List<OtaResponse>> otaResult = this.hotelOtaOutboundService
        .findHotelOta(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(this.LIST_RESULT, otaResult);

    verify(this.hotelScrappingEndPointService).findHotelOta(this.HEADER);

  }

  @Test
  public void findHotelOtaTestErrorRequest() throws Exception {

    when(this.hotelScrappingEndPointService.findHotelOta(HEADER)).thenReturn(null);

    try {
      this.hotelOtaOutboundService
          .findHotelOta(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findHotelOta(this.HEADER);
    }

  }

  @Test
  public void findHotelOtaByIdTest()throws Exception {

    when(this.hotelScrappingEndPointService.findHotelOtaById(HEADER,"1")).thenReturn(OTA_RESPONSE);
    when(this.OTA_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<OtaResponse> otaResult = this.hotelOtaOutboundService
        .findHotelOtaById(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.RESULT, otaResult);

    verify(this.hotelScrappingEndPointService).findHotelOtaById(this.HEADER, "1");

  }

  @Test
  public void findHotelOtaByIdTestErrorRequest()throws Exception {

    when(this.hotelScrappingEndPointService.findHotelOtaById(HEADER,"1")).thenReturn(null);

    try {
      this.hotelOtaOutboundService
          .findHotelOtaById(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findHotelOtaById(this.HEADER, "1");
    }

  }

  @Test
  public void createHotelOtaTest() throws Exception {
    when(this.hotelScrappingEndPointService.createHotelOta(HEADER, OTA_REQUEST)).thenReturn
        (OTA_RESPONSE);
    when(this.OTA_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<OtaResponse> otaResponse = this.hotelOtaOutboundService
        .createHotelOta(CommonTestVariable
            .MANDATORY_REQUEST, OTA_REQUEST).blockingGet();

    assertEquals(this.RESULT, otaResponse);

    verify(this.hotelScrappingEndPointService).createHotelOta(this.HEADER, OTA_REQUEST);
  }

  @Test
  public void createHotelOtaTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.createHotelOta(HEADER, OTA_REQUEST)).thenReturn
        (null);
    try {
      this.hotelOtaOutboundService
          .createHotelOta(CommonTestVariable
              .MANDATORY_REQUEST, OTA_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).createHotelOta(this.HEADER, OTA_REQUEST);
    }
  }

  @Test
  public void updateHotelOtaTest() throws Exception {
    when(this.hotelScrappingEndPointService.updateHotelOta(HEADER, "1", OTA_REQUEST)).thenReturn
        (OTA_RESPONSE);
    when(this.OTA_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<OtaResponse> otaResponse = this.hotelOtaOutboundService
        .updateHotelOta(CommonTestVariable
            .MANDATORY_REQUEST, "1", OTA_REQUEST).blockingGet();

    assertEquals(this.RESULT, otaResponse);

    verify(this.hotelScrappingEndPointService).updateHotelOta(this.HEADER, "1", OTA_REQUEST);
  }

  @Test
  public void updateHotelOtaTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.updateHotelOta(HEADER, "1", OTA_REQUEST)).thenReturn
        (null);
    try {
      this.hotelOtaOutboundService
          .updateHotelOta(CommonTestVariable
              .MANDATORY_REQUEST, "1", OTA_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).updateHotelOta(this.HEADER, "1", OTA_REQUEST);
    }
  }

  @Test
  public void deleteHotelOtaTest() throws Exception {
    when(this.hotelScrappingEndPointService.deleteHotelOta(HEADER, "1")).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.BASE_RESPONSE));

    GatewayBaseResponse otaResponse = this.hotelOtaOutboundService
        .deleteHotelOta(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.BASE_RESPONSE, otaResponse);

    verify(this.hotelScrappingEndPointService).deleteHotelOta(this.HEADER, "1");
  }

  @Test
  public void deleteHotelOtaTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.deleteHotelOta(HEADER, "1")).thenReturn
        (null);
    try {
      this.hotelOtaOutboundService
          .deleteHotelOta(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).deleteHotelOta(this.HEADER, "1");
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

    OtaResponse otaResponse = new OtaResponseBuilder()
        .withId("1")
        .withName("Tiket.com")
        .withEndpoint("tiket")
        .withActive(1)
        .build();

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(otaResponse);

    this.LIST_RESULT = new GatewayBaseResponse<>();
    this.LIST_RESULT.setCode("SUCCCESS");
    this.LIST_RESULT.setData(Arrays.asList(otaResponse));

    this.OTA_REQUEST = new OtaRequestBuilder()
        .withName("Tiket.com")
        .withEndpoint("tiket")
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
