import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.VariableSourceTestVariable;
import com.tl.booking.promo.code.rest.web.controller.VariableSourceController;
import com.tl.booking.promo.code.service.api.VariableSourceService;
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

public class VariableSourceControllerTest extends VariableSourceTestVariable {

  @InjectMocks
  VariableSourceController variableSourceController;

  @Mock
  VariableSourceService variableSourceService;

  private MockMvc mockMvc;

  @Test
  public void findVariableSourceTest() throws Exception {
    when(variableSourceService
        .findVariableSourceBySourceTypeKey(CommonTestVariable.MANDATORY_REQUEST, SOURCE_TYPE, KEY))
        .thenReturn(Single.just(VARIABLE_SOURCE_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.VARIABLE_SOURCE_PATH + "/{sourceType}", SOURCE_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("key", KEY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(variableSourceService)
        .findVariableSourceBySourceTypeKey(CommonTestVariable.MANDATORY_REQUEST, SOURCE_TYPE, KEY);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(variableSourceController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(variableSourceService);
  }
}
