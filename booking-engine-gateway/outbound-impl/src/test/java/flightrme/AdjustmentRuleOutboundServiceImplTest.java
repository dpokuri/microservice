package flightrme;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.FlightRMEEndPointService;
import com.tl.booking.gateway.outbound.impl.flightrme.AdjustmentRuleOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleColumn;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.flightrme.AdjustmentRuleTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;

public class AdjustmentRuleOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> RESULT_ACTIVE;

  RestResponsePage<AdjustmentRuleResponse> DATA;

  RestResponsePage<AdjustmentRuleResponse> DATA_PENDING;

  RestResponsePage<AdjustmentRuleResponse> DATA_ACTIVE;

  AdjustmentRuleRequest ADJUSTMENT_RULE_REQUEST;

  AdjustmentRuleRequest ADJUSTMENT_RULE_WITHOUT_REQUEST;

  AdjustmentRuleResponse ADJUSTMENT_RULE_RESPONSE_GENERATED;

  AdjustmentRuleResponse ADJUSTMENT_RULE_RESPONSE_GENERATED_PENDING;

  AdjustmentRuleResponse ADJUSTMENT_RULE_RESPONSE_GENERATED_ACTIVE;

  AdjustmentRuleResponse ADJUSTMENT_RULE_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<AdjustmentRuleResponse> ADJUSTMENT_RULE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<AdjustmentRuleResponse> ADJUSTMENT_RULE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<AdjustmentRuleResponse> ADJUSTMENT_RULE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  AdjustmentRuleOutboundServiceImpl adjustmentRuleOutboundService;

  @Mock
  FlightRMEEndPointService flightRMEEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> ADJUSTMENT_RULE_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> ADJUSTMENT_RULE_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> ADJUSTMENT_RULE_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<AdjustmentRuleResponse>> ADJUSTMENT_RULE_RESPONSE;


  @Mock
  Call<GatewayBaseResponse<Boolean>> CALL_TRUE;


  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.HEADER = new HashMap<>();
    this.HEADER.put("storeId", CommonTestVariable.STORE_ID);
    this.HEADER.put("channelId", CommonTestVariable.CHANNEL_ID);
    this.HEADER.put("requestId", CommonTestVariable.REQUEST_ID);
    this.HEADER.put("serviceId", CommonTestVariable.SERVICE_ID);
    this.HEADER.put("username", CommonTestVariable.USERNAME);

    this.QUERY = new HashMap<>();
    this.QUERY.put("airline", AdjustmentRuleTestVariable.AIRLINE);
    this.QUERY.put("origin", AdjustmentRuleTestVariable.ORIGIN);
    this.QUERY.put("destination", AdjustmentRuleTestVariable.DESTINATION);
    this.QUERY.put("status", AdjustmentRuleStatus.ACTIVE.getCode());
    this.QUERY.put("page", AdjustmentRuleTestVariable.PAGE.toString());
    this.QUERY.put("size", AdjustmentRuleTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", AdjustmentRuleColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("adjustmentRuleId", AdjustmentRuleTestVariable.ID);
    this.QUERY_UNACTIVATE.put("page", AdjustmentRuleTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", AdjustmentRuleTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", AdjustmentRuleColumn.ID.getName());
    this.QUERY_UNACTIVATE.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_ACTIVE = new HashMap<>();
    this.QUERY_ACTIVE.put("title", "test123");
    this.QUERY_ACTIVE.put("categories", "cate123");
    this.QUERY_ACTIVE.put("status", "ACTIVE");
    this.QUERY_ACTIVE.put("precedence", "1");
    this.QUERY_ACTIVE.put("page", "0");
    this.QUERY_ACTIVE.put("size", "10");
    this.QUERY_ACTIVE.put("columnSort", "ID");
    this.QUERY_ACTIVE.put("sortDirection", "DESC");

    this.QUERY_NULL = new HashMap<>();
    this.QUERY_NULL.put("page", "0");
    this.QUERY_NULL.put("size", "10");

    this.DATA = new RestResponsePage<>();
    this.DATA.setTotalPages(10);
    this.DATA.setContent(Arrays.asList(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findAdjustmentRuleFilterPaginatedTest() throws Exception {
    when(this.flightRMEEndPointService.findAdjustmentRuleFilterPaginated(this.HEADER, this.QUERY)).thenReturn(ADJUSTMENT_RULE_RESPONSE_PAGE);
    when(this.ADJUSTMENT_RULE_RESPONSE_PAGE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> adjustmentRuleResponse = this.adjustmentRuleOutboundService
        .findAdjustmentRuleFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("airline"), this.QUERY.get("origin"),this.QUERY.get("destination"), AdjustmentRuleStatus.ACTIVE,
            AdjustmentRuleTestVariable.PAGE,  AdjustmentRuleTestVariable.SIZE, AdjustmentRuleColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),AdjustmentRuleTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,adjustmentRuleResponse.getData().getTotalPages());

    verify(this.flightRMEEndPointService).findAdjustmentRuleFilterPaginated(this.HEADER, this.QUERY);
  }


  @Test
  public void findAdjustmentRuleFilterPaginatedNullTest() throws Exception {
    when(this.flightRMEEndPointService.findAdjustmentRuleFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(ADJUSTMENT_RULE_RESPONSE_PAGE);

    when(this.ADJUSTMENT_RULE_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
    GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>> adjustmentRuleResponse = this.adjustmentRuleOutboundService
        .findAdjustmentRuleFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            null, null,null, null, 0,
            10, null,
            null,AdjustmentRuleTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,adjustmentRuleResponse.getData().getTotalPages());

    verify(this.flightRMEEndPointService).findAdjustmentRuleFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findAdjustmentRuleFilterPaginatedTestErrorRequest() throws Exception {
    when(this.flightRMEEndPointService.findAdjustmentRuleFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.adjustmentRuleOutboundService
          .findAdjustmentRuleFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("airline"), this.QUERY.get("origin"),this.QUERY.get("destination"), AdjustmentRuleStatus.ACTIVE,
              0, 10, AdjustmentRuleColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),AdjustmentRuleTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.flightRMEEndPointService).findAdjustmentRuleFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createAdjustmentRuleTest() throws Exception {
    when(this.flightRMEEndPointService.createAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST)).thenReturn
        (ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService.createAdjustmentRule
        (CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).createAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST);
  }

  @Test
  public void createAdjustmentRuleTestErrorRequest() throws Exception {
    when(this.flightRMEEndPointService.createAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST)).thenReturn
        (null);
    try {
      this.adjustmentRuleOutboundService
          .createAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.flightRMEEndPointService).createAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST);
    }
  }

  @Test
  public void updateAdjustmentRuleTest() throws Exception {
    when(this.flightRMEEndPointService.updateAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST, AdjustmentRuleTestVariable.ID)).thenReturn
        (ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService.updateAdjustmentRule
        (CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).updateAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST, AdjustmentRuleTestVariable.ID);
  }

  @Test
  public void updateAdjustmentRuleTestErrorRequest() throws Exception {
    when(this.flightRMEEndPointService.updateAdjustmentRule(HEADER, ADJUSTMENT_RULE_WITHOUT_REQUEST, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);

    try {
      this.adjustmentRuleOutboundService
          .updateAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, ADJUSTMENT_RULE_WITHOUT_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.flightRMEEndPointService).updateAdjustmentRule(this.HEADER, ADJUSTMENT_RULE_WITHOUT_REQUEST, AdjustmentRuleTestVariable.ID);
    }

  }

  @Test
  public void deleteAdjustmentRuleTest() throws Exception {
    when(this.flightRMEEndPointService.deleteAdjustmentRule(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.adjustmentRuleOutboundService
        .deleteAdjustmentRule(CommonTestVariable
            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.flightRMEEndPointService).deleteAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
  }

  @Test
  public void deleteAdjustmentRuleTestErrorRequest() throws Exception {
    when(this.flightRMEEndPointService.deleteAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);
    try {
      this.adjustmentRuleOutboundService
          .deleteAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.flightRMEEndPointService).deleteAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.flightRMEEndPointService.findAdjustmentById(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService
        .findAdjustmentRuleById(CommonTestVariable
            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).findAdjustmentById(this.HEADER, AdjustmentRuleTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.flightRMEEndPointService.findAdjustmentById(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);

    try {
      this.adjustmentRuleOutboundService
          .findAdjustmentRuleById(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.flightRMEEndPointService).findAdjustmentById(this.HEADER, AdjustmentRuleTestVariable.ID);
    }

  }

  @Test
  public void updateAdjustmentRuleToActivatedTest() throws Exception {
    when(this.flightRMEEndPointService.updateStatusActivateAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (this.ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService
        .updateStatusActivateAdjustmentRule(CommonTestVariable
            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).updateStatusActivateAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
  }

  @Test
  public void updateAdjustmentRuleToActivatedTestExceptionError() throws Exception {
    when(this.flightRMEEndPointService.updateStatusActivateAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);

    try{
      this.adjustmentRuleOutboundService
          .updateStatusActivateAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.flightRMEEndPointService).updateStatusActivateAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
    }
  }

  @Test
  public void updateAdjustmentRuleToInActivatedTest() throws Exception {
    when(this.flightRMEEndPointService.updateStatusUnActivateAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (this.ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService
        .updateStatusUnActivateAdjustmentRule(CommonTestVariable
            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).updateStatusUnActivateAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
  }


  @Test
  public void updateAdjustmentRuleToInActivatedTestExceptionError() throws Exception {
    when(this.flightRMEEndPointService.updateStatusUnActivateAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);

    try{
      this.adjustmentRuleOutboundService
          .updateStatusUnActivateAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.flightRMEEndPointService).updateStatusUnActivateAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
    }
  }

  @Test
  public void updateAdjustmentRuleToPendingTest() throws Exception {
    when(this.flightRMEEndPointService.updateStatusPendingAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (this.ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService
        .updateStatusPendingAdjustmentRule(CommonTestVariable
            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).updateStatusPendingAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
  }

  @Test
  public void updateAdjustmentRuleTopendingTestExceptionError() throws Exception {
    when(this.flightRMEEndPointService.updateStatusPendingAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);

    try{
      this.adjustmentRuleOutboundService
          .updateStatusPendingAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.flightRMEEndPointService).updateStatusPendingAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
    }
  }

  @Test
  public void updateAdjustmentRuleToRejectTest() throws Exception {
    when(this.flightRMEEndPointService.updateStatusRejectedAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (this.ADJUSTMENT_RULE_RESPONSE);
    when(this.ADJUSTMENT_RULE_RESPONSE.execute()).thenReturn(Response.success(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<AdjustmentRuleResponse> adjustmentRuleResponseGatewayBaseResponse = this.adjustmentRuleOutboundService
        .updateStatusRejectedAdjustmentRule(CommonTestVariable
            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();

    assertEquals(adjustmentRuleResponseGatewayBaseResponse.getData().getId(), AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE.getData().getId());

    verify(this.flightRMEEndPointService).updateStatusRejectedAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
  }

  @Test
  public void updateAdjustmentRuleToRejectTestExceptionError() throws Exception {
    when(this.flightRMEEndPointService.updateStatusRejectedAdjustmentRule(HEADER, AdjustmentRuleTestVariable.ID)).thenReturn
        (null);

    try{
      this.adjustmentRuleOutboundService
          .updateStatusRejectedAdjustmentRule(CommonTestVariable
              .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.flightRMEEndPointService).updateStatusRejectedAdjustmentRule(this.HEADER, AdjustmentRuleTestVariable.ID);
    }
  }

}
