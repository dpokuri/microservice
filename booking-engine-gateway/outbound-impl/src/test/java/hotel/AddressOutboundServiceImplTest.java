package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelSubsidyEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.AddressOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.AddressTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class AddressOutboundServiceImplTest extends AddressTestVariable {

  @InjectMocks
  private AddressOutboundServiceImpl addressOutboundService;

  @Mock
  private HotelSubsidyEndPointService hotelSubsidyEndPointService;

  @Mock
  private Call<GatewayBaseResponse<List<Address>>> responseCall;

  @Test
  public void getCountry() throws Exception {
    when(hotelSubsidyEndPointService.callGetCountry(CommonTestVariable.getMandatoryRequestAsMap()))
        .thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    GatewayBaseResponse<List<Address>> countryResponse = addressOutboundService
        .getCountry(CommonTestVariable.MANDATORY_REQUEST).blockingGet();

    assertEquals(addressResponseDummy, countryResponse);
    verify(hotelSubsidyEndPointService)
        .callGetCountry(CommonTestVariable.getMandatoryRequestAsMap());
  }

  @Test
  public void getCountryException() throws Exception {
    when(hotelSubsidyEndPointService.callGetCountry(CommonTestVariable.getMandatoryRequestAsMap()))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    try {
      addressOutboundService.getCountry(CommonTestVariable.MANDATORY_REQUEST).blockingGet();
    } catch (Exception e) {
      verify(hotelSubsidyEndPointService)
          .callGetCountry(CommonTestVariable.getMandatoryRequestAsMap());
    }
  }

  @Test
  public void getProvince() throws Exception {
    when(hotelSubsidyEndPointService
        .callGetProvince(CommonTestVariable.getMandatoryRequestAsMap(), COUNTRY_ID))
        .thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    GatewayBaseResponse<List<Address>> countryResponse = addressOutboundService
        .getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID).blockingGet();

    assertEquals(addressResponseDummy, countryResponse);
    verify(hotelSubsidyEndPointService)
        .callGetProvince(CommonTestVariable.getMandatoryRequestAsMap(), COUNTRY_ID);
  }

  @Test
  public void getProvinceException() throws Exception {
    when(hotelSubsidyEndPointService
        .callGetProvince(CommonTestVariable.getMandatoryRequestAsMap(), COUNTRY_ID))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    try {
      addressOutboundService.getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID)
          .blockingGet();
    } catch (Exception e) {
      verify(hotelSubsidyEndPointService)
          .callGetProvince(CommonTestVariable.getMandatoryRequestAsMap(), COUNTRY_ID);
    }
  }

  @Test
  public void getCity() throws Exception {
    when(hotelSubsidyEndPointService
        .callGetCity(CommonTestVariable.getMandatoryRequestAsMap(), PROVINCE_ID))
        .thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    GatewayBaseResponse<List<Address>> provinceResponse = addressOutboundService
        .getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID).blockingGet();

    assertEquals(addressResponseDummy, provinceResponse);
    verify(hotelSubsidyEndPointService)
        .callGetCity(CommonTestVariable.getMandatoryRequestAsMap(), PROVINCE_ID);
  }

  @Test
  public void getCityException() throws Exception {
    when(hotelSubsidyEndPointService
        .callGetCity(CommonTestVariable.getMandatoryRequestAsMap(), PROVINCE_ID))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    try {
      addressOutboundService.getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID)
          .blockingGet();
    } catch (Exception e) {
      verify(hotelSubsidyEndPointService)
          .callGetCity(CommonTestVariable.getMandatoryRequestAsMap(), PROVINCE_ID);
    }
  }

  @Test
  public void getArea() throws Exception {
    when(hotelSubsidyEndPointService
        .callGetArea(CommonTestVariable.getMandatoryRequestAsMap(), CITY_ID))
        .thenReturn(responseCall);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    GatewayBaseResponse<List<Address>> areaResponse = addressOutboundService
        .getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID).blockingGet();

    assertEquals(addressResponseDummy, areaResponse);
    verify(hotelSubsidyEndPointService)
        .callGetArea(CommonTestVariable.getMandatoryRequestAsMap(), CITY_ID);
  }

  @Test
  public void getAreaException() throws Exception {
    when(hotelSubsidyEndPointService
        .callGetArea(CommonTestVariable.getMandatoryRequestAsMap(), CITY_ID))
        .thenReturn(null);
    when(responseCall.execute()).thenReturn(Response.success(addressResponseDummy));

    try {
      addressOutboundService.getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID)
          .blockingGet();
    } catch (Exception e) {
      verify(hotelSubsidyEndPointService)
          .callGetArea(CommonTestVariable.getMandatoryRequestAsMap(), CITY_ID);
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
