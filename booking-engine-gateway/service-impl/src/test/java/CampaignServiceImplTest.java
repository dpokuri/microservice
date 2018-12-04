import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.CampaignOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.CampaignServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.CampaignColumn;
import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.CampaignTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CampaignServiceImplTest {

  @InjectMocks
  CampaignServiceImpl campaignServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  CampaignOutboundService campaignOutboundService;

  @Test
  public void findCampaignFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.campaignOutboundService.findCampaignFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, CampaignTestVariable.NAME, CampaignStatus.ACTIVE, CampaignTestVariable.PAGE, CampaignTestVariable.SIZE,
        CampaignColumn.ID, SortDirection.ASC, CampaignTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(CampaignTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<CampaignResponse>> baseResponse =  this.campaignServiceImpl
        .findCampaignFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.NAME, CampaignStatus.ACTIVE, CampaignTestVariable.PAGE, CampaignTestVariable.SIZE, CampaignColumn.ID, SortDirection.ASC,
        CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(CampaignTestVariable.RESULT, baseResponse);
    verify(this.campaignOutboundService).findCampaignFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.NAME, CampaignStatus.ACTIVE, CampaignTestVariable.PAGE, CampaignTestVariable.SIZE,
        CampaignColumn.ID, SortDirection.ASC, CampaignTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findCampaignByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.campaignOutboundService.findCampaignById(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID)).thenReturn(Single.just
        (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .findCampaignById(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.ID,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES);

    verify(this.campaignOutboundService).findCampaignById(CommonTestVariable
        .MANDATORY_REQUEST, CampaignTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);

  }

  @Test
  public void createCampaignTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.campaignOutboundService.createCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.CAMPAIGN_REQUEST)).thenReturn
        (Single.just
            (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .createCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.CAMPAIGN_REQUEST,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.campaignOutboundService).createCampaign(CommonTestVariable
        .MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updateCampaignTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.campaignOutboundService.updateCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID
            )).thenReturn
        (Single.just
        (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .updateCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.CAMPAIGN_REQUEST,
            CampaignTestVariable.ID,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES);
    verify(this.campaignOutboundService).updateCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }

  @Test
  public void deleteCampaignTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.campaignOutboundService.deleteCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.CAMPAIGN_RESPONSE.getId())).thenReturn(Single.just
        (CampaignTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.campaignServiceImpl
        .deleteCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.CAMPAIGN_RESPONSE.getId(),
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.campaignOutboundService).deleteCampaign(CommonTestVariable
        .MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_RESPONSE.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }

  @Test
  public void updateCampaignToActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.campaignOutboundService.updateStatusActivateCampaign(CommonTestVariable.MANDATORY_REQUEST,
         CampaignTestVariable.ID
    )).thenReturn
        (Single.just
            (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .updateStatusActivateCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.ID,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.campaignOutboundService).updateStatusActivateCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.ACTIVATE_CAMPAIGN, CampaignTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }


  @Test
  public void updateCampaignToInActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UNACTIVATE_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.campaignOutboundService.updateStatusUnActivateCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID
    )).thenReturn
        (Single.just
            (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .updateStatusUnActivateCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.ID,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.campaignOutboundService).updateStatusUnActivateCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UNACTIVATE_CAMPAIGN, CampaignTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }


  @Test
  public void updateCampaignToPendingTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.PENDING_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.campaignOutboundService.updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID
    )).thenReturn
        (Single.just
            (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .updateStatusPendingCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.ID,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.campaignOutboundService).updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.PENDING_CAMPAIGN, CampaignTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }

  @Test
  public void updateCampaignToRejectTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.REJECTED_CAMPAIGN, CampaignTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.campaignOutboundService.updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID
    )).thenReturn
        (Single.just
            (CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<CampaignResponse> baseResponse =  this.campaignServiceImpl
        .updateStatusRejectedCampaign(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.ID,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().getId());
    verify(this.campaignOutboundService).updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.REJECTED_CAMPAIGN, CampaignTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }

  @Test
  public void findCampaignActivateTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_CAMPAIGN_ACTIVATE, CampaignTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.campaignOutboundService.findCampaignActivate(CommonTestVariable.MANDATORY_REQUEST)).thenReturn
        (Single.just
            (CampaignTestVariable.CAMPAIGN_DROPDOWN_RESPONSE_LIST));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES)).thenReturn(Single.just(CampaignTestVariable
        .PRIVILEGE_RESPONSE));
    GatewayBaseResponse<List<CampaignDropdownResponse>> baseResponse =  this.campaignServiceImpl
        .findCampaignActivate(
            CommonTestVariable.MANDATORY_REQUEST,
            CampaignTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CampaignTestVariable.ID, baseResponse.getData().get(0).getId());
    verify(this.campaignOutboundService).findCampaignActivate(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_CAMPAIGN_ACTIVATE, CampaignTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.PRIVILEGES);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.campaignOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
