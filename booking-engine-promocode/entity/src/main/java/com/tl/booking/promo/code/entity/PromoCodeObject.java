package com.tl.booking.promo.code.entity;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.Variable;
import java.io.Serializable;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCodeObject extends CommonModel implements Serializable {

  private PromoCode promoCode;
  private PromoCodeAdjustment promoCodeAdjustment;
  private Campaign campaign;
  private Map<String, Variable> variableMap;

  public PromoCode getPromoCode() {
    return promoCode;
  }

  public void setPromoCode(PromoCode promoCode) {
    this.promoCode = promoCode;
  }

  public PromoCodeAdjustment getPromoCodeAdjustment() {
    return promoCodeAdjustment;
  }

  public void setPromoCodeAdjustment(
      PromoCodeAdjustment promoCodeAdjustment) {
    this.promoCodeAdjustment = promoCodeAdjustment;
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }

  public Map<String, Variable> getVariableMap() {
    return variableMap;
  }

  public void setVariableMap(
      Map<String, Variable> variableMap) {
    this.variableMap = variableMap;
  }

  @Override
  public String toString() {
    return "PromoCodeObject{" +
        "promoCode=" + promoCode +
        ", promoCodeAdjustment=" + promoCodeAdjustment +
        ", campaign=" + campaign +
        ", variableMap=" + variableMap +
        '}';
  }
}
