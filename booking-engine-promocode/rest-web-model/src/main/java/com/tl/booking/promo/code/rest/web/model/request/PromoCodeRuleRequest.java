package com.tl.booking.promo.code.rest.web.model.request;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class PromoCodeRuleRequest extends CommonModel implements Serializable {

  @NotNull
  @NotBlank
  @NotEmpty
  private String param;

  @Valid
  @NotNull
  private PromoCodeRuleValueRequest promoCodeRuleValue;

  @Valid
  @NotNull
  private Boolean usedForCalculate;

  @Valid
  @NotNull
  private String productType;

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public PromoCodeRuleValueRequest getPromoCodeRuleValue() {
    return promoCodeRuleValue;
  }

  public void setPromoCodeRuleValue(
      PromoCodeRuleValueRequest promoCodeRuleValue) {
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
    return "PromoCodeRuleRequest{" +
        "param='" + param + '\'' +
        ", promoCodeRuleValue=" + promoCodeRuleValue +
        ", usedForCalculate=" + usedForCalculate +
        ", productType='" + productType + '\'' +
        '}';
  }
}
