package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import io.reactivex.Single;
import java.util.List;

public interface VariableSourceService {

  Single<Boolean> updateVariableSources(MandatoryRequest mandatoryRequest, List<VariableSource>
      variableSources);

  Single<List<VariableSource>> findVariableSourceBySourceTypeKey(MandatoryRequest mandatoryRequest,
      String
          sourceType, String key);
}
