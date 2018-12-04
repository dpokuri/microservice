package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelPromoTypeOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelPromoTypeServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelPromoTypeTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoTypeResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.MDC;

public class HotelPromoTypeServiceImplTest extends HotelPromoTypeTestVariable {

  @InjectMocks
  private
  HotelPromoTypeServiceImpl hotelPromoTypeService;

  @Mock
  private HotelPromoTypeOutboundService hotelPromoTypeOutboundService;

  @Mock
  private PrivilegeService privilegeService;

  @Test
  public void findAllHotelPromoTypeTest() {

    when(hotelPromoTypeOutboundService
        .findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_LIST_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<HotelPromoTypeResponse>> hotelPromoTypeResponse =
        hotelPromoTypeService.findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_LIST_DUMMY, hotelPromoTypeResponse);
    verify(hotelPromoTypeOutboundService)
        .findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findHotelPromoTypeByIdTest() {

    when(hotelPromoTypeOutboundService
        .findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelPromoTypeResponse> hotelPromoTypeResponse =
        hotelPromoTypeService.findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelPromoTypeOutboundService)
        .findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void createHotelPromoTypeTest() {

    when(hotelPromoTypeOutboundService
        .createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_TYPE_REQUEST_DUMMY))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelPromoTypeResponse> hotelPromoTypeResponse =
        hotelPromoTypeService.createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelPromoTypeOutboundService)
        .createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_TYPE_REQUEST_DUMMY);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void updateHotelPromoTypeTest() {

    when(hotelPromoTypeOutboundService
        .updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<HotelPromoTypeResponse> hotelPromoTypeResponse =
        hotelPromoTypeService.updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY, CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelPromoTypeOutboundService)
        .updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void deleteHotelPromoTypeTest() {

    when(hotelPromoTypeOutboundService
        .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_BASE_RESPONSE_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse hotelPromoTypeResponse = hotelPromoTypeService
        .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_TYPE_BASE_RESPONSE_DUMMY, hotelPromoTypeResponse);
    verify(hotelPromoTypeOutboundService)
        .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelPromoTypeOutboundService);
  }

}
