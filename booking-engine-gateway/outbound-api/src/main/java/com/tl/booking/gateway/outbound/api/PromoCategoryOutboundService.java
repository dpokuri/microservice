package com.tl.booking.gateway.outbound.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.PromoCategoryColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;

import io.reactivex.Single;
import java.util.List;
import java.util.Map;

public interface PromoCategoryOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>> findPromoCategoryFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String category,
      Integer page,
      Integer size,
      PromoCategoryColumn columnSort,
      SortDirection sortDirection
  );


  Single<GatewayBaseResponse<PromoCategoryResponse>> createPromoCategory(MandatoryRequest mandatoryRequest,
      PromoCategoryRequest promoCategoryRequest);

  Single<GatewayBaseResponse<PromoCategoryResponse>> updatePromoCategory(MandatoryRequest mandatoryRequest,
      PromoCategoryRequest promoCategoryRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deletePromoCategory(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse<PromoCategoryResponse>> findPromoCategoryById(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<Map<String, Object>>>> getCategories(MandatoryRequest
      mandatoryRequest, String lang);

  Single<GatewayBaseResponse<List<Map<String, Object>>>> getListCategories(MandatoryRequest
      mandatoryRequest, String lang);
}
