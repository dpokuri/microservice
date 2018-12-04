import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.PaymentOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.PaymentServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PaymentTestVariable;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PaymentServiceImplTest {

  @InjectMocks
  PaymentServiceImpl paymentServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  PaymentOutboundService paymentOutboundService;

  @Test
  public void paymentTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_PAYMENT, PaymentTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.paymentOutboundService.findAll(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (PaymentTestVariable.PAYMENT_RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PaymentTestVariable.PRIVILEGES)).thenReturn(Single.just(PaymentTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<PaymentResponse>> baseResponse =  this.paymentServiceImpl
        .payment(
            CommonTestVariable.MANDATORY_REQUEST,
            PaymentTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(PaymentTestVariable.campaignDropdownResponseList, baseResponse.getData());
    verify(this.paymentOutboundService).findAll(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_PAYMENT, PaymentTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        PaymentTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.paymentOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
