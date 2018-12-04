package com.tl.booking.promo.code.service.api.log;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.dao.Campaign;
import io.reactivex.Single;

public interface CampaignLogService {

  Single<Campaign> createCampaignLog(MandatoryRequest mandatoryRequest, Campaign campaign);

}
