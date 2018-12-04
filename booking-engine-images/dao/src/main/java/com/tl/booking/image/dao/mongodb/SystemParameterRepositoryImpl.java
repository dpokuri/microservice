package com.tl.booking.image.dao.mongodb;

import com.mongodb.WriteResult;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.image.dao.api.SystemParameterRepositoryCustom;
import com.tl.booking.image.entity.constant.fields.BaseMongoFields;
import com.tl.booking.image.entity.dao.SystemParameter;
import java.util.Date;
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
  public WriteResult updateSystemParameterIsDeleteById(MandatoryRequest mandatoryRequest, String id,
      Integer isDeleted) {
    Update update = new Update();
    update.set(BaseMongoFields.IS_DELETED, isDeleted)
        .set(BaseMongoFields.UPDATED_BY, mandatoryRequest.getUsername())
        .set(BaseMongoFields.UPDATED_DATE, new Date());

    Query query = new Query().addCriteria(Criteria.where(BaseMongoFields.ID).is(id));

    return this.mongoTemplate.updateFirst(query, update, SystemParameter.class);
  }
}
