package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.HotelPromoConfigOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelPromoConfigTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class HotelPromoConfigOutboundServiceImplTest extends HotelPromoConfigTestVariable {

  @InjectMocks
  private HotelPromoConfigOutboundServiceImpl hotelPromoConfigOutboundService;

  @Mock
  private HotelAggregateEndPointService endPointService;

  @Mock
  private Call<GatewayBaseResponse<Object>> responseCall;

  @Test
  public void create() throws Exception {
    when(endPointService
        .createHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
            HOTEL_PROMO_CONFIG_REQUEST)).thenReturn(responseCall);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigOutboundService
        .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(endPointService)
        .createHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
            HOTEL_PROMO_CONFIG_REQUEST);
  }

  @Test
  public void createException() throws Exception {
    when(endPointService
        .createHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
            HOTEL_PROMO_CONFIG_REQUEST)).thenReturn(null);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    try {
      hotelPromoConfigOutboundService
          .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(endPointService)
          .createHotelPromoConfig(MandatoryRequestHelper
                  .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
              HOTEL_PROMO_CONFIG_REQUEST);
    }
  }

  @Test
  public void update() throws Exception {
    when(endPointService
        .updateHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID,
            HOTEL_PROMO_CONFIG_REQUEST)).thenReturn(responseCall);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigOutboundService
        .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(endPointService).updateHotelPromoConfig(MandatoryRequestHelper
            .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID,
        HOTEL_PROMO_CONFIG_REQUEST);
  }

  @Test
  public void updateException() throws Exception {
    when(endPointService
        .updateHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID,
            HOTEL_PROMO_CONFIG_REQUEST)).thenReturn(null);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    try {
      hotelPromoConfigOutboundService
          .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST)
          .blockingGet();
    } catch (Exception e) {
      verify(endPointService)
          .updateHotelPromoConfig(MandatoryRequestHelper
                  .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID,
              HOTEL_PROMO_CONFIG_REQUEST);
    }
  }

  @Test
  public void delete() throws Exception {
    when(endPointService
        .deleteHotelPromoConfig(MandatoryRequestHelper
            .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID))
        .thenReturn(responseCall);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigOutboundService
        .delete(CommonTestVariable.MANDATORY_REQUEST, ID).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(endPointService).deleteHotelPromoConfig(MandatoryRequestHelper
        .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID);
  }

  @Test
  public void deleteException() throws Exception {
    when(endPointService
        .deleteHotelPromoConfig(MandatoryRequestHelper
            .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID))
        .thenReturn(null);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    try {
      hotelPromoConfigOutboundService
          .delete(CommonTestVariable.MANDATORY_REQUEST, ID)
          .blockingGet();
    } catch (Exception e) {
      verify(endPointService)
          .deleteHotelPromoConfig(MandatoryRequestHelper
              .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), ID);
    }
  }

  @Test
  public void getOne() throws Exception {
    when(endPointService
        .getOneHotelPromoConfig(MandatoryRequestHelper
            .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), HOTEL_ID))
        .thenReturn(responseCall);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigOutboundService
        .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(endPointService).getOneHotelPromoConfig(MandatoryRequestHelper
        .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), HOTEL_ID);
  }

  @Test
  public void getOneException() throws Exception {
    when(endPointService
        .getOneHotelPromoConfig(MandatoryRequestHelper
            .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), HOTEL_ID))
        .thenReturn(null);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    try {
      hotelPromoConfigOutboundService
          .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID)
          .blockingGet();
    } catch (Exception e) {
      verify(endPointService)
          .getOneHotelPromoConfig(MandatoryRequestHelper
              .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST), HOTEL_ID);
    }
  }

  @Test
  public void getAll() throws Exception {
    when(endPointService
        .getAllHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
            getHotelHotelPromoConfigFindAllParamAsMap())).thenReturn(responseCall);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigOutboundService
        .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS)
        .blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(endPointService).getAllHotelPromoConfig(MandatoryRequestHelper
            .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
        getHotelHotelPromoConfigFindAllParamAsMap());
  }

  @Test
  public void getAllException() throws Exception {
    when(endPointService
        .getAllHotelPromoConfig(MandatoryRequestHelper
                .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
            getHotelHotelPromoConfigFindAllParamAsMap()))
        .thenReturn(null);

    when(responseCall.execute()).thenReturn(Response.success(HOTEL_PROMO_CONFIG_RESPONSE));

    try {
      hotelPromoConfigOutboundService
          .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS)
          .blockingGet();
    } catch (Exception e) {
      verify(endPointService)
          .getAllHotelPromoConfig(MandatoryRequestHelper
                  .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST),
              getHotelHotelPromoConfigFindAllParamAsMap());
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(endPointService);
  }


}
