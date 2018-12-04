package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.Payment;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String>,
    PaymentRepositoryCustom {

  List<Payment> findByIsDeletedAndStoreId(Integer isDeleted, String storeId);

  Payment save(Payment payment);

  Payment findPaymentByStoreIdAndNameAndIsDeleted(String storeId, String name, Integer isDeleted);

  Payment findPaymentByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);
}
