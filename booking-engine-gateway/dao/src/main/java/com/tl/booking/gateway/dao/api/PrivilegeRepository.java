package com.tl.booking.gateway.dao.api;

import com.tl.booking.gateway.entity.dao.Privilege;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivilegeRepository extends MongoRepository<Privilege, String> {
  Privilege findPrivilegeByStoreIdAndId(String storeId, String id);
  List<Privilege> findByStoreId(String storeId);
}
