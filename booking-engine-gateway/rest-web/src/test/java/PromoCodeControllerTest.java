import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.PromoCodeController;
import com.tl.booking.gateway.service.api.promocode.PromoCodeService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.PromoCodeTestVariable;

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
public class PromoCodeControllerTest {

  @InjectMocks
  PromoCodeController promoCodeController;

  @Mock
  PromoCodeService promoCodeService;

  private MockMvc mockMvc;

//   @Test
//   public void findPromoCodeFilterPaginatedTest() throws Exception {
//     when(this.promoCodeService.findPromoCodeFilterPaginated(
//         CommonTestVariable.MANDATORY_REQUEST,
//         PromoCodeTestVariable.CODE,
//         PromoCodeTestVariable.CAMPAIGN_ID,
//         PromoCodeTestVariable.PAGE,
//         PromoCodeTestVariable.SIZE,
//         PromoCodeColumn.ID,
//         SortDirection.ASC,
//         PromoCodeTestVariable.PRIVILEGES,
//         CommonTestVariable.SESSION_DATA
//     )).thenReturn(Single.just(PromoCodeTestVariable.RESULT));

//     MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//         .get(ApiPath.PROMO_CODE)
//         .accept(MediaType.APPLICATION_JSON)
//         .contentType(MediaType.APPLICATION_JSON)
//         .header("storeId", CommonTestVariable.STORE_ID)
//         .header("username", CommonTestVariable.USERNAME)
//         .header("channelId", CommonTestVariable.CHANNEL_ID)
//         .header("serviceId", CommonTestVariable.SERVICE_ID)
//         .header("requestId", CommonTestVariable.REQUEST_ID)
//         .param("code", PromoCodeTestVariable.CODE)
//         .param("campaignId", PromoCodeTestVariable.CAMPAIGN_ID)
//         .param("page", PromoCodeTestVariable.PAGE.toString())
//         .param("size", PromoCodeTestVariable.SIZE.toString())
//         .param("columnSort", PromoCodeColumn.ID.toString())
//         .param("sortDirection", SortDirection.ASC.toString());

//     MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

//     this.mockMvc.perform(asyncDispatch(deferredResult))
//         .andExpect(status().isOk())
//         .andReturn();

//     verify(this.promoCodeService).findPromoCodeFilterPaginated(
//         CommonTestVariable.MANDATORY_REQUEST,
//         PromoCodeTestVariable.CODE,
//         PromoCodeTestVariable.CAMPAIGN_ID,
//         PromoCodeTestVariable.PAGE,
//         PromoCodeTestVariable.SIZE,
//         PromoCodeColumn.ID,
//         SortDirection.ASC,
//         PromoCodeTestVariable.PRIVILEGES,
//         CommonTestVariable.SESSION_DATA
//     );


//   }


  // @Test
//   public void createPromoCodeTest() throws Exception {
//     when(this.promoCodeService.createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.PRIVILEGES,
//         CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));
//
//     MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//         .post(ApiPath.PROMO_CODE)
//         .accept(MediaType.APPLICATION_JSON)
//         .contentType(MediaType.APPLICATION_JSON)
//         .header("storeId", CommonTestVariable.STORE_ID)
//         .header("username", CommonTestVariable.USERNAME)
//         .header("channelId", CommonTestVariable.CHANNEL_ID)
//         .header("serviceId", CommonTestVariable.SERVICE_ID)
//         .header("requestId", CommonTestVariable.REQUEST_ID)
//         .content(PromoCodeTestVariable.PROMO_CODE_JSON_BODY);
//
//     MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();
//
//     this.mockMvc.perform(asyncDispatch(deferredResult))
//         .andExpect(status().isOk())
//         .andReturn();
//
//     verify(this.promoCodeService).createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.PRIVILEGES,
//         CommonTestVariable.SESSION_DATA);
//
//
//   }

  @Test
  public void updatePromoCodeTest() throws Exception {
    when(this.promoCodeService.updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE + "/{id}", PromoCodeTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(PromoCodeTestVariable.PROMO_CODE_JSON_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.promoCodeService).updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.PROMO_CODE_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findByIdPromoCodeTest() throws Exception {

    when(this.promoCodeService.findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.PROMO_CODE + "/{id}", PromoCodeTestVariable.ID)
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

    verify(this.promoCodeService).findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

    @Test
  public void updateToActivatedPromoCodeTest() throws Exception {

    when(this.promoCodeService.updateStatusActivatePromoCode(CommonTestVariable
            .MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE + "/" + PromoCodeTestVariable.ID + "/activate")
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

    verify(this.promoCodeService).updateStatusActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateToInActivatedPromoCodeTest() throws Exception {

    when(this.promoCodeService.updateStatusUnActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeTestVariable.PROMO_CODE_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE + "/" + PromoCodeTestVariable.ID + "/unActivate")
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

    verify(this.promoCodeService).updateStatusUnActivatePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void deletePromoCodeTest() throws Exception {

    when(this.promoCodeService.deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(PromoCodeTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PROMO_CODE + "/{id}", PromoCodeTestVariable.ID)
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

    verify(this.promoCodeService).deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, PromoCodeTestVariable.ID, PromoCodeTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (PromoCodeTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.promoCodeController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeService);
  }
}
