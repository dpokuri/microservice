package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelPromoAggregateOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelAggregateTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class HotelPromoAggregateOutboundServiceImplTest extends HotelAggregateTestVariable {

  @InjectMocks
  private HotelPromoAggregateOutboundServiceImpl hotelAggregateOutboundService;

  @Mock
  private HotelAggregateEndPointService hotelAggregateEndPointService;

  @Mock
  private Call<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>> responseCallPage;

  @Mock
  private Call<GatewayBaseResponse<HotelPromoAggregateResponse>> responseCall;

  @Mock
  private Call<GatewayBaseResponse> GatewayBaseResponseCall;

  @Test
  public void findAllHotelPromoByHotelIdAndRoomIdAndDateTest() throws Exception {
    when(hotelAggregateEndPointService
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.getMandatoryRequestAsMap(),
            findAllPromoParam.getFindAllPromoParamAsMap())).thenReturn(responseCallPage);
    when(responseCallPage.execute()).thenReturn(Response.success(
        hotelPromoAggregateResponsePageDummy));

    GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>
        hotelPromoAggregateResponse = hotelAggregateOutboundService
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
            findAllPromoParam).blockingGet();

    assertEquals(hotelPromoAggregateResponsePageDummy, hotelPromoAggregateResponse);
    verify(hotelAggregateEndPointService).findAllHotelPromoByHotelIdAndRoomIdAndDate(
        CommonTestVariable.getMandatoryRequestAsMap(),
        findAllPromoParam.getFindAllPromoParamAsMap());

  }

  @Test
  public void findAllHotelPromoByHotelIdAndRoomIdAndDateExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.getMandatoryRequestAsMap(),
            params)).thenReturn(null);
    when(responseCallPage.execute()).thenReturn(Response.success(null));

    try {
      hotelAggregateOutboundService
          .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
              findAllPromoParam).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.getMandatoryRequestAsMap(),
              findAllPromoParam.getFindAllPromoParamAsMap());
    }
  }

  @Test
  public void findHotelPromoByIdTest() throws Exception {
    when(hotelAggregateEndPointService
        .findHotelPromoById(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(hotelPromoAggregateResponseDummy));

    GatewayBaseResponse<HotelPromoAggregateResponse>
        hotelPromoAggregateResponse = hotelAggregateOutboundService
        .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    assertEquals(hotelPromoAggregateResponseDummy, hotelPromoAggregateResponse);
    verify(hotelAggregateEndPointService)
        .findHotelPromoById(CommonTestVariable.getMandatoryRequestAsMap(), ID);

  }

  @Test
  public void findHotelPromoByIdExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .findHotelPromoById(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelAggregateOutboundService
          .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .findHotelPromoById(CommonTestVariable.getMandatoryRequestAsMap(), ID);
    }
  }

  @Test
  public void createHotelPromoAggregateTest() throws Exception {
    when(hotelAggregateEndPointService
        .createHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(),
            hotelPromoAggregateRequest)).thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(hotelPromoAggregateResponseDummy));

    GatewayBaseResponse<HotelPromoAggregateResponse>
        hotelPromoAggregateResponse = hotelAggregateOutboundService.createHotelPromoAggregate(
        CommonTestVariable.MANDATORY_REQUEST, hotelPromoAggregateRequest).blockingGet();

    assertEquals(hotelPromoAggregateResponseDummy, hotelPromoAggregateResponse);
    verify(hotelAggregateEndPointService)
        .createHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(),
            hotelPromoAggregateRequest);

  }

  @Test
  public void createHotelPromoAggregateExceptionTest() throws Exception {
    when(hotelAggregateEndPointService.createHotelPromoAggregate(
        CommonTestVariable.getMandatoryRequestAsMap(), hotelPromoAggregateRequest))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelAggregateOutboundService
          .createHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST,
              hotelPromoAggregateRequest).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .createHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(),
              hotelPromoAggregateRequest);
    }
  }

  @Test
  public void updateHotelPromoAggregateTest() throws Exception {
    when(hotelAggregateEndPointService
        .updateHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID,
            hotelPromoAggregateRequest)).thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(hotelPromoAggregateResponseDummy));

    GatewayBaseResponse<HotelPromoAggregateResponse>
        hotelPromoAggregateResponse = hotelAggregateOutboundService
        .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            hotelPromoAggregateRequest).blockingGet();

    assertEquals(hotelPromoAggregateResponseDummy, hotelPromoAggregateResponse);
    verify(hotelAggregateEndPointService)
        .updateHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID,
            hotelPromoAggregateRequest);

  }

  @Test
  public void updateHotelPromoAggregateExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .updateHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID,
            hotelPromoAggregateRequest)).thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelAggregateOutboundService
          .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
              hotelPromoAggregateRequest).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .updateHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID,
              hotelPromoAggregateRequest);
    }
  }

  @Test
  public void deleteHotelPromoAggregateTest() throws Exception {
    when(hotelAggregateEndPointService
        .deleteHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(GatewayBaseResponseCall);
    when(GatewayBaseResponseCall.execute()).thenReturn(Response.success(CommonTestVariable.GATEWAY_BASE_RESPONSE));

    GatewayBaseResponse hotelPromoAggregateResponse = hotelAggregateOutboundService
        .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    assertEquals(CommonTestVariable.GATEWAY_BASE_RESPONSE, hotelPromoAggregateResponse);
    verify(hotelAggregateEndPointService)
        .deleteHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID);

  }

  @Test
  public void deleteHotelPromoAggregateExceptionTest() throws Exception {
    when(hotelAggregateEndPointService
        .deleteHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID))
        .thenReturn(null);
    when(GatewayBaseResponseCall.execute()).thenReturn(Response.success(null));

    try {
      hotelAggregateOutboundService
          .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();
    } catch (Exception e) {
      verify(hotelAggregateEndPointService)
          .deleteHotelPromoAggregate(CommonTestVariable.getMandatoryRequestAsMap(), ID);
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
