package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelMappingOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelMappingServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelMappingTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelMappingServiceImplTest {

  @InjectMocks
  HotelMappingServiceImpl hotelMappingService;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  HotelMappingOutboundService hotelMappingOutboundService;

  @Test
  public void findHotelMappingsByStoreId() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelMappingOutboundService.findHotelMappingsByStoreId(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.page,
        HotelMappingTestVariable.limit,
        HotelMappingTestVariable.q,
        HotelMappingTestVariable.regionId,
        HotelMappingTestVariable.sort,
        HotelMappingTestVariable.method
        )).thenReturn(Single.just(HotelMappingTestVariable.RESULT_PAGE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<HotelMappingResponse>> GatewayBaseResponse =
        this.hotelMappingService.findHotelMappingsByStoreId(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelMappingTestVariable.page,
            HotelMappingTestVariable.limit,
            HotelMappingTestVariable.q,
            HotelMappingTestVariable.regionId,
            HotelMappingTestVariable.sort,
            HotelMappingTestVariable.method,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelMappingTestVariable.RESULT_PAGE, GatewayBaseResponse);
    verify(this.hotelMappingOutboundService).findHotelMappingsByStoreId(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.page,
        HotelMappingTestVariable.limit,
        HotelMappingTestVariable.q,
        HotelMappingTestVariable.regionId,
        HotelMappingTestVariable.sort,
        HotelMappingTestVariable.method);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void findHotelMappingByStoreIdAndId() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelMappingOutboundService.findHotelMappingByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.ID
    )).thenReturn(Single.just(HotelMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelMappingResponse> GatewayBaseResponse =
        this.hotelMappingService.findHotelMappingByStoreIdAndId(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelMappingTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.hotelMappingOutboundService).findHotelMappingByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void createHotelMapping() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelMappingOutboundService.createHotelMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.REQUEST
    )).thenReturn(Single.just(HotelMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.hotelMappingService.createHotelMapping(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelMappingTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.hotelMappingOutboundService).createHotelMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void updateHotelMapping() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelMappingOutboundService.updateHotelMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.ID,
        HotelMappingTestVariable.REQUEST
    )).thenReturn(Single.just(HotelMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.hotelMappingService.updateHotelMapping(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelMappingTestVariable.ID,
            HotelMappingTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.hotelMappingOutboundService).updateHotelMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.ID,
        HotelMappingTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void deleteHotelMapping() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelMappingOutboundService.deleteHotelMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.ID
    )).thenReturn(Single.just(HotelMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.hotelMappingService.deleteHotelMapping(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelMappingTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.hotelMappingOutboundService).deleteHotelMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelMappingTestVariable.ID
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
    verifyNoMoreInteractions(this.hotelMappingOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }

}
