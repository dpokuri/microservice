import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PromoCodeUsageLogColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PromoCodeUsageTestVariable;
import com.tl.booking.promo.code.rest.web.controller.PromoCodeUsageController;
import com.tl.booking.promo.code.service.api.ApplyPromoCodeService;
import com.tl.booking.promo.code.service.api.CalculatePromoCodeService;
import com.tl.booking.promo.code.service.api.PromoCodeObjectService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageLogService;
import com.tl.booking.promo.code.service.api.PromoCodeUsageService;
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


public class PromoCodeUsageControllerTest extends PromoCodeUsageTestVariable {

  @InjectMocks
  PromoCodeUsageController promoCodeUsageController;

  @Mock
  CalculatePromoCodeService calculatePromoCodeService;

  @Mock
  ApplyPromoCodeService applyPromoCodeService;

  @Mock
  PromoCodeUsageLogService promoCodeUsageLogService;

  @Mock
  PromoCodeUsageService promoCodeUsageService;

  @Mock
  PromoCodeObjectService promoCodeObjectService;

  private MockMvc mockMvc;

  @Test
  public void calculateDiscountTest() throws Exception {
    when(calculatePromoCodeService
        .calculatePromoCode(any(), any(), any(),
            any(), any(), any(), any(), any())).thenReturn(Single.just
        (CALC_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BASE_PATH + ApiPath.CALCULATE_DISCOUNT)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CALC_PROMO_CODE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(calculatePromoCodeService)
        .calculatePromoCode(any(), any(), any(),
            any(), any(), any(), any(), any());
  }

  @Test
  public void applyPromoCodeTest() throws Exception {
    when(applyPromoCodeService
        .applyPromoCode(any(), any(), any(),
            any(), any(), any(), any(), any())).thenReturn(Single.just(
        CALC_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BASE_PATH + ApiPath.APPLY_PROMO_CODE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CALC_PROMO_CODE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(applyPromoCodeService)
        .applyPromoCode(any(), any(), any(),
            any(), any(), any(), any(), any());
  }


  @Test
  public void applyPromoCodeWithoutPaymentTest() throws Exception {
    when(applyPromoCodeService
        .applyPromoCode(any(), any(), any(),
            any(), any(), any(), any(), any())).thenReturn(Single.just(
        CALC_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BASE_PATH + ApiPath.APPLY_PROMO_CODE_WITHOUT_PAYMENT_VALIDATION)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CALC_PROMO_CODE_WITHOUT_PAYMENT);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(applyPromoCodeService)
        .applyPromoCode(any(), any(), any(),
            any(), any(), any(), any(), any());
  }


  @Test
  public void unApplyPromoCodeTest() throws Exception {

    when(applyPromoCodeService
        .unApplyPromoCode(any(), any(), any(), any())).thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BASE_PATH + ApiPath.UNAPPLY_PROMO_CODE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(UNAPPLY_CODE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(applyPromoCodeService)
        .unApplyPromoCode(CommonTestVariable.MANDATORY_REQUEST, CODE, CARD_NUMBER, "123123123");
  }

  @Test
  public void promoCodeUsageLogTest() throws Exception {
    when(promoCodeUsageLogService.findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST,
        CODE_USAGE_LOG, START_DATE, END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID,
        SortDirection.ASC))
        .thenReturn(Single.just(PROMO_CODE_USAGE_LOG_LIST_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BASE_PATH + ApiPath.PROMO_CODE_USAGE_LOG)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", MANDATORY_REQUEST)
        .param("code", CODE_USAGE_LOG)
        .param("startDate", START_DATE)
        .param("endDate", END_DATE)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", PromoCodeUsageLogColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(promoCodeUsageLogService)
        .findPromoCodeUsageLogFilterPaginated(MANDATORY_REQUEST,
            CODE_USAGE_LOG, START_DATE, END_DATE, PAGE, SIZE, PromoCodeUsageLogColumn.ID,
            SortDirection.ASC);
  }


  @Test
  public void calculateDiscountPaymentValidationTest() throws Exception {
    when(calculatePromoCodeService
        .calculatePromoCode(any(), any(), any(),
            any(), any(), any(), any(), any())).thenReturn(Single.just
        (CALC_RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BASE_PATH + ApiPath.CALCULATE_DISCOUNT_PAYMENT_VALIDATION)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CALC_PROMO_CODE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(calculatePromoCodeService)
        .calculatePromoCode(any(), any(), any(),
            any(), any(), any(), any(), any());
  }

  @Test
  public void checkPaymentValidationTest() throws Exception {

    when(promoCodeObjectService
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST, CODE))
        .thenReturn(Single.just
            (PROMO_CODE_OBJECT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BASE_PATH + ApiPath.CHECK_VALID_PAYMENT)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("code", CODE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(promoCodeObjectService)
        .findPromoCodeObjectByStoreIdAndCode(CommonTestVariable.MANDATORY_REQUEST, CODE);
  }

  @Test
  public void checkQuotaTest() throws Exception {
    when(this.promoCodeUsageService.findCurrentQuotaByPromoCode(CommonTestVariable
        .MANDATORY_REQUEST, CODE)).thenReturn(Single.just(100));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BASE_PATH + ApiPath.PROMO_CODE_QUOTA)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("code", CODE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(this.promoCodeUsageService).findCurrentQuotaByPromoCode(CommonTestVariable
        .MANDATORY_REQUEST, CODE);

  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(promoCodeUsageController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(calculatePromoCodeService);
    verifyNoMoreInteractions(applyPromoCodeService);
    verifyNoMoreInteractions(promoCodeUsageLogService);
  }

}
