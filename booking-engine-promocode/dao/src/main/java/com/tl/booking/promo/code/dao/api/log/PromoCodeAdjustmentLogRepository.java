package com.tl.booking.promo.code.dao.api.log;

import com.tl.booking.promo.code.entity.dao.log.PromoCodeAdjustmentLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoCodeAdjustmentLogRepository extends
    MongoRepository<PromoCodeAdjustmentLog, String> {

}
