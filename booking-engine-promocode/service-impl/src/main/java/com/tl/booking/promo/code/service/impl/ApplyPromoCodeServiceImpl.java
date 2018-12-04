package com.tl.booking.promo.code.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PaymentDTO;
import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import com.tl.booking.promo.code.service.api.ApplyPromoCodeService;
import com.tl.booking.promo.code.service.api.CalculatePromoCodeService;
import com.tl.booking.promo.code.service.api.DecrementPromoCodeUsageService;
import com.tl.booking.promo.code.service.api.IncrementPromoCodeUsageService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ApplyPromoCodeServiceImpl implements ApplyPromoCodeService {

  @Autowired
  private IncrementPromoCodeUsageService incrementPromoCodeUsageService;

  @Autowired
  private DecrementPromoCodeUsageService decrementPromoCodeUsageService;

  @Autowired
  private CalculatePromoCodeService calculatePromoCodeService;

  @Autowired
  private Scheduler schedulerPromoCode;

  @Override
  public Single<CalculationResult> applyPromoCode(MandatoryRequest mandatoryRequest,
      String code, List<OrderDetail> orderDetails, Set<String> usedPromoCodes, Double totalPrice,
      PaymentDTO paymentDTO, String referenceId, IgnoreValidation ignoreValidation) {

    return this.calculatePromoCodeService.calculatePromoCode(mandatoryRequest, code,
        orderDetails, usedPromoCodes, totalPrice, paymentDTO, referenceId, ignoreValidation)
        .flatMap(calculationResult -> this
            .incrementPromoCodeUsageService
            .incrementPromoCodeUsage(mandatoryRequest,
                calculationResult, paymentDTO.getCardNumber(), referenceId).map(validIncrement ->
                calculationResult)
        ).subscribeOn(schedulerPromoCode);
  }

  @Override
  public Single<Boolean> unApplyPromoCode(MandatoryRequest mandatoryRequest, String promoCode,
      String cardNumber, String referenceId) {

    return this.decrementPromoCodeUsageService
        .decrementPromoCodeUsage(mandatoryRequest, promoCode, cardNumber, referenceId)
        .subscribeOn(schedulerPromoCode);
  }

}
