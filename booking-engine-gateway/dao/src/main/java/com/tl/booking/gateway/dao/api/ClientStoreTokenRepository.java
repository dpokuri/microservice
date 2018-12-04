package com.tl.booking.gateway.dao.api;

import com.tl.booking.gateway.entity.dao.ClientStoreToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientStoreTokenRepository extends MongoRepository<ClientStoreToken, String> {
  ClientStoreToken findClientStoreTokenByClientToken(String clientToken);
}
