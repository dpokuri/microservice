package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeRule extends CommonModel implements Serializable {

  private String param;
  private PromoCodeRuleValue promoCodeRuleValue;
  private Boolean usedForCalculate;
  private String productType;

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public PromoCodeRuleValue getPromoCodeRuleValue() {
    return promoCodeRuleValue;
  }

  public void setPromoCodeRuleValue(PromoCodeRuleValue promoCodeRuleValue) {
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
    return "PromoCodeRule{" +
        "param='" + param + '\'' +
        ", promoCodeRuleValue=" + promoCodeRuleValue +
        ", usedForCalculate=" + usedForCalculate +
        ", productType='" + productType + '\'' +
        '}';
  }
}
