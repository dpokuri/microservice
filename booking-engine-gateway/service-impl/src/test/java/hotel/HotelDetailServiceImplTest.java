package hotel;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelDetailOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelDetailServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelDetailServiceImplTest {

  @InjectMocks
  private HotelDetailServiceImpl hotelDetailService;

  @Mock
  private HotelDetailOutboundService hotelDetailOutboundService;

  @Mock
  private PrivilegeService privilegeService;

  String PRIVILEGES = "300,301,302,303,304,305,306,207";

  Map<String, Object> HOTEL_POLICY_RESPONSE;

  GatewayBaseResponse<List<Map<String, Object>>> HOTEL_POLICY_RESPONSE_BASE;

  @Test
  public void getHotelRoomList() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, this.PRIVILEGES))
        .thenReturn
            (Single
                .just(true));
    when(this.hotelDetailOutboundService.getHotelRoomList(CommonTestVariable
        .MANDATORY_REQUEST, "1"))
        .thenReturn
            (Single.just(this.HOTEL_POLICY_RESPONSE_BASE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        this.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
  }

  @Test
  public void getHotelPolicy() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, this.PRIVILEGES))
        .thenReturn
            (Single
                .just(true));
    when(this.hotelDetailOutboundService.getHotelPolicy(CommonTestVariable
        .MANDATORY_REQUEST ))
        .thenReturn
            (Single.just(this.HOTEL_POLICY_RESPONSE_BASE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        this.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.HOTEL_POLICY_RESPONSE_BASE = new GatewayBaseResponse<>();
    this.HOTEL_POLICY_RESPONSE_BASE.setCode("Success");
    this.HOTEL_POLICY_RESPONSE_BASE.setData(Arrays.asList(this.HOTEL_POLICY_RESPONSE, this.HOTEL_POLICY_RESPONSE));
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelDetailOutboundService);
  }

}
