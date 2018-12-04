package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.ChannelId;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChannelIdRepository extends MongoRepository<ChannelId, String>,
    ChannelIdRepositoryCustom {

  List<ChannelId> findByStoreIdAndIsDeleted(String storeId, Integer isDeleted);

  ChannelId save(ChannelId channelId);

  ChannelId findByStoreIdAndValueAndIsDeleted(String storeId, String value, Integer
      isDeleted);

  ChannelId findByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);
}
