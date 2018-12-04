package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.BinNumber;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BinNumberRepository extends MongoRepository<BinNumber, String>,
    BinNumberRepositoryCustom {

  BinNumber save(BinNumber binNumber);

  BinNumber findBinNumberByStoreIdAndBinNumberAndIsDeleted(String storeId,
      String binNumber, Integer isDeleted);

  BinNumber findBinNumberByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);
}
