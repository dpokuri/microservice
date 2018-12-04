package hotel;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.gateway.rest.web.controller.hotel.RegionMappingController;
import com.tl.booking.gateway.service.api.hotel.RegionMappingService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.DefaultValues;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.hotel.RegionMappingTestVariable;

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
public class RegionMappingControllerTest {
  
  @InjectMocks
  RegionMappingController regionMappingController;
  
  @Mock
  RegionMappingService regionMappingService;

  private MockMvc mockMvc;

  @Test
  public void findRegionMappingsByStoreIdTest() throws Exception {
    when(this.regionMappingService.findRegionMappingsByStoreId(CommonTestVariable.MANDATORY_REQUEST,
        Integer.parseInt(DefaultValues.PAGE), Integer.parseInt(DefaultValues.PAGE_LIMIT), "",
        DefaultValues.SORT_BY, DefaultValues.SORT_METHOD, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(RegionMappingTestVariable.RESULT_PAGE));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.REGION_MAPPING)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());
    
    verify(this.regionMappingService).findRegionMappingsByStoreId(CommonTestVariable.MANDATORY_REQUEST,
        Integer.parseInt(DefaultValues.PAGE), Integer.parseInt(DefaultValues.PAGE_LIMIT), "",
        DefaultValues.SORT_BY, DefaultValues.SORT_METHOD, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void findRegionMappingByStoreIdAndIdTest() throws Exception {
    when(this.regionMappingService.findRegionMappingByStoreIdAndId(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .get(ApiPath.REGION_MAPPING + "/{id}", RegionMappingTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(this.regionMappingService).findRegionMappingByStoreIdAndId(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void createRegionMappingTest() throws Exception {
    when(this.regionMappingService.createRegionMapping(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .post(ApiPath.REGION_MAPPING)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(RegionMappingTestVariable.REGION_MAPPING_REQUEST)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(this.regionMappingService).createRegionMapping(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void updateRegionMappingTest() throws Exception {
    when(this.regionMappingService.updateRegionMapping(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID, RegionMappingTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(RegionMappingTestVariable.RESULT));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .put(ApiPath.REGION_MAPPING + "/{id}", RegionMappingTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(RegionMappingTestVariable.REGION_MAPPING_REQUEST)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(this.regionMappingService).updateRegionMapping(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID, RegionMappingTestVariable.REQUEST, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
  }

  @Test
  public void deleteRegionMappingTest() throws Exception {
    when(this.regionMappingService.deleteRegionMapping(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA))
        .thenReturn(Single.just(RegionMappingTestVariable.BASE_RESPONSE_BOOLEAN));

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
        .delete(ApiPath.REGION_MAPPING + "/{id}", RegionMappingTestVariable.ID)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .header("storeId", CommonTestVariable.STORE_ID)
        .header("username", CommonTestVariable.USERNAME)
        .header("channelId", CommonTestVariable.CHANNEL_ID)
        .header("serviceId", CommonTestVariable.SERVICE_ID)
        .header("requestId", CommonTestVariable.REQUEST_ID);

    MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

    this.mockMvc.perform(asyncDispatch(deferredResult))
        .andExpect(status().isOk());

    verify(this.regionMappingService).deleteRegionMapping(CommonTestVariable.MANDATORY_REQUEST,
        RegionMappingTestVariable.ID, (String)MDC.get("privileges"), CommonTestVariable.SESSION_DATA);
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

    this.mockMvc = standaloneSetup(this.regionMappingController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.regionMappingService);
  }
}
