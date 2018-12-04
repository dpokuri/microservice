package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.CardTypeColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.CardType;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CardTypeService {

  Single<CardType> createCardType(MandatoryRequest mandatoryRequest, CardType cardType);

  Single<List<CardType>> findCardTypes(MandatoryRequest mandatoryRequest, String bankId);

  Single<CardType> findCardTypeById(MandatoryRequest mandatoryRequest, String id);

  Single<CardType> updateCardType(MandatoryRequest mandatoryRequest, CardType cardType,
      String id);

  Single<Boolean> deleteCardType(MandatoryRequest mandatoryRequest, String id);

  Single<Page<CardType>> findCardTypeFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, Integer page, Integer size, CardTypeColumn columnSort,
      SortDirection sortDirection);
}
