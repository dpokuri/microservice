package com.tl.booking.promo.code.rest.web.model;


import java.io.Serializable;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class OrderDetailDTO extends CommonModel implements Serializable {

  @NotNull
  private String referenceId;

  @Valid
  private Map<String, String> orderAttribute;

  @NotNull
  private Double amount;

  @NotNull
  private Double discount;

  @NotNull
  private String productType;

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public Map<String, String> getOrderAttribute() {
    return orderAttribute;
  }

  public void setOrderAttribute(Map<String, String> orderAttribute) {
    this.orderAttribute = orderAttribute;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  @Override
  public String toString() {
    return "OrderDetailDTO{" +
        "referenceId='" + referenceId + '\'' +
        ", orderAttribute=" + orderAttribute +
        ", amount=" + amount +
        ", discount=" + discount +
        ", productType='" + productType + '\'' +
        '}';
  }
}
