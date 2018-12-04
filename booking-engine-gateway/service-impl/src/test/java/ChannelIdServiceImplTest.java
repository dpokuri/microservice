import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.ChannelIdOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.ChannelIdServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.ChannelIdTestVariable;
import com.tl.booking.gateway.entity.outbound.PromoCode.ChannelIdResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ChannelIdServiceImplTest {

  @InjectMocks
  ChannelIdServiceImpl channelIdServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  ChannelIdOutboundService channelIdOutboundService;

  @Test
  public void channelIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_CHANNEL_ID, ChannelIdTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.channelIdOutboundService.findAll(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (ChannelIdTestVariable.CHANNEL_ID_RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ChannelIdTestVariable.PRIVILEGES)).thenReturn(Single.just(ChannelIdTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<ChannelIdResponse>> baseResponse =  this.channelIdServiceImpl
        .findAllChannelId(
            CommonTestVariable.MANDATORY_REQUEST,
            ChannelIdTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(ChannelIdTestVariable.campaignDropdownResponseList, baseResponse.getData());
    verify(this.channelIdOutboundService).findAll(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_CHANNEL_ID, ChannelIdTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        ChannelIdTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.channelIdOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
