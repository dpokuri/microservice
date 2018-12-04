package com.tl.booking.gateway.dao.api;

import com.tl.booking.gateway.entity.dao.SystemParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemParameterRepository extends MongoRepository<SystemParameter, String>,
    SystemParameterRepositoryCustom {

  SystemParameter findSystemParameterByIdAndIsDeleted(String id, Integer isDeleted);

  SystemParameter findSystemParameterByStoreIdAndVariableAndIsDeleted(String storeId,
      String variable, Integer isDeleted);

  Page<SystemParameter> findSystemParametersByStoreIdAndIsDeleted(String storeId, Integer isDeleted,
      Pageable pageable);
}
