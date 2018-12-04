package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.AdditionalDiscountController;
import com.tl.booking.gateway.service.api.hotel.AdditionalDiscountService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.AdditionalDiscountTestVariable;

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
public class AdditionalDiscountControllerTest extends AdditionalDiscountTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private AdditionalDiscountController additionalDiscountController;

  @Mock
  private AdditionalDiscountService additionalDiscountService;

  @Test
  public void createAdditionalDiscountTest() throws Exception {
    when(additionalDiscountService.createAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        REQUEST,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.URL_ADDITIONAL_DISCOUNT)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(REQUEST_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(additionalDiscountService).createAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        REQUEST,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );

  }

  @Test
  public void updateAdditionalDiscountTest() throws Exception {
    when(additionalDiscountService.updateAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        REQUEST,
        ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.URL_ADDITIONAL_DISCOUNT + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(REQUEST_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(additionalDiscountService).updateAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        REQUEST,
        ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void deleteAdditionalDiscountTest() throws Exception {
    when(additionalDiscountService.deleteAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.URL_ADDITIONAL_DISCOUNT + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(additionalDiscountService).deleteAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void findAdditionalDiscountByHotel() throws Exception {
    when(additionalDiscountService.findAdditionalDiscountByHotel(
        CommonTestVariable.MANDATORY_REQUEST,
        HOTEL_ID,
        TYPE,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.URL_ADDITIONAL_DISCOUNT + "/hotel")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param("hotelId", HOTEL_ID.toString())
        .param("type", TYPE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(additionalDiscountService).findAdditionalDiscountByHotel(
        CommonTestVariable.MANDATORY_REQUEST,
        HOTEL_ID,
        TYPE,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void findAdditionalDiscountById() throws Exception {
    when(additionalDiscountService.findAdditionalDiscountById(
        CommonTestVariable.MANDATORY_REQUEST,
        ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.URL_ADDITIONAL_DISCOUNT + ApiPath.ID, ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(additionalDiscountService).findAdditionalDiscountById(
        CommonTestVariable.MANDATORY_REQUEST,
        ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void findAdditionalDiscount() throws Exception {
    when(additionalDiscountService.findAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        PAGE,
        LIMIT,
        TYPE,
        HOTEL_ID.toString(),
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.URL_ADDITIONAL_DISCOUNT)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", PAGE.toString())
        .param("limit", LIMIT.toString())
        .param("type", TYPE)
        .param("hotelId", HOTEL_ID.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(additionalDiscountService).findAdditionalDiscount(
        CommonTestVariable.MANDATORY_REQUEST,
        PAGE,
        LIMIT,
        TYPE,
        HOTEL_ID.toString(),
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Before
  public void setUp() {
    initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get("privileges")).thenReturn
        (PrivilegeId.MM_MODULE);
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

    this.mockMvc = standaloneSetup(this.additionalDiscountController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.additionalDiscountService);
  }

}
