package com.tl.booking.promo.code.entity.dao.log;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@GeneratePojoBuilder
@Document(collection = CollectionName.PROMO_CODE_LOG)
public class PromoCodeLog extends BaseMongo {

  private PromoCode value;

  public PromoCode getValue() {
    return value;
  }

  public void setValue(PromoCode value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PromoCodeLog{" +
        "value=" + value +
        '}' + super.toString();
  }
}
