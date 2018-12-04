package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelDetailOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

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

public class HotelDetailOutboundServiceImplTest {

  @InjectMocks
  private HotelDetailOutboundServiceImpl hotelDetailOutboundService;

  @Mock
  private HotelAggregateEndPointService hotelAggregateEndPointService;

  @Mock
  Call<GatewayBaseResponse<List<Map<String, Object>>>> CALL_HOTEL_POLICY_RESPONSE;

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, Object> HOTEL_POLICY_RESPONSE;

  GatewayBaseResponse<List<Map<String, Object>>> HOTEL_POLICY_RESPONSE_BASE;

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
    this.QUERY.put("hotelId", "1");

    this.HOTEL_POLICY_RESPONSE_BASE = new GatewayBaseResponse<>();
    this.HOTEL_POLICY_RESPONSE_BASE.setCode("Success");
    this.HOTEL_POLICY_RESPONSE_BASE.setData(Arrays.asList(this.HOTEL_POLICY_RESPONSE, this.HOTEL_POLICY_RESPONSE));
  }

  @Test
  public void getHotelRoomListTest() throws Exception{
    when(this.hotelAggregateEndPointService.getHotelRoomList(this.HEADER, this.QUERY)).thenReturn
        (CALL_HOTEL_POLICY_RESPONSE);
    when(this.CALL_HOTEL_POLICY_RESPONSE.execute()).thenReturn(Response.success(this.HOTEL_POLICY_RESPONSE_BASE));

    GatewayBaseResponse<List<Map<String, Object>>> hotelPolicy = this.hotelDetailOutboundService
        .getHotelRoomList(CommonTestVariable
            .MANDATORY_REQUEST, "1").blockingGet();

    verify(this.hotelAggregateEndPointService).getHotelRoomList(this.HEADER, this.QUERY);
  }

  @Test
  public void getHotelRoomListErrorTest() throws Exception{
    when(this.hotelAggregateEndPointService.getHotelRoomList(this.HEADER, this.QUERY)).thenReturn
        (null);
    try {
      this.hotelDetailOutboundService
          .getHotelRoomList(CommonTestVariable
              .MANDATORY_REQUEST, "1").blockingGet();
    } catch (Exception e){
      verify(this.hotelAggregateEndPointService).getHotelRoomList(this.HEADER, this.QUERY);
    }
  }

  @Test
  public void getHotelPolicyTest() throws Exception{
    when(this.hotelAggregateEndPointService.getHotelPolicy(this.HEADER)).thenReturn
        (CALL_HOTEL_POLICY_RESPONSE);
    when(this.CALL_HOTEL_POLICY_RESPONSE.execute()).thenReturn(Response.success(this.HOTEL_POLICY_RESPONSE_BASE));

    GatewayBaseResponse<List<Map<String, Object>>> hotelPolicy = this.hotelDetailOutboundService
        .getHotelPolicy(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    verify(this.hotelAggregateEndPointService).getHotelPolicy(this.HEADER);
  }

  @Test
  public void getHotelPolicyErrorTest() throws Exception {
    when(this.hotelAggregateEndPointService.getHotelPolicy(this.HEADER)).thenReturn
        (null);
    try {
       this.hotelDetailOutboundService
          .getHotelPolicy(CommonTestVariable
              .MANDATORY_REQUEST).blockingGet();
    } catch (Exception e){
      verify(this.hotelAggregateEndPointService).getHotelPolicy(this.HEADER);
    }

  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelAggregateEndPointService);
  }

}
