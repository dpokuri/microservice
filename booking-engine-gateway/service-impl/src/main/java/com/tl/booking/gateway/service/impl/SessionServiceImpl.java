package com.tl.booking.gateway.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.service.api.SessionService;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.service.api.PrivilegeService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

  @Autowired
  private PrivilegeService privilegeService;

  @Override
  public Single<GatewayBaseResponse<String>> getSessionData(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    return this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              GatewayBaseResponse<String> gatewayBaseResponse = new GatewayBaseResponse<>();
              gatewayBaseResponse.setPrivileges(privileges);
              gatewayBaseResponse.setSessionData(sessionData);
              gatewayBaseResponse.setCode(ResponseCode.SUCCESS.getCode());
              gatewayBaseResponse.setMessage(ResponseCode.SUCCESS.getMessage());
              gatewayBaseResponse.setServerTime(new Date());
              return gatewayBaseResponse;
            }).subscribeOn(Schedulers.io());
  }
}
