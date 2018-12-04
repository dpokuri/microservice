package com.tl.booking.image.dao.api;

import com.mongodb.WriteResult;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;

public interface SystemParameterRepositoryCustom {

  WriteResult updateSystemParameterIsDeleteById(MandatoryRequest mandatoryRequest, String id,
      Integer isDeleted);
}
