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

import com.tl.booking.gateway.rest.web.controller.hotel.HotelPromoTypeController;
import com.tl.booking.gateway.service.api.hotel.HotelPromoTypeService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelPromoTypeTestVariable;

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

public class HotelPromoTypeControllerTest extends HotelPromoTypeTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private HotelPromoTypeController hotelPromoTypeController;

  @Mock
  private HotelPromoTypeService hotelPromoTypeService;

  @Test
  public void findAllHotelPromoTypeTest() throws Exception {

    when(hotelPromoTypeService
        .findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_LIST_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_PROMO_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data[0].id", equalTo(ID)))
        .andDo(print());

    verify(hotelPromoTypeService)
        .findAllHotelPromoType(CommonTestVariable.MANDATORY_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findHotelPromoTypeByIdTest() throws Exception {

    when(hotelPromoTypeService
        .findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_PROMO_TYPE + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id", equalTo(ID)))
        .andDo(print());

    verify(hotelPromoTypeService)
        .findHotelPromoTypeById(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void createHotelPromoTypeTest() throws Exception {

    when(hotelPromoTypeService
        .createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_TYPE_REQUEST_DUMMY,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.HOTEL_PROMO_TYPE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(HOTEL_PROMO_TYPE_REQUEST_JSON_DUMMY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id", equalTo(ID)))
        .andDo(print());

    verify(hotelPromoTypeService)
        .createHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, HOTEL_PROMO_TYPE_REQUEST_DUMMY,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void updateHotelPromoTypeTest() throws Exception {

    when(hotelPromoTypeService
        .updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY, (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_RESPONSE_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.HOTEL_PROMO_TYPE + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(HOTEL_PROMO_TYPE_REQUEST_JSON_DUMMY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id", equalTo(ID)))
        .andDo(print());

    verify(hotelPromoTypeService)
        .updateHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            HOTEL_PROMO_TYPE_REQUEST_DUMMY, (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void deleteHotelPromoTypeTest() throws Exception {

    when(hotelPromoTypeService
        .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_PROMO_TYPE_BASE_RESPONSE_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.HOTEL_PROMO_TYPE + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data", equalTo(null)))
        .andDo(print());

    verify(hotelPromoTypeService)
        .deleteHotelPromoType(CommonTestVariable.MANDATORY_REQUEST, ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.hotelPromoTypeController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.hotelPromoTypeService);
  }
}
