import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.outbound.impl.promocode.CampaignOutboundServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.CampaignColumn;
import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.CampaignTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.CampaignResponse;
import com.tl.booking.gateway.entity.outbound.PromoList.TitleDescriptionRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;


public class CampaignOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  Map<String, String> QUERY_UNACTIVATE;

  Map<String, String> QUERY_ACTIVE;

  Map<String, String> QUERY_NULL;

  GatewayBaseResponse<RestResponsePage<CampaignResponse>> RESULT;

  GatewayBaseResponse<RestResponsePage<CampaignResponse>> RESULT_PENDING;

  GatewayBaseResponse<RestResponsePage<CampaignResponse>> RESULT_ACTIVE;

  RestResponsePage<CampaignResponse> DATA;

  RestResponsePage<CampaignResponse> DATA_PENDING;

  RestResponsePage<CampaignResponse> DATA_ACTIVE;

  CampaignRequest CAMPAIGN_REQUEST;

  CampaignRequest CAMPAIGN_WITHOUT_REQUEST;

  CampaignResponse CAMPAIGN_RESPONSE_GENERATED;

  CampaignResponse CAMPAIGN_RESPONSE_GENERATED_PENDING;

  CampaignResponse CAMPAIGN_RESPONSE_GENERATED_ACTIVE;

  CampaignResponse CAMPAIGN_RESPONSE_GENERATED_NULL;

  GatewayBaseResponse<CampaignResponse> CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE;

  GatewayBaseResponse<CampaignResponse> CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE_PENDING;

  GatewayBaseResponse<CampaignResponse> CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE_ACTIVE;

  GatewayBaseResponse<Boolean> BASE_RESPONSE_BOOLEAN;

  TitleDescriptionRequest TITLE_DESCRIPTION_REQUEST;

  @InjectMocks
  CampaignOutboundServiceImpl campaignOutboundService;

  @Mock
  PromoCodeEndPointService promoCodeEndPointService;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> CAMPAIGN_RESPONSE_PAGE;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> CAMPAIGN_RESPONSE_PAGE_PENDING;

  @Mock
  Call<GatewayBaseResponse<RestResponsePage<CampaignResponse>>> CAMPAIGN_RESPONSE_PAGE_ACTIVE;

  @Mock
  Call<GatewayBaseResponse<List<CampaignDropdownResponse>>> CAMPAIGN_DROPDOWN_RESPONSE_DATA;

  @Mock
  Call<GatewayBaseResponse<CampaignResponse>> CAMPAIGN_RESPONSE;


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
    this.QUERY.put("name", CampaignTestVariable.CODE);
    this.QUERY.put("campaignStatus", CampaignStatus.ACTIVE.getCode());
    this.QUERY.put("page", CampaignTestVariable.PAGE.toString());
    this.QUERY.put("size", CampaignTestVariable.SIZE.toString());
    this.QUERY.put("columnSort", CampaignColumn.ID.getName());
    this.QUERY.put("sortDirection", SortDirection.ASC.getName());

    this.QUERY_UNACTIVATE = new HashMap<>();
    this.QUERY_UNACTIVATE.put("name", CampaignTestVariable.CODE);
    this.QUERY_UNACTIVATE.put("campaignId", CampaignTestVariable.CAMPAIGN_ID);
    this.QUERY_UNACTIVATE.put("page", CampaignTestVariable.PAGE.toString());
    this.QUERY_UNACTIVATE.put("size", CampaignTestVariable.SIZE.toString());
    this.QUERY_UNACTIVATE.put("columnSort", CampaignColumn.ID.getName());
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
    this.DATA.setContent(Arrays.asList(CampaignTestVariable.CAMPAIGN_RESPONSE));

    this.RESULT = new GatewayBaseResponse<>();
    this.RESULT.setCode("SUCCCESS");
    this.RESULT.setData(this.DATA);

    BASE_RESPONSE_BOOLEAN = new GatewayBaseResponse<>();
    BASE_RESPONSE_BOOLEAN.setCode("SUCCESS");
    BASE_RESPONSE_BOOLEAN.setData(true);

  }

  @Test
  public void findCampaignFilterPaginatedTest() throws Exception {
    when(this.promoCodeEndPointService.findCampaignFilterPaginated(this.HEADER, this.QUERY)).thenReturn(CAMPAIGN_RESPONSE_PAGE);
    when(this.CAMPAIGN_RESPONSE_PAGE.execute()).thenReturn(Response.success(CampaignTestVariable.RESULT));

    GatewayBaseResponse<RestResponsePage<CampaignResponse>> promoCodeResponse = this.campaignOutboundService
        .findCampaignFilterPaginated(CommonTestVariable
                .MANDATORY_REQUEST,
            this.QUERY.get("name"), CampaignStatus.ACTIVE, CampaignTestVariable.PAGE,
            CampaignTestVariable.SIZE, CampaignColumn.ID,
            SortDirection.valueOf(this.QUERY.get("sortDirection")),CampaignTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findCampaignFilterPaginated(this.HEADER, this.QUERY);
  }


    @Test
  public void findCampaignFilterPaginatedNullTest() throws Exception {
    when(this.promoCodeEndPointService.findCampaignFilterPaginated(this.HEADER, this
        .QUERY_NULL))
        .thenReturn(CAMPAIGN_RESPONSE_PAGE);

    when(this.CAMPAIGN_RESPONSE_PAGE.execute()).thenReturn(Response.success(this
        .RESULT));
      GatewayBaseResponse<RestResponsePage<CampaignResponse>> promoCodeResponse = this.campaignOutboundService
          .findCampaignFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              null, null, 0,
              10, null,
              null,CampaignTestVariable.PRIVILEGES).blockingGet();

    assertEquals(10,promoCodeResponse.getData().getTotalPages());

    verify(this.promoCodeEndPointService).findCampaignFilterPaginated(this.HEADER, this.QUERY_NULL);
  }

  @Test
  public void findCampaignFilterPaginatedTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.findCampaignFilterPaginated(any(), any())).thenReturn(null);

    try{
      this.campaignOutboundService
          .findCampaignFilterPaginated(CommonTestVariable
                  .MANDATORY_REQUEST,
              this.QUERY.get("name"), CampaignStatus.ACTIVE, 0,
              10, CampaignColumn.valueOf(this.QUERY.get("columnSort")),
              SortDirection.valueOf(this.QUERY.get("sortDirection")),CampaignTestVariable.PRIVILEGES).blockingGet();
    }
    catch (Exception e) {
      verify(this.promoCodeEndPointService).findCampaignFilterPaginated(this.HEADER, this.QUERY);
    }

  }

  @Test
  public void createCampaignTest() throws Exception {
    when(this.promoCodeEndPointService.createCampaign(HEADER, CampaignTestVariable.CAMPAIGN_REQUEST)).thenReturn
        (CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService.createCampaign
        (CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).createCampaign(this.HEADER, CampaignTestVariable.CAMPAIGN_REQUEST);
  }

  @Test
  public void createCampaignTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.createCampaign(HEADER, CampaignTestVariable.CAMPAIGN_REQUEST)).thenReturn
        (null);
    try {
      this.campaignOutboundService
          .createCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).createCampaign(this.HEADER, CampaignTestVariable.CAMPAIGN_REQUEST);
    }
  }

  @Test
  public void updateCampaignTest() throws Exception {
    when(this.promoCodeEndPointService.updateCampaign(HEADER, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID)).thenReturn
        (CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService.updateCampaign
        (CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateCampaign(HEADER, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID);
  }

  @Test
  public void updateCampaignTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.updateCampaign(HEADER, CAMPAIGN_WITHOUT_REQUEST, CampaignTestVariable.ID)).thenReturn
        (null);

    try {
      this.campaignOutboundService
          .updateCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CAMPAIGN_WITHOUT_REQUEST, CampaignTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).updateCampaign(this.HEADER, CAMPAIGN_WITHOUT_REQUEST, CampaignTestVariable.ID);
    }

  }

  @Test
  public void deleteCampaignTest() throws Exception {
    when(this.promoCodeEndPointService.deleteCampaign(any(), any())).thenReturn
        (CALL_TRUE);
    when(this.CALL_TRUE.execute()).thenReturn(Response.success(BASE_RESPONSE_BOOLEAN));

    GatewayBaseResponse<Boolean> valid = this.campaignOutboundService
        .deleteCampaign(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();

    assertEquals(true, valid.getData().booleanValue());

    verify(this.promoCodeEndPointService).deleteCampaign(this.HEADER, CampaignTestVariable.ID);
  }

  @Test
  public void deleteCampaignTestErrorRequest() throws Exception {
    when(this.promoCodeEndPointService.deleteCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (null);
    try {
      this.campaignOutboundService
          .deleteCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();
    }catch (Exception e) {
      verify(this.promoCodeEndPointService).deleteCampaign(this.HEADER, CampaignTestVariable.ID);
    }

  }

  @Test
  public void findByIdTest() throws Exception {
    when(this.promoCodeEndPointService.findCampaignById(HEADER, CampaignTestVariable.ID)).thenReturn
        (CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService
        .findCampaignById(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).findCampaignById(this.HEADER, CampaignTestVariable.ID);
  }

  @Test
  public void findByIdTestRequestError() throws Exception {
    when(this.promoCodeEndPointService.findCampaignById(HEADER, CampaignTestVariable.ID)).thenReturn
        (null);

    try {
      this.campaignOutboundService
          .findCampaignById(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();
    } catch (Exception e) {
      verify(this.promoCodeEndPointService).findCampaignById(this.HEADER, CampaignTestVariable.ID);
    }

  }

   @Test
  public void updateCampaignToActivatedTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusActivateCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (this.CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService
        .updateStatusActivateCampaign(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();

     assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateStatusActivateCampaign(this.HEADER, CampaignTestVariable.ID);
  }

  @Test
  public void updateCampaignToActivatedTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusActivateCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (null);

    try{
      this.campaignOutboundService
          .updateStatusActivateCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusActivateCampaign(this.HEADER, CampaignTestVariable.ID);
    }
  }


  @Test
  public void updateCampaignToInActivatedTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusUnActivateCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (this.CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService
        .updateStatusUnActivateCampaign(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateStatusUnActivateCampaign(this.HEADER, CampaignTestVariable.ID);
  }


  @Test
  public void updateCampaignToInActivatedTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusUnActivateCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (null);

    try{
      this.campaignOutboundService
          .updateStatusUnActivateCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusUnActivateCampaign(this.HEADER, CampaignTestVariable.ID);
    }
  }


  @Test
  public void updateCampaignToPendingTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusPendingCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (this.CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService
        .updateStatusPendingCampaign(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateStatusPendingCampaign(this.HEADER, CampaignTestVariable.ID);
  }

  @Test
  public void updateCampaignTopendingTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusPendingCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (null);

    try{
      this.campaignOutboundService
          .updateStatusPendingCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusPendingCampaign(this.HEADER, CampaignTestVariable.ID);
    }
  }



  @Test
  public void updateCampaignToRejectTest() throws Exception {
    when(this.promoCodeEndPointService.updateStatusRejectedCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (this.CAMPAIGN_RESPONSE);
    when(this.CAMPAIGN_RESPONSE.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    GatewayBaseResponse<CampaignResponse> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService
        .updateStatusRejectedCampaign(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().getCode(), CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE.getData().getCode());

    verify(this.promoCodeEndPointService).updateStatusRejectedCampaign(this.HEADER, CampaignTestVariable.ID);
  }

  @Test
  public void updateCampaignToRejectTestExceptionError() throws Exception {
    when(this.promoCodeEndPointService.updateStatusRejectedCampaign(HEADER, CampaignTestVariable.ID)).thenReturn
        (null);

    try{
      this.campaignOutboundService
          .updateStatusRejectedCampaign(CommonTestVariable
              .MANDATORY_REQUEST, CampaignTestVariable.ID).blockingGet();
    }
    catch(Exception e)
    {
      verify(this.promoCodeEndPointService).updateStatusRejectedCampaign(this.HEADER, CampaignTestVariable.ID);
    }
  }

  @Test
  public void findCampaignActive() throws Exception {
    when(this.promoCodeEndPointService.findCampaignActivate(HEADER)).thenReturn
        (this.CAMPAIGN_DROPDOWN_RESPONSE_DATA);
    when(this.CAMPAIGN_DROPDOWN_RESPONSE_DATA.execute()).thenReturn(Response.success(CampaignTestVariable.CAMPAIGN_DROPDOWN_RESPONSE_LIST));

    GatewayBaseResponse<List<CampaignDropdownResponse>> promoCodeResponseGatewayBaseResponse = this.campaignOutboundService
        .findCampaignActivate(CommonTestVariable
            .MANDATORY_REQUEST).blockingGet();

    assertEquals(promoCodeResponseGatewayBaseResponse.getData().get(0).getId(), CampaignTestVariable.CAMPAIGN_DROPDOWN_RESPONSE_LIST.getData().get(0).getId());

    verify(this.promoCodeEndPointService).findCampaignActivate(this.HEADER);
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeEndPointService);
  }

  private static Date stringToDate(String date) {
    String pattern = "";
    if(date.length() == 19){
      pattern = "yyyy-MM-dd HH:mm:ss";
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
      return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    } else if(date.length() == 10){
      pattern = "yyyy-MM-dd";

      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(pattern, Locale.ENGLISH);

      LocalDate localDate = LocalDate.parse(date, formatter);

      return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    return null;
  }

}
