import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.BusinessLogicResponseController;
import com.tl.booking.gateway.service.api.promocode.BusinessLogicResponseService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BusinessLogicResponseTestVariable;

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
public class BusinessLogicControllerTest {

  @InjectMocks
  BusinessLogicResponseController variableController;

  @Mock
  BusinessLogicResponseService variableService;

  private MockMvc mockMvc;

  @Test
  public void findBusinessLogicResponseFilterPaginatedTest() throws Exception {
    when(this.variableService.findBusinessLogicResponseFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.CODE,
        BusinessLogicResponseTestVariable.PAGE,
        BusinessLogicResponseTestVariable.SIZE,
        BusinessLogicResponseColumn.ID,
        SortDirection.ASC,
        BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(BusinessLogicResponseTestVariable.RESULT));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BUSINESS_LOGIC)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("responseCode", BusinessLogicResponseTestVariable.CODE)
        .param("page", BusinessLogicResponseTestVariable.PAGE.toString())
        .param("size", BusinessLogicResponseTestVariable.SIZE.toString())
        .param("columnSort", BusinessLogicResponseColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).findBusinessLogicResponseFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.CODE,
        BusinessLogicResponseTestVariable.PAGE,
        BusinessLogicResponseTestVariable.SIZE,
        BusinessLogicResponseColumn.ID,
        SortDirection.ASC,
        BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }


  @Test
  public void createBusinessLogicResponseTest() throws Exception {
    when(this.variableService.createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BUSINESS_LOGIC)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateBusinessLogicResponseTest() throws Exception {
    when(this.variableService.updateBusinessLogicResponse(
        CommonTestVariable.MANDATORY_REQUEST,
        BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST,
        BusinessLogicResponseTestVariable.ID,
        BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.BUSINESS_LOGIC + "/{id}", BusinessLogicResponseTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.BUSINESS_LOGIC_REQUEST, BusinessLogicResponseTestVariable.ID, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findByIdBusinessLogicResponseTest() throws Exception {

    when(this.variableService.findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.BUSINESS_LOGIC + "/{id}", BusinessLogicResponseTestVariable.ID)
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

    verify(this.variableService).findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void deleteBusinessLogicResponseTest() throws Exception {

    when(this.variableService.deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BusinessLogicResponseTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.BUSINESS_LOGIC + "/{id}", BusinessLogicResponseTestVariable.ID)
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

    verify(this.variableService).deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.ID, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void findBusinessLogicResponseTest() throws Exception {

    when(this.variableService.findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BusinessLogicResponseTestVariable.BUSINESS_LOGIC_RESPONSE_LIST_DATA));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BUSINESS_LOGIC + "/findAll")
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

    verify(this.variableService).findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST, BusinessLogicResponseTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (BusinessLogicResponseTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.variableController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.variableService);
  }
}
