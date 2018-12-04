import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.BusinessLogicResponseOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.BusinessLogicResponseServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BusinessLogicResponseTestVariable;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BusinessLogicServiceImplTest {

  @InjectMocks
  BusinessLogicResponseServiceImpl businessLogicResponseServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  BusinessLogicResponseOutboundService businessLogicResponseOutboundService;

  @Test
  public void findBusinessLogicResponseFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.businessLogicResponseOutboundService.findBusinessLogicResponseFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.CODE, BusinessLogicResponseTestVariable.PAGE, BusinessLogicResponseTestVariable.SIZE,
        BusinessLogicResponseColumn.ID, SortDirection.ASC, BusinessLogicResponseTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(BusinessLogicResponseTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES)).thenReturn(Single.just(BusinessLogicResponseTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<BusinessLogicResponseResponse>> baseResponse =  this.businessLogicResponseServiceImpl
        .findBusinessLogicResponseFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.CODE, BusinessLogicResponseTestVariable.PAGE, BusinessLogicResponseTestVariable.SIZE, BusinessLogicResponseColumn.ID, SortDirection.ASC,
        BusinessLogicResponseTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(BusinessLogicResponseTestVariable.RESULT, baseResponse);
    verify(this.businessLogicResponseOutboundService).findBusinessLogicResponseFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.CODE, BusinessLogicResponseTestVariable.PAGE, BusinessLogicResponseTestVariable.SIZE,
        BusinessLogicResponseColumn.ID, SortDirection.ASC, BusinessLogicResponseTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findBusinessLogicResponseByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.businessLogicResponseOutboundService.findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.ID)).thenReturn(Single.just
        (BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES)).thenReturn(Single.just(BusinessLogicResponseTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<BusinessLogicResponseResponse> baseResponse =  this.businessLogicResponseServiceImpl
        .findBusinessLogicResponseById(
            CommonTestVariable.MANDATORY_REQUEST,
            BusinessLogicResponseTestVariable.ID,
            BusinessLogicResponseTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BusinessLogicResponseTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES);

    verify(this.businessLogicResponseOutboundService).findBusinessLogicResponseById(CommonTestVariable
        .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES);

  }

  @Test
  public void createBusinessLogicResponseTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.businessLogicResponseOutboundService.createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST)).thenReturn
        (Single.just
            (BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES)).thenReturn(Single.just(BusinessLogicResponseTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<BusinessLogicResponseResponse> baseResponse =  this.businessLogicResponseServiceImpl
        .createBusinessLogicResponse(
            CommonTestVariable.MANDATORY_REQUEST,
            BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST,
            BusinessLogicResponseTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BusinessLogicResponseTestVariable.ID, baseResponse.getData().getId());
    verify(this.businessLogicResponseOutboundService).createBusinessLogicResponse(CommonTestVariable
        .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updateBusinessLogicResponseTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.businessLogicResponseOutboundService.updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.ID
            )).thenReturn
        (Single.just
        (BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES)).thenReturn(Single.just(BusinessLogicResponseTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<BusinessLogicResponseResponse> baseResponse =  this.businessLogicResponseServiceImpl
        .updateBusinessLogicResponse(
            CommonTestVariable.MANDATORY_REQUEST,
            BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST,
            BusinessLogicResponseTestVariable.ID,
            BusinessLogicResponseTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BusinessLogicResponseTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES);
    verify(this.businessLogicResponseOutboundService).updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES);
  }

  @Test
  public void deleteBusinessLogicResponseTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.businessLogicResponseOutboundService.deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE.getId())).thenReturn(Single.just
        (BusinessLogicResponseTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES)).thenReturn(Single.just(BusinessLogicResponseTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.businessLogicResponseServiceImpl
        .deleteBusinessLogicResponse(
            CommonTestVariable.MANDATORY_REQUEST,
            BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE.getId(),
            BusinessLogicResponseTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.businessLogicResponseOutboundService).deleteBusinessLogicResponse(CommonTestVariable
        .MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES);
  }

  @Test
  public void findBusinessLogicResponseTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.businessLogicResponseOutboundService.findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_LIST_DATA));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES)).thenReturn(Single.just(BusinessLogicResponseTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<BusinessLogicResponseResponse>> baseResponse =  this.businessLogicResponseServiceImpl
        .findBusinessLogicResponses(
            CommonTestVariable.MANDATORY_REQUEST,
            BusinessLogicResponseTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_BUSINESS_LOGIC_RESPONSE, BusinessLogicResponseTestVariable
        .PRIVILEGES);
    verify(this.businessLogicResponseOutboundService).findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.businessLogicResponseOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
