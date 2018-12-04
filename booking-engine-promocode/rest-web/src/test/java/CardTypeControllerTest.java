import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.promo.code.entity.constant.ApiPath;
import com.tl.booking.promo.code.entity.constant.enums.CardTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.unit.test.CardTypeTestVariable;
import com.tl.booking.promo.code.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.promo.code.rest.web.controller.CardTypeController;
import com.tl.booking.promo.code.service.api.CardTypeService;
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

public class CardTypeControllerTest extends CardTypeTestVariable {

  @InjectMocks
  CardTypeController cardTypeController;

  @Mock
  CardTypeService cardTypeService;

  private MockMvc mockMvc;

  @Test
  public void findCardTypeTest() throws Exception {
//    when(cardTypeService.findCardTypes(CommonTestVariable.MANDATORY_REQUEST))
//        .thenReturn(Single.just(CARD_TYPE_LIST));
//
//    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//        .get(ApiPath.CARD_TYPE_PATH + "/findAll")
//        .accept(MediaType.APPLICATION_JSON)
//        .contentType(MediaType.APPLICATION_JSON)
//        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);
//
//    MvcResult deferredResult = mockMvc.perform(builder).andReturn();
//
//    mockMvc.perform(asyncDispatch(deferredResult))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
//        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
//        .andExpect(jsonPath("$.errors", equalTo(null)));
//
//    verify(cardTypeService)
//        .findCardTypes(CommonTestVariable.MANDATORY_REQUEST);
  }

  @Test
  public void findCardTypeByIdTest() throws Exception {
    when(cardTypeService.findCardTypeById(CommonTestVariable.MANDATORY_REQUEST,
        ID)).thenReturn(Single.just(CARD_TYPE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CARD_TYPE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(cardTypeService)
        .findCardTypeById(CommonTestVariable.MANDATORY_REQUEST, ID);
  }

  @Test
  public void findCardTypeFilterPaginatedTest() throws Exception {
    when(cardTypeService.findCardTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
        NAME, PAGE, SIZE, CardTypeColumn.ID, SortDirection.ASC)).thenReturn(
        Single.just(CARD_TYPE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.CARD_TYPE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .param("name", NAME)
        .param("page", PAGE.toString())
        .param("size", SIZE.toString())
        .param("columnSort", CardTypeColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)));

    verify(cardTypeService)
        .findCardTypeFilterPaginated(CommonTestVariable.MANDATORY_REQUEST,
            NAME, PAGE,
            SIZE, CardTypeColumn.ID, SortDirection.ASC);
  }

  @Test
  public void createCardTypeTest() throws Exception {
    when(cardTypeService
        .createCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE_REQUEST))
        .thenReturn(Single.just(CARD_TYPE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.CARD_TYPE_PATH)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CARD_TYPE_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(cardTypeService)
        .createCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE_REQUEST);
  }

  @Test
  public void updateCardTypeTest() throws Exception {
    when(cardTypeService
        .updateCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE_REQUEST, ID))
        .thenReturn(Single.just(CARD_TYPE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.CARD_TYPE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST)
        .content(CARD_TYPE_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(cardTypeService)
        .updateCardType(CommonTestVariable.MANDATORY_REQUEST, CARD_TYPE_REQUEST, ID);
  }

  @Test
  public void deleteCardTypeTest() throws Exception {
    when(cardTypeService
        .deleteCardType(CommonTestVariable.MANDATORY_REQUEST, ID))
        .thenReturn(Single.just(true));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.CARD_TYPE_PATH + "/" + ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr("mandatory", CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null))).andReturn();

    verify(cardTypeService)
        .deleteCardType(CommonTestVariable.MANDATORY_REQUEST, ID);
  }


  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(cardTypeController).build();
  }


  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(cardTypeService);
  }
}
