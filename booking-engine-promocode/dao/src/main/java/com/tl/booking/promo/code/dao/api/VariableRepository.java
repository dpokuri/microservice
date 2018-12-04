package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.Variable;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VariableRepository extends MongoRepository<Variable, String>,
    VariableRepositoryCustom {

  Variable findVariableByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);

  Variable findVariableByStoreIdAndParamAndIsDeleted(String storeId, String param,
      Integer isDeleted);

  List<Variable> findAllByIsDeleted(Integer isDeleted);

  Variable save(Variable variable);
}
