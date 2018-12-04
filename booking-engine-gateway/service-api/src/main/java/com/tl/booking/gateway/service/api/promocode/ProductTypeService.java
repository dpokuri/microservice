package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;

import io.reactivex.Single;
import java.util.List;

public interface ProductTypeService {

  Single<GatewayBaseResponse<ProductTypeResponse>> findProductTypeById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> findProductTypeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      Integer page,
      Integer size,
      ProductTypeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<ProductTypeResponse>> createProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest variableRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<ProductTypeResponse>> updateProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest variableRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteProductType(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<List<ProductTypeResponse>>> findProductTypes(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  );
}
