package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeCostRequest extends CommonModel implements Serializable {

  @Valid
  @NotNull
  private List<CostRequest> partnerCost;

  @Valid
  @NotNull
  private List<CostRequest> companyCost;

  public List<CostRequest> getPartnerCost() {
    return partnerCost;
  }

  public void setPartnerCost(
      List<CostRequest> partnerCost) {
    this.partnerCost = partnerCost;
  }

  public List<CostRequest> getCompanyCost() {
    return companyCost;
  }

  public void setCompanyCost(
      List<CostRequest> companyCost) {
    this.companyCost = companyCost;
  }

  @Override
  public String toString() {
    return "PromoCodeCostRequest{" +
        "partnerCost=" + partnerCost +
        ", companyCost=" + companyCost +
        '}' + super.toString();
  }
}
