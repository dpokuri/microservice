package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.PaymentColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Payment;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PaymentService {

  Single<Payment> createPayment(MandatoryRequest mandatoryRequest, Payment payment);

  Single<List<Payment>> findPayments(MandatoryRequest mandatoryRequest);

  Single<Payment> updatePayment(MandatoryRequest mandatoryRequest, Payment payment,
      String id);

  Single<Boolean> deletePayment(MandatoryRequest mandatoryRequest, String id);

  Single<Payment> findPaymentById(MandatoryRequest mandatoryRequest, String id);

  Single<Page<Payment>> findPaymentFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, String paymentId, Integer page, Integer size, PaymentColumn columnSort,
      SortDirection sortDirection);
}
