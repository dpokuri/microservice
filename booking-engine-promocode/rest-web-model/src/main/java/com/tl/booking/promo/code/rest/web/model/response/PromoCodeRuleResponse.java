package com.tl.booking.promo.code.rest.web.model.response;


import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;

public class PromoCodeRuleResponse extends CommonModel implements Serializable {

  private String param;
  private PromoCodeRuleValueResponse promoCodeRuleValue;
  private Boolean usedForCalculate;
  private String productType;

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public PromoCodeRuleValueResponse getPromoCodeRuleValue() {
    return promoCodeRuleValue;
  }

  public void setPromoCodeRuleValue(
      PromoCodeRuleValueResponse promoCodeRuleValue) {
    this.promoCodeRuleValue = promoCodeRuleValue;
  }

  public Boolean getUsedForCalculate() {
    return usedForCalculate;
  }

  public void setUsedForCalculate(Boolean usedForCalculate) {
    this.usedForCalculate = usedForCalculate;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }


  @Override
  public String toString() {
    return "PromoCodeRuleResponse{" +
        "param='" + param + '\'' +
        ", promoCodeRuleValue=" + promoCodeRuleValue +
        ", usedForCalculate=" + usedForCalculate +
        ", productType='" + productType + '\'' +
        '}' + super.toString();
  }
}
