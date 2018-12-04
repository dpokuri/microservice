package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.ChannelIdColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.ChannelId;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ChannelIdService {

  Single<ChannelId> createChannelId(MandatoryRequest mandatoryRequest, ChannelId channelId);

  Single<List<ChannelId>> findChannelIds(MandatoryRequest mandatoryRequest);

  Single<ChannelId> updateChannelId(MandatoryRequest mandatoryRequest, ChannelId storeId,
      String id);

  Single<Boolean> deleteChannelId(MandatoryRequest mandatoryRequest, String id);

  Single<ChannelId> findChannelIdById(MandatoryRequest mandatoryRequest, String id);

  Single<Page<ChannelId>> findChannelIdFilterPaginated(MandatoryRequest mandatoryRequest,
      String value, Integer page, Integer size, ChannelIdColumn columnSort,
      SortDirection sortDirection);

}
