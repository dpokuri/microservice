package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.BinNumber;
import java.util.List;
import org.springframework.data.domain.Page;

public interface BinNumberRepositoryCustom {

  Boolean softDeleted(BinNumber binNumber, String id);

  Page<BinNumber> findBinNumberFilterPaginated(String storeId, String binNumber, String bankId,
      String cardTypeId, Integer page, Integer size, String columnSort,
      SortDirection sortDirection);

  List<BinNumber> findBinNumbers(String storeId, String binNumber,
      String bankId, String cardTypeid);
}
