import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ChannelIdColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.constant.unit.test.ChannelIdTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.rest.web.controller.ChannelIdController;
import com.tl.booking.promo.code.service.api.ChannelIdService;
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

public class ChannelIdControllerTest extends ChannelIdTestVariable {

  @InjectMocks
  ChannelIdController storeIdController;

  @Mock
  ChannelIdService storeIdService;

  private MockMvc mockMvc;

  @Test
  public void findChannelIdByIdTest() throws Exception {
    when(storeIdService.findChannelIdById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(CHANNEL_ID));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CHANNEL_ID_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(storeIdService)
        .findChannelIdById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findChannelIdFilterPaginatedTest() throws Exception {
    when(storeIdService.findChannelIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, PAGE,
        SIZE, ChannelIdColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(CHANNEL_ID_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CHANNEL_ID_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("name", NAME)
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

    verify(storeIdService)
        .findChannelIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE,
            SIZE, ChannelIdColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findChannelIdFilterPaginatedTestWithoutParameter() throws Exception {
    when(storeIdService.findChannelIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        null, PAGE,
        SIZE, null, null)).thenReturn(
        Single.just(CHANNEL_ID_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CHANNEL_ID_PATH)
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

    verify(storeIdService)
        .findChannelIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, PAGE,
            SIZE, null, null);
  }


  @Test
  public void createChannelIdTest() throws Exception {
    when(storeIdService
        .createChannelId(any(), any()))
        .thenReturn(Single.just(CHANNEL_ID));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.CHANNEL_ID_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CHANNEL_ID_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(storeIdService)
        .createChannelId(any(), any());
  }

  @Test
  public void updateChannelIdTest() throws Exception {
    when(storeIdService
        .updateChannelId(any(), any(), any()))
        .thenReturn(Single.just(CHANNEL_ID));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CHANNEL_ID_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CHANNEL_ID_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(storeIdService)
        .updateChannelId(any(), any(), any());
  }

  @Test
  public void deleteChannelIdTest() throws Exception {
    when(storeIdService
        .deleteChannelId(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.CHANNEL_ID_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(storeIdService)
        .deleteChannelId(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findAllChannelIdTest() throws Exception {
    when(storeIdService
        .findChannelIds(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(CHANNEL_ID_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CHANNEL_ID_PATH + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(storeIdService)
        .findChannelIds(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(storeIdController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(storeIdService);
  }
}
