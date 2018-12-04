import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.PromoPageController;
import com.tl.booking.gateway.service.api.PromoPageService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;

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
public class PromoPageControllerTest {

  @InjectMocks
  PromoPageController promoPageController;

  @Mock
  PromoPageService promoPageService;

  private MockMvc mockMvc;

  @Test
  public void findPromoPageFilterPaginatedTest() throws Exception {
    when(this.promoPageService.findPromoPageFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.TITLE,
        PromoPageTestVariable.CATEGORIES,
        PromoPageStatus.DRAFT,
        1,
        PromoPageTestVariable.PAGE,
        PromoPageTestVariable.SIZE,
        PromoPageColumn.ID,
        SortDirection.ASC,
        PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(PromoPageTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_PAGE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("title", PromoPageTestVariable.TITLE)
        .param("categories", PromoPageTestVariable.CATEGORIES)
        .param("status", PromoPageStatus.DRAFT.getCode())
        .param("precedence", "1")
        .param("page", PromoPageTestVariable.PAGE.toString())
        .param("size", PromoPageTestVariable.SIZE.toString())
        .param("columnSort", PromoPageColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoPageService).findPromoPageFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.TITLE,
        PromoPageTestVariable.CATEGORIES,
        PromoPageStatus.DRAFT,
        1,
        PromoPageTestVariable.PAGE,
        PromoPageTestVariable.SIZE,
        PromoPageColumn.ID,
        SortDirection.ASC,
        PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }

  @Test
  public void createPromoPageTest() throws Exception {
    when(this.promoPageService.createPromoPage(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.PROMO_PAGE_REQUEST,
        PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PROMO_PAGE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoPageTestVariable.PROMO_PAGE_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoPageService).createPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updatePromoPageTest() throws Exception {
    when(this.promoPageService.updatePromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_PAGE + "/{id}", PromoPageTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoPageTestVariable.PROMO_PAGE_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoPageService).updatePromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.PROMO_PAGE_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void findBySlugPromoPageTest() throws Exception {

    when(this.promoPageService.findPromoPageBySlug(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.PROMO_PAGE + "/{slug}", PromoPageTestVariable.ID)
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

    verify(this.promoPageService).findPromoPageBySlug(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToPendingPromoPageTest() throws Exception {

    when(this.promoPageService.updateStatusPendingPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_PAGE + "/" + PromoPageTestVariable.ID + "/pending")
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

    verify(this.promoPageService).updateStatusPendingPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToActivatedPromoPageTest() throws Exception {

    when(this.promoPageService.updateStatusActivatedPromoPage(CommonTestVariable
            .MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_PAGE + "/" + PromoPageTestVariable.ID + "/activated")
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

    verify(this.promoPageService).updateStatusActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToInActivatedPromoPageTest() throws Exception {

    when(this.promoPageService.updateStatusInActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_PAGE + "/" + PromoPageTestVariable.ID + "/inActivated")
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

    verify(this.promoPageService).updateStatusInActivatedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToRejectedPromoPageTest() throws Exception {

    when(this.promoPageService.updateStatusRejectedPromoPage(CommonTestVariable.MANDATORY_REQUEST,
        PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.PROMO_PAGE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_PAGE + "/" + PromoPageTestVariable.ID + "/rejected")
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

    verify(this.promoPageService).updateStatusRejectedPromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void deletePromoPageTest() throws Exception {

    when(this.promoPageService.deletePromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoPageTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PROMO_PAGE + "/{slug}", PromoPageTestVariable.ID)
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

    verify(this.promoPageService).deletePromoPage(CommonTestVariable.MANDATORY_REQUEST, PromoPageTestVariable.ID, PromoPageTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (PromoPageTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.promoPageController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoPageService);
  }
}
