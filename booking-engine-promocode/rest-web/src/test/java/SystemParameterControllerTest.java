import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.SystemParameterTestVariable;
import com.tl.booking.promo.code.rest.web.controller.SystemParameterController;
import com.tl.booking.promo.code.service.api.SystemParameterService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class SystemParameterControllerTest {

  @InjectMocks
  SystemParameterController systemParameterController;
  @Mock
  SystemParameterService systemParameterService;
  private MockMvc mockMvc;

  @Test
  public void findAllTest() throws Exception {
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER + "/all").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE)
                .param("page", SystemParameterTestVariable.PAGE.toString())
                .param("limit", (SystemParameterTestVariable.SIZE_STRING)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.systemParameterService)
        .findAll(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.PAGE, SystemParameterTestVariable.SIZE);
  }

  @Test
  public void findByStoreIdAndVariableTest() throws Exception {
    when(
        this.systemParameterService
            .findSystemParameterByStoreId(CommonTestVariable.STORE_ID,
                SystemParameterTestVariable.VARIABLE))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.variable", equalTo(SystemParameterTestVariable.VARIABLE)))
        .andExpect(jsonPath("$.data.value", equalTo(SystemParameterTestVariable.VALUE)))
        .andExpect(
            jsonPath("$.data.description", equalTo(SystemParameterTestVariable.DESCRIPTION)));

    verify(this.systemParameterService)
        .findSystemParameterByStoreId(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);
  }

  @Test
  public void createSystemParameterTest() throws Exception {
    this.mockMvc
        .perform(
            post(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data", equalTo(null)));

    verify(this.systemParameterService)
        .createSystemParameter(anyObject());
  }

  @Test
  public void updateSystemParameterTest() throws Exception {
    this.mockMvc
        .perform(
            put(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data", equalTo(null)));

    verify(this.systemParameterService)
        .updateSystemParameter(anyObject());
  }

  @Test
  public void deleteByStoreIdAndVariableTest() throws Exception {
    this.mockMvc
        .perform(
            delete(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data", equalTo(null)));

    verify(this.systemParameterService)
        .deleteByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.VARIABLE);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.systemParameterController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.systemParameterService);
  }
}
