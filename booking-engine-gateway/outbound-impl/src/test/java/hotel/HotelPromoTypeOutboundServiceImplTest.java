package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelPromoTypeOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelPromoTypeTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class HotelPromoTypeOutboundServiceImplTest extends HotelPromoTypeTestVariable {

  @InjectMocks
  private HotelPromoTypeOutboundServiceImpl hotelPromoTypeOutboundService;

  @Mock
  private HotelAggregateEndPointService hotelAggregateEndPointService;

  @Mock
  private Call<GatewayBaseResponse<List<HotelPromoTypeResponse>>> responseCallList;

  @Mock
  private Call<GatewayBaseResponse<HotelPromoTypeResponse>> responseCall;

  @Mock
  private Call<GatewayBaseResponse> GatewayBaseResponseCall;

  @Test
  public void findAllHotelPromoTypeTest() throws Exception {
    when(hotelAggregateEndPointService
        .findAllHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap()))
        .thenReturn(responseCallList);
    when(responseCallList.execute())
        .thenReturn(Response.success(HOTEL_PROMO_TYPE_RESPONSE_LIST_DUMMY));

    GatewayBaseResponse<List<HotelPromoTypeResponse>> hotelPromoTypeResponse =
        hotelPromoTypeOutboundService.findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST)
            .blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_LIST_DUMMY, hotelPromoTypeResponse);
    verify(hotelAggregateEndPointService).findAllHotelPromoType(
        CommonTestVariable.getMandatoryRequestAsMap());
  }

  @Test
  public void findAllHotelPromoTypeExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .findAllHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap())).thenReturn(null);
    when(responseCallList.execute()).thenReturn(Response.success(null));

    try {
      hotelPromoTypeOutboundService
          .findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService).findAllHotelPromoType(
          CommonTestVariable.getMandatoryRequestAsMap());
    }
  }

  @Test
  public void findHotelPromoTypeByIdTest() throws Exception {
    when(hotelAggregateEndPointService
        .findHotelPromoTypeById(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    GatewayBaseResponse<HotelPromoTypeResponse>
        hotelPromoTypeResponse = hotelPromoTypeOutboundService
        .findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelAggregateEndPointService).findHotelPromoTypeById(
        CommonTestVariable.getMandatoryRequestAsMap(), ID);
  }

  @Test
  public void findHotelPromoTypeByIdExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .findHotelPromoTypeById(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelPromoTypeOutboundService
          .findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService).findHotelPromoTypeById(
          CommonTestVariable.getMandatoryRequestAsMap(), ID);
    }
  }

  @Test
  public void createHotelPromoTypeTest() throws Exception {
    when(hotelAggregateEndPointService
        .createHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(),
            HOTEL_PROMO_TYPE_REQUEST_DUMMY)).thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    GatewayBaseResponse<HotelPromoTypeResponse>
        hotelPromoTypeResponse = hotelPromoTypeOutboundService
        .createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_TYPE_REQUEST_DUMMY)
        .blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelAggregateEndPointService).createHotelPromoType(
        CommonTestVariable.getMandatoryRequestAsMap(), HOTEL_PROMO_TYPE_REQUEST_DUMMY);
  }

  @Test
  public void createHotelPromoTypeExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .createHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(),
            HOTEL_PROMO_TYPE_REQUEST_DUMMY)).thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelPromoTypeOutboundService
          .createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST,
              HOTEL_PROMO_TYPE_REQUEST_DUMMY).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .createHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(),
              HOTEL_PROMO_TYPE_REQUEST_DUMMY);
    }
  }

  @Test
  public void updateHotelPromoTypeTest() throws Exception {
    when(hotelAggregateEndPointService
        .updateHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(), ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY)).thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    GatewayBaseResponse<HotelPromoTypeResponse>
        hotelPromoTypeResponse = hotelPromoTypeOutboundService
        .updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelAggregateEndPointService).updateHotelPromoType(
        CommonTestVariable.getMandatoryRequestAsMap(), ID, HOTEL_PROMO_TYPE_REQUEST_DUMMY);
  }

  @Test
  public void updateHotelPromoTypeExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .updateHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(), ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY)).thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelPromoTypeOutboundService
          .updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
              HOTEL_PROMO_TYPE_REQUEST_DUMMY).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .updateHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(), ID,
              HOTEL_PROMO_TYPE_REQUEST_DUMMY);
    }
  }

  @Test
  public void deleteHotelPromoTypeTest() throws Exception {
    when(hotelAggregateEndPointService
        .deleteHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(GatewayBaseResponseCall);
    when(GatewayBaseResponseCall.execute())
        .thenReturn(Response.success(HOTEL_PROMO_TYPE_BASE_RESPONSE_DUMMY));

    GatewayBaseResponse hotelPromoTypeResponse = hotelPromoTypeOutboundService
        .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_BASE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelAggregateEndPointService).deleteHotelPromoType(
        CommonTestVariable.getMandatoryRequestAsMap(), ID);
  }

  @Test
  public void deletePromoTypeByIdExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .deleteHotelPromoType(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(null);
    when(GatewayBaseResponseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelPromoTypeOutboundService
          .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService).deleteHotelPromoType(
          CommonTestVariable.getMandatoryRequestAsMap(), ID);
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelAggregateEndPointService);
  }

}
