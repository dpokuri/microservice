package com.tl.booking.promo.code.service.api;

import com.tl.booking.promo.code.entity.dao.SystemParameter;
import org.springframework.data.domain.Page;

public interface SystemParameterService {

  Page<SystemParameter> findAll(String storeId, Integer page, Integer size);

  SystemParameter findSystemParameterByStoreId(String storeId, String variable);

  void createSystemParameter(SystemParameter systemParameter);

  void updateSystemParameter(SystemParameter systemParameter);

  void deleteByStoreIdAndVariable(String storeId, String variable);
}
