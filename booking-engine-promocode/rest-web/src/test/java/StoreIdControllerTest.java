import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.StoreIdColumn;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.StoreIdTestVariable;
import com.tl.booking.promo.code.rest.web.controller.StoreIdController;
import com.tl.booking.promo.code.service.api.StoreIdService;
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

public class StoreIdControllerTest extends StoreIdTestVariable {

  @InjectMocks
  StoreIdController storeIdController;

  @Mock
  StoreIdService storeIdService;

  private MockMvc mockMvc;

  @Test
  public void findStoreIdByIdTest() throws Exception {
    when(storeIdService.findStoreIdById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(STORE_ID));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.STORE_ID_PATH + "/" + ID)
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
        .findStoreIdById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findStoreIdFilterPaginatedTest() throws Exception {
    when(storeIdService.findStoreIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, PAGE,
        SIZE, StoreIdColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(STORE_ID_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.STORE_ID_PATH)
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
        .findStoreIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE,
            SIZE, StoreIdColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findStoreIdFilterPaginatedTestWithoutParameter() throws Exception {
    when(storeIdService.findStoreIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        null, PAGE,
        SIZE, null, null)).thenReturn(
        Single.just(STORE_ID_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.STORE_ID_PATH)
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
        .findStoreIdFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, PAGE,
            SIZE, null, null);
  }


  @Test
  public void createStoreIdTest() throws Exception {
    when(storeIdService
        .createStoreId(any(), any()))
        .thenReturn(Single.just(STORE_ID));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.STORE_ID_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(STORE_ID_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(storeIdService)
        .createStoreId(any(), any());
  }

  @Test
  public void updateStoreIdTest() throws Exception {
    when(storeIdService
        .updateStoreId(any(), any(), any()))
        .thenReturn(Single.just(STORE_ID));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.STORE_ID_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(STORE_ID_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(storeIdService)
        .updateStoreId(any(), any(), any());
  }

  @Test
  public void deleteStoreIdTest() throws Exception {
    when(storeIdService
        .deleteStoreId(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.STORE_ID_PATH + "/" + ID)
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
        .deleteStoreId(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findAllStoreIdTest() throws Exception {
    when(storeIdService
        .findStoreIds(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(STORE_ID_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.STORE_ID_PATH + "/findAll")
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
        .findStoreIds(CommonTestVariable.MANDATORY_REQUEST);
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
