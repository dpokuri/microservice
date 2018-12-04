package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelPromoAggregateOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelPromoAggregateServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelAggregateTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelPromoAggregateServiceImplTest extends HotelAggregateTestVariable {

  @InjectMocks
  private
  HotelPromoAggregateServiceImpl hotelAggregateService;

  @Mock
  private
  HotelPromoAggregateOutboundService hotelPromoAggregateOutboundService;

  @Mock
  private PrivilegeService privilegeService;

  @Test
  public void findAllHotelPromoByHotelIdAndRoomIdAndDateTest() {

    when(hotelPromoAggregateOutboundService
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
            findAllPromoParam)).thenReturn(Single.just(hotelPromoAggregateResponsePageDummy));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>> hotelPromoAggregateResponse =
        hotelAggregateService
            .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
                findAllPromoParam, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(hotelPromoAggregateResponsePageDummy, hotelPromoAggregateResponse);
    verify(hotelPromoAggregateOutboundService)
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
            findAllPromoParam);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findHotelPromoByIdTest() {

    when(hotelPromoAggregateOutboundService
        .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(hotelPromoAggregateResponseDummy));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelPromoAggregateResponse> hotelPromoAggregateResponse = hotelAggregateService
        .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST,
            ID, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(hotelPromoAggregateResponseDummy, hotelPromoAggregateResponse);
    verify(hotelPromoAggregateOutboundService)
        .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST, ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void createHotelPromoAggregateTest() {

    when(hotelPromoAggregateOutboundService
        .createHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST,
            hotelPromoAggregateRequest))
        .thenReturn(Single.just(hotelPromoAggregateResponseDummy));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelPromoAggregateResponse> hotelPromoAggregateResponse = hotelAggregateService
        .createHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST,
            hotelPromoAggregateRequest, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(hotelPromoAggregateResponseDummy, hotelPromoAggregateResponse);
    verify(hotelPromoAggregateOutboundService)
        .createHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST,
            hotelPromoAggregateRequest);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void updateHotelPromoAggregateTest() {

    when(hotelPromoAggregateOutboundService
        .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            hotelPromoAggregateRequest))
        .thenReturn(Single.just(hotelPromoAggregateResponseDummy));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelPromoAggregateResponse> hotelPromoAggregateResponse = hotelAggregateService
        .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            hotelPromoAggregateRequest, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(hotelPromoAggregateResponseDummy, hotelPromoAggregateResponse);
    verify(hotelPromoAggregateOutboundService)
        .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            hotelPromoAggregateRequest);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void deleteHotelPromoAggregateTest() {

    when(hotelPromoAggregateOutboundService
        .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(CommonTestVariable.GATEWAY_BASE_RESPONSE));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse hotelPromoAggregateResponse = hotelAggregateService
        .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST,
            ID, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(CommonTestVariable.GATEWAY_BASE_RESPONSE, hotelPromoAggregateResponse);
    verify(hotelPromoAggregateOutboundService)
        .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelPromoAggregateOutboundService);
  }

}
