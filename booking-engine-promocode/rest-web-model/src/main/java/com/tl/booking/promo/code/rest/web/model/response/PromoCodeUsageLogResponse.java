package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.promo.code.entity.CostAmount;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PromoCodeUsageLogResponse extends CommonModel implements Serializable {

  private String id;

  private String code;

  private Double discountAmount;

  private Date date;

  private List<CostAmount> partnerCostAmount;

  private List<CostAmount> companyCostAmount;

  private Integer usedQty;

  public String getId() {

    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Double getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(Double discountAmount) {
    this.discountAmount = discountAmount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
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

  public Integer getUsedQty() {
    return usedQty;
  }

  public void setUsedQty(Integer usedQty) {
    this.usedQty = usedQty;
  }

  @Override
  public String toString() {
    return "PromoCodeUsageLogResponse{" +
        "id='" + id + '\'' +
        ", code='" + code + '\'' +
        ", discountAmount=" + discountAmount +
        ", date=" + date +
        ", partnerCostAmount=" + partnerCostAmount +
        ", companyCostAmount=" + companyCostAmount +
        ", usedQty=" + usedQty +
        '}';
  }
}
