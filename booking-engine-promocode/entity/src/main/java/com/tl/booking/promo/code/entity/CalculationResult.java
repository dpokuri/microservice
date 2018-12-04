package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class CalculationResult extends CommonModel implements Serializable {

  private List<OrderDetail> orderDetails;
  private PromoCodeObject promoCodeObject;
  private Double totalPrice;
  private Double totalDiscount;
  private Set<String> usedPromoCodes;
  private String referenceId;
  private List<CostAmount> partnerCostAmount;
  private List<CostAmount> companyCostAmount;

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }

  public PromoCodeObject getPromoCodeObject() {
    return promoCodeObject;
  }

  public void setPromoCodeObject(PromoCodeObject promoCodeObject) {
    this.promoCodeObject = promoCodeObject;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Double getTotalDiscount() {
    return totalDiscount;
  }

  public void setTotalDiscount(Double totalDiscount) {
    this.totalDiscount = totalDiscount;
  }

  public Set<String> getUsedPromoCodes() {
    return usedPromoCodes;
  }

  public void setUsedPromoCodes(Set<String> usedPromoCodes) {
    this.usedPromoCodes = usedPromoCodes;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public List<CostAmount> getPartnerCostAmount() {
    return partnerCostAmount;
  }

  public void setPartnerCostAmount(
      List<CostAmount> partnerCostAmount) {
    this.partnerCostAmount = partnerCostAmount;
  }

  public List<CostAmount> getCompanyCostAmount() {
    return companyCostAmount;
  }

  public void setCompanyCostAmount(
      List<CostAmount> companyCostAmount) {
    this.companyCostAmount = companyCostAmount;
  }

  @Override
  public String toString() {
    return "CalculationResult{" +
        "orderDetails=" + orderDetails +
        ", promoCodeObject=" + promoCodeObject +
        ", totalPrice=" + totalPrice +
        ", totalDiscount=" + totalDiscount +
        ", usedPromoCodes=" + usedPromoCodes +
        ", referenceId='" + referenceId + '\'' +
        ", partnerCostAmount=" + partnerCostAmount +
        ", companyCostAmount=" + companyCostAmount +
        '}';
  }
}
