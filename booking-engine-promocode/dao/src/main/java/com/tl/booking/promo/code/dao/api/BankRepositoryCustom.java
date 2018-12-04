package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Bank;
import org.springframework.data.domain.Page;

public interface BankRepositoryCustom {

  Boolean softDeleted(Bank bank, String id);

  Page<Bank> findBankFilterPaginated(String storeId, String name, Integer page,
      Integer size, String columnSort,
      SortDirection sortDirection);

}
