package com.tl.booking.image.dao.api;

import com.tl.booking.image.entity.dao.SystemParameter;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SystemParameterRepository extends MongoRepository<SystemParameter, String>, SystemParameterRepositoryCustom {

  SystemParameter findSystemParameterByStoreIdAndVariable(String storeId, String variable);

  List<SystemParameter> findAllByStoreIdAndVariableInAndIsDeleted(String storeId, List<String> systemParameterVariables, Integer isDeleted);

  Page<SystemParameter> findByStoreId(String storeId, Pageable pageable);

  void deleteSystemParameterByStoreIdAndVariable(String storeId, String variable);
}
