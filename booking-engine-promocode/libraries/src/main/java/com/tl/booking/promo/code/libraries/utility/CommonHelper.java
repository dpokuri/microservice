package com.tl.booking.promo.code.libraries.utility;

import com.tl.booking.promo.code.entity.UsageRule;
import com.tl.booking.promo.code.entity.constant.PromoCodeUsageType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonHelper {

  private CommonHelper() {
    // constructors class
  }

  public static Map<String, UsageRule> generateUsageRuleMap(List<UsageRule> usageRules){
    Map<String, UsageRule> usageRulesMap = new HashMap<>();
    for(UsageRule usageRule : usageRules){
      StringBuilder usageRuleCode = new StringBuilder();
      usageRuleCode.append(usageRule.getUsagePeriod().getCode());
      usageRuleCode.append(usageRule.getValidatedBy()
          .getName());
      usageRulesMap.put(usageRuleCode.toString(), usageRule);
    }

    usageRulesMap.put(PromoCodeUsageType.GENERAL, new UsageRule());
    return usageRulesMap;
  }
}
