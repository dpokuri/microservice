import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.VariableTestVariable;
import com.tl.booking.promo.code.rest.web.controller.VariableController;
import com.tl.booking.promo.code.service.api.VariableService;
import io.reactivex.Single;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class VariableControllerTest extends VariableTestVariable {

  @Mock
  VariableService variableService;

  @InjectMocks
  VariableController variableController;

  private MockMvc mockMvc;

  @Test
  public void findVariableByIdTest() throws Exception {
    when(variableService.findVariableById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(VARIABLE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(variableService)
        .findVariableById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findVariableFilterPaginatedTest() throws Exception {
    when(variableService.findVariableFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        PARAM, InputType.DROPDOWN.getName(), PAGE,
        SIZE, VariableColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(VARIABLE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("param", PARAM)
        .param("inputType", InputType.DROPDOWN.getValue())
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", VariableColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(variableService)
        .findVariableFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            PARAM, InputType.DROPDOWN.getName(), PAGE,
            SIZE, VariableColumn.ID, SortDirection.ASC);
  }


  @Test
  public void findVariableFilterPaginatedTestWithoutParameter() throws Exception {
    when(variableService.findVariableFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        null, null, PAGE,
        SIZE, null, null)).thenReturn(
        Single.just(VARIABLE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE_PATH)
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

    verify(variableService)
        .findVariableFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, null, PAGE,
            SIZE, null, null);
  }

  @Test
  public void createVariableTest() throws Exception {
    when(variableService
        .createVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE_UPDATE_PARAM))
        .thenReturn(Single.just(VARIABLE_RESULT_CREATE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.VARIABLE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(VARIABLE_JSON_BODY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(variableService)
        .createVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE_UPDATE_PARAM);
  }

  @Test
  public void updateVariableTest() throws Exception {
    when(variableService
        .updateVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE_UPDATE_PARAM, ID))
        .thenReturn(Single.just(VARIABLE_RESULT_CREATE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.VARIABLE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(VARIABLE_JSON_BODY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(variableService)
        .updateVariable(CommonTestVariable.MANDATORY_REQUEST, VARIABLE_UPDATE_PARAM, ID);
  }

  @Test
  public void deleteVariableTest() throws Exception {
    when(variableService
        .deleteVariable(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.VARIABLE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(variableService)
        .deleteVariable(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findAllVariableTest() throws Exception {
    when(variableService.findVariables(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(VARIABLE_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE_PATH + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(variableService)
        .findVariables(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(variableController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(variableService);
  }
}
