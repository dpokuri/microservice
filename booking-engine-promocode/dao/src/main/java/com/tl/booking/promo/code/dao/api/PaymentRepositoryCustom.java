package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Payment;
import org.springframework.data.domain.Page;

public interface PaymentRepositoryCustom {

  Boolean softDeleted(Payment payment, String id);

  Page<Payment> findPaymentFilterPaginated(String storeId, String name, String paymentId,
      Integer page, Integer size, String columnSort,
      SortDirection sortDirection);

}
