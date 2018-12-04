package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelAutoCompleteOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelAutocompleteServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelAutocompleteTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelAutocompleteServiceImplTest {

  @InjectMocks
  HotelAutocompleteServiceImpl hotelAutocompleteService;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  HotelAutoCompleteOutboundService hotelAutoCompleteOutboundService;

  @Test
  public void getAutoCompleteHotel() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single
        .just(true));
    when(this.hotelAutoCompleteOutboundService.getAutoCompleteHotel(
        CommonTestVariable.MANDATORY_REQUEST, HotelAutocompleteTestVariable.otaID, HotelAutocompleteTestVariable.q))
        .thenReturn(Single.just(HotelAutocompleteTestVariable.AUTOCOMPLETE_RETURN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<List<Map<String, String>>> GatewayBaseResponse =
        this.hotelAutocompleteService.getAutoCompleteHotel(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelAutocompleteTestVariable.otaID,
            HotelAutocompleteTestVariable.q,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(HotelAutocompleteTestVariable.AUTOCOMPLETE_RETURN, GatewayBaseResponse);
    verify(this.hotelAutoCompleteOutboundService).getAutoCompleteHotel(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelAutocompleteTestVariable.otaID,
        HotelAutocompleteTestVariable.q);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelAutoCompleteOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }

}
