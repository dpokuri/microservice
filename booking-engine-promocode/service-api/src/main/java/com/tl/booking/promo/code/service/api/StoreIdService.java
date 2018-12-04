package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.constant.enums.StoreIdColumn;
import com.tl.booking.promo.code.entity.dao.StoreId;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface StoreIdService {

  Single<StoreId> createStoreId(MandatoryRequest mandatoryRequest, StoreId storeId);

  Single<List<StoreId>> findStoreIds(MandatoryRequest mandatoryRequest);

  Single<StoreId> updateStoreId(MandatoryRequest mandatoryRequest, StoreId storeId, String id);

  Single<Boolean> deleteStoreId(MandatoryRequest mandatoryRequest, String id);

  Single<StoreId> findStoreIdById(MandatoryRequest mandatoryRequest, String id);

  Single<Page<StoreId>> findStoreIdFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, Integer page, Integer size, StoreIdColumn columnSort,
      SortDirection sortDirection);

}
