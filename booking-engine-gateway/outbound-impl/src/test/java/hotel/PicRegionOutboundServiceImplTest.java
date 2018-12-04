package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.PicRegionOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionRequestBuilder;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponseBuilder;

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

public class PicRegionOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  GatewayBaseResponse<RestResponsePage<PicRegionResponse>> PAGE_RESULT;

  RestResponsePage<PicRegionResponse> DATA;

  GatewayBaseResponse<PicRegionResponse> RESULT;

  GatewayBaseResponse BASE_RESPONSE;

  PicRegionRequest PIC_REGION_REQUEST;

  @InjectMocks
  PicRegionOutboundServiceImpl picRegionOutboundService;

  @Mock
  HotelScrappingEndPointService hotelScrappingEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<PicRegionResponse>>> PIC_REGION_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<PicRegionResponse>> PIC_REGION_RESPONSE;

  @Mock
  Call<GatewayBaseResponse> RESPONSE;

  @Test
  public void findPicRegionListTest() throws Exception {

    when(this.hotelScrappingEndPointService.findPicRegionList(HEADER, QUERY)).thenReturn(PIC_REGION_RESPONSE_PAGE);
    when(this.PIC_REGION_RESPONSE_PAGE.execute()).thenReturn(Response.success(this.PAGE_RESULT));

    GatewayBaseResponse<RestResponsePage<PicRegionResponse>> hotelMappingResponse = this.picRegionOutboundService
        .findPicRegionList(CommonTestVariable
                .MANDATORY_REQUEST,
            Integer.parseInt(this.QUERY.get("page")),
            Integer.parseInt(this.QUERY.get("limit")),
            this.QUERY.get("q"),
            this.QUERY.get("sort"),
            this.QUERY.get("method")
        ).blockingGet();

    assertEquals(this.PAGE_RESULT, hotelMappingResponse);

    verify(this.hotelScrappingEndPointService).findPicRegionList(this.HEADER, QUERY);

  }

  @Test
  public void findPicRegionListTestErrorRequest() throws Exception {

    when(this.hotelScrappingEndPointService.findPicRegionList(HEADER, QUERY)).thenReturn(null);

    try {
      this.picRegionOutboundService
          .findPicRegionList(CommonTestVariable
                  .MANDATORY_REQUEST,
              Integer.parseInt(this.QUERY.get("page")),
              Integer.parseInt(this.QUERY.get("limit")),
              this.QUERY.get("q"),
              this.QUERY.get("sort"),
              this.QUERY.get("method")
          ).blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findPicRegionList(HEADER, QUERY);
    }

  }

  @Test
  public void findPicRegionByIdTest()throws Exception {

    when(this.hotelScrappingEndPointService.findPicRegionById(HEADER,"1")).thenReturn(PIC_REGION_RESPONSE);
    when(this.PIC_REGION_RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<PicRegionResponse> picRegionResponse = this.picRegionOutboundService
        .findPicRegionById(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.RESULT, picRegionResponse);

    verify(this.hotelScrappingEndPointService).findPicRegionById(this.HEADER, "1");

  }

  @Test
  public void findPicRegionByIdTestErrorRequest()throws Exception {

    when(this.hotelScrappingEndPointService.findPicRegionById(HEADER,"1")).thenReturn(null);

    try {
      this.picRegionOutboundService
          .findPicRegionById(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).findPicRegionById(this.HEADER, "1");
    }

  }

  @Test
  public void createPicRegionTest() throws Exception {
    when(this.hotelScrappingEndPointService.createPicRegion(HEADER, PIC_REGION_REQUEST)).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse picRegionResult = this.picRegionOutboundService
        .createPicRegion(CommonTestVariable
            .MANDATORY_REQUEST, PIC_REGION_REQUEST).blockingGet();

    assertEquals(this.RESULT, picRegionResult);

    verify(this.hotelScrappingEndPointService).createPicRegion(this.HEADER, PIC_REGION_REQUEST);
  }

  @Test
  public void createPicRegionTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.createPicRegion(HEADER, PIC_REGION_REQUEST)).thenReturn
        (null);
    try {
      this.picRegionOutboundService
          .createPicRegion(CommonTestVariable
              .MANDATORY_REQUEST, PIC_REGION_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).createPicRegion(this.HEADER, PIC_REGION_REQUEST);
    }
  }

  @Test
  public void updatePicRegionTest() throws Exception {
    when(this.hotelScrappingEndPointService.updatePicRegion(HEADER, "1", PIC_REGION_REQUEST)).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse picRegionResult = this.picRegionOutboundService
        .updatePicRegion(CommonTestVariable
            .MANDATORY_REQUEST, "1", PIC_REGION_REQUEST).blockingGet();

    assertEquals(this.RESULT, picRegionResult);

    verify(this.hotelScrappingEndPointService).updatePicRegion(this.HEADER, "1", PIC_REGION_REQUEST);
  }

  @Test
  public void updatePicRegionTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.updatePicRegion(HEADER, "1", PIC_REGION_REQUEST)).thenReturn
        (null);
    try {
      this.picRegionOutboundService
          .updatePicRegion(CommonTestVariable
              .MANDATORY_REQUEST, "1", PIC_REGION_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).updatePicRegion(this.HEADER, "1", PIC_REGION_REQUEST);
    }
  }

  @Test
  public void deletePicRegionTest() throws Exception {
    when(this.hotelScrappingEndPointService.deletePicRegionById(HEADER, "1")).thenReturn
        (RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.BASE_RESPONSE));

    GatewayBaseResponse picRegionResult = this.picRegionOutboundService
        .deletePicRegionById(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    assertEquals(this.BASE_RESPONSE, picRegionResult);

    verify(this.hotelScrappingEndPointService).deletePicRegionById(this.HEADER, "1");
  }

  @Test
  public void deletePicRegionTestErrorRequest() throws Exception {
    when(this.hotelScrappingEndPointService.deletePicRegionById(HEADER, "1")).thenReturn
        (null);
    try {
      this.picRegionOutboundService
          .deletePicRegionById(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    }catch (Exception e) {
      verify(this.hotelScrappingEndPointService).deletePicRegionById(this.HEADER, "1");
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

    PicRegionResponse picRegionResponse = new PicRegionResponseBuilder()
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


    this.PIC_REGION_REQUEST = new PicRegionRequestBuilder()
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
