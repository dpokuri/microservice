package com.tl.booking.promo.code.entity.dao.log;

import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@GeneratePojoBuilder
@Document(collection = CollectionName.CAMPAIGN_LOG)
public class CampaignLog extends BaseMongo {

  private Campaign value;

  public Campaign getValue() {
    return value;
  }

  public void setValue(Campaign value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "CampaignLog{" +
        "value=" + value +
        '}' + super.toString();
  }
}
