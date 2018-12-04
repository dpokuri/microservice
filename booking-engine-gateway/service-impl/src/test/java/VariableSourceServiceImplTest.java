import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.VariableSourceOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.VariableSourceServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.VariableSourceTestVariable;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class VariableSourceServiceImplTest {

  @InjectMocks
  VariableSourceServiceImpl variableSourceServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  VariableSourceOutboundService variableSourceOutboundService;

  @Test
  public void variableSourceTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_VARIABLE_SOURCE, VariableSourceTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.variableSourceOutboundService.findAll(CommonTestVariable.MANDATORY_REQUEST, 
        VariableSourceTestVariable.SOURCE_TYPE, VariableSourceTestVariable.KEY)).thenReturn(Single.just
        (VariableSourceTestVariable.VARIABLE_SOURCE_RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableSourceTestVariable.PRIVILEGES)).thenReturn(Single.just(VariableSourceTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<VariableSourceResponse>> baseResponse =  this.variableSourceServiceImpl
        .findAllVariableSource(
            CommonTestVariable.MANDATORY_REQUEST,
            VariableSourceTestVariable.SOURCE_TYPE,
            VariableSourceTestVariable.KEY,
            VariableSourceTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(VariableSourceTestVariable.campaignDropdownResponseList, baseResponse.getData());
    verify(this.variableSourceOutboundService).findAll(CommonTestVariable.MANDATORY_REQUEST,
        VariableSourceTestVariable.SOURCE_TYPE, VariableSourceTestVariable.KEY);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_VARIABLE_SOURCE, VariableSourceTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        VariableSourceTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.variableSourceOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
