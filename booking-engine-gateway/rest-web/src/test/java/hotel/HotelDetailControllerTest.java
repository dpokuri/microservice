package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.HotelDetailController;
import com.tl.booking.gateway.service.api.hotel.HotelDetailService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MDC.class})
public class HotelDetailControllerTest {

  @InjectMocks
  private HotelDetailController hotelDetailController;

  @Mock
  private HotelDetailService hotelDetailService;

  private MockMvc mockMvc;

  String PRIVILEGES = "300,301,302,303,304,305,306";

  Map<String, Object> HOTEL_POLICY_RESPONSE;

  GatewayBaseResponse<List<Map<String, Object>>> HOTEL_POLICY_RESPONSE_BASE;

  @Test
  public void getHotelRoomListTest() throws Exception{
    when(this.hotelDetailService.getHotelRoomList(
        CommonTestVariable.MANDATORY_REQUEST, "1", PRIVILEGES, CommonTestVariable.SESSION_DATA))
        .thenReturn
            (Single.just
                (HOTEL_POLICY_RESPONSE_BASE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_DETAIL + ApiPath.HOTEL_ROOM_LIST)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("hotelId", "1");

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.hotelDetailService).getHotelRoomList(
        CommonTestVariable.MANDATORY_REQUEST, "1",
        PRIVILEGES, CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void getHotelPolicyTest() throws Exception{
    when(this.hotelDetailService.getHotelPolicy(
        CommonTestVariable.MANDATORY_REQUEST, PRIVILEGES, CommonTestVariable.SESSION_DATA))
        .thenReturn
            (Single.just
                (HOTEL_POLICY_RESPONSE_BASE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOTEL_DETAIL + ApiPath.HOTEL_POLICY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.hotelDetailService).getHotelPolicy(
        CommonTestVariable.MANDATORY_REQUEST,
        PRIVILEGES, CommonTestVariable.SESSION_DATA);
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    this.HOTEL_POLICY_RESPONSE_BASE = new GatewayBaseResponse<>();
    this.HOTEL_POLICY_RESPONSE_BASE.setCode("Success");
    this.HOTEL_POLICY_RESPONSE_BASE.setData(Arrays.asList(this.HOTEL_POLICY_RESPONSE, this.HOTEL_POLICY_RESPONSE));

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (PRIVILEGES);
    PowerMockito.when((String) MDC.get(BaseMongoFields.STORE_ID)).thenReturn
        (CommonTestVariable.STORE_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.CHANNEL_ID)).thenReturn
        (CommonTestVariable.CHANNEL_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.SERVICE_ID)).thenReturn
        (CommonTestVariable.SERVICE_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.REQUEST_ID)).thenReturn
        (CommonTestVariable.REQUEST_ID);
    PowerMockito.when((String) MDC.get(BaseMongoFields.USERNAME)).thenReturn
        (CommonTestVariable.USERNAME);

    this.mockMvc = MockMvcBuilders.standaloneSetup(this.hotelDetailController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.hotelDetailService);
  }
}
