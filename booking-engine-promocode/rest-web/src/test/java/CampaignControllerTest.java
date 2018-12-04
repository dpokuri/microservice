import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.CampaignColumn;
import com.tl.booking.promo.code.entity.constant.enums.CampaignStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CampaignTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.libraries.configuration.TimeZoneProperties;
import com.tl.booking.promo.code.libraries.utility.DateFormatterHelper;
import com.tl.booking.promo.code.rest.web.controller.CampaignController;
import com.tl.booking.promo.code.service.api.CampaignService;
import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DateFormatterHelper.class})
public class CampaignControllerTest extends CampaignTestVariable {

  @InjectMocks
  CampaignController campaignController;

  @Mock
  CampaignService campaignService;
  @Mock
  TimeZoneProperties timeZoneProperties;
  private MockMvc mockMvc;

  @Test
  public void createCampaignSuccess() throws Exception {

    when(timeZoneProperties.getOffsetToDate()).thenReturn(0);

    CAMPAIGN_PERIOD.setStartDate(DateFormatterHelper.stringToDate("2018-01-11 17:08:32", 0));
    CAMPAIGN_PERIOD.setEndDate(DateFormatterHelper.stringToDate("2018-01-11 17:08:32", 0));

    when(campaignService
        .createCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST_CREATE))
        .thenReturn(Single.just(CAMPAIGN_REQUEST_CREATE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.CAMPAIGN_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CAMPAIGN_REQUEST_BODY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(campaignService)
        .createCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST_CREATE);
  }

  @Test
  public void updateCampaignTest() throws Exception {

    when(timeZoneProperties.getOffsetToDate()).thenReturn(0);

    CAMPAIGN_PERIOD.setStartDate(DateFormatterHelper.stringToDate("2018-01-11 17:08:32", 0));
    CAMPAIGN_PERIOD.setEndDate(DateFormatterHelper.stringToDate("2018-01-11 17:08:32", 0));

    when(campaignService
        .updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST_CREATE, ID))
        .thenReturn(Single.just(CAMPAIGN_REQUEST_CREATE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CAMPAIGN_REQUEST_BODY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(campaignService)
        .updateCampaign(CommonTestVariable.MANDATORY_REQUEST, CAMPAIGN_REQUEST_CREATE, ID);
  }

  @Test
  public void findCampaignByIdTest() throws Exception {
    when(campaignService.findCampaignById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(CAMPAIGN_REQUEST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CAMPAIGN_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.data.name", equalTo(NAME)))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(campaignService)
        .findCampaignById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findCampaignFilterPaginatedTest() throws Exception {
    when(campaignService.findCampaignFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, CampaignStatus.PENDING, PAGE, SIZE, CampaignColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(CAMPAIGN_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CAMPAIGN_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("name", NAME)
        .param("campaignStatus", CampaignStatus.PENDING.getCode())
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", CampaignColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(campaignService)
        .findCampaignFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, CampaignStatus.PENDING, PAGE, SIZE, CampaignColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findCampaignFilterPaginatedTestWithoutParameter() throws Exception {
    when(campaignService.findCampaignFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        null, null, PAGE, SIZE, null, null)).thenReturn(
        Single.just(CAMPAIGN_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CAMPAIGN_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(campaignService)
        .findCampaignFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, null, PAGE, SIZE, null, null);
  }

  @Test
  public void deleteCampaignTest() throws Exception {
    when(campaignService
        .deleteCampaign(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.CAMPAIGN_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(campaignService)
        .deleteCampaign(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void updateCampaignPendingTest() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.PENDING);

    when(campaignService
        .updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN_PATH + "/" + ID + "/pending")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(campaignService)
        .updateStatusPendingCampaign(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void updateCampaignActiveTest() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.PENDING);

    when(campaignService
        .updateStatusActiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN_PATH + "/" + ID + "/activate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(campaignService)
        .updateStatusActiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void updateCampaignInactiveTest() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.ACTIVE);

    when(campaignService
        .updateStatusInactiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN_PATH + "/" + ID + "/unActivate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(campaignService)
        .updateStatusInactiveCampaign(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findCampaignByStoreIdAndPublishedSuccess() throws Exception {

    when(campaignService.findCampaigns(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(CampaignTestVariable.CAMPAIGN_DROPDOWN_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CAMPAIGN_PATH + ApiPath.FIND_ACTIVATE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(campaignService).findCampaigns(CommonTestVariable.MANDATORY_REQUEST);

  }

  @Test
  public void updateCampaignRejetedTest() throws Exception {
    CAMPAIGN_REQUEST.setCampaignStatus(CampaignStatus.PENDING);

    when(campaignService
        .updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(CAMPAIGN_REQUEST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CAMPAIGN_PATH + "/" + ID + "/rejected")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(campaignService)
        .updateStatusRejectedCampaign(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(campaignService);
  }

}
