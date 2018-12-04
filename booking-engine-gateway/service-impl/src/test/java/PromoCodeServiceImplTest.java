import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.PromoCodeOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.PromoCodeServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeTestVariable;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PromoCodeServiceImplTest {

  @InjectMocks
  PromoCodeServiceImpl promoCodeServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  PromoCodeOutboundService promoCodeOutboundService;

  @Test
  public void findPromoCodeFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoCodeOutboundService.findPromoCodeFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeTestVariable.CODE, PromoCodeTestVariable.CAMPAIGN_ID, PromoCodeTestVariable.PAGE, PromoCodeTestVariable.SIZE,
        PromoCodeColumn.ID, SortDirection.ASC, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(PromoCodeTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<PromoCodeResponse>> baseResponse =  this.promoCodeServiceImpl
        .findPromoCodeFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.CODE, PromoCodeTestVariable.CAMPAIGN_ID, PromoCodeTestVariable.PAGE, PromoCodeTestVariable.SIZE, PromoCodeColumn.ID, SortDirection.ASC,
        PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(PromoCodeTestVariable.RESULT, baseResponse);
    verify(this.promoCodeOutboundService).findPromoCodeFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeTestVariable.CODE, PromoCodeTestVariable.CAMPAIGN_ID, PromoCodeTestVariable.PAGE, PromoCodeTestVariable.SIZE,
        PromoCodeColumn.ID, SortDirection.ASC, PromoCodeTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findPromoCodeByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoCodeOutboundService.findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.ID)).thenReturn(Single.just
        (PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<PromoCodeResponse> baseResponse =  this.promoCodeServiceImpl
        .findPromoCodeById(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeTestVariable.ID,
            PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES);

    verify(this.promoCodeOutboundService).findPromoCodeById(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);

  }

  @Test
  public void createPromoCodeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeOutboundService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PROMO_CODE_REQUEST)).thenReturn
        (Single.just
            (PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeResponse> baseResponse =  this.promoCodeServiceImpl
        .createPromoCode(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeTestVariable.PROMO_CODE_REQUEST,
            PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeOutboundService).createPromoCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updatePromoCodeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeOutboundService.updatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID
            )).thenReturn
        (Single.just
        (PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeResponse> baseResponse =  this.promoCodeServiceImpl
        .updatePromoCode(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeTestVariable.PROMO_CODE_REQUEST,
            PromoCodeTestVariable.ID,
            PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES);
    verify(this.promoCodeOutboundService).updatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);
  }

  @Test
  public void deletePromoCodeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeOutboundService.deletePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED.getId())).thenReturn(Single.just
        (PromoCodeTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.promoCodeServiceImpl
        .deletePromoCode(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED.getId(),
            PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.promoCodeOutboundService).deletePromoCode(CommonTestVariable
        .MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoCodeToActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoCodeOutboundService.updateStatusActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
         PromoCodeTestVariable.ID
    )).thenReturn
        (Single.just
            (PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<PromoCodeResponse> baseResponse =  this.promoCodeServiceImpl
        .updateStatusActivatePromoCode(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeTestVariable.ID,
            PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeOutboundService).updateStatusActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.ACTIVATE_PROMO_CODE, PromoCodeTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);
  }


  @Test
  public void updatePromoCodeToInActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCodeOutboundService.updateStatusUnActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.ID
    )).thenReturn
        (Single.just
            (PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCodeTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoCodeResponse> baseResponse =  this.promoCodeServiceImpl
        .updateStatusUnActivatePromoCode(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCodeTestVariable.ID,
            PromoCodeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCodeTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCodeOutboundService).updateStatusUnActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.ID
    );
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_CODE, PromoCodeTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeTestVariable.PRIVILEGES);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
