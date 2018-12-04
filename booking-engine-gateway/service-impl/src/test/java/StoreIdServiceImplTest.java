import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.StoreIdOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.StoreIdServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.StoreIdTestVariable;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class StoreIdServiceImplTest {

  @InjectMocks
  StoreIdServiceImpl storeIdServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  StoreIdOutboundService storeIdOutboundService;

  @Test
  public void storeIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_STORE_ID, StoreIdTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.storeIdOutboundService.findAll(CommonTestVariable.MANDATORY_REQUEST)).thenReturn(Single.just
        (StoreIdTestVariable.STORE_ID_RESPONSE_LIST));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        StoreIdTestVariable.PRIVILEGES)).thenReturn(Single.just(StoreIdTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<StoreIdResponse>> baseResponse =  this.storeIdServiceImpl
        .findStoreId(
            CommonTestVariable.MANDATORY_REQUEST,
            StoreIdTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(StoreIdTestVariable.campaignDropdownResponseList, baseResponse.getData());
    verify(this.storeIdOutboundService).findAll(CommonTestVariable.MANDATORY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_ALL_STORE_ID, StoreIdTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        StoreIdTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.storeIdOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
