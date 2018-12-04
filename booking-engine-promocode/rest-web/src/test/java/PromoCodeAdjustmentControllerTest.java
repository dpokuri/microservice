import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeAdjustmentStatus;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeAdjustmentTestVariable;
import com.tl.booking.promo.code.rest.web.controller.PromoCodeAdjustmentController;
import com.tl.booking.promo.code.service.api.PromoCodeAdjustmentService;
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


public class PromoCodeAdjustmentControllerTest extends PromoCodeAdjustmentTestVariable {

  @InjectMocks
  PromoCodeAdjustmentController promoCodeAdjustmentController;

  @Mock
  PromoCodeAdjustmentService promoCodeAdjustmentService;

  private MockMvc mockMvc;


  @Test
  public void createPromoCodeAdjustmentSuccess() throws Exception {
    when(this.promoCodeAdjustmentService
        .createPromoCodeAdjustment(
            CommonTestVariable.MANDATORY_REQUEST,
            PROMO_CODE_ADJUSTMENT_REQUEST
        ))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT_CREATE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PROMO_CODE_ADJUSTMENT_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(PROMO_CODE_ADJUSTMENT_CREATE_REQUEST_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.promoCodeAdjustmentService)
        .createPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
            PROMO_CODE_ADJUSTMENT_REQUEST);
  }

  @Test
  public void updatedPromoCodeAdjustmentSuccess() throws Exception {
    when(this.promoCodeAdjustmentService
        .updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
            PROMO_CODE_ADJUSTMENT_REQUEST, ID))
        .thenReturn(Single.just(PROMO_CODE_ADJUSTMENT_CREATE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(PROMO_CODE_ADJUSTMENT_CREATE_REQUEST_BODY);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.promoCodeAdjustmentService)
        .updatePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST,
            PROMO_CODE_ADJUSTMENT_REQUEST, this.ID);
  }

  @Test
  public void findPromoCodeAdjustmentByIdTest() throws Exception {
    when(this.promoCodeAdjustmentService
        .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST,
            this.ID)).thenReturn(Single.just(this.PROMO_CODE_ADJUSTMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.data.name", equalTo(this.NAME)))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.promoCodeAdjustmentService)
        .findPromoCodeAdjustmentById(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void findPromoCodeAdjustmentFilterPaginatedTest() throws Exception {
    when(this.promoCodeAdjustmentService
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            this.NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT,
            PromoCodeAdjustmentStatus.ACTIVE, PAGE, SIZE, PromoCodeAdjustmentColumn.ID,
            SortDirection.ASC)).thenReturn(
        Single.just(this.PROMO_CODE_ADJUSTMENTS_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_ADJUSTMENT_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("name", NAME)
        .param("isPromoCodeCombine", PROMO_CODE_COMBINE.toString())
        .param("maxDiscount", MAX_DISCOUNT.toString())
        .param("promoCodeAdjustmentStatus", PromoCodeAdjustmentStatus.ACTIVE.getCode())
        .param("page", this.PAGE.toString())
        .param("size", this.SIZE.toString())
        .param("sort", PromoCodeAdjustmentColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.promoCodeAdjustmentService)
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            this.NAME, PROMO_CODE_COMBINE, MAX_DISCOUNT,
            PromoCodeAdjustmentStatus.ACTIVE, PAGE, SIZE, PromoCodeAdjustmentColumn.ID,
            SortDirection.ASC);
  }


  @Test
  public void findPromoCodeAdjustmentFilterPaginatedTestWithoutParameter() throws Exception {
    when(this.promoCodeAdjustmentService
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, null, null,
            null, PAGE, SIZE, null, null)).thenReturn(
        Single.just(this.PROMO_CODE_ADJUSTMENTS_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_ADJUSTMENT_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("page", this.PAGE.toString())
        .param("size", this.SIZE.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(this.promoCodeAdjustmentService)
        .findPromoCodeAdjustmentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, null, null,
            null, PAGE, SIZE, null, null);
  }

  @Test
  public void deletePromoCodeAdjustmentTest() throws Exception {
    when(this.promoCodeAdjustmentService
        .deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(this.promoCodeAdjustmentService)
        .deletePromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentPendingTest() throws Exception {
    this.PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(this.promoCodeAdjustmentService
        .updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE_ADJUSTMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID + "/pending")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(this.promoCodeAdjustmentService)
        .updateStatusPendingPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentActivedTest() throws Exception {
    this.PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.ACTIVE);

    when(this.promoCodeAdjustmentService
        .updateStatusActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE_ADJUSTMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID + "/activate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(this.promoCodeAdjustmentService)
        .updateStatusActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentInActivedTest() throws Exception {
    this.PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.INACTIVE);

    when(this.promoCodeAdjustmentService
        .updateStatusInActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE_ADJUSTMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID + "/unActivate")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(this.promoCodeAdjustmentService)
        .updateStatusInActivedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }

  @Test
  public void updatePromoCodeAdjustmentRejectedTest() throws Exception {
    this.PROMO_CODE_ADJUSTMENT.setPromoCodeAdjustmentStatus(PromoCodeAdjustmentStatus.PENDING);

    when(this.promoCodeAdjustmentService
        .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID))
        .thenReturn(Single.just(this.PROMO_CODE_ADJUSTMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + "/" + this.ID + "/rejected")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(this.promoCodeAdjustmentService)
        .updateStatusRejectedPromoCodeAdjustment(CommonTestVariable.MANDATORY_REQUEST, this.ID);
  }


  @Test
  public void findActivatePromoCodeAdjustmentTest() throws Exception {

    when(this.promoCodeAdjustmentService
        .findPromoCodeAdjustments(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(this.PROMO_CODE_ADJUSTMENT_DROPDOWN_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PROMO_CODE_ADJUSTMENT_PATH + ApiPath.FIND_ACTIVATE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andReturn();

    verify(this.promoCodeAdjustmentService)
        .findPromoCodeAdjustments(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders.standaloneSetup(this.promoCodeAdjustmentController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.promoCodeAdjustmentService);
  }
}
