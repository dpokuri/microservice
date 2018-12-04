package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeCost extends CommonModel implements Serializable {

  private List<Cost> partnerCost;
  private List<Cost> companyCost;

  public List<Cost> getPartnerCost() {
    return partnerCost;
  }

  public void setPartnerCost(List<Cost> partnerCost) {
    this.partnerCost = partnerCost;
  }

  public List<Cost> getCompanyCost() {
    return companyCost;
  }

  public void setCompanyCost(List<Cost> companyCost) {
    this.companyCost = companyCost;
  }

  @Override
  public String toString() {
    return "PromoCodeCost{" +
        "partnerCost=" + partnerCost +
        ", companyCost=" + companyCost +
        '}';
  }
}
