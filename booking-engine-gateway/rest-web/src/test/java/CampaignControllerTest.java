import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.CampaignController;
import com.tl.booking.gateway.service.api.promocode.CampaignService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.CampaignColumn;
import com.tl.booking.gateway.entity.constant.enums.CampaignStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.CampaignTestVariable;

import io.reactivex.Single;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(PowerMockRunner.class)
@PrepareForTest({MDC.class})
public class CampaignControllerTest {

  @InjectMocks
  CampaignController campaignController;

  @Mock
  CampaignService campaignService;

  private MockMvc mockMvc;

  @Test
  public void findCampaignFilterPaginatedTest() throws Exception {
    when(this.campaignService.findCampaignFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.NAME,
        CampaignStatus.ACTIVE,
        CampaignTestVariable.PAGE,
        CampaignTestVariable.SIZE,
        CampaignColumn.ID,
        SortDirection.ASC,
        CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(CampaignTestVariable.RESULT));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CAMPAIGN)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("name", CampaignTestVariable.NAME)
        .param("campaignStatus", CampaignStatus.ACTIVE.toString())
        .param("page", CampaignTestVariable.PAGE.toString())
        .param("size", CampaignTestVariable.SIZE.toString())
        .param("columnSort", CampaignColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).findCampaignFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        CampaignTestVariable.NAME,
        CampaignStatus.ACTIVE,
        CampaignTestVariable.PAGE,
        CampaignTestVariable.SIZE,
        CampaignColumn.ID,
        SortDirection.ASC,
        CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }


  @Test
  public void createCampaignTest() throws Exception {
    when(this.campaignService.createCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.CAMPAIGN)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(CampaignTestVariable.CAMPAIGN_REQUEST_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).createCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateCampaignTest() throws Exception {
    when(this.campaignService.updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN + "/{id}", CampaignTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(CampaignTestVariable.CAMPAIGN_REQUEST_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.CAMPAIGN_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findByIdCampaignTest() throws Exception {

    when(this.campaignService.findCampaignById(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.CAMPAIGN + "/{id}", CampaignTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).findCampaignById(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

    @Test
  public void updateToActivatedCampaignTest() throws Exception {

    when(this.campaignService.updateStatusActivateCampaign(CommonTestVariable
            .MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN + "/" + CampaignTestVariable.ID + "/activate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).updateStatusActivateCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToInActivatedCampaignTest() throws Exception {

    when(this.campaignService.updateStatusUnActivateCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN + "/" + CampaignTestVariable.ID + "/unActivate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).updateStatusUnActivateCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToPendingCampaignTest() throws Exception {

    when(this.campaignService.updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN + "/" + CampaignTestVariable.ID + "/pending")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }


  @Test
  public void updateToRejectedCampaignTest() throws Exception {

    when(this.campaignService.updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN + "/" + CampaignTestVariable.ID + "/rejected")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }


  // @Test
//   public void findCampaignActivateTest() throws Exception {
//
//     when(this.campaignService.findCampaignActivate(CommonTestVariable.MANDATORY_REQUEST,
//         CampaignTestVariable.PRIVILEGES,
//         CommonTestVariable.SESSION_DATA))
//     .thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_DROPDOWN_RESPONSE_LIST));
//
//     MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//         .get(ApiPath.CAMPAIGN + ApiPath.FIND_ACTIVATE)
//         .accept(MediaType.APPLICATION_JSON)
//         .contentType(MediaType.APPLICATION_JSON)
//         .header("storeId", CommonTestVariable.STORE_ID)
//         .header("username", CommonTestVariable.USERNAME)
//         .header("channelId", CommonTestVariable.CHANNEL_ID)
//         .header("serviceId", CommonTestVariable.SERVICE_ID)
//         .header("requestId", CommonTestVariable.REQUEST_ID);
//
//     MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//     this.mockMvc.perform(asyncDispatch(deferredResult))
//         .andExpect(status().isOk())
//         .andReturn();
//
//     verify(this.campaignService).findCampaignActivate(CommonTestVariable.MANDATORY_REQUEST,
//         CampaignTestVariable.PRIVILEGES,
//         CommonTestVariable.SESSION_DATA);
//
//
//   }


  @Test
  public void deleteCampaignTest() throws Exception {

    when(this.campaignService.deleteCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(CampaignTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.CAMPAIGN + "/{id}", CampaignTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).deleteCampaign(CommonTestVariable.MANDATORY_REQUEST, CampaignTestVariable.ID, CampaignTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (CampaignTestVariable.PRIVILEGES);
    PowerMockito.when((String) MDC.get(BaseMongoFields.STORE_ID)).thenReturn
        (CommonTestVariable.STORE_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.CHANNEL_ID)).thenReturn
        (CommonTestVariable.CHANNEL_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.SERVICE_ID)).thenReturn
        (CommonTestVariable.SERVICE_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.REQUEST_ID)).thenReturn
        (CommonTestVariable.REQUEST_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.USERNAME)).thenReturn
        (CommonTestVariable.USERNAME);


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.campaignController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.campaignService);
  }
}
