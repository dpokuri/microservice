import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.PaymentColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.PaymentTestVariable;
import com.tl.booking.promo.code.rest.web.controller.PaymentController;
import com.tl.booking.promo.code.service.api.PaymentService;
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

public class PaymentControllerTest extends PaymentTestVariable {

  @InjectMocks
  PaymentController paymentController;

  @Mock
  PaymentService paymentService;

  private MockMvc mockMvc;

  @Test
  public void findPaymentTest() throws Exception {
    when(paymentService.findPayments(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(PAYMENT_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PAYMENT_PATH + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(paymentService)
        .findPayments(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Test
  public void findPaymentByIdTest() throws Exception {
    when(paymentService.findPaymentById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(PAYMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PAYMENT_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(paymentService)
        .findPaymentById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findPaymentFilterPaginatedTest() throws Exception {
    when(paymentService.findPaymentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, PAYMENT_ID, PAGE, SIZE, PaymentColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(PAYMENT_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.PAYMENT_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("name", NAME)
        .param("paymentId", PAYMENT_ID)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", PaymentColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(paymentService)
        .findPaymentFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAYMENT_ID, PAGE, SIZE, PaymentColumn.ID, SortDirection.ASC);
  }

  @Test
  public void createPaymentTest() throws Exception {
    when(paymentService
        .createPayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT_REQUEST))
        .thenReturn(Single.just(PAYMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.PAYMENT_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(PAYMENT_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(paymentService)
        .createPayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT_REQUEST);
  }

  @Test
  public void updatePaymentTest() throws Exception {
    when(paymentService
        .updatePayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT_REQUEST, ID))
        .thenReturn(Single.just(PAYMENT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.PAYMENT_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(PAYMENT_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(paymentService)
        .updatePayment(CommonTestVariable.MANDATORY_REQUEST, PAYMENT_REQUEST, ID);
  }

  @Test
  public void deletePaymentTest() throws Exception {
    when(paymentService
        .deletePayment(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.PAYMENT_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(paymentService)
        .deletePayment(CommonTestVariable.MANDATORY_REQUEST, ID);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(paymentService);
  }
}
