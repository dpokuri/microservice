import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.ProductTypeOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.ProductTypeServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.ProductTypeTestVariable;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ProductTypeServiceImplTest {

  @InjectMocks
  ProductTypeServiceImpl productTypeServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  ProductTypeOutboundService productTypeOutboundService;

  @Test
  public void findProductTypeFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_PRODUCT_TYPE, ProductTypeTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.productTypeOutboundService.findProductTypeFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, ProductTypeTestVariable.NAME, ProductTypeTestVariable.PAGE, ProductTypeTestVariable.SIZE,
        ProductTypeColumn.ID, SortDirection.ASC, ProductTypeTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(ProductTypeTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(ProductTypeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<ProductTypeResponse>> baseResponse =  this.productTypeServiceImpl
        .findProductTypeFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.NAME, ProductTypeTestVariable.PAGE, ProductTypeTestVariable.SIZE, ProductTypeColumn.ID, SortDirection.ASC,
        ProductTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(ProductTypeTestVariable.RESULT, baseResponse);
    verify(this.productTypeOutboundService).findProductTypeFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, ProductTypeTestVariable.NAME, ProductTypeTestVariable.PAGE, ProductTypeTestVariable.SIZE,
        ProductTypeColumn.ID, SortDirection.ASC, ProductTypeTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findProductTypeByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_PRODUCT_TYPE, ProductTypeTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.productTypeOutboundService.findProductTypeById(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.ID)).thenReturn(Single.just
        (ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(ProductTypeTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<ProductTypeResponse> baseResponse =  this.productTypeServiceImpl
        .findProductTypeById(
            CommonTestVariable.MANDATORY_REQUEST,
            ProductTypeTestVariable.ID,
            ProductTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(ProductTypeTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES);

    verify(this.productTypeOutboundService).findProductTypeById(CommonTestVariable
        .MANDATORY_REQUEST, ProductTypeTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES);

  }

  @Test
  public void createProductTypeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PRODUCT_TYPE, ProductTypeTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.productTypeOutboundService.createProductType(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRODUCT_TYPE_REQUEST)).thenReturn
        (Single.just
            (ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(ProductTypeTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<ProductTypeResponse> baseResponse =  this.productTypeServiceImpl
        .createProductType(
            CommonTestVariable.MANDATORY_REQUEST,
            ProductTypeTestVariable.PRODUCT_TYPE_REQUEST,
            ProductTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(ProductTypeTestVariable.ID, baseResponse.getData().getId());
    verify(this.productTypeOutboundService).createProductType(CommonTestVariable
        .MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updateProductTypeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.productTypeOutboundService.updateProductType(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.ID
            )).thenReturn
        (Single.just
        (ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(ProductTypeTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<ProductTypeResponse> baseResponse =  this.productTypeServiceImpl
        .updateProductType(
            CommonTestVariable.MANDATORY_REQUEST,
            ProductTypeTestVariable.PRODUCT_TYPE_REQUEST,
            ProductTypeTestVariable.ID,
            ProductTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(ProductTypeTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES);
    verify(this.productTypeOutboundService).updateProductType(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES);
  }

  @Test
  public void deleteProductTypeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PRODUCT_TYPE, ProductTypeTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.productTypeOutboundService.deleteProductType(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE.getId())).thenReturn(Single.just
        (ProductTypeTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(ProductTypeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.productTypeServiceImpl
        .deleteProductType(
            CommonTestVariable.MANDATORY_REQUEST,
            ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE.getId(),
            ProductTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.productTypeOutboundService).deleteProductType(CommonTestVariable
        .MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES);
  }

  @Test
  public void findProductTypeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_PRODUCT_TYPE, ProductTypeTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.productTypeOutboundService.findProductTypes(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_LIST_DATA));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(ProductTypeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<ProductTypeResponse>> baseResponse =  this.productTypeServiceImpl
        .findProductTypes(
            CommonTestVariable.MANDATORY_REQUEST,
            ProductTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_PRODUCT_TYPE, ProductTypeTestVariable
        .PRIVILEGES);
    verify(this.productTypeOutboundService).findProductTypes(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.productTypeOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
