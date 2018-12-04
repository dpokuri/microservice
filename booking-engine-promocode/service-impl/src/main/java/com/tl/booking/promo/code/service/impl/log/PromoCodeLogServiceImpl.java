package com.tl.booking.promo.code.service.impl.log;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.log.PromoCodeLogRepository;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.PromoCode;
import com.tl.booking.promo.code.entity.dao.log.PromoCodeLog;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.log.PromoCodeLogService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PromoCodeLogServiceImpl implements PromoCodeLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCodeLogServiceImpl.class);
  @Autowired
  PromoCodeLogRepository promoCodeLogRepository;

  @Override
  public Single<PromoCode> createPromoCodeLog(MandatoryRequest mandatoryRequest,
      PromoCode promoCode) {
    return Single.<PromoCode>create(singleEmitter -> {

      LOGGER.info("createPromoCodeLog Request mandatoryRequest, promoCodeLog {} ", mandatoryRequest,
          promoCode);

      PromoCodeLog promoCodeLog = this.toPromoCodeLog(mandatoryRequest, promoCode);

      PromoCodeLog createdPromoCode = this.promoCodeLogRepository.save(promoCodeLog);

      if (!isExistPromoCodeLog(createdPromoCode)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      LOGGER.info("createPromoCodeLog Response promoCodeLog {} ", createdPromoCode);

      singleEmitter.onSuccess(promoCode);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistPromoCodeLog(PromoCodeLog createdPromoCode) {
    return createdPromoCode != null;
  }

  private PromoCodeLog toPromoCodeLog(MandatoryRequest mandatoryRequest, PromoCode promoCode) {
    PromoCodeLog promoCodeLog = new PromoCodeLog();
    promoCodeLog.setValue(promoCode);

    promoCodeLog.setChannelId(mandatoryRequest.getChannelId());
    promoCodeLog.setStoreId(mandatoryRequest.getStoreId());
    promoCodeLog.setIsDeleted(0);
    promoCodeLog.setCreatedBy(mandatoryRequest.getUsername());
    promoCodeLog.setUpdatedBy(mandatoryRequest.getUsername());

    return promoCodeLog;
  }


}
