import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.BinNumberColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.BinNumberTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.rest.web.controller.BinNumberController;
import com.tl.booking.promo.code.service.api.BinNumberService;
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

public class BinNumberControllerTest extends BinNumberTestVariable {

  @InjectMocks
  BinNumberController binNumberController;

  @Mock
  BinNumberService binNumberService;

  private MockMvc mockMvc;

  @Test
  public void findBinNumberByIdTest() throws Exception {
    when(binNumberService.findBinNumberById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(BIN_NUMBER_BUILDER));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BIN_NUMBER_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(binNumberService)
        .findBinNumberById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findBinNumberFilterPaginatedTest() throws Exception {
    when(binNumberService.findBinNumberFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        BIN_NUMBER, BANK_ID, CARD_TYPE_ID, PAGE, SIZE, BinNumberColumn.ID, SortDirection.ASC))
        .thenReturn(Single.just(BIN_NUMBER_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BIN_NUMBER_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("binNumber", BIN_NUMBER)
        .param("bankId", BANK_ID)
        .param("cardTypeId", CARD_TYPE_ID)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", BinNumberColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(binNumberService)
        .findBinNumberFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            BIN_NUMBER, BANK_ID, CARD_TYPE_ID, PAGE, SIZE, BinNumberColumn.ID, SortDirection.ASC);
  }

  @Test
  public void findAllBinNumberTest() throws Exception {
    when(binNumberService.findBinNumbers(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER, BANK_ID,
        CARD_TYPE_ID))
        .thenReturn(Single.just(BIN_NUMBER_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BIN_NUMBER_PATH + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("binNumber", BIN_NUMBER)
        .param("bankId",BANK_ID)
        .param("cardTypeId",CARD_TYPE_ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(binNumberService)
        .findBinNumbers(CommonTestVariable.MANDATORY_REQUEST,
            BIN_NUMBER, BANK_ID, CARD_TYPE_ID);
  }

  @Test
  public void createBinNumberTest() throws Exception {
    when(binNumberService
        .createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_REQUEST))
        .thenReturn(Single.just(BIN_NUMBER_BUILDER));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BIN_NUMBER_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(BIN_NUMBER_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(binNumberService)
        .createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_REQUEST);
  }

  @Test
  public void updateBinNumberTest() throws Exception {
    when(binNumberService
        .updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_REQUEST, ID))
        .thenReturn(Single.just(BIN_NUMBER_BUILDER));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.BIN_NUMBER_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(BIN_NUMBER_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(binNumberService)
        .updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BIN_NUMBER_REQUEST, ID);
  }

  @Test
  public void deleteBinNumberTest() throws Exception {
    when(binNumberService
        .deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.BIN_NUMBER_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(binNumberService)
        .deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, ID);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(binNumberController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(binNumberService);
  }
}
