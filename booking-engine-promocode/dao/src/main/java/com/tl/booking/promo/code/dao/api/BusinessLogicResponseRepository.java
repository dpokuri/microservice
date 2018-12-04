package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.BusinessLogicResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessLogicResponseRepository extends
    MongoRepository<BusinessLogicResponse, String>,
    BusinessLogicResponseRepositoryCustom {

  List<BusinessLogicResponse> findByStoreIdAndIsDeleted(String storeId, Integer isDeleted);

  BusinessLogicResponse findBusinessLogicResponseByResponseCodeAndStoreIdAndIsDeleted(
      String responseCode, String storeId, Integer isDeleted);

  BusinessLogicResponse save(BusinessLogicResponse businessLogicResponse);

  BusinessLogicResponse findByResponseCodeAndStoreIdAndIsDeleted(String responseCode, String
      storeId, Integer
      isDeleted);

  BusinessLogicResponse findBusinessLogicResponseByStoreIdAndIdAndIsDeleted(String storeId,
      String id, Integer isDeleted);

  Page<BusinessLogicResponse> findAllByStoreIdAndIsDeleted(String storeId, Integer isDeleted,
      Pageable pageable);

}
