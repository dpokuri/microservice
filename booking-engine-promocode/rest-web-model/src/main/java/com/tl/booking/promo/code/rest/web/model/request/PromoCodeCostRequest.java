package com.tl.booking.promo.code.rest.web.model.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;

public class PromoCodeCostRequest implements Serializable {

  @Valid
  private List<CostRequest> partnerCost;

  @Valid
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
