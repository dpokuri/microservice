package com.tl.booking.promo.code.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.enums.BankColumn;
import com.tl.booking.promo.code.entity.constant.enums.SortDirection;
import com.tl.booking.promo.code.entity.dao.Bank;
import io.reactivex.Single;
import java.util.List;
import org.springframework.data.domain.Page;

public interface BankService {

  Single<Bank> createBank(MandatoryRequest mandatoryRequest, Bank bank);

  Single<List<Bank>> findBanks(MandatoryRequest mandatoryRequest);

  Single<Bank> updateBank(MandatoryRequest mandatoryRequest, Bank bank,
      String id);

  Single<Boolean> deleteBank(MandatoryRequest mandatoryRequest, String id);

  Single<Bank> findBankById(MandatoryRequest mandatoryRequest, String id);

  Single<Page<Bank>> findBankFilterPaginated(MandatoryRequest mandatoryRequest,
      String name, Integer page, Integer size, BankColumn columnSort,
      SortDirection sortDirection);
}
