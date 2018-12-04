import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.promocode.BinNumberOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.promocode.BinNumberServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BinNumberTestVariable;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;

import io.reactivex.Single;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BinNumberServiceImplTest {

  @InjectMocks
  BinNumberServiceImpl binNumberServiceImpl;

  @Mock
  PrivilegeService privilegeService;

  @Mock
  BinNumberOutboundService binNumberOutboundService;

  @Test
  public void findBinNumberFilterPaginatedTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.GET_BIN_NUMBER, BinNumberTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.binNumberOutboundService.findBinNumberFilterPaginated(CommonTestVariable
        .MANDATORY_REQUEST, BinNumberTestVariable.NAME, BinNumberTestVariable.PAGE, BinNumberTestVariable.SIZE,
        BinNumberColumn.ID, SortDirection.ASC, BinNumberTestVariable.PRIVILEGES))
        .thenReturn
        (Single.just(BinNumberTestVariable.RESULT));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES)).thenReturn(Single.just(BinNumberTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<RestResponsePage<BinNumberResponse>> baseResponse =  this.binNumberServiceImpl
        .findBinNumberFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.NAME, BinNumberTestVariable.PAGE, BinNumberTestVariable.SIZE, BinNumberColumn.ID, SortDirection.ASC,
        BinNumberTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
    ).blockingGet();

    assertEquals(BinNumberTestVariable.RESULT, baseResponse);
    verify(this.binNumberOutboundService).findBinNumberFilterPaginated(CommonTestVariable
            .MANDATORY_REQUEST, BinNumberTestVariable.NAME, BinNumberTestVariable.PAGE, BinNumberTestVariable.SIZE,
        BinNumberColumn.ID, SortDirection.ASC, BinNumberTestVariable.PRIVILEGES);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.GET_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES);
  }


  @Test
  public void findBinNumberByIdTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_BIN_NUMBER, BinNumberTestVariable.PRIVILEGES))
        .thenReturn
        (Single
        .just(true));
    when(this.binNumberOutboundService.findBinNumberById(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.ID)).thenReturn(Single.just
        (BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES)).thenReturn(Single.just(BinNumberTestVariable
        .PRIVILEGE_RESPONSE));


    BaseResponse<BinNumberResponse> baseResponse =  this.binNumberServiceImpl
        .findBinNumberById(
            CommonTestVariable.MANDATORY_REQUEST,
            BinNumberTestVariable.ID,
            BinNumberTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BinNumberTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BY_ID_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES);

    verify(this.binNumberOutboundService).findBinNumberById(CommonTestVariable
        .MANDATORY_REQUEST, BinNumberTestVariable.ID);

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES);

  }

  @Test
  public void createBinNumberTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_BIN_NUMBER, BinNumberTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.binNumberOutboundService.createBinNumber(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER_REQUEST)).thenReturn
        (Single.just
            (BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES)).thenReturn(Single.just(BinNumberTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<BinNumberResponse> baseResponse =  this.binNumberServiceImpl
        .createBinNumber(
            CommonTestVariable.MANDATORY_REQUEST,
            BinNumberTestVariable.BIN_NUMBER_REQUEST,
            BinNumberTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BinNumberTestVariable.ID, baseResponse.getData().getId());
    verify(this.binNumberOutboundService).createBinNumber(CommonTestVariable
        .MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES);
  }

  @Test
  public void updateBinNumberTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.binNumberOutboundService.updateBinNumber(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.ID
            )).thenReturn
        (Single.just
        (BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));
    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES)).thenReturn(Single.just(BinNumberTestVariable
        .PRIVILEGE_RESPONSE));
    BaseResponse<BinNumberResponse> baseResponse =  this.binNumberServiceImpl
        .updateBinNumber(
            CommonTestVariable.MANDATORY_REQUEST,
            BinNumberTestVariable.BIN_NUMBER_REQUEST,
            BinNumberTestVariable.ID,
            BinNumberTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BinNumberTestVariable.ID, baseResponse.getData().getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.UPDATE_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES);
    verify(this.binNumberOutboundService).updateBinNumber(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.ID);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES);
  }

  @Test
  public void deleteBinNumberTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.DELETE_BIN_NUMBER, BinNumberTestVariable.PRIVILEGES))
        .thenReturn(Single
        .just(true));
    when(this.binNumberOutboundService.deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER_RESPONSE.getId())).thenReturn(Single.just
        (BinNumberTestVariable.BASE_RESPONSE_BOOLEAN));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES)).thenReturn(Single.just(BinNumberTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<Boolean> baseResponse =  this.binNumberServiceImpl
        .deleteBinNumber(
            CommonTestVariable.MANDATORY_REQUEST,
            BinNumberTestVariable.BIN_NUMBER_RESPONSE.getId(),
            BinNumberTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(true, baseResponse.getData().booleanValue());
    verify(this.binNumberOutboundService).deleteBinNumber(CommonTestVariable
        .MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_RESPONSE.getId());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.DELETE_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES);
  }


  @Test
  public void binNumberTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.FIND_BIN_NUMBER, BinNumberTestVariable.PRIVILEGES))
        .thenReturn(Single
            .just(true));
    when(this.binNumberOutboundService.binNumber(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER, BinNumberTestVariable.BANK_ID,BinNumberTestVariable.CARD_TYPE)).thenReturn(Single.just
        (BinNumberTestVariable.BIN_NUMBER_RESPONSE_LIST_DATA));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES)).thenReturn(Single.just(BinNumberTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<List<String>> baseResponse =  this.binNumberServiceImpl
        .binNumber(
            CommonTestVariable.MANDATORY_REQUEST,
            BinNumberTestVariable.BIN_NUMBER,
            BinNumberTestVariable.BANK_ID,
            BinNumberTestVariable.CARD_TYPE,
            BinNumberTestVariable.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(BinNumberTestVariable.BIN_NUMBER_STRING_LIST, baseResponse.getData());

    verify(this.binNumberOutboundService).binNumber(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER, BinNumberTestVariable.BANK_ID,BinNumberTestVariable.CARD_TYPE);
    verify(this.privilegeService).checkAuthorized(PrivilegeId.FIND_BIN_NUMBER, BinNumberTestVariable
        .PRIVILEGES);
    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.PRIVILEGES);
  }


  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.binNumberOutboundService);
    verifyNoMoreInteractions(this.privilegeService);
  }
}
