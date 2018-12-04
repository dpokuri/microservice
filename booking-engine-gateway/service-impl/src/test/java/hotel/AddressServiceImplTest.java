package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.AddressOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.AddressServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.AddressTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.MDC;

public class AddressServiceImplTest extends AddressTestVariable {

  @InjectMocks
  private AddressServiceImpl addressService;

  @Mock
  private AddressOutboundService addressOutboundService;

  @Mock
  private PrivilegeService privilegeService;

  @Test
  public void getCountry() {
    when(addressOutboundService.getCountry(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(addressResponseDummy));
    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Address>> countries = addressService
        .getCountry(CommonTestVariable.MANDATORY_REQUEST, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA)
        .blockingGet();

    assertEquals(addressResponseDummy, countries);
    verify(addressOutboundService).getCountry(CommonTestVariable.MANDATORY_REQUEST);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void getProvince() {
    when(addressOutboundService.getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID))
        .thenReturn(Single.just(addressResponseDummy));
    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Address>> provinces = addressService
        .getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA)
        .blockingGet();

    assertEquals(addressResponseDummy, provinces);
    verify(addressOutboundService).getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void getCity() {
    when(addressOutboundService.getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID))
        .thenReturn(Single.just(addressResponseDummy));
    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Address>> cities = addressService
        .getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA)
        .blockingGet();

    assertEquals(addressResponseDummy, cities);
    verify(addressOutboundService).getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void getArea() {
    when(addressOutboundService.getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID))
        .thenReturn(Single.just(addressResponseDummy));
    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Address>> areas = addressService
        .getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA)
        .blockingGet();

    assertEquals(addressResponseDummy, areas);
    verify(addressOutboundService).getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.addressOutboundService);
  }

}
