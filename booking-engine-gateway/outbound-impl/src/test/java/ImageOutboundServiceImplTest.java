import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.outbound.impl.ImageOutboundServiceImpl;
import com.tl.booking.gateway.outbound.impl.configuration.ImageEndPointService;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;
import retrofit2.Response;


public class ImageOutboundServiceImplTest {

  Map<String, String> HEADER;

  Map<String, String> QUERY;

  ImagesResponse IMAGES_RESPONSE;

  GatewayBaseResponse<ImagesResponse> IMAGES_RESPONSE_BASE;

  ImagesRequest IMAGES_REQUEST;

  @InjectMocks
  ImageOutboundServiceImpl imageOutboundServiceImpl;

  @Mock
  ImageEndPointService imageEndPointService;

  @Mock
  Call<GatewayBaseResponse<ImagesResponse>> IMAGE_RESPONSE_CALL;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.HEADER = new HashMap<>();
    this.HEADER.put("storeId", CommonTestVariable.STORE_ID);
    this.HEADER.put("channelId", CommonTestVariable.CHANNEL_ID);
    this.HEADER.put("requestId", CommonTestVariable.REQUEST_ID);
    this.HEADER.put("serviceId", CommonTestVariable.SERVICE_ID);
    this.HEADER.put("username", CommonTestVariable.USERNAME);

    this.IMAGES_REQUEST = new ImagesRequest();
    this.IMAGES_REQUEST.setData("hjknlmghvbjknnhgvhjbknjhjg");
    this.IMAGES_REQUEST.setName("yosia.jpg");

    this.IMAGES_RESPONSE_BASE = new GatewayBaseResponse<>();
    this.IMAGES_RESPONSE_BASE.setCode("SUCCCESS");
    this.IMAGES_RESPONSE_BASE.setData(this.IMAGES_RESPONSE);
  }

  @Test
  public void createImagesTest() throws Exception {
    when(this.imageEndPointService.createImages(this.HEADER, this.IMAGES_REQUEST)).thenReturn
        (IMAGE_RESPONSE_CALL);
    when(this.IMAGE_RESPONSE_CALL.execute()).thenReturn(Response.success(this.IMAGES_RESPONSE_BASE));

    GatewayBaseResponse<ImagesResponse> imagesResponseGatewayBaseResponse = this.imageOutboundServiceImpl
        .createImages(CommonTestVariable
                .MANDATORY_REQUEST, IMAGES_REQUEST).blockingGet();

    verify(this.imageEndPointService).createImages(this.HEADER, this.IMAGES_REQUEST);
  }

  @Test
  public void createImagesErrorTest() throws Exception {
    when(this.imageEndPointService.createImages(this.HEADER, this.IMAGES_REQUEST)).thenReturn
        (null);
    try {
      GatewayBaseResponse<ImagesResponse> imagesResponseGatewayBaseResponse = this.imageOutboundServiceImpl
          .createImages(CommonTestVariable
              .MANDATORY_REQUEST, IMAGES_REQUEST).blockingGet();
    } catch (Exception e){
      verify(this.imageEndPointService).createImages(this.HEADER, this.IMAGES_REQUEST);
    }

  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.imageEndPointService);
  }

}
