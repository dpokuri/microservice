import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.PromoCodeAdjustmentOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.PromoCodeAdjustmentServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeAdjustmentTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PromoCodeAdjustmentServiceImplTest {

  @InjectMocks
  PromoCodeAdjustmentServiceImpl promoCodeAdjustmentServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  PromoCodeAdjustmentOutboundService promoCodeAdjustmentOutboundService;

  @Test
  public void findPromoCodeAdjustmentFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoCodeAdjustmentOutboundService.findPromoCodeAdjustmentFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.NAME, true, PromoCodeAdjustmentTestVariable.PAGE, PromoCodeAdjustmentTestVariable.SIZE,
        PromoCodeAdjustmentColumn.ID, SortDirection.ASC, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(PromoCodeAdjustmentTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.NAME, true, PromoCodeAdjustmentTestVariable.PAGE, PromoCodeAdjustmentTestVariable.SIZE, PromoCodeAdjustmentColumn.ID, SortDirection.ASC,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.RESULT, baseResponse);
    verify(this.promoCodeAdjustmentOutboundService).findPromoCodeAdjustmentFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.NAME, true, PromoCodeAdjustmentTestVariable.PAGE, PromoCodeAdjustmentTestVariable.SIZE,
        PromoCodeAdjustmentColumn.ID, SortDirection.ASC, PromoCodeAdjustmentTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findPromoCodeAdjustmentByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoCodeAdjustmentOutboundService.findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID)).thenReturn(Single.just
        (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .findPromoCodeAdjustmentById(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.ID,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable
        .PRIVILEGES);

    verify(this.promoCodeAdjustmentOutboundService).findPromoCodeAdjustmentById(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);

  }

  @Test
  public void createPromoCodeAdjustmentTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeAdjustmentOutboundService.createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST)).thenReturn
        (Single.just
            (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .createPromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeAdjustmentOutboundService).createPromoCodeAdjustment(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updatePromoCodeAdjustmentTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeAdjustmentOutboundService.updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST, PromoCodeAdjustmentTestVariable.ID
            )).thenReturn
        (Single.just
        (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .updatePromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST,
            PromoCodeAdjustmentTestVariable.ID,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable
        .PRIVILEGES);
    verify(this.promoCodeAdjustmentOutboundService).updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST, PromoCodeAdjustmentTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }

  @Test
  public void deletePromoCodeAdjustmentTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeAdjustmentOutboundService.deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_RESPONSE.getId())).thenReturn(Single.just
        (PromoCodeAdjustmentTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .deletePromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_RESPONSE.getId(),
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.promoCodeAdjustmentOutboundService).deletePromoCodeAdjustment(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_RESPONSE.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoCodeAdjustmentToInActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCodeAdjustmentOutboundService.updateStatusUnActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    )).thenReturn
        (Single.just
            (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .updateStatusUnActivatePromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.ID,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeAdjustmentOutboundService).updateStatusUnActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }


  @Test
  public void updatePromoCodeAdjustmentToActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCodeAdjustmentOutboundService.updateStatusActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    )).thenReturn
        (Single.just
            (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .updateStatusActivatePromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.ID,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeAdjustmentOutboundService).updateStatusActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.ACTIVATE_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoCodeAdjustmentToPendingTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.PENDING_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCodeAdjustmentOutboundService.updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    )).thenReturn
        (Single.just
            (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .updateStatusPendingPromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.ID,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeAdjustmentOutboundService).updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.PENDING_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoCodeAdjustmentToRejectedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.REJECTED_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCodeAdjustmentOutboundService.updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    )).thenReturn
        (Single.just
            (PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeAdjustmentResponse> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .updateStatusRejectedPromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.ID,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeAdjustmentOutboundService).updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.REJECTED_PROMO_ADJUSTMENT, PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }

  @Test
  public void findpromoCodeActivateTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_PROMO_ADJUSTMENT_ACTIVATE, PromoCodeAdjustmentTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCodeAdjustmentOutboundService.findPromoCodeAdjustmentActivate(CommonTestVariable.MANDATORY_REQUEST)).thenReturn
        (Single.just
            (PromoCodeAdjustmentTestVariable.PRO_DROPDOWN_RESPONSE_LIST));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable
        .PRIVILEGE_RESPONSE));
    GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>> baseResponse =  this.promoCodeAdjustmentServiceImpl
        .findPromoCodeAdjustmentActivate(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeAdjustmentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeAdjustmentTestVariable.ID, baseResponse.getData().get(0).getId());
    verify(this.promoCodeAdjustmentOutboundService).findPromoCodeAdjustmentActivate(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_PROMO_ADJUSTMENT_ACTIVATE, PromoCodeAdjustmentTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeAdjustmentOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
