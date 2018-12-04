import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.BusinessLogicResponseTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.rest.web.controller.BusinessLogicResponseController;
import com.tl.booking.promo.code.service.api.BusinessLogicResponseService;
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

public class BusinessLogicResponseControllerTest extends BusinessLogicResponseTestVariable {

  @InjectMocks
  BusinessLogicResponseController businessLogicResponseController;

  @Mock
  BusinessLogicResponseService businessLogicResponseService;

  private MockMvc mockMvc;

  @Test
  public void findBusinessLogicResponseTest() throws Exception {
    when(businessLogicResponseService
        .findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(BUSINESS_LOGIC_RESPONSE_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BUSINESS_LOGIC_RESPONSE + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(businessLogicResponseService)
        .findBusinessLogicResponses(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Test
  public void findBusinessLogicResponseByIdTest() throws Exception {
    when(businessLogicResponseService
        .findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST,
            ID)).thenReturn(Single.just(BUSINESS_LOGIC_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BUSINESS_LOGIC_RESPONSE + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(businessLogicResponseService)
        .findBusinessLogicResponseById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findBusinessLogicResponseFilterPaginatedTest() throws Exception {
    when(businessLogicResponseService
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            RESPONSE_CODE, PAGE, SIZE, BusinessLogicResponseColumn.ID, SortDirection.ASC))
        .thenReturn(
            Single.just(BUSINESS_LOGIC_RESPONSE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BUSINESS_LOGIC_RESPONSE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("responseCode", RESPONSE_CODE)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", BusinessLogicResponseColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(businessLogicResponseService)
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            RESPONSE_CODE, PAGE,
            SIZE, BusinessLogicResponseColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findBusinessLogicResponseFilterPaginatedTestWithoutParameter() throws Exception {
    when(businessLogicResponseService
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, PAGE,
            SIZE, null, null)).thenReturn(
        Single.just(BUSINESS_LOGIC_RESPONSE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BUSINESS_LOGIC_RESPONSE)
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

    verify(businessLogicResponseService)
        .findBusinessLogicResponseFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, PAGE,
            SIZE, null, null);
  }

  @Test
  public void createBusinessLogicResponseTest() throws Exception {
    when(this.businessLogicResponseService
        .createBusinessLogicResponse(
            CommonTestVariable.MANDATORY_REQUEST,
            BUSINESS_LOGIC_RESPONSE_REQUEST))
        .thenReturn(Single.just(BUSINESS_LOGIC_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BUSINESS_LOGIC_RESPONSE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(BUSINESS_LOGIC_RESPONSE_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.businessLogicResponseService)
        .createBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
            BUSINESS_LOGIC_RESPONSE_REQUEST);
  }

  @Test
  public void updateBusinessLogicResponseTest() throws Exception {
    when(this.businessLogicResponseService
        .updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
            BUSINESS_LOGIC_RESPONSE_REQUEST, ID))
        .thenReturn(Single.just(BUSINESS_LOGIC_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.BUSINESS_LOGIC_RESPONSE + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(BUSINESS_LOGIC_RESPONSE_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.businessLogicResponseService)
        .updateBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST,
            BUSINESS_LOGIC_RESPONSE_REQUEST, this.ID);
  }

  @Test
  public void deleteBusinessLogicResponseTest() throws Exception {
    when(businessLogicResponseService
        .deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.BUSINESS_LOGIC_RESPONSE + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(businessLogicResponseService)
        .deleteBusinessLogicResponse(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(businessLogicResponseController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(businessLogicResponseService);
  }

}
