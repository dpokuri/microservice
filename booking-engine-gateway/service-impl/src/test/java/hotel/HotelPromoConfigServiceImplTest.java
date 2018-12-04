package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelPromoConfigOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelPromoConfigServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelPromoConfigTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelPromoConfigServiceImplTest extends HotelPromoConfigTestVariable {

  @InjectMocks
  private HotelPromoConfigServiceImpl hotelPromoConfigService;

  @Mock
  private HotelPromoConfigOutboundService hotelPromoConfigOutboundService;

  @Mock
  private PrivilegeService privilegeService;

  @Test
  public void create() {
    when(hotelPromoConfigOutboundService
        .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigService
        .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(hotelPromoConfigOutboundService)
        .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST);
  }

  @Test
  public void update() {
    when(hotelPromoConfigOutboundService
        .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigService
        .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(hotelPromoConfigOutboundService)
        .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST);
  }

  @Test
  public void delete() {
    when(hotelPromoConfigOutboundService
        .delete(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigService
        .delete(CommonTestVariable.MANDATORY_REQUEST, ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(hotelPromoConfigOutboundService)
        .delete(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void getOne() {
    when(hotelPromoConfigOutboundService
        .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigService
        .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE, response);

    verify(hotelPromoConfigOutboundService)
        .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID);
  }

  @Test
  public void getAll() {
    when(hotelPromoConfigOutboundService
        .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE_PAGE));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<Object> response = hotelPromoConfigService
        .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_PROMO_CONFIG_RESPONSE_PAGE, response);

    verify(hotelPromoConfigOutboundService)
        .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(hotelPromoConfigOutboundService);
  }


}
