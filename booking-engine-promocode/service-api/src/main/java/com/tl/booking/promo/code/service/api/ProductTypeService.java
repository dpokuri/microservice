package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ProductType;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductTypeService {

  Single<ProductType> createProductType(MandatoryRequest mandatoryRequest, ProductType productType);

  Single<ProductType> updateProductType(MandatoryRequest mandatoryRequest, ProductType productType,
      String id);

  Single<Boolean> deleteProductType(MandatoryRequest mandatoryRequest, String id);

  Single<ProductType> findProductTypeById(MandatoryRequest mandatoryRequest, String id);

  Single<Page<ProductType>> findProductTypeFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, Integer page, Integer size, ProductTypeColumn columnSort,
      SortDirection sortDirection);

  Single<List<ProductType>> findProductTypes(MandatoryRequest mandatoryRequest);

}
