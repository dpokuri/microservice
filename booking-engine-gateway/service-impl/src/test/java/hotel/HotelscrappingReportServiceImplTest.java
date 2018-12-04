package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.api.hotel.HotelScrappingReportOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.hotel.HotelScrappingReportServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelScrappingReportTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class HotelscrappingReportServiceImplTest {

  @InjectMocks
  HotelScrappingReportServiceImpl hotelScrappingReportService;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  HotelScrappingReportOutboundService hotelScrappingReportOutboundService;

  @Test
  public void getPriceReportData() throws Exception{
    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelScrappingReportOutboundService.getPriceReportData(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelScrappingReportTestVariable.page,
        HotelScrappingReportTestVariable.limit,
        HotelScrappingReportTestVariable.regionId,
        "1",
        "1",
        HotelScrappingReportTestVariable.hotelID,
        HotelScrappingReportTestVariable.sort,
        HotelScrappingReportTestVariable.method
    )).thenReturn(Single.just(HotelScrappingReportTestVariable.RESULT_PAGE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> GatewayBaseResponse =
        this.hotelScrappingReportService.getPriceReportData(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelScrappingReportTestVariable.page,
            HotelScrappingReportTestVariable.limit,
            HotelScrappingReportTestVariable.regionId,
            "1",
            "1",
            HotelScrappingReportTestVariable.hotelID,
            HotelScrappingReportTestVariable.sort,
            HotelScrappingReportTestVariable.method,
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelScrappingReportTestVariable.RESULT_PAGE, GatewayBaseResponse);
    verify(this.hotelScrappingReportOutboundService).getPriceReportData(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelScrappingReportTestVariable.page,
        HotelScrappingReportTestVariable.limit,
        HotelScrappingReportTestVariable.regionId,
        "1",
        "1",
        HotelScrappingReportTestVariable.hotelID,
        HotelScrappingReportTestVariable.sort,
        HotelScrappingReportTestVariable.method);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);
  }

  @Test
  public void getEmailReport(){

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelScrappingReportOutboundService.getEmailReport(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelScrappingReportTestVariable.regionId,
        HotelScrappingReportTestVariable.hotelID,
        "1",
        "1",
        "testing@testing.com"
    )).thenReturn(Single.just(HotelScrappingReportTestVariable.RESULT_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<Boolean> GatewayBaseResponse =
        this.hotelScrappingReportService.getEmailReport(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelScrappingReportTestVariable.regionId,
            HotelScrappingReportTestVariable.hotelID,
            "1",
            "1",
            "testing@testing.com",
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelScrappingReportTestVariable.RESULT_BOOLEAN, GatewayBaseResponse);
    verify(this.hotelScrappingReportOutboundService).getEmailReport(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelScrappingReportTestVariable.regionId,
        HotelScrappingReportTestVariable.hotelID,
        "1",
        "1",
        "testing@testing.com");
    verify(this.privilegeService).checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE);

  }

  @Test
  public void exportReportTest(){

    when(this.privilegeService
        .checkAuthorized(PrivilegeId.MM_MODULE, PrivilegeId.MM_MODULE)).thenReturn(Single.just(true));

    when(this.hotelScrappingReportOutboundService.exportReport(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelScrappingReportTestVariable.regionId,
        HotelScrappingReportTestVariable.hotelID,
        "1",
        "1"
    )).thenReturn(Single.just(HotelScrappingReportTestVariable.RESULT_STRING));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        CommonTestVariable.MM_MODULE)).thenReturn(Single.just(CommonTestVariable
        .PRIVILEGE_RESPONSE));

    GatewayBaseResponse<String> GatewayBaseResponse =
        this.hotelScrappingReportService.exportReport(
            CommonTestVariable.MANDATORY_REQUEST,
            HotelScrappingReportTestVariable.regionId,
            HotelScrappingReportTestVariable.hotelID,
            "1",
            "1",
            PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA)
            .blockingGet();

    assertEquals(HotelScrappingReportTestVariable.RESULT_STRING, GatewayBaseResponse);
    verify(this.hotelScrappingReportOutboundService).exportReport(
        CommonTestVariable.MANDATORY_REQUEST,
        HotelScrappingReportTestVariable.regionId,
        HotelScrappingReportTestVariable.hotelID,
        "1",
        "1");
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
    verifyNoMoreInteractions(this.hotelScrappingReportOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }

}
