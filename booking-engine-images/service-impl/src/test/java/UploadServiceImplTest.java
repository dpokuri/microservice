import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.unit.test.UploadTestVariable;
import com.tl.booking.image.libraries.configuration.AssetsFileProperties;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.libraries.utility.FileHelper;
import com.tl.booking.image.service.impl.UploadServiceImpl;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileHelper.class})
public class UploadServiceImplTest {

  @InjectMocks
  private UploadServiceImpl uploadService;

  @Mock
  private AssetsFileProperties assetsFileProperties;


  @Test
  public void createPhotoTestSuccess()   throws Exception {

    File file = new File(UploadTestVariable.PHOTO_FILE);


    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(assetsFileProperties.getMaxFileSize()).thenReturn(100000);

    uploadService.createdPhoto(UploadTestVariable.PHOTO_FILE_OBJECT, UploadTestVariable.PHOTO_PATH_URL, UploadTestVariable.PATH_DATE);

  }

  @Test
  public void createPhotoTestExceptionMaxLimit()   throws Exception {

    File file = new File(UploadTestVariable.PHOTO_FILE);


    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(assetsFileProperties.getMaxFileSize()).thenReturn(1);

    try{
      uploadService.createdPhoto(UploadTestVariable.PHOTO_FILE_OBJECT, UploadTestVariable.PHOTO_PATH_URL, UploadTestVariable.PATH_DATE);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.FILE_SIZE_EXCEEDS_MAXIMUM_LIMIT.getCode(), be.getCode());
      assertEquals(ResponseCode.FILE_SIZE_EXCEEDS_MAXIMUM_LIMIT.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getPhotoTestSuccess()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY)).thenReturn(UploadTestVariable.FILE_PROPERTY_READ);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY)).thenReturn(UploadTestVariable.TYPES);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY, UploadTestVariable.PHOTO_FILE);

  }


  @Test
  public void getPhotoTestSuccessWithCROP()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_WITH_CRO)).thenReturn(UploadTestVariable.FILE_PROPERTY_READ_WITH_CRO);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_WITH_CRO)).thenReturn(UploadTestVariable.TYPES_WITH_CRO);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_WITH_CRO, UploadTestVariable.PHOTO_FILE);

  }

  @Test
  public void getPhotoTestExceptionWithoutWR()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_WITHOUT_CRO)).thenReturn(UploadTestVariable.FILE_PROPERTY_READ_WITHOUT_CRO);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_WITHOUT_CRO)).thenReturn(UploadTestVariable.TYPES_WITHOUT_CRO);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    try{
      uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_WITHOUT_CRO, UploadTestVariable.PHOTO_FILE);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.WIDTH_RATIO_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.WIDTH_RATIO_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getPhotoTestExceptionWithoutRatWidthOrHeight()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_WITHOUT_WIDTH)).thenReturn(UploadTestVariable.FILE_PROPERTY_READ_WITHOUT_WIDTH);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_WITHOUT_WIDTH)).thenReturn(UploadTestVariable.TYPES_WITHOUT_WIDTH);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    try{
      uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_WITHOUT_WIDTH, UploadTestVariable.PHOTO_FILE);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getPhotoTestExceptionWithoutResWidthOrHeight()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_RES_WITHOUT_WIDTH)).thenReturn(UploadTestVariable.FILE_PROPERTY_READ_RES_WITHOUT_WIDTH);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_RES_WITHOUT_WIDTH)).thenReturn(UploadTestVariable.TYPES_RES_WITHOUT_WIDTH);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    try{
      uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_RES_WITHOUT_WIDTH, UploadTestVariable.PHOTO_FILE);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage(), be.getMessage());
    }

  }


  @Test
  public void getPhotoTestSuccessQ100()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_RES_Q_100)).thenReturn(UploadTestVariable.FILE_PROPERTY_READ_RES_Q_100);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_RES_Q_100)).thenReturn(UploadTestVariable.TYPES_RES_Q_100);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_RES_Q_100, UploadTestVariable.PHOTO_FILE);

  }

  @Test
  public void getPhotoTestSuccessRESCRO()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_RES_CRO)).thenReturn(UploadTestVariable.FILE_PROPERTY_RES_CRO);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_RES_CRO)).thenReturn(UploadTestVariable.TYPES_RES_CRO);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_RES_CRO, UploadTestVariable.PHOTO_FILE);

  }

  @Test
  public void getPhotoTestSuccessRESRAT()   throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_RES_RAT)).thenReturn(UploadTestVariable.FILE_PROPERTY_RES_RAT);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_RES_RAT)).thenReturn(UploadTestVariable.TYPES_RES_RAT);

    when(assetsFileProperties.getPhoto()).thenReturn(UploadTestVariable.PHOTO_PATH);

    uploadService.generateImage(UploadTestVariable.IMAGE_DATE_PATH, UploadTestVariable.PROPERTY_RES_RAT, UploadTestVariable.PHOTO_FILE);

  }


  @Before
  public void setUp() {

    initMocks(this);

  }


  @After
  public void tearDown() {

  }
}
