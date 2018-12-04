package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.CostAmount;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeUsageLogFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import java.util.Date;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.PROMO_CODE_USAGE_LOG)
public class PromoCodeUsageLog extends BaseMongo {

  @Field(value = PromoCodeUsageLogFields.CODE)
  private String code;

  @Field(value = PromoCodeUsageLogFields.DISCOUNT_AMOUNT)
  private Double discountAmount;

  @Field(value = PromoCodeUsageLogFields.DATE)
  private Date date;

  @Field(value = PromoCodeUsageLogFields.PARTNER_COST_AMOUNT)
  private List<CostAmount> partnerCostAmount;

  @Field(value = PromoCodeUsageLogFields.COMPANY_COST_AMOUNT)
  private List<CostAmount> companyCostAmount;

  @Field(value = PromoCodeUsageLogFields.USED_QTY)
  private Integer usedQty;

  @Field(value = PromoCodeUsageLogFields.TOTAL_PRICE)
  private Double totalPrice;

  @Field(value = PromoCodeUsageLogFields.REFERENCE_ID)
  private String referenceId;

  @Field(value = PromoCodeUsageLogFields.PRODUCT_TYPES)
  private String productTypes;

  @Field(value = PromoCodeUsageLogFields.ORDER_DETAIL)
  private List<OrderDetail> orderDetails;

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

  public Double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    this.referenceId = referenceId;
  }

  public String getProductTypes() {
    return productTypes;
  }

  public void setProductTypes(String productTypes) {
    this.productTypes = productTypes;
  }

  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }

  @Override
  public String toString() {
    return "PromoCodeUsageLog{" +
        "code='" + code + '\'' +
        ", discountAmount=" + discountAmount +
        ", date=" + date +
        ", partnerCostAmount=" + partnerCostAmount +
        ", companyCostAmount=" + companyCostAmount +
        ", usedQty=" + usedQty +
        ", totalPrice=" + totalPrice +
        ", referenceId='" + referenceId + '\'' +
        ", productTypes='" + productTypes + '\'' +
        ", orderDetails=" + orderDetails +
        '}';
  }
}
