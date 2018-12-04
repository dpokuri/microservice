package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.VariableSourceOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.VariableSourceService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariableSourceServiceImpl implements VariableSourceService {

  @Autowired
  private VariableSourceOutboundService variableSourceOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(VariableSourceServiceImpl.class);

  
  @Override
  public Single<GatewayBaseResponse<List<VariableSourceResponse>>> findAllVariableSource(
      MandatoryRequest mandatoryRequest,
      String sourceType,
      String key,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findVariableSource request = mandatoryRequest, sourceType, key, sessionData, privilegeToCheck ",
        mandatoryRequest,
        sourceType,
        key,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_VARIABLE_SOURCE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .variableSourceOutboundService.findAll(mandatoryRequest, sourceType, key))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findVariableSource response = findVariableSource-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
