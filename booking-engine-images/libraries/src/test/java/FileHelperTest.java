import static org.junit.Assert.assertEquals;

import com.tl.booking.image.entity.PhotoFile;
import com.tl.booking.image.entity.constant.enums.ResponseCode;
import com.tl.booking.image.entity.constant.unit.test.ImageTestVariable;
import com.tl.booking.image.libraries.exception.BusinessLogicException;
import com.tl.booking.image.libraries.utility.FileHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.imageio.*", "javax.security.*","javax.net.ssl.*"})
@PrepareForTest({ImageIO.class, FileOutputStream.class})
public class FileHelperTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

  private MockMvc mockMvc;

  @InjectMocks
  private FileHelper fileHelper;


  private static PhotoFile PHOTO_FILE;
  private static String PHOTO_NAME = "nama.jpg";
  private static String PHOTO_DATA = "dataPhoto";
  private static String PATH = "123";
  public static String YEAR = "2018";
  public static String MONTH = "02";
  public static String DAY = "02";

  private static String PATH_DATE = YEAR + "/" + MONTH +"/" + DAY +"/";
  private static String PATH_IMAGE = "assets/photo/";
  private static String PROPERTY = "w_100,h_100,type_res-rat,wr_100,q_10";
  private static String PROPERTY_ERROR = "w_100,h_,type_res-rat,wr_100,q_10";
  private static Float QUALITY = 0.2f;
  private static String URL = "https://www.google.co.id/";

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  @Test
  public void createFileFromBase64TestSuccess() throws Exception {

    PhotoFile photoFile = photoFile(PHOTO_NAME, PHOTO_DATA);

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    temp.newFile(pathFolder + PHOTO_NAME);

    String pathFile = absoluteFolder[0];

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.createPhotoFromBase64(photoFile, pathFile, PATH_DATE);

  }

  @Test
  public void createFileFromBase64TestExceptionUPloadFailed() throws Exception {

    PhotoFile photoFile = photoFile(PHOTO_NAME, PHOTO_DATA);

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    temp.newFile(pathFolder + PHOTO_NAME);

    String pathFile = absoluteFolder[0];

    try{
      fileHelper.createPhotoFromBase64(photoFile, pathFile, PATH_DATE);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.UPLOAD_FILE_FAILED.getCode(), be.getCode());
    }

  }

  @Test
  public void createFileFromBase64TestExceptionCannotReadImage() throws Exception {

    PhotoFile photoFile = photoFile(PHOTO_NAME, PHOTO_DATA);

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    temp.newFile(pathFolder + PHOTO_NAME);

    String pathFile = absoluteFolder[0];

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenThrow(new IOException());

    try{
      fileHelper.createPhotoFromBase64(photoFile, pathFile, PATH_DATE);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_READ_IMAGE.getCode(), be.getCode());
    }

  }


  @Test
  public void compressImageSuccess() throws Exception {

    PhotoFile photoFile = photoFile(PHOTO_NAME, PHOTO_DATA);

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    temp.newFile(pathFolder + PHOTO_NAME);

    String pathFile = absoluteFolder[0];

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input))
        .thenThrow(new IOException());

    try{
      fileHelper.compressImage(absoluteFolder[0], PATH_DATE, PROPERTY, PHOTO_NAME, PHOTO_NAME, 10f, false);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_READ_IMAGE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_READ_IMAGE.getMessage(), be.getMessage());
    }

  }


  @Test
  public void generateNamePhotoTest() throws Exception {

    fileHelper.generateNamePhoto("name.jpg");

  }

  @Test
  public void cropImageCenterTest() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
