package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.InternalSubsidyController;
import com.tl.booking.gateway.service.api.hotel.InternalSubsidyService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.InternalSubsidyTestVariable;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
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
public class InternalSubsidyControllerTest extends InternalSubsidyTestVariable {

  private MockMvc mockMvc;

  @InjectMocks
  private InternalSubsidyController internalSubsidyController;

  @Mock
  private InternalSubsidyService internalSubsidyService;

  @Test
  public void createSubsidyTest() throws Exception {

    when(internalSubsidyService.createInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.REQUEST,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(this.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.INTERNAL_SUBSIDY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.REQUEST_ACTIVE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(internalSubsidyService).createInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.REQUEST,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );

  }

  @Test
  public void updateSubsidyTest() throws Exception {
    when(internalSubsidyService.updateInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.REQUEST,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(this.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.INTERNAL_SUBSIDY + "/{id}", this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.REQUEST_ACTIVE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(internalSubsidyService).updateInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.REQUEST,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void setActiveSubsidyTest() throws Exception {

    when(internalSubsidyService.setActiveInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.REQUEST_ACTIVATE,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(this.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.INTERNAL_SUBSIDY + "/activate/{id}", this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"active\": 1}");

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(internalSubsidyService).setActiveInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.REQUEST_ACTIVATE,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void deleteInternalSubsidyTest() throws Exception {
    when(internalSubsidyService.deleteInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(this.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.INTERNAL_SUBSIDY + "/{id}", this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.REQUEST_ACTIVE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(internalSubsidyService).deleteInternalSubsidy(
        CommonTestVariable.MANDATORY_REQUEST,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void findInternalSubsidyByStoreIdAndIdTest() throws Exception {
    when(internalSubsidyService.findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(this.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.INTERNAL_SUBSIDY + "/{id}", this.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.REQUEST_ACTIVE);

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(internalSubsidyService).findInternalSubsidyByStoreIdAndId(
        CommonTestVariable.MANDATORY_REQUEST,
        this.ID,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    );
  }

  @Test
  public void findInternalSubsidyByStoreId() throws Exception {
    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", page.toString());
    queryParam.put("limit", limit.toString());
    queryParam.put("sort", sort);
    queryParam.put("method", method);

    when(internalSubsidyService.findInternalSubsidyByStoreId(
        CommonTestVariable.MANDATORY_REQUEST, queryParam,
        PrivilegeId.MM_MODULE, CommonTestVariable.SESSION_DATA
    )).thenReturn(Single.just(RESULT_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.INTERNAL_SUBSIDY)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", page.toString())
        .param("limit", limit.toString())
        .param("q", q.toString())
        .param("countryId", countryId.toString())
        .param("cityId", cityId.toString())
        .param("areaId", areaId.toString())
        .param("sort", sort.toString())
        .param("method", method.toString());

    MvcResult deferredResult = mockMvc.perform(builder).andReturn();

    mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk())
        .andDo(print());

    verify(internalSubsidyService).findInternalSubsidyByStoreId(
        CommonTestVariable.MANDATORY_REQUEST, queryParam,
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

    this.mockMvc = standaloneSetup(this.internalSubsidyController).build();
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.internalSubsidyService);
  }
}
