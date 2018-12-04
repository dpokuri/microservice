import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.impl.PromoPageOutboundServiceImpl;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.PromoPageServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PromoPageServiceImplTest {

  @InjectMocks
  PromoPageServiceImpl promoPageServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  PromoPageOutboundServiceImpl promoPageOutboundService;

  @Test
  public void findPromoPageFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoPageOutboundService.findPromoPageFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, PromoPageTestVariable.TITLE, PromoPageTestVariable.CATEGORIES, PromoPageStatus.DRAFT, 1, PromoPageTestVariable.PAGE, PromoPageTestVariable.SIZE,
        PromoPageTestVariable.COLUMN_SORT, PromoPageTestVariable.SORT_DIRECTION, PromoPageTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(PromoPageTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<PromoPageResponse>> baseResponse =  this.promoPageServiceImpl
        .findPromoPageFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.TITLE, PromoPageTestVariable.CATEGORIES, PromoPageStatus.DRAFT, 1, PromoPageTestVariable.PAGE, PromoPageTestVariable.SIZE, PromoPageTestVariable.COLUMN_SORT, PromoPageTestVariable.SORT_DIRECTION,
        PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(PromoPageTestVariable.RESULT, baseResponse);
    verify(this.promoPageOutboundService).findPromoPageFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, PromoPageTestVariable.TITLE, PromoPageTestVariable.CATEGORIES, PromoPageStatus.DRAFT, 1, PromoPageTestVariable.PAGE, PromoPageTestVariable.SIZE,
        PromoPageTestVariable.COLUMN_SORT, PromoPageTestVariable.SORT_DIRECTION, PromoPageTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findPromoPageBySlugTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoPageOutboundService.findPromoPageBySlug(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.SLUG)).thenReturn(Single.just
        (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .findPromoPageBySlug(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_WITH_SLUG.getSlug(),
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoPageOutboundService).findPromoPageBySlug(CommonTestVariable
            .MANDATORY_REQUEST, PromoPageTestVariable.SLUG);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Test
  public void createPromoPageTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoPageOutboundService.createPromoPage(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_REQUEST)).thenReturn
        (Single.just
            (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .createPromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_REQUEST,
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoPageOutboundService).createPromoPage(CommonTestVariable
        .MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updatePromoPageTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoPageOutboundService.updatePromoPage(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_REQUEST, PromoPageTestVariable.SLUG
            )).thenReturn
        (Single.just
        (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .updatePromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_REQUEST,
            PromoPageTestVariable.SLUG,
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES);
    verify(this.promoPageOutboundService).updatePromoPage(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_REQUEST, PromoPageTestVariable.SLUG);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Test
  public void deletePromoPageTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoPageOutboundService.deletePromoPage(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED.getSlug())).thenReturn(Single.just
        (PromoPageTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.promoPageServiceImpl
        .deletePromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED.getSlug(),
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.promoPageOutboundService).deletePromoPage(CommonTestVariable
        .MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED.getSlug());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoPageToActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.promoPageOutboundService.updateStatusActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST,
         PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug()
    )).thenReturn
        (Single.just
            (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .updateStatusActivatedPromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_WITH_SLUG.getSlug(),
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoPageOutboundService).updateStatusActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.ACTIVATE_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoPageToPendingTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.REQUEST_APPROVAL_PROMO_LIST, PromoPageTestVariable
        .PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.promoPageOutboundService.updateStatusPendingPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug()
    )).thenReturn
        (Single.just
            (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_PENDING));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .updateStatusPendingPromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_WITH_SLUG.getSlug(),
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoPageOutboundService).updateStatusPendingPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.REQUEST_APPROVAL_PROMO_LIST, PromoPageTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoPageToInActivatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoPageOutboundService.updateStatusInActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug()
    )).thenReturn
        (Single.just
            (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVATED));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .updateStatusInActivatedPromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_WITH_SLUG.getSlug(),
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoPageOutboundService).updateStatusInActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoPageToRejectedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoPageOutboundService.updateStatusRejectedPromoPage(CommonTestVariable
            .MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug()
    )).thenReturn
        (Single.just
            (PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVATED));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<PromoPageResponse> baseResponse =  this.promoPageServiceImpl
        .updateStatusRejectedPromoPage(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_WITH_SLUG.getSlug(),
            PromoPageTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoPageTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoPageOutboundService).updateStatusRejectedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST_WITH_SLUG.getSlug());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.ACTIVATE_PROMO_LIST, PromoPageTestVariable.PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PRIVILEGES);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoPageOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
