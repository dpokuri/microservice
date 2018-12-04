package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.HotelOtaController;
import com.tl.booking.gateway.service.api.hotel.HotelOtaService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelOtaTestVariable;

import io.reactivex.Single;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MDC.class})
public class HotelOtaControllerTest {

  @InjectMocks
  HotelOtaController hotelOtaController;

  @Mock
  HotelOtaService hotelOtaService;

  private MockMvc mockMvc;

  @Test
  public void findHotelOtaTest() throws Exception {

    when(this.hotelOtaService.findHotelOta(CommonTestVariable.MANDATORY_REQUEST,(String) MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HotelOtaTestVariable.RESPONSE_LIST));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.OTA)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    this.mockMvc.perform(builder)
        .andExpect(status().isOk());


    verify(this.hotelOtaService).findHotelOta(CommonTestVariable.MANDATORY_REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);

  }

  @Test
  public void findHotelOtaByIdTest() throws Exception {

    when(this.hotelOtaService.findHotelOtaById(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.OTA + "/{id}", HotelOtaTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    this.mockMvc.perform(builder)
        .andExpect(status().isOk())
        .andDo(print());

    verify(this.hotelOtaService)
        .findHotelOtaById(CommonTestVariable.MANDATORY_REQUEST, HotelOtaTestVariable.ID,
            (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void createHotelOtaTest() throws Exception {
    when(this.hotelOtaService.createHotelOta(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.OTA)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(HotelOtaTestVariable.OTA_REQUEST)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult =this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(this.hotelOtaService).createHotelOta(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void updateHotelOtaTest() throws Exception {
    when(this.hotelOtaService.updateHotelOta(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID, HotelOtaTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HotelOtaTestVariable.RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.OTA + "/{id}", HotelOtaTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(HotelOtaTestVariable.OTA_REQUEST)
        .param("storeId", CommonTestVariable.STORE_ID)
        .param("requestId", CommonTestVariable.REQUEST_ID)
        .param("channelId", CommonTestVariable.CHANNEL_ID)
        .param("serviceId", CommonTestVariable.SERVICE_ID)
        .param("username", CommonTestVariable.USERNAME);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(this.hotelOtaService).updateHotelOta(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID, HotelOtaTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);

  }

  @Test
  public void deleteByStoreIdAndIdTest() throws Exception {

    when(this.hotelOtaService.deleteHotelOta(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HotelOtaTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.OTA + "/{id}", HotelOtaTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.hotelOtaService).deleteHotelOta(CommonTestVariable.MANDATORY_REQUEST,
        HotelOtaTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) org.apache.log4j.MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (PromoPageTestVariable.PRIVILEGES);
    PowerMockito.when((String) org.apache.log4j.MDC.get(BaseMongoFields.STORE_ID)).thenReturn
        (CommonTestVariable.STORE_ID);
    PowerMockito.when((String) org.apache.log4j.MDC.get(BaseMongoFields.CHANNEL_ID)).thenReturn
        (CommonTestVariable.CHANNEL_ID);
    PowerMockito.when((String) org.apache.log4j.MDC.get(BaseMongoFields.SERVICE_ID)).thenReturn
        (CommonTestVariable.SERVICE_ID);
    PowerMockito.when((String) org.apache.log4j.MDC.get(BaseMongoFields.REQUEST_ID)).thenReturn
        (CommonTestVariable.REQUEST_ID);
    PowerMockito.when((String) org.apache.log4j.MDC.get(BaseMongoFields.USERNAME)).thenReturn
        (CommonTestVariable.USERNAME);

    this.mockMvc = standaloneSetup(this.hotelOtaController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelOtaService);
  }
}
