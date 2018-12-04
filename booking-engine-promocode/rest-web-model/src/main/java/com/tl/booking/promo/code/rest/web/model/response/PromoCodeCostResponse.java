package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;

public class PromoCodeCostResponse extends CommonModel implements Serializable {

  private List<CostResponse> partnerCost;
  private List<CostResponse> companyCost;

  public List<CostResponse> getPartnerCost() {
    return partnerCost;
  }

  public void setPartnerCost(
      List<CostResponse> partnerCost) {
    this.partnerCost = partnerCost;
  }

  public List<CostResponse> getCompanyCost() {
    return companyCost;
  }

  public void setCompanyCost(
      List<CostResponse> companyCost) {
    this.companyCost = companyCost;
  }

  @Override
  public String toString() {
    return "PromoCodeCostResponse{" +
        "partnerCost=" + partnerCost +
        ", companyCost=" + companyCost +
        '}' + super.toString();
  }
}
