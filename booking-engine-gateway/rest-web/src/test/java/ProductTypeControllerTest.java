import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.ProductTypeController;
import com.tl.booking.gateway.service.api.promocode.ProductTypeService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.ProductTypeTestVariable;

import io.reactivex.Single;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(PowerMockRunner.class)
@PrepareForTest({MDC.class})
public class ProductTypeControllerTest {

  @InjectMocks
  ProductTypeController variableController;

  @Mock
  ProductTypeService variableService;

  private MockMvc mockMvc;

  @Test
  public void findProductTypeFilterPaginatedTest() throws Exception {
    when(this.variableService.findProductTypeFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.NAME,
        ProductTypeTestVariable.PAGE,
        ProductTypeTestVariable.SIZE,
        ProductTypeColumn.ID,
        SortDirection.ASC,
        ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(ProductTypeTestVariable.RESULT));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PRODUCT_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("name", ProductTypeTestVariable.NAME)
        .param("page", ProductTypeTestVariable.PAGE.toString())
        .param("size", ProductTypeTestVariable.SIZE.toString())
        .param("columnSort", ProductTypeColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).findProductTypeFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.NAME,
        ProductTypeTestVariable.PAGE,
        ProductTypeTestVariable.SIZE,
        ProductTypeColumn.ID,
        SortDirection.ASC,
        ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }


  @Test
  public void createProductTypeTest() throws Exception {
    when(this.variableService.createProductType(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PRODUCT_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(ProductTypeTestVariable.PRODUCT_TYPE_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).createProductType(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateProductTypeTest() throws Exception {
    when(this.variableService.updateProductType(
        CommonTestVariable.MANDATORY_REQUEST,
        ProductTypeTestVariable.PRODUCT_TYPE_REQUEST,
        ProductTypeTestVariable.ID,
        ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PRODUCT_TYPE + "/{id}", ProductTypeTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(ProductTypeTestVariable.PRODUCT_TYPE_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).updateProductType(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRODUCT_TYPE_REQUEST, ProductTypeTestVariable.ID, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findByIdProductTypeTest() throws Exception {

    when(this.variableService.findProductTypeById(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.ID, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.PRODUCT_TYPE + "/{id}", ProductTypeTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).findProductTypeById(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.ID, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void deleteProductTypeTest() throws Exception {

    when(this.variableService.deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.ID, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(ProductTypeTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PRODUCT_TYPE + "/{id}", ProductTypeTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).deleteProductType(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.ID, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void findProductTypeTest() throws Exception {

    when(this.variableService.findProductTypes(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(ProductTypeTestVariable.PRODUCT_TYPE_RESPONSE_LIST_DATA));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PRODUCT_TYPE + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.variableService).findProductTypes(CommonTestVariable.MANDATORY_REQUEST, ProductTypeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (ProductTypeTestVariable.PRIVILEGES);
    PowerMockito.when((String) MDC.get(BaseMongoFields.STORE_ID)).thenReturn
        (CommonTestVariable.STORE_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.CHANNEL_ID)).thenReturn
        (CommonTestVariable.CHANNEL_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.SERVICE_ID)).thenReturn
        (CommonTestVariable.SERVICE_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.REQUEST_ID)).thenReturn
        (CommonTestVariable.REQUEST_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.USERNAME)).thenReturn
        (CommonTestVariable.USERNAME);


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.variableController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.variableService);
  }
}
