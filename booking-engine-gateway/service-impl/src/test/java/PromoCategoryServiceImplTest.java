import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.impl.PromoCategoryOutboundServiceImpl;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.PromoCategoryServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoCategoryTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PromoCategoryServiceImplTest {

  @InjectMocks
  PromoCategoryServiceImpl promoCategoryServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  PromoCategoryOutboundServiceImpl promoCategoryOutboundService;

  @Test
  public void findPromoCategoryFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCategoryOutboundService.findPromoCategoryFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, PromoCategoryTestVariable.CATEGORY, PromoCategoryTestVariable.PAGE, PromoCategoryTestVariable.SIZE,
        PromoCategoryTestVariable.COLUMN_SORT, PromoCategoryTestVariable.SORT_DIRECTION)).thenReturn(Single.just(PromoCategoryTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<PromoCategoryResponse>> baseResponse =  this.promoCategoryServiceImpl
        .findPromoCategoryFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.CATEGORY, PromoCategoryTestVariable.PAGE, PromoCategoryTestVariable.SIZE, PromoCategoryTestVariable.COLUMN_SORT, PromoCategoryTestVariable.SORT_DIRECTION,
        PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(PromoCategoryTestVariable.RESULT, baseResponse);
    verify(this.promoCategoryOutboundService).findPromoCategoryFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, PromoCategoryTestVariable.CATEGORY, PromoCategoryTestVariable.PAGE, PromoCategoryTestVariable.SIZE,
        PromoCategoryTestVariable.COLUMN_SORT, PromoCategoryTestVariable.SORT_DIRECTION);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);

  }


  @Test
  public void findPromoCategoryByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCategoryOutboundService.findPromoCategoryById(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.ID)).thenReturn(Single.just
        (PromoCategoryTestVariable.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));

    BaseResponse<PromoCategoryResponse> baseResponse =  this.promoCategoryServiceImpl
        .findPromoCategoryById(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCategoryTestVariable.ID,
            PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCategoryTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCategoryOutboundService).findPromoCategoryById(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.ID);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);
  }

  @Test
  public void createPromoCategoryTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.promoCategoryOutboundService.createPromoCategory(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST)).thenReturn
        (Single.just
            (PromoCategoryTestVariable.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));

    BaseResponse<PromoCategoryResponse> baseResponse =  this.promoCategoryServiceImpl
        .createPromoCategory(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST,
            PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCategoryTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCategoryOutboundService).createPromoCategory(CommonTestVariable
        .MANDATORY_REQUEST, PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);
  }

  @Test
  public void updatePromoCategoryTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.promoCategoryOutboundService.updatePromoCategory(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST, PromoCategoryTestVariable.ID
            )).thenReturn
        (Single.just
        (PromoCategoryTestVariable.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));


    BaseResponse<PromoCategoryResponse> baseResponse =  this.promoCategoryServiceImpl
        .updatePromoCategory(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST,
            PromoCategoryTestVariable.ID,
            PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCategoryTestVariable.ID, baseResponse.getData().getId());
    verify(this.promoCategoryOutboundService).updatePromoCategory(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST, PromoCategoryTestVariable.ID);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);
  }

  @Test
  public void deletePromoCategoryTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.promoCategoryOutboundService.deletePromoCategory(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.ID)).thenReturn(Single.just
        (PromoCategoryTestVariable.BASE_RESPONSE_BOOLEAN));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.promoCategoryServiceImpl
        .deletePromoCategory(
            CommonTestVariable.MANDATORY_REQUEST,
            PromoCategoryTestVariable.ID,
            PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.promoCategoryOutboundService).deletePromoCategory(CommonTestVariable
        .MANDATORY_REQUEST, PromoCategoryTestVariable.ID);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);
  }

  @Test
  public void getCategoriesTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCategoryOutboundService.getCategories(CommonTestVariable
            .MANDATORY_REQUEST, PromoCategoryTestVariable.LANG)).thenReturn(Single.just
        (PromoCategoryTestVariable
        .MAP_RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Map<String, Object>>> baseResponse =  this.promoCategoryServiceImpl
        .getCategories(
            CommonTestVariable.MANDATORY_REQUEST,
            "EN", PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCategoryTestVariable.MAP_RESULT, baseResponse);

    verify(this.promoCategoryOutboundService).getCategories(CommonTestVariable
            .MANDATORY_REQUEST, PromoCategoryTestVariable.LANG);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);

  }

  @Test
  public void getListCategoriesTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.promoCategoryOutboundService.getListCategories(CommonTestVariable
        .MANDATORY_REQUEST, PromoCategoryTestVariable.LANG)).thenReturn(Single.just
        (PromoCategoryTestVariable
            .MAP_RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES)).thenReturn(Single.just(PromoCategoryTestVariable.PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Map<String, Object>>> baseResponse =  this.promoCategoryServiceImpl
        .getListCategories(
            CommonTestVariable.MANDATORY_REQUEST,
            "EN", PromoCategoryTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PromoCategoryTestVariable.MAP_RESULT, baseResponse);

    verify(this.promoCategoryOutboundService).getListCategories(CommonTestVariable
        .MANDATORY_REQUEST, PromoCategoryTestVariable.LANG);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, PromoCategoryTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.PRIVILEGES);

  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCategoryOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
