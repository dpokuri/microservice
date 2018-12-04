import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.rest.web.controller.ErrorHandlerController;
import com.tl.booking.gateway.rest.web.controller.SystemParameterController;
import com.tl.booking.gateway.service.api.SystemParameterService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.SystemParameterTestVariable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class ErrorHandlerControllerTest {

  @InjectMocks
  SystemParameterController systemParameterController;

  @Mock
  SystemParameterService systemParameterService;
  private MockMvc mockMvc;

  @Test
  public void bindExceptionTestEmptyStoreId() throws Exception {
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", "")
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.BIND_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.BIND_ERROR.getMessage())));
  }

  @Test
  public void bindExceptionTestEmptyRequestId() throws Exception {
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", "")
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.BIND_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.BIND_ERROR.getMessage())));
  }

  @Test
  public void bindExceptionTestEmptyChannelId() throws Exception {
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", "")
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.BIND_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.BIND_ERROR.getMessage())));
  }

  @Test
  public void bindExceptionTestEmptyServiceId() throws Exception {
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", "")
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.BIND_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.BIND_ERROR.getMessage())));
  }

  @Test
  public void bindExceptionTestEmptyUsername() throws Exception {
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", "")
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.BIND_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.BIND_ERROR.getMessage())));
  }

  @Test
  public void runTimeExceptionfindSystemParameterByIdTest() throws Exception {
    when(
        this.systemParameterService
            .findSystemParameterById(SystemParameterTestVariable.ID))
        .thenThrow(new RuntimeException("Runtime"));

    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER + ApiPath.ID,
                SystemParameterTestVariable.ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.RUNTIME_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.RUNTIME_ERROR.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.systemParameterService)
        .findSystemParameterById(SystemParameterTestVariable.ID);
  }

  @Test
  public void businessLogicExceptionfindSystemParameterByIdTest() throws Exception {
    when(
        this.systemParameterService
            .findSystemParameterById(SystemParameterTestVariable.ID))
        .thenThrow(new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
            ResponseCode.DATA_NOT_EXIST.getMessage()));

    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER + ApiPath.ID,
                SystemParameterTestVariable.ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.DATA_NOT_EXIST.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.DATA_NOT_EXIST.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.systemParameterService)
        .findSystemParameterById(SystemParameterTestVariable.ID);
  }

  @Test
  public void exceptionfindSystemParameterByIdTest() throws Exception {

    this.mockMvc
        .perform(
            post(ApiPath.SYSTEM_PARAMETER + ApiPath.ID,
                SystemParameterTestVariable.ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SYSTEM_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SYSTEM_ERROR.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.systemParameterController)
        .setControllerAdvice(new ErrorHandlerController()).build();
  }

  @Test
  public void methodNotArgumentNotValidExceptionTest() throws Exception {
    this.mockMvc
        .perform(
            post(ApiPath.SYSTEM_PARAMETER)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .content(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST_BODY_METHOD))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getMessage())));
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.systemParameterService);
  }
}
