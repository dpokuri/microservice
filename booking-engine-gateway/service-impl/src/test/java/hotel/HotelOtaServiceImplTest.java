package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelOtaOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelOtaServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelOtaTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.OtaResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelOtaServiceImplTest {

  @InjectMocks
  HotelOtaServiceImpl hotelOtaService;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  HotelOtaOutboundService hotelOtaOutboundService;

  @Test
  public void findHotelOtaTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelOtaOutboundService.findHotelOta(
        CommonTestVariable.MANDATORY_REQUEST
    )).thenReturn(Single.just(HotelOtaTestVariable.RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<OtaResponse>> GatewayBaseResponse =
        this.hotelOtaService.findHotelOta(
            CommonTestVariable.MANDATORY_REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelOtaTestVariable.RESPONSE_LIST, GatewayBaseResponse);
    verify(this.hotelOtaOutboundService).findHotelOta(
        CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void findHotelMappingByStoreIdAndId() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelOtaOutboundService.findHotelOtaById(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID
    )).thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<OtaResponse> GatewayBaseResponse =
        this.hotelOtaService.findHotelOtaById(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelOtaTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelOtaTestVariable.RESPONSE, GatewayBaseResponse);

    verify(this.hotelOtaOutboundService).findHotelOtaById(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void createHotelMapping() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelOtaOutboundService.createHotelOta(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.REQUEST
    )).thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.hotelOtaService.createHotelOta(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelOtaTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelOtaTestVariable.RESPONSE, GatewayBaseResponse);

    verify(this.hotelOtaOutboundService).createHotelOta(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void updateHotelMapping() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelOtaOutboundService.updateHotelOta(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID,
        HotelOtaTestVariable.REQUEST
    )).thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.hotelOtaService.updateHotelOta(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelOtaTestVariable.ID,
            HotelOtaTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelOtaTestVariable.RESPONSE, GatewayBaseResponse);

    verify(this.hotelOtaOutboundService).updateHotelOta(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID,
        HotelOtaTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void deleteHotelMapping() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelOtaOutboundService.deleteHotelOta(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID
    )).thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.hotelOtaService.deleteHotelOta(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelOtaTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelOtaTestVariable.RESPONSE, GatewayBaseResponse);

    verify(this.hotelOtaOutboundService).deleteHotelOta(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelOtaOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }

}
