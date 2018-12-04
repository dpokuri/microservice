import static org.junit.Assert.assertEquals;

import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;

import java.util.Map;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MandatoryRequestHelperTest {

  @InjectMocks
  MandatoryRequestHelper mandatoryRequestHelper;

  @Test
  public void buildMandatoryRequestHeaderTest() throws Exception {
    Map<String, String> mapMandatoryRequest = this.mandatoryRequestHelper
        .buildMandatoryRequestHeader(CommonTestVariable.MANDATORY_REQUEST);
    assertEquals(CommonTestVariable.STORE_ID, mapMandatoryRequest.get("storeId"));
    assertEquals(CommonTestVariable.CHANNEL_ID, mapMandatoryRequest.get("channelId"));
    assertEquals(CommonTestVariable.REQUEST_ID, mapMandatoryRequest.get("requestId"));
    assertEquals(CommonTestVariable.SERVICE_ID, mapMandatoryRequest.get("serviceId"));
    assertEquals(CommonTestVariable.USERNAME, mapMandatoryRequest.get("username"));

  }

}
