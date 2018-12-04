package com.tl.booking.promo.code.service.impl.log;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.dao.api.log.PromoCodeAdjustmentLogRepository;
import com.tl.booking.promo.code.entity.constant.enums.ResponseCode;
import com.tl.booking.promo.code.entity.dao.PromoCodeAdjustment;
import com.tl.booking.promo.code.entity.dao.log.PromoCodeAdjustmentLog;
import com.tl.booking.promo.code.libraries.exception.BusinessLogicException;
import com.tl.booking.promo.code.service.api.log.PromoCodeAdjustmentLogService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PromoCodeAdjustmentLogServiceImpl implements PromoCodeAdjustmentLogService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(PromoCodeAdjustmentLogServiceImpl.class);
  @Autowired
  PromoCodeAdjustmentLogRepository promoCodeAdjustmentLogRepository;

  @Override
  public Single<PromoCodeAdjustment> createPromoCodeAdjustmentLog(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCodeAdjustment) {
    return Single.<PromoCodeAdjustment>create(singleEmitter -> {

      LOGGER
          .info("createPromoCodeAdjustmentLog Request mandatoryRequest, promoCodeAdjustmentLog {} ",
              mandatoryRequest, promoCodeAdjustment);

      PromoCodeAdjustmentLog promoCodeLog = this
          .toPromoCodeAdjustmentLog(mandatoryRequest, promoCodeAdjustment);

      PromoCodeAdjustmentLog createdPromoCode = this.promoCodeAdjustmentLogRepository
          .save(promoCodeLog);

      if (!isExistPromoCodeAdjustmentLog(createdPromoCode)) {
        throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
            ResponseCode.SYSTEM_ERROR.getMessage());
      }

      LOGGER.info("createPromoCodeAdjustmentLog Response promoCodeAdjustmentLog {} ",
          createdPromoCode);

      singleEmitter.onSuccess(promoCodeAdjustment);

    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistPromoCodeAdjustmentLog(PromoCodeAdjustmentLog createdPromoCode) {
    return createdPromoCode != null;
  }

  private PromoCodeAdjustmentLog toPromoCodeAdjustmentLog(MandatoryRequest mandatoryRequest,
      PromoCodeAdjustment promoCode) {
    PromoCodeAdjustmentLog promoCodeLog = new PromoCodeAdjustmentLog();
    promoCodeLog.setValue(promoCode);

    promoCodeLog.setChannelId(mandatoryRequest.getChannelId());
    promoCodeLog.setStoreId(mandatoryRequest.getStoreId());
    promoCodeLog.setIsDeleted(0);
    promoCodeLog.setCreatedBy(mandatoryRequest.getUsername());
    promoCodeLog.setUpdatedBy(mandatoryRequest.getUsername());

    return promoCodeLog;
  }


}
