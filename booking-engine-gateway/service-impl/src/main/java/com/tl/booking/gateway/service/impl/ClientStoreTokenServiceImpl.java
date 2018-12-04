package com.tl.booking.gateway.service.impl;

import com.tl.booking.gateway.dao.api.ClientStoreTokenRepository;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.api.ClientStoreTokenService;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.dao.ClientStoreToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientStoreTokenServiceImpl implements
    ClientStoreTokenService {

  @Autowired
  private ClientStoreTokenRepository clientStoreTokenRepository;

  @Override
  public ClientStoreToken findClientTokenByToken(String token) {
    ClientStoreToken clientStoreToken = this.clientStoreTokenRepository
        .findClientStoreTokenByClientToken(token);

    if(!isExistClientStoreToken(clientStoreToken)){
      throw new BusinessLogicException(ResponseCode.TOKEN_NOT_MATCH.getCode(), ResponseCode
          .TOKEN_NOT_MATCH.getMessage());
    }

    return clientStoreToken;
  }

  private Boolean isExistClientStoreToken(ClientStoreToken clientStoreToken){
    return clientStoreToken != null;
  }
}
