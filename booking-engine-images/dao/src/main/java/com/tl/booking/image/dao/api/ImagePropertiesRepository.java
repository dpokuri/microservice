package com.tl.booking.image.dao.api;

import com.tl.booking.image.entity.dao.ImageProperties;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImagePropertiesRepository extends MongoRepository<ImageProperties, String> {
  ImageProperties findByPropertiesAndIsDeleted(String properties, Integer isDeleted);
}
