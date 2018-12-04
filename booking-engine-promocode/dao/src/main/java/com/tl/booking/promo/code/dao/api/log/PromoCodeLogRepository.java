package com.tl.booking.promo.code.dao.api.log;

import com.tl.booking.promo.code.entity.dao.log.PromoCodeLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromoCodeLogRepository extends MongoRepository<PromoCodeLog, String> {

}
