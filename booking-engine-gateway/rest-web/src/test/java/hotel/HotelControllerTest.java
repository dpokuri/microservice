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

import com.tl.booking.gateway.rest.web.controller.hotel.HotelController;
import com.tl.booking.gateway.service.api.hotel.HotelService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelTestVariable;

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

public class HotelControllerTest extends HotelTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private HotelController hotelController;

  @Mock
  private HotelService hotelService;

  @Test
  public void findHotelByAddressTest() throws Exception {

    when(hotelService
        .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_ADDRESS_PARAM,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_RESPONSE_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_BY_ADDRESS)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param("countryId", COUNTRY_ID)
        .param("provinceId", PROVINCE_ID)
        .param("cityId", CITY_ID)
        .param("areaId", AREA_ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data[0].name", equalTo(HOTEL_NAME)))
        .andDo(print());

    verify(hotelService)
        .findHotelByAddress(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_ADDRESS_PARAM,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findHotelNameByHotelIdsTest() throws Exception {

    when(hotelService.findHotelNameByHotelIds(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_HOTEL_ID_PARAM,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HOTEL_RESPONSE_DUMMY));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.HOTEL)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .content(HOTEL_REQUEST_JSON_DUMMY);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data[0].name", equalTo(HOTEL_NAME)))
        .andDo(print());

    verify(hotelService)
        .findHotelNameByHotelIds(CommonTestVariable.MANDATORY_REQUEST, FIND_HOTEL_BY_HOTEL_ID_PARAM,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.hotelController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.hotelService);
  }
}
