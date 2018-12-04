import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.BinNumberController;
import com.tl.booking.gateway.service.api.promocode.BinNumberService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BinNumberTestVariable;

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
public class BinNumberControllerTest {

  @InjectMocks
  BinNumberController binNumberController;

  @Mock
  BinNumberService binNumberService;

  private MockMvc mockMvc;

  @Test
  public void findBinNumberFilterPaginatedTest() throws Exception {
    when(this.binNumberService.findBinNumberFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER,
        BinNumberTestVariable.PAGE,
        BinNumberTestVariable.SIZE,
        BinNumberColumn.ID,
        SortDirection.ASC,
        BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(BinNumberTestVariable.RESULT));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BIN_NUMBER)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("binNumber", BinNumberTestVariable.BIN_NUMBER)
        .param("page", BinNumberTestVariable.PAGE.toString())
        .param("size", BinNumberTestVariable.SIZE.toString())
        .param("columnSort", BinNumberColumn.ID.toString())
        .param("sortDirection", SortDirection.ASC.toString());

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.binNumberService).findBinNumberFilterPaginated(
        CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER,
        BinNumberTestVariable.PAGE,
        BinNumberTestVariable.SIZE,
        BinNumberColumn.ID,
        SortDirection.ASC,
        BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA
    );


  }


  @Test
  public void createBinNumberTest() throws Exception {
    when(this.binNumberService.createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.BIN_NUMBER)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(BinNumberTestVariable.BIN_NUMBER_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.binNumberService).createBinNumber(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void updateBinNumberTest() throws Exception {
    when(this.binNumberService.updateBinNumber(
        CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER_REQUEST,
        BinNumberTestVariable.ID,
        BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.BIN_NUMBER + "/{id}", BinNumberTestVariable.ID).accept(MediaType
            .APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .content(BinNumberTestVariable.BIN_NUMBER_JSON);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.binNumberService).updateBinNumber(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.BIN_NUMBER_REQUEST, BinNumberTestVariable.ID, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findByIdBinNumberTest() throws Exception {

    when(this.binNumberService.findBinNumberById(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.ID, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BinNumberTestVariable.BIN_NUMBER_RESPONSE_GENERATED_BASE_RESPONSE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
    .get(ApiPath.BIN_NUMBER + "/{id}", BinNumberTestVariable.ID)
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

    verify(this.binNumberService).findBinNumberById(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.ID, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Test
  public void deleteBinNumberTest() throws Exception {

    when(this.binNumberService.deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.ID, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BinNumberTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.BIN_NUMBER + "/{id}", BinNumberTestVariable.ID)
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

    verify(this.binNumberService).deleteBinNumber(CommonTestVariable.MANDATORY_REQUEST, BinNumberTestVariable.ID, BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }


  @Test
  public void binNumberTest() throws Exception {

    when(this.binNumberService.binNumber(
        CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER,
        BinNumberTestVariable.BANK_ID,
        BinNumberTestVariable.CARD_TYPE,
        BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BinNumberTestVariable.BIN_NUMBER_RESPONSE_LIST_DATA));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BIN_NUMBER + "/findAll")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID)
        .param("binNumber", BinNumberTestVariable.BIN_NUMBER)
        .param("bankId", BinNumberTestVariable.BANK_ID)
        .param("cardTypeId", BinNumberTestVariable.CARD_TYPE);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andReturn();

    verify(this.binNumberService).binNumber(
        CommonTestVariable.MANDATORY_REQUEST,
        BinNumberTestVariable.BIN_NUMBER,
        BinNumberTestVariable.BANK_ID,
        BinNumberTestVariable.CARD_TYPE,
        BinNumberTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (BinNumberTestVariable.PRIVILEGES);
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


    this.mockMvc = MockMvcBuilders.standaloneSetup(this.binNumberController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.binNumberService);
  }
}
