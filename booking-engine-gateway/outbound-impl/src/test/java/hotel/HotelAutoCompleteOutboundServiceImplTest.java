package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelAutoCompleteOutboundServiceImpl;
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

public class HotelAutoCompleteOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  GatewayBaseResponse<List<Map<String, String>>> RESULT;

  @InjectMocks
  HotelAutoCompleteOutboundServiceImpl hotelAutoCompleteOutboundService;

  @Mock
  HotelScrappingEndPointService hotelScrappingEndPointService;

  @Mock
  Call<GatewayBaseResponse<List<Map<String, String>>>> RESPONSE;

  @Test
  public void getAutoCompleteHotelTest() throws Exception {

    when(this.hotelScrappingEndPointService.getAutoCompleteHotel(HEADER, QUERY)).thenReturn(RESPONSE);
    when(this.RESPONSE.execute()).thenReturn(Response.success(this.RESULT));

    GatewayBaseResponse<List<Map<String, String>>> autocompleteResponse = this.hotelAutoCompleteOutboundService
        .getAutoCompleteHotel(CommonTestVariable
            .MANDATORY_REQUEST, "1" , "tes").blockingGet();

    assertEquals(this.RESULT, autocompleteResponse);

    verify(this.hotelScrappingEndPointService).getAutoCompleteHotel(this.HEADER, QUERY);

  }

  @Test
  public void getAutoCompleteHotelTestErrorRequest() throws Exception {

    when(this.hotelScrappingEndPointService.getAutoCompleteHotel(HEADER, QUERY)).thenReturn(null);

    try {
      this.hotelAutoCompleteOutboundService
          .getAutoCompleteHotel(CommonTestVariable
              .MANDATORY_REQUEST, "1" , "tes").blockingGet();
    }catch (Exception e){
      verify(this.hotelScrappingEndPointService).getAutoCompleteHotel(HEADER, QUERY);
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
    this.QUERY.put("otaId", "1");
    this.QUERY.put("q", "tes");

    Map<String, String> data = new HashMap<>();
    data.put("tes", "tes");

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(Arrays.asList(data));
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelScrappingEndPointService);
  }

}
