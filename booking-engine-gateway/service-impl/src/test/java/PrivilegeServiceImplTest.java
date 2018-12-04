import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.gateway.dao.api.PrivilegeRepository;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.impl.PrivilegeServiceImpl;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.dao.Privilege;
import com.tl.booking.gateway.entity.dao.PrivilegeBuilder;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PrivilegeServiceImplTest {

  @InjectMocks
  PrivilegeServiceImpl privilegeServiceImpl;

  @Mock
  PrivilegeRepository privilegeRepository;

  private List<Privilege> PRIVILEGE_LIST = Arrays.asList(new PrivilegeBuilder().withPrivilegeId
      ("1").withPrivilegeName("coba").withPrivilegeDescription("cobacoba").build(), new PrivilegeBuilder().withPrivilegeId
      ("2").withPrivilegeName("coba2").withPrivilegeDescription("cobacoba2").build());

  @Test
  public void checkAuthorizedTest() throws Exception {
    Boolean valid = this.privilegeServiceImpl.checkAuthorized(PrivilegeId.HOLIDAY,PrivilegeId.HOLIDAY).blockingGet();
    assertEquals(true, valid);
  }

  @Test
  public void checkAuthorizedTestException() throws Exception {
    try {
      this.privilegeServiceImpl.checkAuthorized(PrivilegeId.HOLIDAY, null)
          .blockingGet();
    } catch (BusinessLogicException ble){
      assertEquals(ResponseCode.NOT_AUTHORIZED.getCode(), ble.getCode());
      assertEquals(ResponseCode.NOT_AUTHORIZED.getMessage(), ble.getMessage());
    }
  }

  @Test
  public void checkAuthorizedTestExceptionNoValue() throws Exception {
    try {
      this.privilegeServiceImpl.checkAuthorized(PrivilegeId.HOLIDAY, "1")
          .blockingGet();
    } catch (BusinessLogicException ble){
      assertEquals(ResponseCode.NOT_AUTHORIZED.getCode(), ble.getCode());
      assertEquals(ResponseCode.NOT_AUTHORIZED.getMessage(), ble.getMessage());
    }
  }

  @Test
  public void getAuthorizedPrivilegesTest() throws Exception {
    when(this.privilegeRepository.findByStoreId(CommonTestVariable.STORE_ID)).thenReturn
        (PRIVILEGE_LIST);

    List<PrivilegeResponse> privileges = this.privilegeServiceImpl.getAuthorizedPrivileges
        (CommonTestVariable.MANDATORY_REQUEST, "1,2").blockingGet();

    assertEquals(PRIVILEGE_LIST.size(), privileges.size());

    verify(this.privilegeRepository).findByStoreId(CommonTestVariable.STORE_ID);
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
    verifyNoMoreInteractions(this.privilegeRepository);
  }
}
