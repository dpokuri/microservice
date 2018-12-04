package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.MDC;

public class HotelServiceImplTest extends HotelTestVariable {

  @InjectMocks
  private
  HotelServiceImpl hotelService;

  @Mock
  private HotelOutboundService hotelOutboundService;

  @Mock
  private PrivilegeService privilegeService;

  @Test
  public void findHotelByAddressTest() {

    when(hotelOutboundService
        .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_ADDRESS_PARAM))
        .thenReturn(Single.just(HOTEL_RESPONSE_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Map<String, String>>> hotelResponse =
        hotelService
            .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_ADDRESS_PARAM,
                CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_RESPONSE_DUMMY, hotelResponse);
    verify(hotelOutboundService)
        .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_ADDRESS_PARAM);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findHotelNameByHotelIdsTest() {

    when(hotelOutboundService
        .findHotelNameByHotelIds(CommonTestVariable.MANDATORY_REQUEST,
            FIND_HOTEL_BY_HOTEL_ID_PARAM))
        .thenReturn(Single.just(HOTEL_RESPONSE_DUMMY));

    when(privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, CommonTestVariable.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Map<String, String>>> hotelResponse =
        hotelService.findHotelNameByHotelIds(
            CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_HOTEL_ID_PARAM,
            CommonTestVariable.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(HOTEL_RESPONSE_DUMMY, hotelResponse);
    verify(hotelOutboundService)
        .findHotelNameByHotelIds(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_HOTEL_ID_PARAM);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelOutboundService);
  }

}
