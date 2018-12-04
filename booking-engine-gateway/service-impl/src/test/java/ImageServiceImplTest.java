import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.outbound.api.ImageOutboundService;
import com.tl.booking.gateway.outbound.impl.ImageOutboundServiceImpl;
import com.tl.booking.gateway.outbound.impl.configuration.ImageEndPointService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.impl.ImageServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.unit.test.CacheTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.PromoPageTestVariable;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import retrofit2.Call;

public class ImageServiceImplTest extends CacheTestVariable {

  @InjectMocks
  ImageServiceImpl imageServiceImpl;

  @Mock
  ImageOutboundService imageOutboundService;

  @Mock
  PrivilegeService privilegeService;

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

  String PRIVILEGES = "300,301,302,303,304,305,306";

  String IMAGE_NAME = "bababia.jpg";

  @Test
  public void createImagesTest() throws Exception {
    when(this.privilegeService.checkAuthorized(PrivilegeId.CREATE_IMAGES, this.PRIVILEGES))
        .thenReturn
            (Single
                .just(true));
    when(this.imageOutboundService.createImages(CommonTestVariable
            .MANDATORY_REQUEST, this.IMAGES_REQUEST ))
        .thenReturn
            (Single.just(this.IMAGES_RESPONSE_BASE));

    when(this.privilegeService.getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        this.PRIVILEGES)).thenReturn(Single.just(PromoPageTestVariable
        .PRIVILEGE_RESPONSE));

    BaseResponse<ImagesResponse> imagesResponseBaseResponse =  this.imageServiceImpl
        .createImages(
            CommonTestVariable.MANDATORY_REQUEST,
            this.IMAGES_REQUEST,this.PRIVILEGES,
            CommonTestVariable.SESSION_DATA
        ).blockingGet();

    assertEquals(IMAGE_NAME, imagesResponseBaseResponse.getData().getName());
    verify(this.privilegeService).checkAuthorized(PrivilegeId.CREATE_IMAGES, this.PRIVILEGES);
    verify(this.imageOutboundService).createImages(CommonTestVariable
        .MANDATORY_REQUEST, this.IMAGES_REQUEST );

    verify(this.privilegeService).getAuthorizedPrivileges(CommonTestVariable.MANDATORY_REQUEST,
        this.PRIVILEGES);
  }

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

    this.IMAGES_RESPONSE = new ImagesResponse();
    this.IMAGES_RESPONSE.setName(IMAGE_NAME);

    this.IMAGES_RESPONSE_BASE = new GatewayBaseResponse<>();
    this.IMAGES_RESPONSE_BASE.setCode("SUCCCESS");
    this.IMAGES_RESPONSE_BASE.setData(this.IMAGES_RESPONSE);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.imageOutboundService);
  }
}
