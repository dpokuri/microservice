import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.PromoCodeAdjustmentController;
import com.tl.booking.gateway.service.api.promocode.PromoCodeAdjustmentService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeAdjustmentTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeTestVariable;

import io.reactivex.Single;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
public class PromoCodeAdjustmentControllerTest {

  @InjectMocks
  PromoCodeAdjustmentController campaignController;

  @Mock
  PromoCodeAdjustmentService campaignService;

  private MockMvc mockMvc;

  @Test
  public void findPromoCodeAdjustmentFilterPaginatedTest() throws Exception {
    when(this.campaignService.findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.NAME,
        true,
        PromoCodeAdjustmentTestVariable.PAGE,
        PromoCodeAdjustmentTestVariable.SIZE,
        PromoCodeAdjustmentColumn.ID,
        SortDirection.ASC,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_ADJUSTMENT)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("name", PromoCodeAdjustmentTestVariable.NAME)
        .param("isPromoCodeAdjustmentCombine", String.valueOf(true))
        .param("page", PromoCodeAdjustmentTestVariable.PAGE.toString())
        .param("size", PromoCodeAdjustmentTestVariable.SIZE.toString())
        .param("columnSort", PromoCodeAdjustmentColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).findPromoCodeAdjustmentFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.NAME,
        true,
        PromoCodeAdjustmentTestVariable.PAGE,
        PromoCodeAdjustmentTestVariable.SIZE,
        PromoCodeAdjustmentColumn.ID,
        SortDirection.ASC,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }


  @Ignore
  public void createPromoCodeAdjustmentTest() throws Exception {
    when(this.campaignService.createPromoCodeAdjustment(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PROMO_ADJUSTMENT)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_CREATE_REQUEST_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).createPromoCodeAdjustment(
        CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }

  @Ignore
  public void updatePromoCodeAdjustmentTest() throws Exception {
    when(this.campaignService.updatePromoCodeAdjustment(
        any(),
        any(),
        any(),
        any(),
        any()
    )).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_ADJUSTMENT + "/{id}", PromoCodeAdjustmentTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUSTMENT_CREATE_REQUEST_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.campaignService).updatePromoCodeAdjustment(any(), any(), any(), any(),
        any());
  }

  @Test
  public void findByIdPromoCodeAdjustmentTest() throws Exception {

    when(this.campaignService.findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.PROMO_ADJUSTMENT + "/{id}", PromoCodeAdjustmentTestVariable.ID)
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

    verify(this.campaignService).findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

    @Test
  public void updateToActivatedPromoCodeAdjustmentTest() throws Exception {

    when(this.campaignService.updateStatusActivatePromoCodeAdjustment(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_ADJUSTMENT + "/" + PromoCodeAdjustmentTestVariable.ID + "/activate")
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

    verify(this.campaignService).updateStatusActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToInActivatedPromoCodeAdjustmentTest() throws Exception {

    when(this.campaignService.updateStatusUnActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_ADJUSTMENT + "/" + PromoCodeAdjustmentTestVariable.ID + "/unActivate")
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

    verify(this.campaignService).updateStatusUnActivatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToPendingPromoCodeAdjustmentTest() throws Exception {

    when(this.campaignService.updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_ADJUSTMENT + "/" + PromoCodeAdjustmentTestVariable.ID + "/pending")
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

    verify(this.campaignService).updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }


  @Test
  public void updateToRejectedPromoCodeAdjustmentTest() throws Exception {

    when(this.campaignService.updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PROMO_CODE_ADJUST_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_ADJUSTMENT + "/" + PromoCodeAdjustmentTestVariable.ID + "/rejected")
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

    verify(this.campaignService).updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }


  @Test
  public void findPromoCodeAdjustmentActivateTest() throws Exception {

    when(this.campaignService.findPromoCodeAdjustmentActivate(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA))
    .thenReturn(Single.just(PromoCodeAdjustmentTestVariable.PRO_DROPDOWN_RESPONSE_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_ADJUSTMENT + ApiPath.FIND_ACTIVATE)
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

    verify(this.campaignService).findPromoCodeAdjustmentActivate(CommonTestVariable.MANDATORY_REQUEST,
        PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }


  @Test
  public void deletePromoCodeAdjustmentTest() throws Exception {

    when(this.campaignService.deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeAdjustmentTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PROMO_ADJUSTMENT + "/{id}", PromoCodeAdjustmentTestVariable.ID)
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

    verify(this.campaignService).deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, PromoCodeAdjustmentTestVariable.ID, PromoCodeAdjustmentTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (PromoCodeAdjustmentTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.campaignController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.campaignService);
  }
}
