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

import com.tl.booking.gateway.rest.web.controller.hotel.AddressController;
import com.tl.booking.gateway.service.api.hotel.AddressService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.AddressTestVariable;

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

public class AddressControllerTest extends AddressTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private AddressController addressController;

  @Mock
  private AddressService addressService;

  @Test
  public void getCountry() throws Exception {
    when(addressService
        .getCountry(CommonTestVariable.MANDATORY_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(addressResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.ADDRESS_COUNTRY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data[0].id", equalTo(COUNTRY_ID)))
        .andExpect(jsonPath("$.data[0].name", equalTo(COUNTRY_NAME)))
        .andDo(print());

    verify(addressService)
        .getCountry(CommonTestVariable.MANDATORY_REQUEST,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void getProvince() throws Exception {
    when(addressService
        .getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(addressResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.ADDRESS_PROVINCE)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param("countryId", COUNTRY_ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data[0].id", equalTo(COUNTRY_ID)))
        .andExpect(jsonPath("$.data[0].name", equalTo(COUNTRY_NAME)))
        .andDo(print());

    verify(addressService)
        .getProvince(CommonTestVariable.MANDATORY_REQUEST, COUNTRY_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void getCity() throws Exception {
    when(addressService
        .getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(addressResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.ADDRESS_CITY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param("provinceId", PROVINCE_ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data[0].id", equalTo(COUNTRY_ID)))
        .andExpect(jsonPath("$.data[0].name", equalTo(COUNTRY_NAME)))
        .andDo(print());

    verify(addressService)
        .getCity(CommonTestVariable.MANDATORY_REQUEST, PROVINCE_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void getArea() throws Exception {
    when(addressService
        .getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(addressResponseDummy));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.ADDRESS_AREA)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .requestAttr(MandatoryFields.MANDATORY_REQUEST, CommonTestVariable.MANDATORY_REQUEST)
        .param("cityId", CITY_ID);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResponseCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.message", equalTo(ResponseCode.SUCCESS.getMessage())))
        .andExpect(jsonPath("$.errors", equalTo(null)))
        .andExpect(jsonPath("$.data[0].id", equalTo(COUNTRY_ID)))
        .andExpect(jsonPath("$.data[0].name", equalTo(COUNTRY_NAME)))
        .andDo(print());

    verify(addressService)
        .getArea(CommonTestVariable.MANDATORY_REQUEST, CITY_ID,
            (String) MDC.get(BaseMongoFields.PRIVILEGES), CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.addressController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.addressService);
  }

}
