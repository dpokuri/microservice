package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.rest.web.model.OrderDetailDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ApplyPromoCodeResponse extends CommonModel implements Serializable {

  private String code;
  private List<OrderDetailDTO> orderDetails;

  private Double totalPrice;

  private Double totalDiscount;

  private Set<String> usedPromoCodes;

  private String referenceId;

  private List<CostAmount> partnerCostAmount;

  private List<CostAmount> companyCostAmount;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<OrderDetailDTO> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(
      List<OrderDetailDTO> orderDetails) {
    this.orderDetails = orderDetails;
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
    return "ApplyPromoCodeResponse{" +
        "code='" + code + '\'' +
        ", orderDetails=" + orderDetails +
        ", totalPrice=" + totalPrice +
        ", totalDiscount=" + totalDiscount +
        ", usedPromoCodes=" + usedPromoCodes +
        ", referenceId='" + referenceId + '\'' +
        ", partnerCostAmount=" + partnerCostAmount +
        ", companyCostAmount=" + companyCostAmount +
        '}';
  }
}
