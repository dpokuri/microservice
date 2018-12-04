package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelAggregateEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.AdditionalDiscountOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.AdditionalDiscountTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class AdditionalDiscountOutboundServiceImplTest {

  @InjectMocks
  private AdditionalDiscountOutboundServiceImpl additionalDiscountOutboundService;

  @Mock
  private HotelAggregateEndPointService hotelAggregateEndPointService;

  @Mock
  private Call<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> RESPONSE_PAGE;

  @Mock
  private Call<GatewayBaseResponse<AdditionalDiscountResponse>> RESPONSE;

  @Test
  public void createAdditionalDiscountTest() throws IOException {
    when(this.hotelAggregateEndPointService.createAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.REQUEST
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    GatewayBaseResponse additionalDiscount = this.additionalDiscountOutboundService
        .createAdditionalDiscount(CommonTestVariable.MANDATORY_REQUEST,
            AdditionalDiscountTestVariable.REQUEST).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, additionalDiscount);

    verify(this.hotelAggregateEndPointService).createAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(), AdditionalDiscountTestVariable.REQUEST
    );
  }

  @Test
  public void createAdditionalDiscountxceptionTest() throws IOException {
    when(this.hotelAggregateEndPointService.createAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.REQUEST
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    try {
      this.additionalDiscountOutboundService
          .createAdditionalDiscount(CommonTestVariable.MANDATORY_REQUEST,
              AdditionalDiscountTestVariable.REQUEST).blockingGet();
    } catch (Exception e) {
      verify(this.hotelAggregateEndPointService).createAdditionalDiscount(
          CommonTestVariable.getMandatoryRequestAsMap(), AdditionalDiscountTestVariable.REQUEST
      );
    }
  }

  @Test
  public void updateAdditionalDiscountTest() throws IOException {
    when(this.hotelAggregateEndPointService.updateAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.REQUEST,
        AdditionalDiscountTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    GatewayBaseResponse additionalDiscount = this.additionalDiscountOutboundService
        .updateAdditionalDiscount(
            CommonTestVariable.MANDATORY_REQUEST,
            AdditionalDiscountTestVariable.REQUEST,
            AdditionalDiscountTestVariable.ID).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, additionalDiscount);

    verify(this.hotelAggregateEndPointService).updateAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.REQUEST,
        AdditionalDiscountTestVariable.ID
    );
  }

  @Test
  public void updateAdditionalDiscountExceptionTest() throws IOException {
    when(this.hotelAggregateEndPointService.updateAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.REQUEST,
        AdditionalDiscountTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    try {
      this.additionalDiscountOutboundService
          .updateAdditionalDiscount(
              CommonTestVariable.MANDATORY_REQUEST,
              AdditionalDiscountTestVariable.REQUEST,
              AdditionalDiscountTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.hotelAggregateEndPointService).updateAdditionalDiscount(
          CommonTestVariable.getMandatoryRequestAsMap(),
          AdditionalDiscountTestVariable.REQUEST,
          AdditionalDiscountTestVariable.ID
      );
    }
  }

  @Test
  public void deleteAdditionalDiscountTest() throws IOException {
    when(this.hotelAggregateEndPointService.deleteAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    GatewayBaseResponse additionalDiscount = this.additionalDiscountOutboundService
        .deleteAdditionalDiscount(
            CommonTestVariable.MANDATORY_REQUEST,
            AdditionalDiscountTestVariable.ID).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, additionalDiscount);

    verify(this.hotelAggregateEndPointService).deleteAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.ID
    );
  }

  @Test
  public void deleteAdditionalDiscountExceptionTest() throws IOException {
    when(this.hotelAggregateEndPointService.deleteAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    try {
      this.additionalDiscountOutboundService
          .deleteAdditionalDiscount(
              CommonTestVariable.MANDATORY_REQUEST,
              AdditionalDiscountTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.hotelAggregateEndPointService).deleteAdditionalDiscount(
          CommonTestVariable.getMandatoryRequestAsMap(),
          AdditionalDiscountTestVariable.ID
      );
    }
  }

  @Test
  public void findAdditionalDiscountByHotelTest() throws IOException {
    when(this.hotelAggregateEndPointService.findAdditionalDiscountByHotel(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.HOTEL_ID,
        AdditionalDiscountTestVariable.TYPE
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    GatewayBaseResponse additionalDiscount = this.additionalDiscountOutboundService
        .findAdditionalDiscountByHotel(
            CommonTestVariable.MANDATORY_REQUEST,
            AdditionalDiscountTestVariable.HOTEL_ID,
            AdditionalDiscountTestVariable.TYPE).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, additionalDiscount);

    verify(this.hotelAggregateEndPointService).findAdditionalDiscountByHotel(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.HOTEL_ID,
        AdditionalDiscountTestVariable.TYPE
    );
  }

  @Test
  public void findAdditionalDiscountByHotelExceptionTest() throws IOException {
    when(this.hotelAggregateEndPointService.findAdditionalDiscountByHotel(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.HOTEL_ID,
        AdditionalDiscountTestVariable.TYPE
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    try {
      this.additionalDiscountOutboundService
          .findAdditionalDiscountByHotel(
              CommonTestVariable.MANDATORY_REQUEST,
              AdditionalDiscountTestVariable.HOTEL_ID,
              AdditionalDiscountTestVariable.TYPE).blockingGet();
    } catch (Exception e) {
      verify(this.hotelAggregateEndPointService).findAdditionalDiscountByHotel(
          CommonTestVariable.getMandatoryRequestAsMap(),
          AdditionalDiscountTestVariable.HOTEL_ID,
          AdditionalDiscountTestVariable.TYPE
      );
    }
  }

  @Test
  public void findAdditionalDiscountByIdTest() throws IOException {
    when(this.hotelAggregateEndPointService.findAdditionalDiscountById(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    GatewayBaseResponse additionalDiscount = this.additionalDiscountOutboundService
        .findAdditionalDiscountById(
            CommonTestVariable.MANDATORY_REQUEST,
            AdditionalDiscountTestVariable.ID).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, additionalDiscount);

    verify(this.hotelAggregateEndPointService).findAdditionalDiscountById(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.ID
    );
  }

  @Test
  public void findAdditionalDiscountByIdExceptionTest() throws IOException {
    when(this.hotelAggregateEndPointService.findAdditionalDiscountById(
        CommonTestVariable.getMandatoryRequestAsMap(),
        AdditionalDiscountTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT));

    try {
      this.additionalDiscountOutboundService
          .findAdditionalDiscountById(
              CommonTestVariable.MANDATORY_REQUEST,
              AdditionalDiscountTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.hotelAggregateEndPointService).findAdditionalDiscountById(
          CommonTestVariable.getMandatoryRequestAsMap(),
          AdditionalDiscountTestVariable.ID
      );
    }
  }

  @Test
  public void findAdditionalDiscountTest() throws IOException {
    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", AdditionalDiscountTestVariable.PAGE.toString());
    queryParam.put("limit", AdditionalDiscountTestVariable.LIMIT.toString());
    queryParam.put("type", AdditionalDiscountTestVariable.TYPE);

    when(this.hotelAggregateEndPointService.findAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        queryParam
    )).thenReturn(RESPONSE_PAGE);

    when(RESPONSE_PAGE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT_PAGE));

    GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>> additionalDiscount =
        this.additionalDiscountOutboundService
            .findAdditionalDiscount(
                CommonTestVariable.MANDATORY_REQUEST,
                AdditionalDiscountTestVariable.PAGE,
                AdditionalDiscountTestVariable.LIMIT,
                AdditionalDiscountTestVariable.TYPE,
                "").blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT_PAGE, additionalDiscount);

    verify(this.hotelAggregateEndPointService).findAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        queryParam
    );
  }

  @Test
  public void findAdditionalDiscountExceptionTest() throws IOException {
    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", AdditionalDiscountTestVariable.PAGE.toString());
    queryParam.put("limit", AdditionalDiscountTestVariable.LIMIT.toString());
    queryParam.put("type", AdditionalDiscountTestVariable.TYPE);
    queryParam.put("hotelId", AdditionalDiscountTestVariable.HOTEL_ID.toString());

    when(this.hotelAggregateEndPointService.findAdditionalDiscount(
        CommonTestVariable.getMandatoryRequestAsMap(),
        queryParam
    )).thenReturn(null);

    when(RESPONSE_PAGE.execute()).thenReturn(
        Response.success(AdditionalDiscountTestVariable.RESULT_PAGE));

    try {
      this.additionalDiscountOutboundService
          .findAdditionalDiscount(
              CommonTestVariable.MANDATORY_REQUEST,
              AdditionalDiscountTestVariable.PAGE,
              AdditionalDiscountTestVariable.LIMIT,
              AdditionalDiscountTestVariable.TYPE,
              AdditionalDiscountTestVariable.HOTEL_ID.toString()).blockingGet();
    } catch (Exception e) {
      verify(this.hotelAggregateEndPointService).findAdditionalDiscount(
          CommonTestVariable.getMandatoryRequestAsMap(),
          queryParam
      );
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
