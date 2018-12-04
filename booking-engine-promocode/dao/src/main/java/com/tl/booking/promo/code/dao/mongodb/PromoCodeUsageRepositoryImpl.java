package com.tl.booking.promo.code.dao.mongodb;

import com.mongodb.WriteResult;
import com.tl.booking.promo.code.dao.api.PromoCodeUsageRepositoryCustom;
import com.tl.booking.promo.code.entity.constant.fields.PromoCodeUsageFields;
import com.tl.booking.promo.code.entity.dao.PromoCodeUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PromoCodeUsageRepositoryImpl implements PromoCodeUsageRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public WriteResult updatePromoCodeUsageById(String id, Integer increment, Integer maxQty) {
    Query query = new Query();

    Criteria criteria = Criteria
        .where(PromoCodeUsageFields.ID).is(id);

    if(increment > 0) {
      criteria.and(PromoCodeUsageFields.USAGE_COUNT).gte(0);
    } else {
      criteria.and(PromoCodeUsageFields.USAGE_COUNT).gt(0);
    }

    if (maxQty != 0) {
      criteria.andOperator(new Criteria().where(PromoCodeUsageFields.USAGE_COUNT).lt(maxQty));
    }

    query.addCriteria(criteria);

    Update update = new Update();
    update.inc(PromoCodeUsageFields.USAGE_COUNT, increment);

    return this.mongoTemplate.updateFirst(query, update, PromoCodeUsage.class);
  }
}
