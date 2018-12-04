import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.image.entity.constant.ApiPath;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.image.entity.constant.unit.test.SystemParameterTestVariable;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.rest.web.controller.ErrorHandlerController;
import com.tl.booking.image.rest.web.controller.SystemParameterController;
import com.tl.booking.image.service.api.SystemParameterService;
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
  public void businessLogicExceptionTest() throws Exception {
    when(this.systemParameterService.findSystemParameterByStoreId(CommonTestVariable.STORE_ID, SystemParameterTestVariable.VARIABLE)).thenThrow(new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
        ResponseCode.DATA_NOT_EXIST.getMessage()));
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
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.DATA_NOT_EXIST.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.DATA_NOT_EXIST.getMessage())));

    verify(this.systemParameterService).findSystemParameterByStoreId(CommonTestVariable.STORE_ID, SystemParameterTestVariable.VARIABLE);
  }

  @Test
  public void exceptionTest() throws Exception {
    this.mockMvc
        .perform(
            patch(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SYSTEM_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SYSTEM_ERROR.getMessage())));
  }

  @Test
  public void runTimeExceptionTest() throws Exception {
    when(this.systemParameterService.findSystemParameterByStoreId(CommonTestVariable.STORE_ID, SystemParameterTestVariable.VARIABLE)).thenThrow(new RuntimeException(ResponseCode.RUNTIME_ERROR.getMessage()));
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
        .andExpect(status().is5xxServerError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SYSTEM_ERROR.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SYSTEM_ERROR.getMessage())));
    verify(this.systemParameterService).findSystemParameterByStoreId(CommonTestVariable.STORE_ID, SystemParameterTestVariable.VARIABLE);
  }

  @Test
  public void arrayIndexOutOfoutBoundsExceptionTest() throws Exception {
    when(this.systemParameterService.findSystemParameterByStoreId(CommonTestVariable.STORE_ID, SystemParameterTestVariable.VARIABLE))
        .thenThrow(new ArrayIndexOutOfBoundsException(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getMessage()));
    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", CommonTestVariable.USERNAME)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE)
        )
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getMessage())))
        .andExpect(jsonPath("$.data", equalTo(null)));
    verify(this.systemParameterService).findSystemParameterByStoreId(CommonTestVariable.STORE_ID, SystemParameterTestVariable.VARIABLE);
  }

  @Test
  public void methodArgumentNotValidExceptionTest() throws Exception {
    this.mockMvc
        .perform(
            post(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST_JSON_METHOD_ARGUMENT)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("variable", SystemParameterTestVariable.VARIABLE))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.METHOD_ARGUMENTS_NOT_VALID.getMessage())));
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.systemParameterController)
        .setControllerAdvice(new ErrorHandlerController()).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.systemParameterService);
  }
}

