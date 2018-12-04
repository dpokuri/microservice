package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.Bank;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankRepository extends MongoRepository<Bank, String>,
    BankRepositoryCustom {

  List<Bank> findByIsDeletedAndStoreId(Integer isDeleted, String storeId);

  Bank findBankByStoreIdAndNameAndIsDeleted(String storeId, String name, Integer isDeleted);

  Bank save(Bank bank);

  Bank findBankByStoreIdAndIdAndIsDeleted(String storeId, String id, Integer isDeleted);
}
