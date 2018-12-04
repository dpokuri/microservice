import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tl.booking.gateway.rest.web.controller.ImageController;
import com.tl.booking.gateway.service.api.ImageService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

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


// @RunWith(PowerMockRunner.class)
// @PrepareForTest({MDC.class})
public class ImageControllerTest {

//   @InjectMocks
//   ImageController imageController;

//   @Mock
//   ImageService imageService;

//   private MockMvc mockMvc;

//   String PRIVILEGES = "300,301,302,303,304,305,306";

//   String IMAGE_NAME = "bababia.jpg";

//   String IMAGE_REQUEST_JSON = "{\"data\":\"asdaqweqwe\",\"name\":\"yosia.jpg\"}";

//   GatewayBaseResponse<ImagesResponse> IMAGES_RESPONSE_BASE;

//   ImagesRequest IMAGES_REQUEST = new ImagesRequest();

//   ImagesResponse IMAGES_RESPONSE = new ImagesResponse();

//   @Test
//   public void createPromoPageTest() throws Exception {
//     when(this.imageService.createImages(
//         CommonTestVariable.MANDATORY_REQUEST, IMAGES_REQUEST, PRIVILEGES, CommonTestVariable.SESSION_DATA))
//         .thenReturn
//         (Single.just
//         (IMAGES_RESPONSE_BASE));

//     MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
//         .post(ApiPath.IMAGES)
//         .accept(MediaType.APPLICATION_JSON)
//         .contentType(MediaType.APPLICATION_JSON)
//         .header("storeId", CommonTestVariable.STORE_ID)
//         .header("username", CommonTestVariable.USERNAME)
//         .header("channelId", CommonTestVariable.CHANNEL_ID)
//         .header("serviceId", CommonTestVariable.SERVICE_ID)
//         .header("requestId", CommonTestVariable.REQUEST_ID)
//         .content(IMAGE_REQUEST_JSON);

//     MvcResult deferredResult = this.mockMvc.perform(builder).andReturn();

//     this.mockMvc.perform(asyncDispatch(deferredResult))
//         .andExpect(status().isOk())
//         .andReturn();

//     verify(this.imageService).createImages(
//         CommonTestVariable.MANDATORY_REQUEST,
//         IMAGES_REQUEST,
//         PRIVILEGES, CommonTestVariable.SESSION_DATA);


//   }

//   @Before
//   public void setUp() throws Exception {
//     MockitoAnnotations.initMocks(this);

//     this.IMAGES_REQUEST = new ImagesRequest();
//     this.IMAGES_REQUEST.setData("asdaqweqwe");
//     this.IMAGES_REQUEST.setName("yosia.jpg");

//     this.IMAGES_RESPONSE = new ImagesResponse();
//     this.IMAGES_RESPONSE.setName(IMAGE_NAME);

//     this.IMAGES_RESPONSE_BASE = new GatewayBaseResponse<>();
//     this.IMAGES_RESPONSE_BASE.setCode("SUCCCESS");
//     this.IMAGES_RESPONSE_BASE.setData(this.IMAGES_RESPONSE);

//     PowerMockito.mockStatic(MDC.class);
//     PowerMockito.when((String) MDC.get(BaseMongoFields.PRIVILEGES)).thenReturn
//         (PRIVILEGES);
//     PowerMockito.when((String) MDC.get(BaseMongoFields.STORE_ID)).thenReturn
//         (CommonTestVariable.STORE_ID);
//     PowerMockito.when((String) MDC.get(BaseMongoFields.CHANNEL_ID)).thenReturn
//         (CommonTestVariable.CHANNEL_ID);
//     PowerMockito.when((String) MDC.get(BaseMongoFields.SERVICE_ID)).thenReturn
//         (CommonTestVariable.SERVICE_ID);
//     PowerMockito.when((String) MDC.get(BaseMongoFields.REQUEST_ID)).thenReturn
//         (CommonTestVariable.REQUEST_ID);
//     PowerMockito.when((String) MDC.get(BaseMongoFields.USERNAME)).thenReturn
//         (CommonTestVariable.USERNAME);


//     this.mockMvc = MockMvcBuilders.standaloneSetup(this.imageController).build();
//   }

//   @After
//   public void tearDown() throws Exception {
//     verifyNoMoreInteractions(this.imageService);
//   }
}
