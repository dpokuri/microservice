package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.InternalSubsidyOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.InternalSubsidyServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.InternalSubsidyTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class InternalSubsidyServiceImplTest {

  @InjectMocks
  private InternalSubsidyServiceImpl internalSubsidyService;

  @Mock
  private PrivilegeService privilegeService;

  @Mock
  private InternalSubsidyOutboundService internalSubsidyOutboundService;

  @Test
  public void createInternalSubsidyTest() {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.internalSubsidyOutboundService.createInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST
    )).thenReturn(Single.just(InternalSubsidyTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.internalSubsidyService.createInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST, InternalSubsidyTestVariable.REQUEST,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
        .blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, GatewayBaseResponse);

    verify(this.internalSubsidyOutboundService).createInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void updateInternalSubsidyTest() {

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.internalSubsidyOutboundService.updateInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST,
        InternalSubsidyTestVariable.ID
    )).thenReturn(Single.just(InternalSubsidyTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.internalSubsidyService.updateInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST,
        InternalSubsidyTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, GatewayBaseResponse);

    verify(this.internalSubsidyOutboundService).updateInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST,
        InternalSubsidyTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void setActiveInternalSubsidyTest() {
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.internalSubsidyOutboundService.setActiveInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST_ACTIVATE,
        InternalSubsidyTestVariable.ID
    )).thenReturn(Single.just(InternalSubsidyTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.internalSubsidyService.setActiveInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST_ACTIVATE,
        InternalSubsidyTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, GatewayBaseResponse);

    verify(this.internalSubsidyOutboundService).setActiveInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.REQUEST_ACTIVATE,
        InternalSubsidyTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void deleteInternalSubsidyTest() {
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.internalSubsidyOutboundService.deleteInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.ID
    )).thenReturn(Single.just(InternalSubsidyTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.internalSubsidyService.deleteInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, GatewayBaseResponse);

    verify(this.internalSubsidyOutboundService).deleteInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findInternalSubsidyByStoreIdAndIdTest() {
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.internalSubsidyOutboundService.findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.ID
    )).thenReturn(Single.just(InternalSubsidyTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse GatewayBaseResponse = this.internalSubsidyService.findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, GatewayBaseResponse);

    verify(this.internalSubsidyOutboundService).findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        InternalSubsidyTestVariable.ID
    );

    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void findInternalSubsidyByStoreIdTest() {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", InternalSubsidyTestVariable.page.toString());
    queryParam.put("limit", InternalSubsidyTestVariable.limit.toString());
    queryParam.put("q", InternalSubsidyTestVariable.q);
    queryParam.put("countryId", InternalSubsidyTestVariable.countryId);
    queryParam.put("provinceId", InternalSubsidyTestVariable.provinceId);
    queryParam.put("cityId", InternalSubsidyTestVariable.cityId);
    queryParam.put("areaId", InternalSubsidyTestVariable.areaId);
    queryParam.put("sort", InternalSubsidyTestVariable.sort);
    queryParam.put("method", InternalSubsidyTestVariable.method);


    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE))
        .thenReturn(Single.just(true));

    when(this.internalSubsidyOutboundService.findInternalSubsidyByStoreId(
        CommonTestVariable.MANDATORY_REQUEST, queryParam
    )).thenReturn(Single.just(InternalSubsidyTestVariable.RESULT_PAGE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>> GatewayBaseResponse =
        this.internalSubsidyService.findInternalSubsidyByStoreId(
            CommonTestVariable.MANDATORY_REQUEST, queryParam,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT_PAGE, GatewayBaseResponse);

    verify(this.internalSubsidyOutboundService).findInternalSubsidyByStoreId(
        CommonTestVariable.MANDATORY_REQUEST, queryParam
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
    verifyNoMoreInteractions(this.internalSubsidyOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
