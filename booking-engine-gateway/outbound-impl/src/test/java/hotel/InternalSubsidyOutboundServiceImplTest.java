package hotel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.HotelSubsidyEndPointService;
import com.tl.booking.gateway.outbound.impl.hotel.InternalSubsidyOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.InternalSubsidyTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class InternalSubsidyOutboundServiceImplTest {

  @InjectMocks
  private InternalSubsidyOutboundServiceImpl internalSubsidyOutboundService;

  @Mock
  private HotelSubsidyEndPointService hotelSubsidyEndPointService;

  @Mock
  private Call<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> RESPONSE_PAGE;

  @Mock
  private Call<GatewayBaseResponse> RESPONSE;

  @Test
  public void createInternalSubsidyTest() throws IOException {
    when(this.hotelSubsidyEndPointService.createInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(Response.success(InternalSubsidyTestVariable.RESULT));

    GatewayBaseResponse internalSubsidyResult = this.internalSubsidyOutboundService
        .createInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
            InternalSubsidyTestVariable.REQUEST)
        .blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, internalSubsidyResult);

    verify(this.hotelSubsidyEndPointService).createInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(), InternalSubsidyTestVariable.REQUEST);
  }

  @Test
  public void createInternalSubsidyExceptionTest() throws IOException {
    when(this.hotelSubsidyEndPointService.createInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(Response.success(InternalSubsidyTestVariable.RESULT));

    try {
      this.internalSubsidyOutboundService
          .createInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
              InternalSubsidyTestVariable.REQUEST)
          .blockingGet();
    } catch (Exception e) {
      verify(this.hotelSubsidyEndPointService).createInternalSubsidy(
          CommonTestVariable.getMandatoryRequestAsMap(), InternalSubsidyTestVariable.REQUEST);
    }
  }

  @Test
  public void updateInternalSubsidyTest() throws IOException {
    when(this.hotelSubsidyEndPointService.updateInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST,
        InternalSubsidyTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    GatewayBaseResponse internalSubsidyResult = this.internalSubsidyOutboundService
        .updateInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
            InternalSubsidyTestVariable.REQUEST,
            InternalSubsidyTestVariable.ID
        ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, internalSubsidyResult);

    verify(this.hotelSubsidyEndPointService).updateInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST,
        InternalSubsidyTestVariable.ID);
  }

  @Test
  public void updateInternalSubsidyExceptionTest() throws IOException {
    when(this.hotelSubsidyEndPointService.updateInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST,
        InternalSubsidyTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    try {
      this.internalSubsidyOutboundService
          .updateInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
              InternalSubsidyTestVariable.REQUEST,
              InternalSubsidyTestVariable.ID
          ).blockingGet();

    } catch (Exception e) {
      verify(this.hotelSubsidyEndPointService).updateInternalSubsidy(
          CommonTestVariable.getMandatoryRequestAsMap(),
          InternalSubsidyTestVariable.REQUEST,
          InternalSubsidyTestVariable.ID);
    }
  }

  @Test
  public void setActiveInternalSubsidyTest() throws IOException {
    when(this.hotelSubsidyEndPointService.setActiveInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST_ACTIVATE,
        InternalSubsidyTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    GatewayBaseResponse internalSubsidyResult = this.internalSubsidyOutboundService
        .setActiveInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
            InternalSubsidyTestVariable.REQUEST_ACTIVATE,
            InternalSubsidyTestVariable.ID
        ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, internalSubsidyResult);

    verify(this.hotelSubsidyEndPointService).setActiveInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST_ACTIVATE,
        InternalSubsidyTestVariable.ID);
  }

  @Test
  public void setActiveInternalSubsidyExceptionTest() throws IOException {
    when(this.hotelSubsidyEndPointService.setActiveInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.REQUEST_ACTIVATE,
        InternalSubsidyTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    try {
      this.internalSubsidyOutboundService
          .setActiveInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
              InternalSubsidyTestVariable.REQUEST_ACTIVATE,
              InternalSubsidyTestVariable.ID
          ).blockingGet();
    } catch (Exception e) {
      verify(this.hotelSubsidyEndPointService).setActiveInternalSubsidy(
          CommonTestVariable.getMandatoryRequestAsMap(),
          InternalSubsidyTestVariable.REQUEST_ACTIVATE,
          InternalSubsidyTestVariable.ID);
    }
  }

  @Test
  public void deleteInternalSubsidyTest() throws IOException {
    when(this.hotelSubsidyEndPointService.deleteInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    GatewayBaseResponse internalSubsidyResult = this.internalSubsidyOutboundService
        .deleteInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
            InternalSubsidyTestVariable.ID
        ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, internalSubsidyResult);

    verify(this.hotelSubsidyEndPointService).deleteInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.ID);
  }

  @Test
  public void deleteInternalSubsidyExceptionTest() throws IOException {
    when(this.hotelSubsidyEndPointService.deleteInternalSubsidy(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    try {
      this.internalSubsidyOutboundService
          .deleteInternalSubsidy(CommonTestVariable.MANDATORY_REQUEST,
              InternalSubsidyTestVariable.ID
          ).blockingGet();
    } catch (Exception e) {
      verify(this.hotelSubsidyEndPointService).deleteInternalSubsidy(
          CommonTestVariable.getMandatoryRequestAsMap(),
          InternalSubsidyTestVariable.ID);
    }
  }

  @Test
  public void findInternalSubsidyByStoreIdAndIdTest() throws IOException {
    when(this.hotelSubsidyEndPointService.findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.ID
    )).thenReturn(RESPONSE);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    GatewayBaseResponse internalSubsidyResult = this.internalSubsidyOutboundService
        .findInternalSubsidyByStoreIdAndId(CommonTestVariable.MANDATORY_REQUEST,
            InternalSubsidyTestVariable.ID
        ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT, internalSubsidyResult);

    verify(this.hotelSubsidyEndPointService).findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.ID);
  }

  @Test
  public void findInternalSubsidyByStoreIdAndIdExceptionTest() throws IOException {
    when(this.hotelSubsidyEndPointService.findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.getMandatoryRequestAsMap(),
        InternalSubsidyTestVariable.ID
    )).thenReturn(null);

    when(RESPONSE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT)
    );

    try {
      this.internalSubsidyOutboundService
          .findInternalSubsidyByStoreIdAndId(CommonTestVariable.MANDATORY_REQUEST,
              InternalSubsidyTestVariable.ID
          ).blockingGet();
    } catch (Exception e) {
      verify(this.hotelSubsidyEndPointService).findInternalSubsidyByStoreIdAndId(
          CommonTestVariable.getMandatoryRequestAsMap(),
          InternalSubsidyTestVariable.ID);
    }
  }

  @Test
  public void findInternalSubsidyByStoreIdTest() throws IOException {
    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", InternalSubsidyTestVariable.page.toString());
    queryParam.put("limit", InternalSubsidyTestVariable.limit.toString());
    queryParam.put("sort", InternalSubsidyTestVariable.sort);
    queryParam.put("method", InternalSubsidyTestVariable.method);

    when(this.hotelSubsidyEndPointService.findInternalSubsidyByStoreId(
        CommonTestVariable.getMandatoryRequestAsMap(),
        queryParam
    )).thenReturn(RESPONSE_PAGE);

    when(RESPONSE_PAGE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT_PAGE)
    );

    GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>> internalSubsidyResult = this.internalSubsidyOutboundService
        .findInternalSubsidyByStoreId(CommonTestVariable.MANDATORY_REQUEST, queryParam
        ).blockingGet();

    assertEquals(InternalSubsidyTestVariable.RESULT_PAGE, internalSubsidyResult);

    verify(this.hotelSubsidyEndPointService).findInternalSubsidyByStoreId(
        CommonTestVariable.getMandatoryRequestAsMap(),
        queryParam);

  }

  @Test
  public void findInternalSubsidyByStoreIdExceptionTest() throws IOException {
    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", InternalSubsidyTestVariable.page.toString());
    queryParam.put("limit", InternalSubsidyTestVariable.limit.toString());
    queryParam.put("sort", InternalSubsidyTestVariable.sort);
    queryParam.put("method", InternalSubsidyTestVariable.method);

    when(this.hotelSubsidyEndPointService.findInternalSubsidyByStoreId(
        CommonTestVariable.getMandatoryRequestAsMap(),
        queryParam
    )).thenReturn(null);

    when(RESPONSE_PAGE.execute()).thenReturn(
        Response.success(InternalSubsidyTestVariable.RESULT_PAGE)
    );

    try {
      this.internalSubsidyOutboundService
          .findInternalSubsidyByStoreId(CommonTestVariable.MANDATORY_REQUEST, queryParam
          ).blockingGet();
    } catch (Exception e) {
      verify(this.hotelSubsidyEndPointService).findInternalSubsidyByStoreId(
          CommonTestVariable.getMandatoryRequestAsMap(),
          queryParam);
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelSubsidyEndPointService);
  }


}
