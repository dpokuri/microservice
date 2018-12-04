import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeTestVariable;
import com.tl.booking.promo.code.rest.web.controller.PromoCodeController;
import com.tl.booking.promo.code.service.api.PromoCodeService;
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

public class PromoCodeControllerTest extends PromoCodeTestVariable {

  @Mock
  PromoCodeService promoCodeService;

  @InjectMocks
  PromoCodeController promoCodeController;

  private MockMvc mockMvc;

  @Test
  public void findPromoCodeByIdTest() throws Exception {
    when(this.promoCodeService.findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST,
        this.ID)).thenReturn(Single.just(this.PROMO_CODE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_PATH + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.data.id", equalTo(this.ID)))
        .andExpect(jsonPath("$.data.code", equalTo(this.CODE)))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.promoCodeService)
        .findPromoCodeById(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void findPromoCodeFilterPaginatedTest() throws Exception {
    when(this.promoCodeService.findPromoCodeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        this.CODE, this.CAMPAIGN_ID, this.PAGE,
        this.SIZE, PromoCodeColumn.CODE, SortDirection.ASC)).thenReturn(
        Single.just(this.PROMO_CODE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("code", this.CODE)
        .param("campaignId", this.CAMPAIGN_ID)
        .param("page", this.PAGE.toString())
        .param("size", this.SIZE.toString())
        .param("columnSort", PromoCodeColumn.CODE.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.data.content", hasSize(2)))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.promoCodeService)
        .findPromoCodeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST, this.CODE,
            this.CAMPAIGN_ID, this.PAGE, this.SIZE, PromoCodeColumn.CODE, SortDirection.ASC);
  }

  @Test
  public void findPromoCodeFilterPaginatedTestWithoutOptionalParameter() throws Exception {
    when(this.promoCodeService.findPromoCodeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        this.CODE, this.CAMPAIGN_ID, this.PAGE,
        this.SIZE, null, null)).thenReturn(
        Single.just(this.PROMO_CODE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("code", this.CODE)
        .param("campaignId", this.CAMPAIGN_ID)
        .param("page", this.PAGE.toString())
        .param("size", this.SIZE.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.data.content", hasSize(2)))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.promoCodeService)
        .findPromoCodeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST, this.CODE,
            this.CAMPAIGN_ID, this.PAGE, this.SIZE, null, null);
  }

  @Test
  public void createPromoCodeTest() throws Exception {
    when(this.promoCodeService
        .createPromoCode(CommonTestVariable.MANDATORY_REQUEST, this.PROMO_CODE_REQUEST))
        .thenReturn(Single.just(this.PROMO_CODE_CREATE_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PROMO_CODE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(this.PROMO_CODE_JSON_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id", equalTo(ID)))
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.promoCodeService)
        .createPromoCode(CommonTestVariable.MANDATORY_REQUEST, PROMO_CODE_REQUEST);
  }

  @Test
  public void updatePromoCodeTest() throws Exception {
    when(this.promoCodeService
        .updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, this.PROMO_CODE_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_PATH + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(this.PROMO_CODE_JSON_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.promoCodeService)
        .updatePromoCode(CommonTestVariable.MANDATORY_REQUEST, this.PROMO_CODE_REQUEST, this.ID);
  }

  @Test
  public void deletePromoCodeTest() throws Exception {
    when(this.promoCodeService
        .deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PROMO_CODE_PATH + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.promoCodeService)
        .deletePromoCode(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void updatePromoCodeActivateTest() throws Exception {
    this.PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.ACTIVE);

    when(this.promoCodeService
        .updateStatusActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_PATH + "/" + this.ID + "/activate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.id", equalTo(this.ID)))
        .andExpect(jsonPath("$.data.promoCodeStatus", equalTo(PromoCodeStatus.ACTIVE.getCode())))
        .andReturn();

    verify(this.promoCodeService)
        .updateStatusActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void updatePromoCodeUnActivateTest() throws Exception {
    this.PROMO_CODE.setPromoCodeStatus(PromoCodeStatus.INACTIVE);

    when(this.promoCodeService
        .updateStatusInActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_PATH + "/" + this.ID + "/unActivate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.id", equalTo(this.ID)))
        .andExpect(jsonPath("$.data.promoCodeStatus", equalTo(PromoCodeStatus.INACTIVE.getCode())))

        .andReturn();

    verify(this.promoCodeService)
        .updateStatusInActivedPromoCode(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(this.promoCodeController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeService);
  }
}
