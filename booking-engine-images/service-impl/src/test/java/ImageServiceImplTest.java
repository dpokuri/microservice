import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.image.dao.api.ImagePropertiesRepository;
import com.tl.booking.image.dao.api.ImageRepository;
import com.tl.booking.image.dao.api.SystemParameterRepository;
import com.tl.booking.image.entity.constant.enums.EncryptionType;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.fields.MandatoryFields;
import com.tl.booking.image.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.image.entity.constant.unit.test.ImageTestVariable;
import com.tl.booking.image.entity.constant.unit.test.UploadTestVariable;
import com.tl.booking.image.entity.dao.Image;
import com.tl.booking.image.libraries.configuration.AssetsFileProperties;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.libraries.utility.DateFormatterHelper;
import com.tl.booking.image.libraries.utility.FileHelper;
import com.tl.booking.image.libraries.utility.SecurityHelper;
import com.tl.booking.image.service.api.CloudinaryService;
import com.tl.booking.image.service.api.UploadService;
import com.tl.booking.image.service.impl.ImageServiceImpl;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.net.*", "javax.security.auth.*"})
@PrepareForTest({MDC.class, DateFormatterHelper.class, FileHelper.class, SecurityHelper.class,
    Files.class})
public class ImageServiceImplTest extends ImageTestVariable {

  @InjectMocks
  private ImageServiceImpl imageService;

  @Mock
  private ImageRepository imageRepository;

  @Mock
  private UploadService uploadService;

  @Mock
  private SystemParameterRepository systemParameterRepository;

  @Mock
  private AssetsFileProperties assetsFileProperties;

  @Mock
  private ImagePropertiesRepository imagePropertiesRepository;

