package com.tl.booking.gateway.service.api;

import com.tl.booking.gateway.entity.dao.ClientStoreToken;

@FunctionalInterface
public interface ClientStoreTokenService {

  ClientStoreToken findClientTokenByToken(String token);
}
