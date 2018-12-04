package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.HotelAutoCompleteController;
import com.tl.booking.gateway.service.api.hotel.HotelAutoCompleteService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.HotelAutocompleteTestVariable;

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
public class HotelAutoCompleteControllerTest {
  @InjectMocks
  HotelAutoCompleteController hotelAutoCompleteController;

  @Mock
  HotelAutoCompleteService hotelAutoCompleteService;

  private MockMvc mockMvc;

  @Test
  public void getAutoCompleteHotelTest() throws Exception {
    when(this.hotelAutoCompleteService.getAutoCompleteHotel(CommonTestVariable.MANDATORY_REQUEST,
        HotelAutocompleteTestVariable.otaID, HotelAutocompleteTestVariable.q,
        (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(HotelAutocompleteTestVariable.AUTOCOMPLETE_RETURN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.AUTO_COMPLETE_HOTEL + "/search/hotel")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("otaId", HotelAutocompleteTestVariable.otaID)
        .param("q", HotelAutocompleteTestVariable.q);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(this.hotelAutoCompleteService).getAutoCompleteHotel(CommonTestVariable.MANDATORY_REQUEST,
        HotelAutocompleteTestVariable.otaID, HotelAutocompleteTestVariable.q,
        (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
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

    this.mockMvc = standaloneSetup(this.hotelAutoCompleteController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelAutoCompleteService);
  }
}
