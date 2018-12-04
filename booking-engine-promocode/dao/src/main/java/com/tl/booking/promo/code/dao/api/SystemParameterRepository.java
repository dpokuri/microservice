package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.SystemParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemParameterRepository extends MongoRepository<SystemParameter, String> {

  SystemParameter findSystemParameterByStoreIdAndVariableAndIsDeleted(String storeId,
      String variable, Integer isDeleted);

  Page<SystemParameter> findByStoreId(String storeId, Pageable pageable);

  void deleteSystemParameterByStoreIdAndVariable(String storeId, String variable);
}
