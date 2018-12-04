package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.enums.UsagePeriod;
import com.tl.booking.gateway.entity.constant.enums.ValidatedBy;

import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class UsageRuleResponse extends CommonModel implements Serializable {

  private Integer usageCount;
  private UsagePeriod usagePeriod;
  private ValidatedBy validatedBy;

  public Integer getUsageCount() {
    return usageCount;
  }

  public void setUsageCount(Integer usageCount) {
    this.usageCount = usageCount;
  }

  public UsagePeriod getUsagePeriod() {
    return usagePeriod;
  }

  public void setUsagePeriod(UsagePeriod usagePeriod) {
    this.usagePeriod = usagePeriod;
  }

  public ValidatedBy getValidatedBy() {
    return validatedBy;
  }

  public void setValidatedBy(ValidatedBy validatedBy) {
    this.validatedBy = validatedBy;
  }

  @Override
  public String toString() {
    return "UsageRuleResponse{" +
        "usageCount=" + usageCount +
        ", usagePeriod=" + usagePeriod +
        ", validatedBy=" + validatedBy +
        '}'+ super.toString();
  }
}
