package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ChannelId;
import org.springframework.data.domain.Page;

public interface ChannelIdRepositoryCustom {

  Page<ChannelId> findChannelIdFilterPaginated(String storeId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection);


  Boolean softDeleted(ChannelId channelId, String id);

}
