package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.BinNumberColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface BinNumberService {

  Single<BinNumber> createBinNumber(MandatoryRequest mandatoryRequest, BinNumber binNumber);

  Single<BinNumber> updateBinNumber(MandatoryRequest mandatoryRequest, BinNumber binNumber,
      String id);

  Single<Boolean> deleteBinNumber(MandatoryRequest mandatoryRequest, String id);

  Single<BinNumber> findBinNumberById(MandatoryRequest mandatoryRequest, String id);

  Single<Page<BinNumber>> findBinNumberFilterPaginated(MandatoryRequest mandatoryRequest,
      String binNumber, String bankId, String cardTypeId, Integer page, Integer size,
      BinNumberColumn columnSort,
      SortDirection sortDirection);

  Single<List<BinNumber>> findBinNumbers(MandatoryRequest mandatoryRequest, String binNumber,
      String bankId, String cardTypeid);
}