  @Mock
  private CloudinaryService cloudinaryService;

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  @Test
  public void createImageTestSuccess() throws Exception {
    PowerMockito.mockStatic(DateFormatterHelper.class, FileHelper.class, SecurityHelper.class);
    PowerMockito.when(DateFormatterHelper.convertDateToString(any(), any())).thenReturn
        (CONVERT_DATE_TO_STRING_CREATE_SERVICE);

    PowerMockito.when(
        SecurityHelper.encrypt(NAME_IMAGE_STRING, EncryptionType.MD5.getValue()))
        .thenReturn(NAME_IMAGE_MD5);

    PowerMockito.when(FileHelper.generateNamePhoto(NAME_IMAGE_MD5 + ".jpg"))
        .thenReturn
            (IMAGE_STRING_GENERATE);

    when(cloudinaryService
        .uploadImage(
            CONVERT_DATE_TO_STRING_CREATE_SERVICE,
            IMAGE_REQUEST)).thenReturn
        (URL_ORIGINAL_IMAGE_CLOUDINARY);

    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            VARIABLE_STRING))
        .thenReturn(CREATE_SYSTEM_PARAMETER_CREATE);

    when(assetsFileProperties.getPhoto()).thenReturn(PHOTO_PATH);

    when(this.imageRepository.save(IMAGE_CREATE_SERVICE_2))
        .thenReturn(IMAGE_CREATE_SERVICE_2);

    when(assetsFileProperties.getPathPhotoUrl()).thenReturn(PHOTO_PATH_URL);

    when(assetsFileProperties.getHostPhoto()).thenReturn(HOST_PHOTO);

    Image createdImage = this.imageService
        .createImage(IMAGE_FOR_CREATE_SERVICE, IMAGE_REQUEST);

    verify(cloudinaryService).uploadImage(
        CONVERT_DATE_TO_STRING_CREATE_SERVICE, IMAGE_REQUEST);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            VARIABLE_STRING);

    verify(this.imageRepository).save(IMAGE_CREATE_SERVICE_2);

    assertEquals(URL_UPLOADED_IMAGE, createdImage.getName());

  }


  @Test
  public void createImageTestExceptionNotValidException() throws Exception {

    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            VARIABLE_STRING)).thenReturn(null);

    try {
      this.imageService
          .createImage(IMAGE_NotValidException, IMAGE_REQUEST);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.MERCHANT_PHOTO_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.MERCHANT_PHOTO_NOT_EXIST.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
              VARIABLE_STRING);

    }

  }

  @Test
  public void createImageTestExceptionPhotoExtensionNotmatch() throws Exception {

    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            VARIABLE_STRING))
        .thenReturn(SYSTEM_PARAMETER_OUTER_EXTENSION);

    try {
      this.imageService.createImage(IMAGE_PhotoExtensionNotmatch,
          IMAGE_REQUEST);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.PHOTO_EXTENSION_NOT_MATCH.getCode(), be.getCode());
      assertEquals(ResponseCode.PHOTO_EXTENSION_NOT_MATCH.getMessage(), be.getMessage());

      verify(this.systemParameterRepository)
          .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
              VARIABLE_STRING);

    }

  }

  @Test
  public void createImageFromUrlTestSuccess() throws Exception {

    PowerMockito.mockStatic(DateFormatterHelper.class, FileHelper.class, SecurityHelper.class);
    PowerMockito.when(DateFormatterHelper.convertDateToString(any(), any())).thenReturn
        (CONVERT_DATE_TO_STRING_CREATE_SERVICE);

    PowerMockito.when(SecurityHelper
        .encrypt(NAME_IMAGE_STRING_FOR_URL, EncryptionType.MD5.getValue()))
        .thenReturn(NAME_IMAGE_MD5);

    PowerMockito.when(
        FileHelper.generateNamePhoto(any()))
        .thenReturn
            (IMAGE_STRING_GENERATE);

    PowerMockito.when(FileHelper.stringToUrl(STRING_URL))
        .thenReturn(IMAGE_URL);

    PowerMockito.when(FileHelper
        .getPhotoDataFromUrl(any(),
            any()))
        .thenReturn(PHOTO_FILE);

    when(cloudinaryService.uploadImage(
        CONVERT_DATE_TO_STRING_CREATE_SERVICE, IMAGE_REQUEST_URL)).thenReturn
        (URL_UPLOADED_IMAGE);

    when(this.systemParameterRepository
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            VARIABLE_STRING))
        .thenReturn(CREATE_SYSTEM_PARAMETER_CREATE);

    when(assetsFileProperties.getPhoto()).thenReturn(PHOTO_PATH);

    when(this.imageRepository.save(IMAGE_CREATED))
        .thenReturn(IMAGE_CREATED);

    when(assetsFileProperties.getPathPhotoUrl()).thenReturn(PHOTO_PATH_URL);

    when(assetsFileProperties.getHostPhoto()).thenReturn(HOST_PHOTO);

    Image createdImage = this.imageService
        .createImageFromURL(IMAGE_PARAM, IMAGE_REQUEST_URL);

    verify(cloudinaryService).uploadImage(
        CONVERT_DATE_TO_STRING_CREATE_SERVICE,
        IMAGE_REQUEST_URL);

    verify(this.systemParameterRepository)
        .findSystemParameterByStoreIdAndVariable(CommonTestVariable.STORE_ID,
            VARIABLE_STRING);

    verify(this.imageRepository).save(IMAGE_CREATE_SERVICE);

    assertEquals(URL_UPLOADED_IMAGE, createdImage.getName());

  }

  @Test
  public void getImageTestSuccessWithoutProperties() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    String urlPathImage = PHOTO_PATH + getDateFormatImage;

    String[] arrayFolder = urlPathImage.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp
        .newFile(urlPathImage + PROPERTIES + "." + IMAGE_NAME);

    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
    final String s = FileUtils.readFileToString(tempFile);

    when(assetsFileProperties.getPhoto()).thenReturn(absoluteFolder[0]);

    String getImage = imageService
        .getImage(dateFormatImage, IMAGE_NAME);

    verify(this.imageRepository).findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0);

    assertEquals(tempFolder.getAbsolutePath() + "/" + IMAGE_NAME, getImage);
  }


  @Test
  public void getImageTestSuccessWithProperties() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTIES, 0))
        .thenReturn(IMAGE_PROPERTIES);

    String urlPathImage = PHOTO_PATH + getDateFormatImage;

    String[] arrayFolder = urlPathImage.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp
        .newFile(urlPathImage + PROPERTIES + "." + IMAGE_NAME);

    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
    final String s = FileUtils.readFileToString(tempFile);

    when(assetsFileProperties.getPhoto()).thenReturn(absoluteFolder[0]);

    String getImage = imageService.getImage(dateFormatImage,
        PROPERTIES + "." + IMAGE_NAME);

    verify(this.imageRepository).findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0);

    verify(this.imagePropertiesRepository)
        .findByPropertiesAndIsDeleted(PROPERTIES, 0);

    verify(this.uploadService).generateImage(
        getDateFormatImage, PROPERTIES,
        IMAGE_NAME);

    assertEquals(tempFolder.getAbsolutePath() + "/" + PROPERTIES + "."
        + IMAGE_NAME, getImage);
  }


  @Test
  public void getImageTestExceptionPropertyNotExist() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTIES, 0))
        .thenReturn(null);

    try {
      imageService.getImage(dateFormatImage,
          PROPERTIES + "." + IMAGE_NAME);
    } catch (BusinessLogicException be) {
      verify(this.imageRepository).findImageByNameAndIsDeleted(
          getDateFormatImage + IMAGE_NAME, 0);

      verify(this.imagePropertiesRepository)
          .findByPropertiesAndIsDeleted(PROPERTIES, 0);

      assertEquals(ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getMessage(), be.getMessage());

    }
  }

  @Test
  public void getImageTestExceptionFileNameImageInvalid() throws Exception {

    try {
      imageService
          .getImage(dateFormatImage, IMAGE_NAME_INVALID);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.FILE_NAME_IMAGE_INVALID.getCode(), be.getCode());
      assertEquals(ResponseCode.FILE_NAME_IMAGE_INVALID.getMessage(), be.getMessage());
    }
  }

  @Test
  public void getImageTestExceptionNotImagesExist() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTIES, 0))
        .thenReturn(IMAGE_PROPERTIES);
    when(assetsFileProperties.getPhoto()).thenReturn(PHOTO_PATH_URL);

    try {
      imageService.getImage(dateFormatImage,
          PROPERTIES + "." + IMAGE_NAME);
    } catch (BusinessLogicException be) {
      verify(this.imageRepository).findImageByNameAndIsDeleted(
          getDateFormatImage + IMAGE_NAME, 0);

      verify(this.imagePropertiesRepository)
          .findByPropertiesAndIsDeleted(PROPERTIES, 0);

      verify(this.uploadService).generateImage(
          getDateFormatImage, PROPERTIES,
          IMAGE_NAME);

      assertEquals(ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getMessage(), be.getMessage());
      assertEquals(ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getCode(), be.getCode());

    }
  }

  @Test
  public void getImageTestExceptionImageNotFound() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(null);

    try {
      imageService.getImage(dateFormatImage,
          PROPERTIES + "." + IMAGE_NAME);
    } catch (BusinessLogicException be) {
      verify(this.imageRepository).findImageByNameAndIsDeleted(
          getDateFormatImage + IMAGE_NAME, 0);

      assertEquals(ResponseCode.IMAGE_NOT_FOUND.getMessage(), be.getMessage());
      assertEquals(ResponseCode.IMAGE_NOT_FOUND.getCode(), be.getCode());

    }
  }

  @Test
  public void outputStreamExist() throws Exception {

    String pathFolder = PHOTO_PATH + getDateFormatImage;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    File tempFile = temp.newFile(pathFolder + NAME_IMAGE_STRING);

    String name = tempFolder.getAbsolutePath() + "/" + NAME_IMAGE_STRING;

    ResponseEntity<InputStreamResource> outputStream = this.imageService.outputStream(name);

    ResponseEntity<InputStreamResource> outputStreamCompare = outputStream(name);

    assertEquals(outputStream.getStatusCode(), outputStreamCompare.getStatusCode());


  }

  @Test
  public void outputStreamURLExistTest() throws Exception {

    ResponseEntity<InputStreamResource> outputStream = this.imageService
        .outputStreamFromURL(URL_IMAGE_CLOUDINARY);

    ResponseEntity<InputStreamResource> outputStreamCompare = outputStreamFromURL(
        URL_IMAGE_CLOUDINARY);

    assertEquals(outputStream.getStatusCode(), outputStreamCompare.getStatusCode());


  }

  @Test
  public void outputStreamURLExecptionTest() throws Exception {

    try {
      this.imageService.outputStreamFromURL(URL_IMAGE_CLOUDINARY_EXCEPTION);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.CANNOT_INPUT_STREAM_RESOURCE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_INPUT_STREAM_RESOURCE.getMessage(), be.getMessage());
    }


  }

  @Test
  public void removeFilePropertyTestSuccess() throws Exception {
    String pathFolder = PHOTO_PATH + getDateFormatImage;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    File tempFile = temp.newFile(pathFolder + NAME_IMAGE_STRING);

    String name = tempFolder.getAbsolutePath() + "/" + PROPERTIES + "."
        + NAME_IMAGE_STRING;

    imageService.removeFileProperty(name);

  }

  @Test
  public void removeFilePropertyFailedTest() throws Exception {

    String pathFolder = PHOTO_PATH + getDateFormatImage;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);
    String name = tempFolder.getAbsolutePath() + "/" + PROPERTIES + "."
        + NAME_IMAGE_STRING;

    try {

      imageService.removeFileProperty(name);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getCode(), be.getCode());
      assertEquals(ResponseCode.IMAGE_NOT_FOUND_IN_ASSETS.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getImageSuccessWithoutPropertiesCloudinaryTest() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(cloudinaryService.getImagesOriginal(IMAGE_NAME_CLOUDINARY + ".jpg"))
        .thenReturn(URL_ORIGINAL_IMAGE_CLOUDINARY);

    String getImage = imageService.getImageCloudinary(dateFormatImage,
        IMAGE_NAME_CLOUDINARY + ".jpg");
    verify(this.imageRepository).findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0);

    assertEquals(URL_ORIGINAL_IMAGE_CLOUDINARY, getImage);

  }

  @Test
  public void getImageCloudinaryExceptionFileNameImageInvalidTest() throws Exception {

    try {
      imageService.getImageCloudinary(dateFormatImage,
          IMAGE_NAME_INVALID);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.FILE_NAME_IMAGE_INVALID.getCode(), be.getCode());
      assertEquals(ResponseCode.FILE_NAME_IMAGE_INVALID.getMessage(), be.getMessage());
    }
  }

  @Test
  public void getImageCloudinaryTestExceptionPropertyNotExist() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTIES, 0))
        .thenReturn(null);

    try {
      imageService.getImageCloudinary(dateFormatImage,
          PROPERTIES + "." + IMAGE_NAME);
    } catch (BusinessLogicException be) {
      verify(this.imageRepository).findImageByNameAndIsDeleted(
          getDateFormatImage + IMAGE_NAME, 0);

      verify(this.imagePropertiesRepository)
          .findByPropertiesAndIsDeleted(PROPERTIES, 0);

      assertEquals(ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.PROPERTY_IMAGE_NOT_EXIST.getMessage(), be.getMessage());

    }
  }

  @Test
  public void getImageSuccessResCloudinaryTest() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTY_RES_CLOUDINARY, 0))
        .thenReturn(IMAGE_PROPERTIES_RES_CLOUDINARY);

    when(cloudinaryService
        .imageTransformationResize("2000", "500", IMAGE_NAME_CLOUDINARY + ".jpg"))
        .thenReturn(URL_IMAGE_RES_CLOUDINARY);

    String getImage = imageService.getImageCloudinary(dateFormatImage,
        PROPERTY_RES_CLOUDINARY + "." + IMAGE_NAME_CLOUDINARY
            + ".jpg");
    verify(this.imageRepository).findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0);

    verify(this.imagePropertiesRepository)
        .findByPropertiesAndIsDeleted(PROPERTY_RES_CLOUDINARY, 0);

    assertEquals(URL_IMAGE_RES_CLOUDINARY, getImage);
  }

  @Test
  public void getImageSuccessRatCloudinaryTest() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTY_RAT_WITHOUT_WR, 0))
        .thenReturn(IMAGE_PROPERTIES_RAT_CLOUDINARY);

    when(cloudinaryService
        .imageTransformationRatio("2000", IMAGE_NAME_CLOUDINARY + ".jpg"))
        .thenReturn(URL_RATIO_IMAGE_CLOUDINARY);

    String getImage = imageService.getImageCloudinary(dateFormatImage,
        PROPERTY_RAT_WITHOUT_WR + "." + IMAGE_NAME_CLOUDINARY
            + ".jpg");
    verify(this.imageRepository).findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0);

    verify(this.imagePropertiesRepository)
        .findByPropertiesAndIsDeleted(PROPERTY_RAT_WITHOUT_WR, 0);

    assertEquals(URL_RATIO_IMAGE_CLOUDINARY, getImage);
  }

  @Test
  public void getImageSuccessCROCloudinaryTest() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0))
        .thenReturn(IMAGE_GET_SERVICE_RESPONSE);

    when(this.imagePropertiesRepository
        .findByPropertiesAndIsDeleted(PROPERTY_CLOUDINARY, 0))
        .thenReturn(IMAGE_PROPERTIES_CRO_CLOUDINARY);

    when(cloudinaryService.imageTransformationCropCenter("1000", "250",
        IMAGE_NAME_CLOUDINARY + ".jpg"))
        .thenReturn(URL_IMAGE_CLOUDINARY);

    String getImage = imageService.getImageCloudinary(dateFormatImage,
        PROPERTY_CLOUDINARY + "." + IMAGE_NAME_CLOUDINARY
            + ".jpg");
    verify(this.imageRepository).findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME_CLOUDINARY + ".jpg", 0);

    verify(this.imagePropertiesRepository)
        .findByPropertiesAndIsDeleted(PROPERTY_CLOUDINARY, 0);

    assertEquals(URL_IMAGE_CLOUDINARY, getImage);
  }

  @Test
  public void getImageRatioExceptionTest() {
    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_WITHOUT_CRO))
        .thenReturn(UploadTestVariable.FILE_PROPERTY_READ_WITHOUT_CRO);

    try {

      imageService.getRatioType(UploadTestVariable.FILE_PROPERTY_READ_RES_WITHOUT_WIDTH,
          Integer.valueOf(QUALITY_CLOUDINARY),
          IMAGE_NAME_CLOUDINARY);
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.WIDTH_RATIO_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.WIDTH_RATIO_NOT_EXIST.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getImageRatioSuccessTest() throws Exception {

    PowerMockito.mockStatic(FileHelper.class);
    PowerMockito.when(FileHelper.filePropertyRead(UploadTestVariable.PROPERTY_RAT))
        .thenReturn(UploadTestVariable.FILE_PROPERTY_RES_RAT);
    PowerMockito.when(FileHelper.generateType(UploadTestVariable.PROPERTY_RAT))
        .thenReturn(UploadTestVariable.TYPES_RAT);

    PowerMockito.when(cloudinaryService.imageTransformationRatio(WR,
        IMAGE_NAME_CLOUDINARY + ".jpg"))
        .thenReturn(URL_RATIO_IMAGE_CLOUDINARY);

    assertEquals(URL_RATIO_IMAGE_CLOUDINARY, cloudinaryService
        .imageTransformationRatio(WR,
            IMAGE_NAME_CLOUDINARY + ".jpg"));
  }

  @Test
  public void getImageResExceptionWithoutRatWidthOrHeightTest() throws Exception {

    try {

      imageService.getResizeType(null, HEIGHT,
          Integer.valueOf(QUALITY_CLOUDINARY),
          IMAGE_NAME_CLOUDINARY + ".jpg");
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void getImageCLoudinaryExceptionImageNotFoundTest() throws Exception {

    when(this.imageRepository.findImageByNameAndIsDeleted(
        getDateFormatImage + IMAGE_NAME, 0))
        .thenReturn(null);

    try {
      imageService.getImageCloudinary(dateFormatImage,
          PROPERTIES + "." + IMAGE_NAME);
    } catch (BusinessLogicException be) {
      verify(this.imageRepository).findImageByNameAndIsDeleted(
          getDateFormatImage + IMAGE_NAME, 0);

      assertEquals(ResponseCode.IMAGE_NOT_FOUND.getMessage(), be.getMessage());
      assertEquals(ResponseCode.IMAGE_NOT_FOUND.getCode(), be.getCode());

    }
  }

  @Test
  public void getImageCropExceptionWithoutWidthOrHeightTest() throws Exception {

    try {

      imageService.getCropType(WIDTH, null,
          Integer.valueOf(QUALITY_CLOUDINARY),
          IMAGE_NAME_CLOUDINARY + ".jpg");
    } catch (BusinessLogicException be) {
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getCode(), be.getCode());
      assertEquals(ResponseCode.WIDTH_OR_HEIGHT_NOT_EXIST.getMessage(), be.getMessage());
    }
  }

  @Test
  public void getQualityTest() {
    Integer qualityActual = imageService
        .getQuality(UploadTestVariable.FILE_PROPERTY_READ_RES_WITHOUT_WIDTH);
    assertEquals(Integer.valueOf(QUALITY_CLOUDINARY), qualityActual);
  }

  @Test
  public void reUploadImageSuccess() {
    when(imageRepository.findAll()).thenReturn(IMAGES);
    when(cloudinaryService
        .uploadImageFromFile(FILE, IMAGE_NAME_CLOUDINARY))
        .thenReturn(URL_UPLOADED_IMAGE);
    when(assetsFileProperties.getPhoto()).thenReturn(PHOTO_PATH);
    when(imageRepository.save(IMAGE_FROM_FILES)).thenReturn(IMAGE_FROM_FILES);

    Boolean uploadedImages = imageService.reUploadImageToCloudinary();

    verify(cloudinaryService).uploadImageFromFile(any(), any());
    verify(imageRepository).findAll();
    verify(assetsFileProperties).getPhoto();
    verify(imageRepository).save(IMAGE_FROM_FILES);

    assertEquals(IMAGE_UPLOADED, uploadedImages);
  }

  public ResponseEntity<InputStreamResource> outputStream(String image) {
    File getImagePath = new File(image);

    InputStream getImage = null;
    try {
      getImage = new FileInputStream(getImagePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_PNG)
        .body(new InputStreamResource(getImage));
  }

  public ResponseEntity<InputStreamResource> outputStreamFromURL(String image) {
    InputStream getImage = null;
    try {
      getImage = new URL(image).openStream();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return ResponseEntity
        .ok()
        .eTag(assetsFileProperties.geteTagVersion())
        .contentType(MediaType.IMAGE_PNG)
        .body(new InputStreamResource(getImage));
  }

  @Before
  public void setUp() {

    initMocks(this);

    PowerMockito.mockStatic(MDC.class);
    PowerMockito.when((String) MDC.get(MandatoryFields.STORE_ID)).thenReturn
        (CommonTestVariable.STORE_ID);
    PowerMockito.when((String) MDC.get(MandatoryFields.CHANNEL_ID)).thenReturn
        (CommonTestVariable.CHANNEL_ID);
    PowerMockito.when((String) MDC.get(MandatoryFields.USERNAME)).thenReturn
        (CommonTestVariable.USERNAME);

  }


  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.imagePropertiesRepository);
    verifyNoMoreInteractions(this.imageRepository);
    verifyNoMoreInteractions(this.uploadService);
    verifyNoMoreInteractions(this.systemParameterRepository);

  }
}
