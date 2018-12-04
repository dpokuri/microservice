import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.UsageRuleBuilder;
import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import com.tl.booking.promo.code.libraries.utility.CommonHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;

public class CommonHelperTest {

  private MockMvc mockMvc;

  @InjectMocks
  private CommonHelper commonHelper;

  @Test
  public void setPageableTest() throws Exception {
    List<UsageRule> usageRules = new ArrayList();
    usageRules.add(new UsageRuleBuilder().withUsageCount(10).withUsagePeriod(UsagePeriod.DAILY)
        .withValidatedBy(ValidatedBy.USERNAME).build());
    Map<String, UsageRule> usageRuleMap = CommonHelper.generateUsageRuleMap(usageRules);

    StringBuilder usageRuleCode = new StringBuilder();
    usageRuleCode.append(UsagePeriod.DAILY.getCode());
    usageRuleCode.append(ValidatedBy.USERNAME.getName());

    assertEquals(new UsageRuleBuilder().withUsageCount(10).withUsagePeriod(UsagePeriod.DAILY)
        .withValidatedBy(ValidatedBy.USERNAME).build(), usageRuleMap.get(usageRuleCode.toString()));
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @After
  public void tearDown() throws Exception {
  }
}
