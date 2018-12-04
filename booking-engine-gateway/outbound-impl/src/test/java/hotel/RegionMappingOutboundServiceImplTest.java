package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.RegionMappingOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponseBuilder;

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

public class RegionMappingOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> PAGE_RESULT;

  RestResponsePage<RegionMappingResponse> DATA;

  GatewayBaseResponse<RegionMappingResponse> RESULT;

  GatewayBaseResponse BASE_RESPONSE;

  RegionMappingRequest REQUEST;

  @InjectMocks
  RegionMappingOutboundServiceImpl regionMappingOutboundService;

  @Mock
  HotelScrappingEndPointService hotelScrappingEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> REGION_MAP_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RegionMappingResponse>> REGION_MAP_RESPONSE;

  @Mock
  Call<GatewayBaseResponse> RESPONSE;

  @Test
  public void findRegionMappingsByStoreIdTest() throws Exception {

    when(this.hotelScrappingEndPointService.findRegionMappingsByStoreId(HEADER, QUERY)).thenReturn(REGION_MAP_RESPONSE_PAGE);
    when(this.REGION_MAP_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.PAGE_RESULT));

    GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> regionMappingResponse = this.regionMappingOutboundService
        .findRegionMappingsByStoreId(CommonTestVariable
                .MANDATORY_REQUEST,
            Integer.parseInt(this.QUERY.get("page")),
            Integer.parseInt(this.QUERY.get("limit")),
            this.QUERY.get("q"),
            this.QUERY.get("sort"),
            this.QUERY.get("method")
        ).blockingGet();

    assertEquals(this.PAGE_RESULT, regionMappingResponse);

    verify(this.hotelScrappingEndPointService).findRegionMappingsByStoreId(this.HEADER, QUERY);

  }

  @Test
  public void findRegionMappingsByStoreIdTestErrorRequest() throws Exception {

    when(this.hotelScrappingEndPointService.findRegionMappingsByStoreId(HEADER, QUERY)).thenReturn(null);

    try {
      this.regionMappingOutboundService
          .findRegionMappingsByStoreId(CommonTestVariable
                  .MANDATORY_REQUEST,
              Integer.parseInt(this.QUERY.get("page")),
              Integer.parseInt(this.QUERY.get("limit")),
              this.QUERY.get("q"),
              this.QUERY.get("sort"),
              this.QUERY.get("method")
          ).blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findRegionMappingsByStoreId(HEADER, QUERY);
    }

  }

  @Test
  public void findRegionMappingByStoreIdTest()throws Exception {

    when(this.hotelScrappingEndPointService.findRegionMappingByStoreIdAndId(HEADER,"1")).thenReturn(REGION_MAP_RESPONSE);
    when(this.REGION_MAP_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<RegionMappingResponse> regionMappingResponse = this.regionMappingOutboundService
        .findRegionMappingByStoreIdAndId(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.RESULT, regionMappingResponse);

    verify(this.hotelScrappingEndPointService).findRegionMappingByStoreIdAndId(this.HEADER, "1");

  }

  @Test
  public void findRegionMappingByStoreIdTestErrorRequest()throws Exception {

    when(this.hotelScrappingEndPointService.findRegionMappingByStoreIdAndId(HEADER,"1")).thenReturn(null);

    try {
      this.regionMappingOutboundService
          .findRegionMappingByStoreIdAndId(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findRegionMappingByStoreIdAndId(this.HEADER, "1");
    }

  }

  @Test
  public void createRegionMappingTest() throws Exception {
    when(this.hotelScrappingEndPointService.createRegionMapping(HEADER, REQUEST)).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse regionMappingResult = this.regionMappingOutboundService
        .createRegionMapping(CommonTestVariable
            .MANDATORY_REQUEST, REQUEST).blockingGet();

    assertEquals(this.RESULT, regionMappingResult);

    verify(this.hotelScrappingEndPointService).createRegionMapping(this.HEADER, REQUEST);
  }

  @Test
  public void createRegionMappingTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.createRegionMapping(HEADER, REQUEST)).thenReturn
        (null);
    try {
      this.regionMappingOutboundService
          .createRegionMapping(CommonTestVariable
              .MANDATORY_REQUEST, REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).createRegionMapping(this.HEADER, REQUEST);
    }
  }

  @Test
  public void updateRegionMappingTest() throws Exception {
    when(this.hotelScrappingEndPointService.updateRegionMapping(HEADER, "1", REQUEST)).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse regionMappingResult = this.regionMappingOutboundService
        .updateRegionMapping(CommonTestVariable
            .MANDATORY_REQUEST, "1", REQUEST).blockingGet();

    assertEquals(this.RESULT, regionMappingResult);

    verify(this.hotelScrappingEndPointService).updateRegionMapping(this.HEADER, "1", REQUEST);
  }

  @Test
  public void updateRegionMappingTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.updateRegionMapping(HEADER, "1", REQUEST)).thenReturn
        (null);
    try {
      this.regionMappingOutboundService
          .updateRegionMapping(CommonTestVariable
              .MANDATORY_REQUEST, "1", REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).updateRegionMapping(this.HEADER, "1", REQUEST);
    }
  }

  @Test
  public void deleteRegionMappingTest() throws Exception {
    when(this.hotelScrappingEndPointService.deleteRegionMapping(HEADER, "1")).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.BASE_RESPONSE));

    GatewayBaseResponse regionMappingResult = this.regionMappingOutboundService
        .deleteRegionMapping(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.BASE_RESPONSE, regionMappingResult);

    verify(this.hotelScrappingEndPointService).deleteRegionMapping(this.HEADER, "1");
  }

  @Test
  public void deleteRegionMappingTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.deleteRegionMapping(HEADER, "1")).thenReturn
        (null);
    try {
      this.regionMappingOutboundService
          .deleteRegionMapping(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).deleteRegionMapping(this.HEADER, "1");
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
    this.QUERY.put("sort", "desc");
    this.QUERY.put("method", "get");

    RegionMappingResponse picRegionResponse = new RegionMappingResponseBuilder()
        .withId("1")
        .withName("Tiket.com")
        .withActive(1)
        .build();

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(picRegionResponse));

    this.PAGE_RESULT = new GatewayBaseResponse<>();
    this.PAGE_RESULT.setCode("SUCCCESS");
    this.PAGE_RESULT.setData(DATA);

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(picRegionResponse);


    this.REQUEST = new RegionMappingRequestBuilder()
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
