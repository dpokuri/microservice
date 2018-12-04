import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.BankOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.BankServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BankTestVariable;
import com.tl.booking.gateway.entity.outbound.PromoCode.BankResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BankServiceImplTest {

  @InjectMocks
  BankServiceImpl binNumberServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  BankOutboundService binNumberOutboundService;

  @Test
  public void binNumberTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_BANK, BankTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.binNumberOutboundService.bank(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (BankTestVariable.BANK_RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BankTestVariable.PRIVILEGES)).thenReturn(Single.just(BankTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<BankResponse>> baseResponse =  this.binNumberServiceImpl
        .bank(
            CommonTestVariable.MANDATORY_REQUEST,
            BankTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BankTestVariable.campaignDropdownResponseList, baseResponse.getData());
    verify(this.binNumberOutboundService).bank(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_BANK, BankTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BankTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.binNumberOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
