import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.ProductTypeTestVariable;
import com.tl.booking.promo.code.rest.web.controller.ProductTypeController;
import com.tl.booking.promo.code.service.api.ProductTypeService;
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

public class ProductTypeControllerTest extends ProductTypeTestVariable {

  @InjectMocks
  ProductTypeController productTypeController;

  @Mock
  ProductTypeService productTypeService;

  private MockMvc mockMvc;

  @Test
  public void findProductTypeByIdTest() throws Exception {
    when(productTypeService.findProductTypeById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(PRODUCT_TYPE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PRODUCT_TYPE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(productTypeService)
        .findProductTypeById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findProductTypeFilterPaginatedTest() throws Exception {
    when(productTypeService.findProductTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, PAGE,
        SIZE, ProductTypeColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(PRODUCT_TYPE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PRODUCT_TYPE_PATH)
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

    verify(productTypeService)
        .findProductTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE,
            SIZE, ProductTypeColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findProductTypeFilterPaginatedTestWithoutParameter() throws Exception {
    when(productTypeService.findProductTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        null, PAGE,
        SIZE, null, null)).thenReturn(
        Single.just(PRODUCT_TYPE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PRODUCT_TYPE_PATH)
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

    verify(productTypeService)
        .findProductTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, PAGE,
            SIZE, null, null);
  }


  @Test
  public void createProductTypeTest() throws Exception {
    when(productTypeService
        .createProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE_REQUEST))
        .thenReturn(Single.just(PRODUCT_TYPE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PRODUCT_TYPE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(PRODUCT_TYPE_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(productTypeService)
        .createProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE_REQUEST);
  }

  @Test
  public void updateProductTypeTest() throws Exception {
    when(productTypeService
        .updateProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE_REQUEST, ID))
        .thenReturn(Single.just(PRODUCT_TYPE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PRODUCT_TYPE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(PRODUCT_TYPE_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(productTypeService)
        .updateProductType(CommonTestVariable.MANDATORY_REQUEST, PRODUCT_TYPE_REQUEST, ID);
  }

  @Test
  public void deleteProductTypeTest() throws Exception {
    when(productTypeService
        .deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PRODUCT_TYPE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(productTypeService)
        .deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findAllProductTypeTest() throws Exception {
    when(productTypeService
        .findProductTypes(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(PRODUCT_TYPE_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PRODUCT_TYPE_PATH + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(productTypeService)
        .findProductTypes(CommonTestVariable.MANDATORY_REQUEST);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(productTypeController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(productTypeService);
  }
}
