package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.promo.code.entity.constant.enums.UsagePeriod;
import com.tl.booking.promo.code.entity.constant.enums.ValidatedBy;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UsageRuleRequest implements Serializable {

  @NotNull
  @Max(value = 99999999)
  @Min(value = 0)
  private Integer usageCount;

  @Valid
  @NotNull
  private UsagePeriod usagePeriod;

  @Valid
  @NotNull
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
    return "UsageRuleRequest{" +
        "usageCount=" + usageCount +
        ", usagePeriod=" + usagePeriod +
        ", validatedBy=" + validatedBy +
        '}';
  }
}
