package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.AdditionalDiscountOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.AdditionalDiscountServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.AdditionalDiscountTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class AdditionalDiscountServiceImplTest {

  @InjectMocks
  private AdditionalDiscountServiceImpl additionalDiscountService;

  @Mock
  private PrivilegeService privilegeService;

  @Mock
  private AdditionalDiscountOutboundService additionalDiscountOutboundService;

  @Test
  public void createAdditionalDiscountTest() {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.additionalDiscountOutboundService.createAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.REQUEST
    )).thenReturn(Single.just(AdditionalDiscountTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.additionalDiscountService.createAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.REQUEST,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, GatewayBaseResponse);

    verify(this.additionalDiscountOutboundService).createAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.REQUEST
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void updateAdditionalDiscountTest() {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.additionalDiscountOutboundService.updateAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.REQUEST,
        AdditionalDiscountTestVariable.ID
    )).thenReturn(Single.just(AdditionalDiscountTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.additionalDiscountService.updateAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.REQUEST,
        AdditionalDiscountTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, GatewayBaseResponse);

    verify(this.additionalDiscountOutboundService).updateAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.REQUEST,
        AdditionalDiscountTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void deleteAdditionalDiscountTest() {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.additionalDiscountOutboundService.deleteAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.ID
    )).thenReturn(Single.just(AdditionalDiscountTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.additionalDiscountService.deleteAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, GatewayBaseResponse);

    verify(this.additionalDiscountOutboundService).deleteAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findAdditionalDiscountByHotelTest() {
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.additionalDiscountOutboundService.findAdditionalDiscountByHotel(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.HOTEL_ID,
        AdditionalDiscountTestVariable.TYPE
    )).thenReturn(Single.just(AdditionalDiscountTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.additionalDiscountService.findAdditionalDiscountByHotel(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.HOTEL_ID,
        AdditionalDiscountTestVariable.TYPE,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, GatewayBaseResponse);

    verify(this.additionalDiscountOutboundService).findAdditionalDiscountByHotel(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.HOTEL_ID,
        AdditionalDiscountTestVariable.TYPE
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findAdditionalDiscountByIdTest() {
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.additionalDiscountOutboundService.findAdditionalDiscountById(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.ID
    )).thenReturn(Single.just(AdditionalDiscountTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.additionalDiscountService.findAdditionalDiscountById(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT, GatewayBaseResponse);

    verify(this.additionalDiscountOutboundService).findAdditionalDiscountById(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findAdditionalDiscountTest() {
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.additionalDiscountOutboundService.findAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.PAGE,
        AdditionalDiscountTestVariable.LIMIT,
        AdditionalDiscountTestVariable.TYPE,
        AdditionalDiscountTestVariable.HOTEL_ID.toString()
    )).thenReturn(Single.just(AdditionalDiscountTestVariable.RESULT_PAGE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>> GatewayBaseResponse =
        this.additionalDiscountService.findAdditionalDiscount(
            CommonTestVariable.MANDATORY_REQUEST,
            AdditionalDiscountTestVariable.PAGE,
            AdditionalDiscountTestVariable.LIMIT,
            AdditionalDiscountTestVariable.TYPE,
            AdditionalDiscountTestVariable.HOTEL_ID.toString(),
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(AdditionalDiscountTestVariable.RESULT_PAGE, GatewayBaseResponse);

    verify(this.additionalDiscountOutboundService).findAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        AdditionalDiscountTestVariable.PAGE,
        AdditionalDiscountTestVariable.LIMIT,
        AdditionalDiscountTestVariable.TYPE,
        AdditionalDiscountTestVariable.HOTEL_ID.toString()
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Before
  public void setUp() {
    initMocks(this);
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.additionalDiscountOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }

}
