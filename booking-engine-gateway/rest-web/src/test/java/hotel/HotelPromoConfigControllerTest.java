package hotel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.HotelPromoConfigController;
import com.tl.booking.gateway.service.api.hotel.HotelPromoConfigService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelPromoConfigTestVariable;

import io.reactivex.Single;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class HotelPromoConfigControllerTest extends HotelPromoConfigTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private HotelPromoConfigController hotelPromoConfigController;

  @Mock
  private HotelPromoConfigService hotelPromoConfigService;

  @Test
  public void create() throws Exception {

    when(hotelPromoConfigService
        .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.HOTEL_PROMO_CONFIG)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(getHotelPromoConfigRequestJson());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.hotelId", equalTo(HOTEL_ID)))
        .andDo(print());

    verify(hotelPromoConfigService)
        .create(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void update() throws Exception {

    when(hotelPromoConfigService
        .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.HOTEL_PROMO_CONFIG + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(getHotelPromoConfigRequestJson());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.hotelId", equalTo(HOTEL_ID)))
        .andDo(print());

    verify(hotelPromoConfigService)
        .update(CommonTestVariable.MANDATORY_REQUEST, ID, HOTEL_PROMO_CONFIG_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void delete() throws Exception {

    when(hotelPromoConfigService
        .delete(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.HOTEL_PROMO_CONFIG + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.hotelId", equalTo(HOTEL_ID)))
        .andDo(print());

    verify(hotelPromoConfigService)
        .delete(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void getOne() throws Exception {

    when(hotelPromoConfigService
        .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_PROMO_CONFIG + ApiPath.ID, HOTEL_ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.hotelId", equalTo(HOTEL_ID)))
        .andDo(print());

    verify(hotelPromoConfigService)
        .getOne(CommonTestVariable.MANDATORY_REQUEST, HOTEL_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void getAll() throws Exception {

    when(hotelPromoConfigService
        .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_CONFIG_RESPONSE_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_PROMO_CONFIG)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.content[0].hotelId", equalTo(HOTEL_ID)))
        .andDo(print());

    verify(hotelPromoConfigService)
        .getAll(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_CONFIG_FIND_ALL_PARAMS,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.hotelPromoConfigController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.hotelPromoConfigService);
  }
}
