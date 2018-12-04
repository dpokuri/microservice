package com.tl.booking.image.dao.mongodb;

import com.tl.booking.image.dao.api.ImageRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImageRepositoryImpl implements ImageRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

}
