import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.HolidayController;
import com.tl.booking.gateway.service.api.HolidayService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.HolidayTestVariable;

import io.reactivex.Single;
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
public class HolidayControllerTest {

  @InjectMocks
  HolidayController holidayController;

  @Mock
  HolidayService holidayService;

  private MockMvc mockMvc;

  @Test
  public void findHolidayFilterPaginatedTest() throws Exception {
    when(this.holidayService.findHolidayFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.START_DATE,
        HolidayTestVariable.END_DATE,
        HolidayTestVariable.LANG,
        HolidayTestVariable.CONTENT,
        HolidayTestVariable.PAGE,
        HolidayTestVariable.SIZE,
        HolidayColumn.DATE,
        SortDirection.ASC,
        HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(HolidayTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOLIDAY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("startDate", HolidayTestVariable.START_DATE)
        .param("endDate", HolidayTestVariable.END_DATE)
        .param("lang", HolidayTestVariable.LANG)
        .param("content", HolidayTestVariable.CONTENT)
        .param("page", HolidayTestVariable.PAGE.toString())
        .param("size", HolidayTestVariable.SIZE.toString())
        .param("columnSort", HolidayColumn.DATE.toString())
        .param("sortDirection", SortDirection.ASC.getValue().toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.holidayService).findHolidayFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.START_DATE,
        HolidayTestVariable.END_DATE,
        HolidayTestVariable.LANG,
        HolidayTestVariable.CONTENT,
        HolidayTestVariable.PAGE,
        HolidayTestVariable.SIZE,
        HolidayColumn.DATE,
        SortDirection.ASC,
        HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void createPromoPageTest() throws Exception {
    when(this.holidayService.createHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.HOLIDAY_REQUEST, HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single
        .just(HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.HOLIDAY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(HolidayTestVariable.HOLIDAY_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.holidayService).createHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.HOLIDAY_REQUEST, HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);

  }

  @Test
  public void updateHolidayTest() throws Exception {
    when(this.holidayService.updateHoliday(
        CommonTestVariable.MANDATORY_REQUEST, HolidayTestVariable
        .HOLIDAY_REQUEST,
        HolidayTestVariable.ID,
        HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn
        (Single
        .just(HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.HOLIDAY + "/{id}", HolidayTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(HolidayTestVariable.HOLIDAY_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.holidayService).updateHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable
        .HOLIDAY_REQUEST, HolidayTestVariable.ID,
        HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void deleteHolidayTest() throws Exception {

    when(this.holidayService.deleteHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.ID, HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just
        (HolidayTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.HOLIDAY + "/{slug}", HolidayTestVariable.ID)
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

    verify(this.holidayService).deleteHoliday(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.ID, HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void findByIdTest() throws Exception {

    when(this.holidayService.findHolidayById(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.ID, HolidayTestVariable
            .PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just
        (HolidayTestVariable.HOLIDAY_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.HOLIDAY + "/{id}", HolidayTestVariable.ID)
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

    verify(this.holidayService).findHolidayById(CommonTestVariable.MANDATORY_REQUEST,
        HolidayTestVariable.ID, HolidayTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (HolidayTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.holidayController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.holidayService);
  }
}
