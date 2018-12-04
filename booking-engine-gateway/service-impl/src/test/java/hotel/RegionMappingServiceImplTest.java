package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.RegionMappingOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.RegionMappingServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.RegionMappingTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class RegionMappingServiceImplTest {

  @InjectMocks
  RegionMappingServiceImpl regionMappingService;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  RegionMappingOutboundService regionMappingOutboundService;

  @Test
  public void findRegionMappingsByStoreIdTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.regionMappingOutboundService.findRegionMappingsByStoreId(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.page,
        RegionMappingTestVariable.limit,
        RegionMappingTestVariable.q,
        RegionMappingTestVariable.sort,
        RegionMappingTestVariable.method
    )).thenReturn(Single.just(RegionMappingTestVariable.RESULT_PAGE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<RegionMappingResponse>> GatewayBaseResponse =
        this.regionMappingService.findRegionMappingsByStoreId(
            CommonTestVariable.MANDATORY_REQUEST,
            RegionMappingTestVariable.page,
            RegionMappingTestVariable.limit,
            RegionMappingTestVariable.q,
            RegionMappingTestVariable.sort,
            RegionMappingTestVariable.method,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(RegionMappingTestVariable.RESULT_PAGE, GatewayBaseResponse);
    verify(this.regionMappingOutboundService).findRegionMappingsByStoreId(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.page,
        RegionMappingTestVariable.limit,
        RegionMappingTestVariable.q,
        RegionMappingTestVariable.sort,
        RegionMappingTestVariable.method);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void findRegionMappingByStoreIdAndIdTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.regionMappingOutboundService.findRegionMappingByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID
    )).thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RegionMappingResponse> GatewayBaseResponse =
        this.regionMappingService.findRegionMappingByStoreIdAndId(
            CommonTestVariable.MANDATORY_REQUEST,
            RegionMappingTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(RegionMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.regionMappingOutboundService).findRegionMappingByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void createPicRegionTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.regionMappingOutboundService.createRegionMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.REQUEST
    )).thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.regionMappingService.createRegionMapping(
            CommonTestVariable.MANDATORY_REQUEST,
            RegionMappingTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(RegionMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.regionMappingOutboundService).createRegionMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void updatePicRegionTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.regionMappingOutboundService.updateRegionMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID,
        RegionMappingTestVariable.REQUEST
    )).thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.regionMappingService.updateRegionMapping(
            CommonTestVariable.MANDATORY_REQUEST,
            RegionMappingTestVariable.ID,
            RegionMappingTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(RegionMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.regionMappingOutboundService).updateRegionMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID,
        RegionMappingTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void deletePicRegionByIdTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.regionMappingOutboundService.deleteRegionMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID
    )).thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.regionMappingService.deleteRegionMapping(
            CommonTestVariable.MANDATORY_REQUEST,
            RegionMappingTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(RegionMappingTestVariable.RESULT, GatewayBaseResponse);

    verify(this.regionMappingOutboundService).deleteRegionMapping(
        CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID
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
    verifyNoMoreInteractions(this.regionMappingOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }

}
