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

import com.tl.booking.gateway.rest.web.controller.hotel.HotelPromoAggregateController;
import com.tl.booking.gateway.service.api.hotel.HotelPromoAggregateService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.HotelPromoAggregateFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelAggregateTestVariable;

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

public class HotelPromoAggregateControllerTest extends HotelAggregateTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private HotelPromoAggregateController hotelPromoAggregateController;

  @Mock
  private HotelPromoAggregateService hotelPromoAggregateService;

  @Test
  public void findAllHotelPromoByHotelIdAndRoomIdAndDateTest() throws Exception {

    when(hotelPromoAggregateService
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
            findAllPromoParam, (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(hotelPromoAggregateResponsePageDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_PROMO_AGGREGATE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param("page", PAGE.toString())
        .param("limit", LIMIT.toString())
        .param("hotelId", HOTEL_ID)
        .param("roomId", ROOM_ID)
        .param("startDate", START_DATE)
        .param("endDate", END_DATE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.content[0].hotelId", equalTo(Integer.parseInt(HOTEL_ID))))
        .andDo(print());

    verify(hotelPromoAggregateService)
        .findAllHotelPromoByHotelIdAndRoomIdAndDate(CommonTestVariable.MANDATORY_REQUEST,
            findAllPromoParam, (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findHotelPromoByIdTest() throws Exception {

    when(hotelPromoAggregateService
        .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(hotelPromoAggregateResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_PROMO_AGGREGATE + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param(HotelPromoAggregateFields.ID, ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.content[0].hotelId", equalTo(Integer.parseInt(HOTEL_ID))))
        .andDo(print());

    verify(hotelPromoAggregateService)
        .findHotelPromoById(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void createHotelPromoAggregateTest() throws Exception {

    when(hotelPromoAggregateService
        .createHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, hotelPromoAggregateRequest,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(hotelPromoAggregateResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.HOTEL_PROMO_AGGREGATE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(HOTEL_PROMO_REQUEST_DUMMY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.content[0].hotelId", equalTo(Integer.parseInt(HOTEL_ID))))
        .andDo(print());

    verify(hotelPromoAggregateService)
        .createHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, hotelPromoAggregateRequest,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void updateHotelPromoAggregateTest() throws Exception {

    when(hotelPromoAggregateService
        .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            hotelPromoAggregateRequest, (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(hotelPromoAggregateResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.HOTEL_PROMO_AGGREGATE + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(HOTEL_PROMO_REQUEST_DUMMY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data.content[0].hotelId", equalTo(Integer.parseInt(HOTEL_ID))))
        .andDo(print());

    verify(hotelPromoAggregateService)
        .updateHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            hotelPromoAggregateRequest, (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void deleteHotelPromoAggregateTest() throws Exception {

    when(hotelPromoAggregateService
        .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(CommonTestVariable.GATEWAY_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.HOTEL_PROMO_AGGREGATE + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param(HotelPromoAggregateFields.ID, ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andDo(print());

    verify(hotelPromoAggregateService)
        .deleteHotelPromoAggregate(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.hotelPromoAggregateController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.hotelPromoAggregateService);
  }
}
