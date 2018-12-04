package com.tl.booking.promo.code.service.impl.log;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.log.CampaignLogRepository;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.Campaign;
import com.tl.booking.promo.code.entity.dao.log.CampaignLog;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.log.CampaignLogService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CampaignLogServiceImpl implements CampaignLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CampaignLogServiceImpl.class);
  @Autowired
  CampaignLogRepository campaignLogRepository;

  @Override
  public Single<Campaign> createCampaignLog(MandatoryRequest mandatoryRequest, Campaign campaign) {
    return Single.<Campaign>create(singleEmitter -> {

      LOGGER.info("createCampaignLog Request mandatoryRequest, campaignLog {} ", mandatoryRequest,
          campaign);

      CampaignLog campaignLog = this.toCampaignLog(mandatoryRequest, campaign);

      CampaignLog createdCampaign = this.campaignLogRepository.save(campaignLog);

      if (!isExistCampaignLog(createdCampaign)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      LOGGER.info("createCampaignLog Response campaignLog {} ", createdCampaign);

      singleEmitter.onSuccess(campaign);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistCampaignLog(CampaignLog createdCampaign) {
    return createdCampaign != null;
  }

  private CampaignLog toCampaignLog(MandatoryRequest mandatoryRequest, Campaign campaign) {
    CampaignLog campaignLog = new CampaignLog();
    campaignLog.setValue(campaign);

    campaignLog.setChannelId(mandatoryRequest.getChannelId());
    campaignLog.setStoreId(mandatoryRequest.getStoreId());
    campaignLog.setIsDeleted(0);
    campaignLog.setCreatedBy(mandatoryRequest.getUsername());
    campaignLog.setUpdatedBy(mandatoryRequest.getUsername());

    return campaignLog;
  }


}
