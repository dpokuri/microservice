import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.PromoCategoryController;
import com.tl.booking.gateway.service.api.PromoCategoryService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.PromoCategoryColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoCategoryTestVariable;

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
public class PromoCategoryControllerTest {

  @InjectMocks
  PromoCategoryController promoCategoryController;

  @Mock
  PromoCategoryService promoCategoryService;

  private MockMvc mockMvc;

  @Test
  public void findPromoPageFilterPaginatedTest() throws Exception {
    when(this.promoCategoryService.findPromoCategoryFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.CATEGORY,
        PromoCategoryTestVariable.PAGE,
        PromoCategoryTestVariable.SIZE,
        PromoCategoryColumn.ID,
        SortDirection.ASC,
        PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(PromoCategoryTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CATEGORY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("category", PromoCategoryTestVariable.CATEGORY)
        .param("page", PromoCategoryTestVariable.PAGE.toString())
        .param("size", PromoCategoryTestVariable.SIZE.toString())
        .param("columnSort", PromoPageColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.getValue().toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoCategoryService).findPromoCategoryFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.CATEGORY,
        PromoCategoryTestVariable.PAGE,
        PromoCategoryTestVariable.SIZE,
        PromoCategoryColumn.ID,
        SortDirection.ASC,
        PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );

  }

  @Test
  public void createPromoPageTest() throws Exception {
    when(this.promoCategoryService.createPromoCategory(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCategoryTestVariable.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PROMO_CATEGORY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoCategoryTestVariable.PROMO_CATEGORY_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoCategoryService).createPromoCategory(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updatePromoPageTest() throws Exception {
    when(this.promoCategoryService.updatePromoCategory(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST, PromoCategoryTestVariable.ID, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCategoryTestVariable.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CATEGORY + "/{id}", PromoCategoryTestVariable.ID).accept(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoCategoryTestVariable.PROMO_CATEGORY_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoCategoryService).updatePromoCategory(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.PROMO_CATEGORY_REQUEST, PromoCategoryTestVariable.ID, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void findBySlugPromoPageTest() throws Exception {

    when(this.promoCategoryService.findPromoCategoryById(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.ID, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCategoryTestVariable.PROMO_CATEGORY_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.PROMO_CATEGORY + "/{id}", PromoCategoryTestVariable.ID)
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

    verify(this.promoCategoryService).findPromoCategoryById(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.ID, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void deletePromoPageTest() throws Exception {

    when(this.promoCategoryService.deletePromoCategory(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.ID, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCategoryTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PROMO_CATEGORY + "/{slug}", PromoCategoryTestVariable.ID)
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

    verify(this.promoCategoryService).deletePromoCategory(CommonTestVariable.MANDATORY_REQUEST, PromoCategoryTestVariable.ID, PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void getCategoriesTest() throws Exception {
    when(this.promoCategoryService.getCategories(
        CommonTestVariable.MANDATORY_REQUEST,PromoCategoryTestVariable.LANG,
        PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(PromoCategoryTestVariable.MAP_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CATEGORY + "/getCategories")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("lang", PromoCategoryTestVariable.LANG);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoCategoryService).getCategories(
        CommonTestVariable.MANDATORY_REQUEST,PromoCategoryTestVariable.LANG,
        PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);

  }

  @Test
  public void getListCategoriesTest() throws Exception {
    when(this.promoCategoryService.getListCategories(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.LANG,
        PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(PromoCategoryTestVariable.MAP_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CATEGORY + "/getListCategories")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("lang", PromoCategoryTestVariable.LANG);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoCategoryService).getListCategories(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCategoryTestVariable.LANG,
        PromoCategoryTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);

  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (PromoCategoryTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.promoCategoryController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCategoryService);
  }
}
