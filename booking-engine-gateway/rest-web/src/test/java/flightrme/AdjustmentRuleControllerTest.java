//package flightrme;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.tl.booking.gateway.entity.constant.ApiPath;
//import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleColumn;
//import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
//import com.tl.booking.gateway.entity.constant.enums.SortDirection;
//import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
//import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
//import com.tl.booking.gateway.entity.constant.unit.test.flightrme.AdjustmentRuleTestVariable;
//import com.tl.booking.gateway.rest.web.controller.flightrme.AdjustmentRuleController;
//import com.tl.booking.gateway.service.api.flightrme.AdjustmentRuleService;
//import io.reactivex.Single;
//import org.apache.log4j.MDC;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({MDC.class})
//public class AdjustmentRuleControllerTest {
//
//  @InjectMocks
//  AdjustmentRuleController adjustmentRuleController;
//
//  @Mock
//  AdjustmentRuleService adjustmentRuleService;
//
//  private MockMvc mockMvc;
//
//  @Test
//  public void findAdjustmentRuleFilterPaginatedTest() throws Exception {
//    when(this.adjustmentRuleService.findAdjustmentRuleFilterPaginated(
//        CommonTestVariable.MANDATORY_REQUEST,
//        AdjustmentRuleTestVariable.AIRLINE,
//        AdjustmentRuleTestVariable.ORIGIN,
//        AdjustmentRuleTestVariable.DESTINATION,
//        AdjustmentRuleStatus.ACTIVE,
//        AdjustmentRuleTestVariable.PAGE,
//        AdjustmentRuleTestVariable.SIZE,
//        AdjustmentRuleColumn.ID,
//        SortDirection.ASC,
//        AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA
//    )).thenReturn(Single.just(AdjustmentRuleTestVariable.RESULT));
//
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .get(ApiPath.ADJUSTMENT_RULE)
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID)
//        .param("airline", AdjustmentRuleTestVariable.AIRLINE)
//        .param("origin", AdjustmentRuleTestVariable.ORIGIN)
//        .param("destination", AdjustmentRuleTestVariable.DESTINATION)
//        .param("adjustmentRuleStatus", AdjustmentRuleStatus.ACTIVE.getCode())
//        .param("page", AdjustmentRuleTestVariable.PAGE.toString())
//        .param("size", AdjustmentRuleTestVariable.SIZE.toString())
//        .param("columnSort", AdjustmentRuleColumn.ID.toString())
//        .param("sortDirection", SortDirection.ASC.toString());
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).findAdjustmentRuleFilterPaginated(
//        CommonTestVariable.MANDATORY_REQUEST,
//        AdjustmentRuleTestVariable.AIRLINE,
//        AdjustmentRuleTestVariable.ORIGIN,
//        AdjustmentRuleTestVariable.DESTINATION,
//        AdjustmentRuleStatus.ACTIVE,
//        AdjustmentRuleTestVariable.PAGE,
//        AdjustmentRuleTestVariable.SIZE,
//        AdjustmentRuleColumn.ID,
//        SortDirection.ASC,
//        AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA
//    );
//  }
//
//  @Test
//  public void createAdjustmentRuleTest() throws Exception {
//    when(this.adjustmentRuleService.createAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST_CONTROLLER, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .post(ApiPath.ADJUSTMENT_RULE)
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID)
//        .content(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST_BODY);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).createAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST_CONTROLLER, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Test
//  public void updateAdjustmentRuleTest() throws Exception {
//    when(this.adjustmentRuleService.updateAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST_CONTROLLER,
//        AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .put(ApiPath.ADJUSTMENT_RULE + "/{id}", AdjustmentRuleTestVariable.ID).accept(MediaType
//            .APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID)
//        .content(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST_BODY);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).updateAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ADJUSTMENT_RULE_REQUEST_CONTROLLER,
//        AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//  }
//
//  @Test
//  public void findByIdAdjustmentRuleTest() throws Exception {
//
//    when(this.adjustmentRuleService.findAdjustmentRuleById(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .get(ApiPath.ADJUSTMENT_RULE + "/{id}", AdjustmentRuleTestVariable.ID)
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).findAdjustmentRuleById(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Test
//  public void updateToActivatedAdjustmentRuleTest() throws Exception {
//
//    when(this.adjustmentRuleService.updateStatusActivatedAdjustmentRule(CommonTestVariable
//            .MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .put(ApiPath.ADJUSTMENT_RULE + "/" + AdjustmentRuleTestVariable.ID + "/activate")
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).updateStatusActivatedAdjustmentRule(
//        CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Test
//  public void updateToInActivatedAdjustmentRuleTest() throws Exception {
//
//    when(this.adjustmentRuleService.updateStatusUnActivatedAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .put(ApiPath.ADJUSTMENT_RULE + "/" + AdjustmentRuleTestVariable.ID + "/unActivate")
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).updateStatusUnActivatedAdjustmentRule(
//        CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Test
//  public void updateToPendingAdjustmentRuleTest() throws Exception {
//
//    when(this.adjustmentRuleService.updateStatusPendingAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .put(ApiPath.ADJUSTMENT_RULE + "/" + AdjustmentRuleTestVariable.ID + "/pending")
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).updateStatusPendingAdjustmentRule(
//        CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Test
//  public void updateToRejectedAdjustmentRuleTest() throws Exception {
//
//    when(this.adjustmentRuleService.updateStatusRejectedAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.ADJUSTMENT_RULE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .put(ApiPath.ADJUSTMENT_RULE + "/" + AdjustmentRuleTestVariable.ID + "/rejected")
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).updateStatusRejectedAdjustmentRule(
//        CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Test
//  public void deleteAdjustmentRuleTest() throws Exception {
//
//    when(this.adjustmentRuleService.deleteAdjustmentRule(CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(AdjustmentRuleTestVariable.BASE_RESPONSE_BOOLEAN));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .delete(ApiPath.ADJUSTMENT_RULE + "/{id}", AdjustmentRuleTestVariable.ID)
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .header("storeId", CommonTestVariable.STORE_ID)
//        .header("username", CommonTestVariable.USERNAME)
//        .header("channelId", CommonTestVariable.CHANNEL_ID)
//        .header("serviceId", CommonTestVariable.SERVICE_ID)
//        .header("requestId", CommonTestVariable.REQUEST_ID);
//
//    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//    this.mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andReturn();
//
//    verify(this.adjustmentRuleService).deleteAdjustmentRule(
//        CommonTestVariable.MANDATORY_REQUEST, AdjustmentRuleTestVariable.ID, AdjustmentRuleTestVariable.PRIVILEGES,
//        CommonTestVariable.SESSION_DATA);
//
//
//  }
//
//  @Before
//  public void setUp() throws Exception {
//    MockitoAnnotations.initMocks(this);
//
//    PowerMockito.mockStatic(MDC.class);
//    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
//        (AdjustmentRuleTestVariable.PRIVILEGES);
//    PowerMockito.when((String) MDC.get(BaseMongoFields.STORE_ID)).thenReturn
//        (CommonTestVariable.STORE_ID);
//    PowerMockito.when((String) MDC.get(BaseMongoFields.CHANNEL_ID)).thenReturn
//        (CommonTestVariable.CHANNEL_ID);
//    PowerMockito.when((String) MDC.get(BaseMongoFields.SERVICE_ID)).thenReturn
//        (CommonTestVariable.SERVICE_ID);
//    PowerMockito.when((String) MDC.get(BaseMongoFields.REQUEST_ID)).thenReturn
//        (CommonTestVariable.REQUEST_ID);
//    PowerMockito.when((String) MDC.get(BaseMongoFields.USERNAME)).thenReturn
//        (CommonTestVariable.USERNAME);
//
//
//    this.mockMvc = MockMvcBuilders.standaloneSetup(this.adjustmentRuleController).build();
//  }
//
//  @After
//  public void tearDown() throws Exception {
//    verifyNoMoreInteractions(this.adjustmentRuleService);
//  }
//}
