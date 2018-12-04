package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.StoreIdOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.StoreIdService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreIdServiceImpl implements StoreIdService {

  @Autowired
  private StoreIdOutboundService storeIdOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreIdServiceImpl.class);

  
  @Override
  public Single<GatewayBaseResponse<List<StoreIdResponse>>> findStoreId(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findStoreId request = mandatoryRequest, sessionData, privilegeToCheck ",
        mandatoryRequest,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_STORE_ID, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .storeIdOutboundService.findAll(mandatoryRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findStoreId response = findStoreId-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
