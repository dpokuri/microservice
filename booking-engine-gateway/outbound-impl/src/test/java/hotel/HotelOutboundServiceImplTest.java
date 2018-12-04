package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelSubsidyEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class HotelOutboundServiceImplTest extends HotelTestVariable {

  @InjectMocks
  private HotelOutboundServiceImpl hotelOutboundService;

  @Mock
  private HotelSubsidyEndPointService hotelSubsidyEndPointService;

  @Mock
  private Call<GatewayBaseResponse<List<Map<String, String>>>> responseCallList;

  @Test
  public void findHotelByAddressTest() throws Exception {
    when(hotelSubsidyEndPointService
        .findHotelByAddress(CommonTestVariable.getMandatoryRequestAsMap(),
            FIND_HOTEL_BY_ADDRESS_PARAM.getFindHotelByAddressAsMap())).thenReturn(responseCallList);
    when(responseCallList.execute())
        .thenReturn(Response.success(HOTEL_RESPONSE_DUMMY));

    GatewayBaseResponse<List<Map<String, String>>> hotelResponse =
        hotelOutboundService
            .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_ADDRESS_PARAM)
            .blockingGet();

    assertEquals(HOTEL_RESPONSE_DUMMY, hotelResponse);
    verify(hotelSubsidyEndPointService).findHotelByAddress(
        CommonTestVariable.getMandatoryRequestAsMap(),
        FIND_HOTEL_BY_ADDRESS_PARAM.getFindHotelByAddressAsMap());
  }

  @Test
  public void findHotelByAddressExceptionTest() throws Exception {
    when(hotelSubsidyEndPointService
        .findHotelByAddress(CommonTestVariable.getMandatoryRequestAsMap(),
            FIND_HOTEL_BY_ADDRESS_PARAM.getFindHotelByAddressAsMap())).thenReturn(null);
    when(responseCallList.execute()).thenReturn(Response.success(null));

    try {
      hotelOutboundService
          .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST,
              FIND_HOTEL_BY_ADDRESS_PARAM).blockingGet();
    } catch (Exception e) {
      verify(hotelSubsidyEndPointService).findHotelByAddress(
          CommonTestVariable.getMandatoryRequestAsMap(),
          FIND_HOTEL_BY_ADDRESS_PARAM.getFindHotelByAddressAsMap());
    }
  }

  @Test
  public void findHotelNameByHotelIdsTest() throws Exception {
    when(hotelSubsidyEndPointService
        .findHotelNameByHotelIds(CommonTestVariable.getMandatoryRequestAsMap(),
            FIND_HOTEL_BY_HOTEL_ID_PARAM))
        .thenReturn(responseCallList);
    when(responseCallList.execute())
        .thenReturn(Response.success(HOTEL_RESPONSE_DUMMY));

    GatewayBaseResponse<List<Map<String, String>>> hotelResponse =
        hotelOutboundService.findHotelNameByHotelIds(
            CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_HOTEL_ID_PARAM)
            .blockingGet();

    assertEquals(HOTEL_RESPONSE_DUMMY, hotelResponse);
    verify(hotelSubsidyEndPointService).findHotelNameByHotelIds(
        CommonTestVariable.getMandatoryRequestAsMap(), FIND_HOTEL_BY_HOTEL_ID_PARAM);
  }

  @Test
  public void findHotelNameByHotelIdsExceptionTest() throws Exception {
    when(hotelSubsidyEndPointService
        .findHotelNameByHotelIds(CommonTestVariable.getMandatoryRequestAsMap(),
            FIND_HOTEL_BY_HOTEL_ID_PARAM)).thenReturn(null);
    when(responseCallList.execute()).thenReturn(Response.success(null));

    try {
      hotelOutboundService.findHotelNameByHotelIds(
          CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_HOTEL_ID_PARAM)
          .blockingGet();
    } catch (Exception e) {
      verify(hotelSubsidyEndPointService).findHotelNameByHotelIds(
          CommonTestVariable.getMandatoryRequestAsMap(), FIND_HOTEL_BY_HOTEL_ID_PARAM);
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelSubsidyEndPointService);
  }

}
