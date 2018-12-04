import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.HolidayOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.HolidayServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.HolidayTestVariable;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HolidayServiceImplTest {

  @InjectMocks
  HolidayServiceImpl holidayServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  HolidayOutboundService holidayOutboundService;

  @Test
  public void findHolidayFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.holidayOutboundService.findHolidayFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, HolidayTestVariable.START_DATE, HolidayTestVariable.END_DATE, HolidayTestVariable.LANG, HolidayTestVariable.CONTENT, HolidayTestVariable.PAGE, HolidayTestVariable.SIZE,
        HolidayTestVariable.COLUMN_SORT, HolidayTestVariable.SORT_DIRECTION)).thenReturn(Single.just(HolidayTestVariable.RESULT));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES)).thenReturn(Single.just(HolidayTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<HolidayResponse>> baseResponse =  this.holidayServiceImpl
        .findHolidayFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.START_DATE, HolidayTestVariable.END_DATE, HolidayTestVariable.LANG, HolidayTestVariable.CONTENT, HolidayTestVariable.PAGE, HolidayTestVariable.SIZE, HolidayTestVariable.COLUMN_SORT, HolidayTestVariable.SORT_DIRECTION,
            HolidayTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(10, baseResponse.getData().getTotalPages());
    verify(this.holidayOutboundService).findHolidayFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, HolidayTestVariable.START_DATE, HolidayTestVariable.END_DATE, HolidayTestVariable.LANG, HolidayTestVariable.CONTENT, HolidayTestVariable.PAGE, HolidayTestVariable.SIZE, HolidayTestVariable.COLUMN_SORT,
        HolidayTestVariable.SORT_DIRECTION);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES);
  }

  @Test
  public void findHolidayByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.holidayOutboundService.findHolidayById(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.ID)).thenReturn(Single.just
        (HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES)).thenReturn(Single.just(HolidayTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<HolidayResponse> baseResponse =  this.holidayServiceImpl
        .findHolidayById(
            CommonTestVariable.MANDATORY_REQUEST,
            HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED.getId(),
            HolidayTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(HolidayTestVariable.ID, baseResponse.getData().getId());
    verify(this.holidayOutboundService).findHolidayById(CommonTestVariable
            .MANDATORY_REQUEST, HolidayTestVariable.ID);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES);
  }

  @Test
  public void createHolidayTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.holidayOutboundService.createHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.HOLIDAY_REQUEST)).thenReturn
        (Single.just
            (HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES)).thenReturn(Single.just(HolidayTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<HolidayResponse> baseResponse =  this.holidayServiceImpl
        .createHoliday(
            CommonTestVariable.MANDATORY_REQUEST,
            HolidayTestVariable.HOLIDAY_REQUEST,
            HolidayTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(HolidayTestVariable.ID, baseResponse.getData().getId());
    verify(this.holidayOutboundService).createHoliday(CommonTestVariable
        .MANDATORY_REQUEST, HolidayTestVariable.HOLIDAY_REQUEST);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES);
  }

  @Test
  public void updateHolidayTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.holidayOutboundService.updateHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.HOLIDAY_REQUEST, HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED.getId
            ())).thenReturn
        (Single.just
        (HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES)).thenReturn(Single.just(HolidayTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<HolidayResponse> baseResponse =  this.holidayServiceImpl
        .updateHoliday(
            CommonTestVariable.MANDATORY_REQUEST,
            HolidayTestVariable.HOLIDAY_REQUEST,
            HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED.getId(),
            HolidayTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(HolidayTestVariable.ID, baseResponse.getData().getId());
    verify(this.holidayOutboundService).updateHoliday(CommonTestVariable
        .MANDATORY_REQUEST, HolidayTestVariable.HOLIDAY_REQUEST, HolidayTestVariable
        .HOLIDAY_RESPONSE_GENERATED.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES);
  }

  @Test
  public void deleteHolidayTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES)).thenReturn(Single
        .just(true));
    when(this.holidayOutboundService.deleteHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED.getId())).thenReturn(Single.just
        (HolidayTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES)).thenReturn(Single.just(HolidayTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.holidayServiceImpl
        .deleteHoliday(
            CommonTestVariable.MANDATORY_REQUEST,
            HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED.getId(),
            HolidayTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.holidayOutboundService).deleteHoliday(CommonTestVariable
        .MANDATORY_REQUEST, HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.HOLIDAY, HolidayTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.PRIVILEGES);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.holidayOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
