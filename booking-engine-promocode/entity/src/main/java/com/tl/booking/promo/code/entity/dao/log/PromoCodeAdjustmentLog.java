package com.tl.booking.promo.code.entity.dao.log;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@GeneratePojoBuilder
@Document(collection = CollectionName.PROMO_CODE_ADJUSTMENT_LOG)
public class  PromoCodeAdjustmentLog extends BaseMongo {

  private PromoCodeAdjustment value;

  public PromoCodeAdjustment getValue() {
    return value;
  }

  public void setValue(PromoCodeAdjustment value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PromoCodeAdjustmentLog{" +
        "value=" + value +
        '}' + super.toString();
  }
}
