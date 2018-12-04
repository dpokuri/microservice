package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.StoreId;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreIdRepository extends MongoRepository<StoreId, String>,
    StoreIdRepositoryCustom {

  List<StoreId> findByIsDeleted(Integer isDeleted);

  StoreId save(StoreId storeId);

  StoreId findStoreIdByIdAndIsDeleted(String id, Integer isDeleted);

  StoreId findStoreIdByValueAndIsDeleted(String value, Integer isDeleted);
}
