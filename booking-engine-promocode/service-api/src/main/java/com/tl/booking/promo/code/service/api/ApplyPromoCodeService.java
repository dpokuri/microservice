package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.CalculationResult;
import com.tl.booking.promo.code.entity.OrderDetail;
import com.tl.booking.promo.code.entity.PaymentDTO;
import com.tl.booking.promo.code.entity.constant.enums.IgnoreValidation;
import io.reactivex.Single;
import java.util.List;
import java.util.Set;

public interface ApplyPromoCodeService {

  Single<CalculationResult> applyPromoCode(MandatoryRequest mandatoryRequest, String code,
      List<OrderDetail> orderDetails, Set<String> oldPromoCodes, Double totalPrice, PaymentDTO
      paymentDTO, String referenceId, IgnoreValidation ignoreValidation);

  Single<Boolean> unApplyPromoCode(MandatoryRequest mandatoryRequest, String promoCode, String
      cardNumber, String referenceId);
}
