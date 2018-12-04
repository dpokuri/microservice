package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;

import io.reactivex.Single;
import java.util.List;

public interface ProductTypeOutboundService {


  Single<GatewayBaseResponse<ProductTypeResponse>> findProductTypeById(
      MandatoryRequest mandatoryRequest,
      String id);


  Single<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> findProductTypeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      Integer page,
      Integer size,
      ProductTypeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<ProductTypeResponse>> createProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest promoCodeRequest);

  Single<GatewayBaseResponse<ProductTypeResponse>> updateProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest promoCodeRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deleteProductType(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<ProductTypeResponse>>> findProductTypes(MandatoryRequest mandatoryRequest);

}
