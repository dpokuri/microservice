import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

import com.tl.booking.gateway.dao.api.ClientStoreTokenRepository;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.impl.ClientStoreTokenServiceImpl;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.dao.ClientStoreToken;
import com.tl.booking.gateway.entity.dao.ClientStoreTokenBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ClientStoreTokenServiceImplTest {

  @InjectMocks
  ClientStoreTokenServiceImpl clientStoreTokenServiceImpl;

  @Mock
  ClientStoreTokenRepository clientStoreTokenRepository;

  ClientStoreToken clientStoreToken = new ClientStoreTokenBuilder()
      .withClientToken("123123")
      .withStoreId("TIKETCOM")
      .build();


  @Test
  public void findClientTokenByTokenTest() throws Exception {
    when(this.clientStoreTokenRepository.findClientStoreTokenByClientToken("123123")).thenReturn
        (clientStoreToken);

    ClientStoreToken clientStoreToken = this.clientStoreTokenServiceImpl.findClientTokenByToken
        ("123123");

    assertEquals("123123", clientStoreToken.getClientToken());
    verify(this.clientStoreTokenRepository).findClientStoreTokenByClientToken("123123");
  }

  @Test
  public void findClientTokenByTokenTestNotFound() throws Exception {
    when(this.clientStoreTokenRepository.findClientStoreTokenByClientToken("123123")).thenReturn
        (null);

    try {
      this.clientStoreTokenServiceImpl.findClientTokenByToken
          ("123123");
    } catch (BusinessLogicException ble) {
      assertEquals(ResponseCode.TOKEN_NOT_MATCH.getCode(), ble.getCode());
      assertEquals(ResponseCode.TOKEN_NOT_MATCH.getMessage(), ble.getMessage());
      verify(this.clientStoreTokenRepository).findClientStoreTokenByClientToken("123123");
    }
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.clientStoreTokenRepository);
  }
}