//    final String s = FileUtils.readFileToString(tempFile);

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.cropImageCenter(absoluteFolder[0], PATH_DATE, PROPERTY,
        PHOTO_NAME, 100, 100, false);
  }

  @Test
  public void cropImageCenterTestExceptionErrorNotFoundFile() throws Exception {

    File input = new File(PATH_IMAGE +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    try{
      fileHelper.cropImageCenter(PATH_IMAGE, PATH_DATE, PROPERTY,
          PHOTO_NAME, 100, 100, false);
    }
    catch (RuntimeException r)
    {
      assertEquals(null, r.getMessage());
    }

  }

  @Test
  public void cropImageCenterTestExecuteTrue() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    File input = new File(tempFolder.getAbsolutePath() + PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.cropImageCenter(absoluteFolder[0], PATH_DATE, PROPERTY,
        tempFolder.getAbsolutePath() + PHOTO_NAME, 100, 100, true);
  }

  @Test
  public void cropImageCenterTestExceptionCannotCropImage() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
//    final String s = FileUtils.readFileToString(tempFile);

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenThrow(new IOException());

    try{
      fileHelper.cropImageCenter(absoluteFolder[0], PATH_DATE, PROPERTY,
          PHOTO_NAME, 100, 100, false);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_CROP_IMAGE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_CROP_IMAGE.getMessage(), be.getMessage());
    }
  }

  @Test
  public void ratioImageGeneratorTestExceptionNullImage() throws Exception {
    File input = new File(PATH_IMAGE +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    try{
      fileHelper.ratioImageGenerator(PATH_IMAGE, PATH_DATE, PROPERTY,
          PHOTO_NAME, 100, false);

    }
    catch (RuntimeException r)
    {
      assertEquals(null, r.getMessage());
    }

  }

  @Test
  public void ratioImageGeneratorTestExecuteTrue() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    File input = new File(tempFolder.getAbsolutePath() + PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.ratioImageGenerator(absoluteFolder[0], PATH_DATE, PROPERTY,
        tempFolder.getAbsolutePath() + PHOTO_NAME, 100, true);
  }

  @Test
  public void ratioImageGeneratorTest() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
//    final String s = FileUtils.readFileToString(tempFile);

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.ratioImageGenerator(absoluteFolder[0], PATH_DATE, PROPERTY,
        PHOTO_NAME, 100, false);

  }

  @Test
  public void ratioImageGeneratorTestExceptionCannotRAtImage() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
