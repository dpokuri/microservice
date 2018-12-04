package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.VariableSource;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VariableSourceRepository extends MongoRepository<VariableSource, String> {

  VariableSource findVariableSourceBySourceTypeAndValueIdAndStoreIdAndIsDeleted(String
      sourceType, String valueId, String
      storeId, Integer isDeleted);

  List<VariableSource> findBySourceTypeAndStoreIdAndIsDeleted(String sourceType, String storeId,
      Integer isDeleted);

  List<VariableSource> findBySourceTypeAndValueSearchRegexAndStoreIdAndIsDeleted(String sourceType,
      String valueNameSearch,
      String storeId,
      Integer isDeleted);

  VariableSource save(VariableSource variableSource);
}
