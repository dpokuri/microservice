import static org.junit.Assert.assertEquals;

import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.unit.test.CloudinaryTestVariabel;
import com.tl.booking.image.libraries.configuration.CloudinaryConfiguration;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.service.impl.CloudinaryServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.net.*", "javax.security.auth.*"})
public class CloudinaryServiceImplTest extends CloudinaryTestVariabel {

  @InjectMocks
  private CloudinaryServiceImpl cloudinaryService;

  @Mock
  private CloudinaryConfiguration cloudinaryConfiguration;

  @Test
  public void uploadFailedTest()  {

    when(cloudinaryConfiguration.createCloudinary()).thenReturn(CLOUDINARY);
    try {
      cloudinaryService.uploadImage( PROPERTY_DATE, IMAGE_REQUEST);
    } catch (BusinessLogicException be) {

      verify(cloudinaryConfiguration).createCloudinary();
      assertEquals(ResponseCode.UPLOAD_FILE_FAILED.getCode(), be.getCode());
      assertEquals(ResponseCode.UPLOAD_FILE_FAILED.getMessage(), be.getMessage());
    }
  }

  @Test
  public void uploadSuccessTest()  {

    when(cloudinaryConfiguration.createCloudinary()).thenReturn(CLOUDINARY);
    String urlCloudinary = cloudinaryService.uploadImage(PROPERTY_DATE, IMAGE_REQUEST);

    verify(cloudinaryConfiguration).createCloudinary();
    assertEquals( URL_UPLOAD_IMAGE,replaceVersion(urlCloudinary));
  }

  @Test
  public void imageOriginal() {

    when(cloudinaryConfiguration.createCloudinary()).thenReturn(CLOUDINARY);
    String urlImage = cloudinaryService.getImagesOriginal(IMAGE_NAME);

    verify(cloudinaryConfiguration).createCloudinary();
    assertEquals(URL_ORIGINAL_IMAGE, urlImage);

  }

  @Test
  public void imageTransformationRatioTest() {

    when(cloudinaryConfiguration.createCloudinary()).thenReturn(CLOUDINARY);
    String urlImage = cloudinaryService.imageTransformationRatio(WIDTH, IMAGE_NAME);

    verify(cloudinaryConfiguration).createCloudinary();
    assertEquals(URL_TRANSFORMATION_RATIO, urlImage);

  }

  @Test
  public void imageTransformationCropCenter() {

    when(cloudinaryConfiguration.createCloudinary()).thenReturn(CLOUDINARY);
    String urlImage = cloudinaryService.imageTransformationCropCenter(WIDTH, HEIGHT, IMAGE_NAME);

    verify(cloudinaryConfiguration).createCloudinary();
    assertEquals(URL_TRANDFORMATION_CROP, urlImage);
  }

  @Test
  public void imageTransformationResizeCenter() {

    when(cloudinaryConfiguration.createCloudinary()).thenReturn(CLOUDINARY);
    String urlImage = cloudinaryService.imageTransformationResize(WIDTH, HEIGHT, IMAGE_NAME);

    verify(cloudinaryConfiguration).createCloudinary();
    assertEquals(URL_TRANSFORMATION_RES, urlImage);
  }


  public String replaceVersion(String urlCLoudinary) {
    String uploadedUrl = "";
    String[] url = urlCLoudinary.split("/");
    if (url != null && url.length > 5) {
      List<String> list = new ArrayList<String>(Arrays.asList(url));
      list.remove(5);
      for (String parts : list) {
        uploadedUrl += parts + "/";
      }
    }
    return uploadedUrl;
  }

  @Before
  public void setUp() {

    initMocks(this);
  }


  @After
  public void tearDown() {

  }
}
