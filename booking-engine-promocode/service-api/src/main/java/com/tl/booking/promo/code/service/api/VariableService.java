package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.VariableColumn;
import com.tl.booking.promo.code.entity.dao.Variable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public interface VariableService {

  Single<Variable> createVariable(MandatoryRequest mandatoryRequest, Variable variable);

  Single<Variable> updateVariable(MandatoryRequest mandatoryRequest, Variable variable, String id);

  Single<Boolean> deleteVariable(MandatoryRequest mandatoryRequest, String id);

  Single<Variable> findVariableById(MandatoryRequest mandatoryRequest, String id);

  Single<Map<String, Variable>> findVariablesMapped(MandatoryRequest mandatoryRequest);

  Single<List<Variable>> findVariables(MandatoryRequest mandatoryRequest);

  Single<Page<Variable>> findVariableFilterPaginated(MandatoryRequest mandatoryRequest,
      String param, String inputType, Integer page, Integer size, VariableColumn columnSort,
      SortDirection sortDirection);
}
