import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.CardTypeOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.CardTypeServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.CardTypeTestVariable;
import com.tl.booking.gateway.entity.outbound.PromoCode.CardTypeResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CardTypeServiceImplTest {

  @InjectMocks
  CardTypeServiceImpl cardTypeServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  CardTypeOutboundService cardTypeOutboundService;

  @Test
  public void cardTypeTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_CARD_TYPE, CardTypeTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.cardTypeOutboundService.findAll(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (CardTypeTestVariable.CARD_TYPE_RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CardTypeTestVariable.PRIVILEGES)).thenReturn(Single.just(CardTypeTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<CardTypeResponse>> baseResponse =  this.cardTypeServiceImpl
        .findAllCardType(
            CommonTestVariable.MANDATORY_REQUEST,
            CardTypeTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(CardTypeTestVariable.campaignDropdownResponseList, baseResponse.getData());
    verify(this.cardTypeOutboundService).findAll(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_CARD_TYPE, CardTypeTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CardTypeTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.cardTypeOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
