package com.tl.booking.gateway.dao.api;

import com.mongodb.WriteResult;

public interface SystemParameterRepositoryCustom {
  WriteResult updateSystemParameterIsDeleteById(String id, Integer isDeleted);
}
