package com.tl.booking.gateway.service.api;

import org.springframework.data.domain.Page;

import com.tl.booking.gateway.entity.dao.SystemParameter;

public interface SystemParameterService {

  Page<SystemParameter> findAllSystemParametersByStoreId(String storeId, Integer page,
      Integer size);

  SystemParameter findSystemParameterById(String id);

  SystemParameter findSystemParameterByStoreIdAndVariable(String storeId, String variable);

  SystemParameter createSystemParameter(SystemParameter systemParameter);

  SystemParameter updateSystemParameter(SystemParameter systemParameter, String id);

  Boolean deleteSystemParameterById(String id);
}
