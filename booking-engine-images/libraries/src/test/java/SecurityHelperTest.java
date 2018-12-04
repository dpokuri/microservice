import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.image.entity.constant.enums.EncryptionType;
import com.tl.booking.image.libraries.utility.SecurityHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;

public class SecurityHelperTest {

  private MockMvc mockMvc;

  @InjectMocks
  private SecurityHelper securityHelper;

  @Test
  public void encryptSHA1Test() throws Exception {
    String encryptedPassword = this.securityHelper.encrypt("12345678", EncryptionType.SHA1.getValue());
    assertEquals("7c222fb2927d828af22f592134e8932480637c0d", encryptedPassword);
  }

  @Test
  public void encryptWrongEncodingTestNoSuchAlgorithmException() throws Exception {
    String encryptedPassword = this.securityHelper.encrypt("12345678","SHA-1222");
    assertEquals(null, encryptedPassword);
  }

  @Test
  public void md5EncryptTest() throws Exception {
    String encryptedPassword = this.securityHelper.encrypt("12345678", EncryptionType.MD5.getValue());
    assertEquals("25d55ad283aa400af464c76d713c07ad", encryptedPassword);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }
}
