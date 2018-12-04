import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.VariableOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.VariableServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.InputType;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.VariableTestVariable;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class VariableServiceImplTest {

  @InjectMocks
  VariableServiceImpl variableServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  VariableOutboundService variableOutboundService;

  @Test
  public void findVariableFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_VARIABLE, VariableTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.variableOutboundService.findVariableFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, VariableTestVariable.NAME, InputType.AUTOCOMPLETE.getName(), VariableTestVariable.PAGE, VariableTestVariable.SIZE,
        VariableColumn.ID, SortDirection.ASC, VariableTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(VariableTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<VariableResponse>> baseResponse =  this.variableServiceImpl
        .findVariableFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.NAME, InputType.AUTOCOMPLETE.getName(), VariableTestVariable.PAGE, VariableTestVariable.SIZE, VariableColumn.ID, SortDirection.ASC,
        VariableTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(VariableTestVariable.RESULT, baseResponse);
    verify(this.variableOutboundService).findVariableFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, VariableTestVariable.NAME, InputType.AUTOCOMPLETE.getName(), VariableTestVariable.PAGE, VariableTestVariable.SIZE,
        VariableColumn.ID, SortDirection.ASC, VariableTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_VARIABLE, VariableTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findVariableByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_VARIABLE, VariableTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.variableOutboundService.findVariableById(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.ID)).thenReturn(Single.just
        (VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<VariableResponse> baseResponse =  this.variableServiceImpl
        .findVariableById(
            CommonTestVariable.MANDATORY_REQUEST,
            VariableTestVariable.ID,
            VariableTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(VariableTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_VARIABLE, VariableTestVariable
        .PRIVILEGES);

    verify(this.variableOutboundService).findVariableById(CommonTestVariable
        .MANDATORY_REQUEST, VariableTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES);

  }

  @Test
  public void createVariableTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_VARIABLE, VariableTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.variableOutboundService.createVariable(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.VARIABLE_REQUEST)).thenReturn
        (Single.just
            (VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<VariableResponse> baseResponse =  this.variableServiceImpl
        .createVariable(
            CommonTestVariable.MANDATORY_REQUEST,
            VariableTestVariable.VARIABLE_REQUEST,
            VariableTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(VariableTestVariable.ID, baseResponse.getData().getId());
    verify(this.variableOutboundService).createVariable(CommonTestVariable
        .MANDATORY_REQUEST, VariableTestVariable.VARIABLE_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_VARIABLE, VariableTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updateVariableTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_VARIABLE, VariableTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.variableOutboundService.updateVariable(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.VARIABLE_REQUEST, VariableTestVariable.ID
            )).thenReturn
        (Single.just
        (VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<VariableResponse> baseResponse =  this.variableServiceImpl
        .updateVariable(
            CommonTestVariable.MANDATORY_REQUEST,
            VariableTestVariable.VARIABLE_REQUEST,
            VariableTestVariable.ID,
            VariableTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(VariableTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_VARIABLE, VariableTestVariable
        .PRIVILEGES);
    verify(this.variableOutboundService).updateVariable(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.VARIABLE_REQUEST, VariableTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES);
  }

  @Test
  public void deleteVariableTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_VARIABLE, VariableTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.variableOutboundService.deleteVariable(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.VARIABLE_RESPONSE.getId())).thenReturn(Single.just
        (VariableTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.variableServiceImpl
        .deleteVariable(
            CommonTestVariable.MANDATORY_REQUEST,
            VariableTestVariable.VARIABLE_RESPONSE.getId(),
            VariableTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.variableOutboundService).deleteVariable(CommonTestVariable
        .MANDATORY_REQUEST, VariableTestVariable.VARIABLE_RESPONSE.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_VARIABLE, VariableTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES);
  }


  @Test
  public void findAllVariableTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_VARIABLE, VariableTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.variableOutboundService.findAllVariable(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (VariableTestVariable.VARIABLE_ALL_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<VariableFindAllResponse>> baseResponse =  this.variableServiceImpl
        .findAllVariable(
            CommonTestVariable.MANDATORY_REQUEST,
            VariableTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(VariableTestVariable.VARIABLE_ALL_RESPONSE_GENERATED_BASE_RESPONSE.getData().get(0).getInputType(),
        baseResponse.getData().get(0).getInputType());
    verify(this.variableOutboundService).findAllVariable(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_VARIABLE, VariableTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PRIVILEGES);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.variableOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
