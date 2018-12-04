package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface BusinessLogicResponseService {

  Single<List<BusinessLogicResponse>> findBusinessLogicResponses(MandatoryRequest mandatoryRequest);

  Single<BusinessLogicResponse> createBusinessLogicResponse(MandatoryRequest mandatoryRequest,
      BusinessLogicResponse businessLogicResponse);

  String findMessageByResponseCodeAndLanguage(String storeId, String
      responseCode, String lang);

  Single<BusinessLogicResponse> updateBusinessLogicResponse(MandatoryRequest mandatoryRequest,
      BusinessLogicResponse businessLogicResponse,
      String id);

  Single<Boolean> deleteBusinessLogicResponse(MandatoryRequest mandatoryRequest, String id);

  Single<BusinessLogicResponse> findBusinessLogicResponseById(MandatoryRequest mandatoryRequest,
      String id);

  Single<Page<BusinessLogicResponse>> findBusinessLogicResponseFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String responseCode, Integer page, Integer size, BusinessLogicResponseColumn columnSort,
      SortDirection sortDirection);
}
