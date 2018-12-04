package com.tl.booking.image.dao.api;

import com.tl.booking.image.entity.dao.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String>, ImageRepositoryCustom {
  Image findImageByStoreIdAndNameAndIsDeleted(String storeId, String name, Integer isDeleted);
  Image findImageByNameAndIsDeleted(String name, Integer isDeleted);
}
