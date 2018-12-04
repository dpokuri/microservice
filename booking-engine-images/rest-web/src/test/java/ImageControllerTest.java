import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.tl.booking.image.entity.ImagesRequest;
import com.tl.booking.image.entity.constant.ApiPath;
import com.tl.booking.image.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.image.entity.constant.unit.test.ImageTestVariable;
import com.tl.booking.image.rest.web.controller.ImageController;
import com.tl.booking.image.service.api.ImageService;

import java.io.*;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

public class ImageControllerTest {

  @InjectMocks
  private ImageController imageController;

  @Mock
  private ImageService imageService;

  private MockMvc mockMvc;

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  @Test
  public void createImageTest() throws Exception {


    ImagesRequest imagesRequest = new ImagesRequest();
    imagesRequest.setData(ImageTestVariable.DATA_BASE64);

    when(this.imageService.createImage(any(), any()))
        .thenReturn(ImageTestVariable.IMAGE_CREATE_SERVICE_RESPONSE);

    this.mockMvc
        .perform(
            post(ApiPath.IMAGES).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ImageTestVariable.CREATE_REQUEST_BODY_JSON)
                .requestAttr("mandatory",CommonTestVariable.MANDATORY_REQUEST)
        )
        .andExpect(status().isOk());

    verify(this.imageService).
        createImage(any(), any());
  }

  @Test
  public void createImageFromUrlTest() throws Exception {


    ImagesRequest imagesRequest = new ImagesRequest();
    imagesRequest.setData(ImageTestVariable.DATA_BASE64);

    when(this.imageService.createImageFromURL(any(), any()))
        .thenReturn(ImageTestVariable.IMAGE_CREATE_SERVICE_RESPONSE);

    this.mockMvc
        .perform(
            post(ApiPath.IMAGES_FROM_URL).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ImageTestVariable.CREATE_REQUEST_BODY_JSON)
                .requestAttr("mandatory",CommonTestVariable.MANDATORY_REQUEST)
        )
        .andExpect(status().isOk());

    verify(this.imageService).
        createImageFromURL(any(), any());
  }

  @Test
  public void getImageCloudinaryTest() throws Exception{

    String urlCloudinary = ImageTestVariable.URL_IMAGE_CLOUDINARY;

    when(this.imageService.getImageCloudinary(ImageTestVariable.dateFormatImage, ImageTestVariable.IMAGE_NAME_CLOUDINARY))
            .thenReturn(urlCloudinary);

    when(this.imageService.outputStreamFromURL(urlCloudinary))
            .thenReturn(outputStreamFromURL(urlCloudinary));

    this.mockMvc
            .perform(
                    get(ApiPath.GET_IMAGES_CLOUDINARY+ "/" + ImageTestVariable.getDateFormatImage + ImageTestVariable.IMAGE_NAME_CLOUDINARY  )
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)

            )
            .andExpect(status().isOk());

    verify(this.imageService).
            getImageCloudinary(ImageTestVariable.dateFormatImage, ImageTestVariable.IMAGE_NAME_CLOUDINARY);
    verify(this.imageService).outputStreamFromURL(urlCloudinary);
    verify(imageService).removeFileProperty(urlCloudinary);

  }

  private ResponseEntity<InputStreamResource> outputStreamFromURL(String image) {

    InputStream getImage = null;
    try {
      getImage =  new URL(image).openStream();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(new InputStreamResource(getImage));
  }


  private ResponseEntity<InputStreamResource> outputStream(String image) {
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

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.mockMvc = standaloneSetup(this.imageController).build();
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.imageService);
  }
}
