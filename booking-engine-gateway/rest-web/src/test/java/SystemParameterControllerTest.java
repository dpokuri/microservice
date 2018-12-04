import static org.hamcrest.CoreMatchers.equalTo;
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

import com.tl.booking.gateway.rest.web.controller.SystemParameterController;
import com.tl.booking.gateway.service.api.SystemParameterService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.SystemParameterTestVariable;
import com.tl.booking.gateway.entity.dao.SystemParameter;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class SystemParameterControllerTest {

  @InjectMocks
  SystemParameterController systemParameterController;
  @Mock
  SystemParameterService systemParameterService;
  private MockMvc mockMvc;

  @Test
  public void findAllSystemParametersByStoreIdTest() throws Exception {
    List<SystemParameter> systemParameterList = new ArrayList<>();
    systemParameterList.add(SystemParameterTestVariable.SYSTEM_PARAMETER);

    Page<SystemParameter> systemParameterPage = new PageImpl<SystemParameter>(systemParameterList);
    when(this.systemParameterService.findAllSystemParametersByStoreId(CommonTestVariable.STORE_ID,
        SystemParameterTestVariable.PAGE, SystemParameterTestVariable.SIZE))
        .thenReturn(systemParameterPage);

    this.mockMvc
        .perform(
            get(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME)
                .param("page", SystemParameterTestVariable.PAGE.toString())
                .param("limit", (SystemParameterTestVariable.SIZE_STRING)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.systemParameterService)
        .findAllSystemParametersByStoreId(CommonTestVariable.STORE_ID,
            SystemParameterTestVariable.PAGE, SystemParameterTestVariable.SIZE);
  }

  @Test
  public void findSystemParameterByIdTest() throws Exception {
    when(
        this.systemParameterService
            .findSystemParameterById(SystemParameterTestVariable.ID))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

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
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.variable", equalTo(SystemParameterTestVariable.VARIABLE)))
        .andExpect(jsonPath("$.data.value", equalTo(SystemParameterTestVariable.VALUE)))
        .andExpect(
            jsonPath("$.data.description", equalTo(SystemParameterTestVariable.DESCRIPTION)));

    verify(this.systemParameterService)
        .findSystemParameterById(SystemParameterTestVariable.ID);
  }

  @Test
  public void createSystemParameterTest() throws Exception {
    when(this.systemParameterService
        .createSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

    this.mockMvc
        .perform(
            post(ApiPath.SYSTEM_PARAMETER).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST_BODY)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.systemParameterService)
        .createSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST);
  }

  @Test
  public void updateSystemParameterTest() throws Exception {
    when(this.systemParameterService
        .updateSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST,
            SystemParameterTestVariable.ID))
        .thenReturn(SystemParameterTestVariable.SYSTEM_PARAMETER);

    this.mockMvc
        .perform(
            put(ApiPath.SYSTEM_PARAMETER + ApiPath.ID,
                SystemParameterTestVariable.ID).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST_BODY)
                .param("storeId", CommonTestVariable.STORE_ID)
                .param("requestId", CommonTestVariable.REQUEST_ID)
                .param("channelId", CommonTestVariable.CHANNEL_ID)
                .param("serviceId", CommonTestVariable.SERVICE_ID)
                .param("username", CommonTestVariable.USERNAME))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.systemParameterService)
        .updateSystemParameter(SystemParameterTestVariable.SYSTEM_PARAMETER_REQUEST,
            SystemParameterTestVariable.ID);
  }

  @Test
  public void deleteSystemParameterByIdTest() throws Exception {
    when(this.systemParameterService.deleteSystemParameterById(SystemParameterTestVariable.ID))
        .thenReturn(true);

    this.mockMvc
        .perform(
            delete(ApiPath.SYSTEM_PARAMETER + ApiPath.ID,
                SystemParameterTestVariable.ID)
                .accept(MediaType.APPLICATION_JSON)
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
        .andExpect(jsonPath("$.data", equalTo(true)));

    verify(this.systemParameterService)
        .deleteSystemParameterById(SystemParameterTestVariable.ID);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.systemParameterController).build();

    MDC.put("storeId", CommonTestVariable.STORE_ID);
    MDC.put("channelId", CommonTestVariable.CHANNEL_ID);
    MDC.put("username", CommonTestVariable.USERNAME);
    MDC.put("requestId", CommonTestVariable.REQUEST_ID);
    MDC.put("serviceId", CommonTestVariable.SERVICE_ID);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.systemParameterService);
  }
}
