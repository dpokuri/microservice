package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.PicRegionOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.PicRegionServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.PicRegionTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.PicRegionResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PicRegionServiceImplTest {

  @InjectMocks
  PicRegionServiceImpl picRegionService;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  PicRegionOutboundService picRegionOutboundService;

  @Test
  public void findPicRegionListTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.picRegionOutboundService.findPicRegionList(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.page,
        PicRegionTestVariable.limit,
        PicRegionTestVariable.q,
        PicRegionTestVariable.sort,
        PicRegionTestVariable.method
    )).thenReturn(Single.just(PicRegionTestVariable.RESULT_PAGE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<PicRegionResponse>> GatewayBaseResponse =
        this.picRegionService.findPicRegionList(
            CommonTestVariable.MANDATORY_REQUEST,
            PicRegionTestVariable.page,
            PicRegionTestVariable.limit,
            PicRegionTestVariable.q,
            PicRegionTestVariable.sort,
            PicRegionTestVariable.method,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(PicRegionTestVariable.RESULT_PAGE, GatewayBaseResponse);
    verify(this.picRegionOutboundService).findPicRegionList(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.page,
        PicRegionTestVariable.limit,
        PicRegionTestVariable.q,
        PicRegionTestVariable.sort,
        PicRegionTestVariable.method);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void findPicRegionByIdTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.picRegionOutboundService.findPicRegionById(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.ID
    )).thenReturn(Single.just(PicRegionTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<PicRegionResponse> GatewayBaseResponse =
        this.picRegionService.findPicRegionById(
            CommonTestVariable.MANDATORY_REQUEST,
            PicRegionTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(PicRegionTestVariable.RESULT, GatewayBaseResponse);

    verify(this.picRegionOutboundService).findPicRegionById(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void createPicRegionTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.picRegionOutboundService.createPicRegion(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.REQUEST
    )).thenReturn(Single.just(PicRegionTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.picRegionService.createPicRegion(
            CommonTestVariable.MANDATORY_REQUEST,
            PicRegionTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(PicRegionTestVariable.RESULT, GatewayBaseResponse);

    verify(this.picRegionOutboundService).createPicRegion(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void updatePicRegionTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.picRegionOutboundService.updatePicRegion(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.ID,
        PicRegionTestVariable.REQUEST
    )).thenReturn(Single.just(PicRegionTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.picRegionService.updatePicRegion(
            CommonTestVariable.MANDATORY_REQUEST,
            PicRegionTestVariable.ID,
            PicRegionTestVariable.REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(PicRegionTestVariable.RESULT, GatewayBaseResponse);

    verify(this.picRegionOutboundService).updatePicRegion(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.ID,
        PicRegionTestVariable.REQUEST
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void deletePicRegionByIdTest() throws Exception {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.picRegionOutboundService.deletePicRegionById(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.ID
    )).thenReturn(Single.just(PicRegionTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse =
        this.picRegionService.deletePicRegionById(
            CommonTestVariable.MANDATORY_REQUEST,
            PicRegionTestVariable.ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(PicRegionTestVariable.RESULT, GatewayBaseResponse);

    verify(this.picRegionOutboundService).deletePicRegionById(
        CommonTestVariable.MANDATORY_REQUEST,
        PicRegionTestVariable.ID
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
    verifyNoMoreInteractions(this.picRegionOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
