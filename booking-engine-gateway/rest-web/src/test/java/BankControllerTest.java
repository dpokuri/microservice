import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.promocode.BankController;
import com.tl.booking.gateway.service.api.promocode.BankService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.promoCode.BankTestVariable;

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
public class BankControllerTest {

  @InjectMocks
  BankController binNumberController;

  @Mock
  BankService binNumberService;

  private MockMvc mockMvc;

  @Test
  public void binNumberTest() throws Exception {

    when(this.binNumberService.bank(
        CommonTestVariable.MANDATORY_REQUEST,
        BankTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA)).thenReturn(Single.just(BankTestVariable.BANK_RESPONSE_LIST));


    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.BANK + "/findAll")
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

    verify(this.binNumberService).bank(
        CommonTestVariable.MANDATORY_REQUEST,
        BankTestVariable.PRIVILEGES,
        CommonTestVariable.SESSION_DATA);


  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
        (BankTestVariable.PRIVILEGES);
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
