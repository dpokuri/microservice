package com.tl.booking.promo.code.dao.api;

import com.tl.booking.promo.code.entity.dao.ProductType;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductTypeRepository extends MongoRepository<ProductType, String>,
    ProductTypeRepositoryCustom {

  ProductType findProductTypeByStoreIdAndNameAndIsDeleted(String storeId, String name,
      Integer isDeleted);

  ProductType findProductTypeByStoreIdAndIdAndIsDeleted(String storeId, String id,
      Integer isDeleted);

  List<ProductType> findProductTypeByStoreIdAndIsDeleted(String storeId, Integer isDeleted);

}