//    final String s = FileUtils.readFileToString(tempFile);

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenThrow(new IOException());

    try{
      fileHelper.ratioImageGenerator(absoluteFolder[0], PATH_DATE, PROPERTY,
          PHOTO_NAME, 100, false);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_RATIO_IMAGE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_RATIO_IMAGE.getMessage(), be.getMessage());
    }

  }


  @Test
  public void resizeImageTest() throws Exception {


    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
//    final String s = FileUtils.readFileToString(tempFile);

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.resizeImage(absoluteFolder[0], PATH_DATE, PROPERTY, PHOTO_NAME,
        100, 100, false);
  }

  @Test
  public void resizeImageTestExceptionCannotResizeImage() throws Exception {


    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    // Write something to it.
    FileUtils.writeStringToFile(tempFile, "hello world");

    // Read it from temp file
//    final String s = FileUtils.readFileToString(tempFile);

    File input = new File(tempFolder.getAbsolutePath() +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenThrow(new IOException());

    try{
      fileHelper.resizeImage(absoluteFolder[0], PATH_DATE, PROPERTY, PHOTO_NAME,
          100, 100, false);

    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_RESIZE_IMAGE.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_RESIZE_IMAGE.getMessage(), be.getMessage());
    }


  }

  @Test
  public void resizeImageTestExecuteTrue() throws Exception {

    String pathFolder = PATH_IMAGE + PATH_DATE;

    String[] arrayFolder = pathFolder.split("/");

    File tempFolder = temp.newFolder(arrayFolder);

    String[] absoluteFolder = tempFolder.getAbsolutePath().split(YEAR);

    File tempFile = temp.newFile(pathFolder + PHOTO_NAME);


    File input = new File(tempFolder.getAbsolutePath() + PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    fileHelper.resizeImage(absoluteFolder[0], PATH_DATE, PROPERTY, tempFolder.getAbsolutePath() + PHOTO_NAME,
        100, 100, true);

  }

  @Test
  public void resizeTestExceptionNullImage() throws Exception {
    File input = new File(PATH_IMAGE +"/"+ PHOTO_NAME);
    BufferedImage image = new BufferedImage(10,10,10);

    PowerMockito.mockStatic(ImageIO.class);
    PowerMockito.when(ImageIO.read(input)).thenReturn
        (image);

    try{
      fileHelper.resizeImage(PATH_IMAGE, PATH_DATE, PROPERTY, PHOTO_NAME,
          100, 100, false);
    }
    catch (RuntimeException r)
    {
      assertEquals(null, r.getMessage());
    }

  }

  @Test
  public void filePropertyReadTest() throws Exception {
    Map<String, Map<String, Object>>  fileproperty = fileHelper.filePropertyRead(PROPERTY);
    Map<String, Map<String, Object>> filePropertyReadExpect = filePropertyRead(PROPERTY);
    assertEquals(fileproperty,filePropertyReadExpect);
  }

  @Test
  public void filePropertyReadTestExceptionArrayIndexOutOfBounds() throws Exception {

    try {
      fileHelper.filePropertyRead(PROPERTY_ERROR);
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getCode(), be.getCode());
      assertEquals(ResponseCode.ARRAY_INDEX_OUT_OF_OUT_BOUNDS_EXCEPTION.getMessage(), be.getMessage());
    }
  }


  @Test
  public void generateTypeTest() throws Exception {
    List<String> generateType = fileHelper.generateType(PROPERTY);
    List<String> generateTypeExpect = generateType(PROPERTY);
    assertEquals(generateType, generateTypeExpect);
  }

  @Test
  public void stringToURLTest() {
    URL expectedURL = FileHelper.stringToUrl(URL);

    URL actualURL = fileHelper.stringToUrl(URL);

    assertEquals(expectedURL, actualURL);
  }

  @Test
  public void stringToURLExceptionTest() {

    try {
      fileHelper.stringToUrl("");
    }
    catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getPhotoDataFromUrlTest(){
    URL expectedURL = FileHelper.stringToUrl(ImageTestVariable.STRING_URL);
    fileHelper.getPhotoDataFromUrl(expectedURL, "name.jpg");
  }

  @Test
  public void getPhotoDataFromUrlExceptionTest(){
    URL expectedURL = FileHelper.stringToUrl(URL);
    try {
      fileHelper.getPhotoDataFromUrl(expectedURL, "name.jpg");
    }catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getMessage(), be.getMessage());
    }

  }

  @Test
  public void getPhotoDataFromUrlStreamExceptionTest(){

    try {
      fileHelper.getPhotoDataFromUrl(null, "name.jpg");
    }catch (BusinessLogicException be)
    {
      assertEquals(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getCode(), be.getCode());
      assertEquals(ResponseCode.CANNOT_GET_IMAGE_FROM_URL.getMessage(), be.getMessage());
    }

  }

  private static List<String> generateType(String fileName)
  {
    String[] paramConfig = fileName.split(",");
    List<String> types = new ArrayList<>();
    for (String getParamConfig: paramConfig) {
      String[] properties = getParamConfig.split("_");
      if(properties[0].equals("type"))
      {
        String[] propertiesType = properties[1].split("-");
        for (String getPropertiesType: propertiesType) {
          types.add(getPropertiesType);
        }
      }
    }

    return types;
  }


  private static PhotoFile photoFile(String photoName, String photoData)
  {
    PhotoFile photoFile = new PhotoFile();
    photoFile.setName(photoName);
    photoFile.setData(photoData);
    return  photoFile;
  }


  private static File createFileFromBase64String(String fileBinary, String fileName) {
    byte[] valueDecoded = Base64.decodeBase64(fileBinary);
    File file = new File(fileName);

    if (file.exists() && !file.delete()) {
      LOGGER.info("failed to delete file.");
    }

    try (OutputStream stream = new FileOutputStream(fileName)) {
      stream.write(valueDecoded);
    } catch (IOException e) {
      LOGGER.info("Failed to write file.", e);
    }

    file = new File(fileName);

    return file;
  }



  private static Map<String, Map<String, Object>> filePropertyRead(String fileName)
  {
    Map<String, Map<String, Object>> paramProperties = new HashMap<>();
    Map<String, Object> dimension = new HashMap<>();
    Map<String, Object> compression = new HashMap<>();

    String[] paramConfig = fileName.split(",");

    for (String getParamConfig: paramConfig) {
      String[] properties = getParamConfig.split("_");
      if(properties[0].equals("wr"))
      {
        Map<String, Object> wr = new HashMap<>();
        wr.put("value", properties[1]);
        paramProperties.put("wr", wr);
      }
      else if(properties[0].equals("w"))
      {
        dimension.put("w", properties[1]);
      }
      else if(properties[0].equals("h"))
      {
        dimension.put("h", properties[1]);
      }
      else if(properties[0].equals("q"))
      {
        compression.put("q", properties[1]);
      }
    }

    paramProperties.put("dimension", dimension);
    paramProperties.put("compression", compression);

    return paramProperties;
  }

}
