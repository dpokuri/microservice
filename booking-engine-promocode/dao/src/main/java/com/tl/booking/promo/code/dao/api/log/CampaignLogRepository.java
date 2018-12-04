package com.tl.booking.promo.code.dao.api.log;

import com.tl.booking.promo.code.entity.dao.log.CampaignLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignLogRepository extends MongoRepository<CampaignLog, String> {

}
