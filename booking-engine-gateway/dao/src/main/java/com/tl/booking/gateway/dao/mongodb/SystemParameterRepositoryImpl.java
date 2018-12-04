package com.tl.booking.gateway.dao.mongodb;

import com.mongodb.WriteResult;
import com.tl.booking.gateway.dao.api.SystemParameterRepositoryCustom;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.dao.SystemParameter;
import java.util.Date;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SystemParameterRepositoryImpl implements SystemParameterRepositoryCustom {

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public WriteResult updateSystemParameterIsDeleteById(String id, Integer isDeleted) {
    Update update = new Update();
    update.set(BaseMongoFields.IS_DELETED, isDeleted)
        .set(BaseMongoFields.UPDATED_BY, MDC.get("username").toString())
        .set(BaseMongoFields.UPDATED_DATE, new Date());

    Query query = new Query().addCriteria(Criteria.where(BaseMongoFields.ID).is(id));

    return this.mongoTemplate.updateFirst(query, update, SystemParameter.class);
  }
}
