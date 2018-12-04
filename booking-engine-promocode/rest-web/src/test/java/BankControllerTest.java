import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.BankColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.BankTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.rest.web.controller.BankController;
import com.tl.booking.promo.code.service.api.BankService;
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

public class BankControllerTest extends BankTestVariable {

  @InjectMocks
  BankController bankController;

  @Mock
  BankService bankService;

  private MockMvc mockMvc;

  @Test
  public void findBankTest() throws Exception {
    when(bankService.findBanks(CommonTestVariable.MANDATORY_REQUEST))
        .thenReturn(Single.just(BANK_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BANK_PATH + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(bankService)
        .findBanks(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Test
  public void findBankByIdTest() throws Exception {
    when(bankService.findBankById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(BANK));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BANK_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(bankService)
        .findBankById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findBankFilterPaginatedTest() throws Exception {
    when(bankService.findBankFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, PAGE, SIZE, BankColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(BANK_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BANK_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("name", NAME)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", BankColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(bankService)
        .findBankFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE,
            SIZE, BankColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findBankFilterPaginatedTestWithoutParameter() throws Exception {
    when(bankService.findBankFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        null, PAGE,
        SIZE, null, null)).thenReturn(
        Single.just(BANK_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BANK_PATH)
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

    verify(bankService)
        .findBankFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            null, PAGE,
            SIZE, null, null);
  }

  @Test
  public void createBankTest() throws Exception {
    when(bankService
        .createBank(CommonTestVariable.MANDATORY_REQUEST, BANK_REQUEST))
        .thenReturn(Single.just(BANK));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BANK_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(BANK_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(bankService)
        .createBank(CommonTestVariable.MANDATORY_REQUEST, BANK_REQUEST);
  }

  @Test
  public void updateBankTest() throws Exception {
    when(bankService
        .updateBank(CommonTestVariable.MANDATORY_REQUEST, BANK_REQUEST, ID))
        .thenReturn(Single.just(BANK));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.BANK_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(BANK_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(bankService)
        .updateBank(CommonTestVariable.MANDATORY_REQUEST, BANK_REQUEST, ID);
  }

  @Test
  public void deleteBankTest() throws Exception {
    when(bankService
        .deleteBank(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.BANK_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(bankService)
        .deleteBank(CommonTestVariable.MANDATORY_REQUEST, ID);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(bankService);
  }
}
