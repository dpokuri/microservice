package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.CardType;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CardTypeRepositoryCustom {

  Boolean softDeleted(CardType cardType, String id);

  Page<CardType> findCardTypeFilterPaginated(String storeId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection);

  List<CardType> findCardTypes(Integer isDeleted, String storeId, String bankId);

}
