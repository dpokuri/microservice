import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.VariableController;
import com.tl.booking.gateway.service.api.promocode.VariableService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.InputType;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.VariableTestVariable;

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
public class VariableControllerTest {

  @InjectMocks
  VariableController variableController;

  @Mock
  VariableService variableService;

  private MockMvc mockMvc;

  @Test
  public void findVariableFilterPaginatedTest() throws Exception {
    when(this.variableService.findVariableFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PARAM,
        InputType.AUTOCOMPLETE.getName(),
        VariableTestVariable.PAGE,
        VariableTestVariable.SIZE,
        VariableColumn.ID,
        SortDirection.ASC,
        VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(VariableTestVariable.RESULT));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("param", VariableTestVariable.PARAM)
        .param("inputType", InputType.AUTOCOMPLETE.toString())
        .param("page", VariableTestVariable.PAGE.toString())
        .param("size", VariableTestVariable.SIZE.toString())
        .param("columnSort", VariableColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).findVariableFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        VariableTestVariable.PARAM,
        InputType.AUTOCOMPLETE.getName(),
        VariableTestVariable.PAGE,
        VariableTestVariable.SIZE,
        VariableColumn.ID,
        SortDirection.ASC,
        VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }


  @Test
  public void findByIdVariableTest() throws Exception {

    when(this.variableService.findVariableById(CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.ID, VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(VariableTestVariable.VARIABLE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.VARIABLE + "/{id}", VariableTestVariable.ID)
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

    verify(this.variableService).findVariableById(CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.ID, VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void deleteVariableTest() throws Exception {

    when(this.variableService.deleteVariable(CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.ID, VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(VariableTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.VARIABLE + "/{id}", VariableTestVariable.ID)
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

    verify(this.variableService).deleteVariable(CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.ID, VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void findAllVariableTest() throws Exception {

    when(this.variableService.findAllVariable(CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(VariableTestVariable.VARIABLE_ALL_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE + "/findAll")
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

    verify(this.variableService).findAllVariable(CommonTestVariable.MANDATORY_REQUEST, VariableTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (VariableTestVariable.PRIVILEGES);
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
