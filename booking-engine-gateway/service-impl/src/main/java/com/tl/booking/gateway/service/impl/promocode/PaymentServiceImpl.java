package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.PaymentOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.PaymentService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentOutboundService paymentOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

  
  @Override
  public Single<GatewayBaseResponse<List<PaymentResponse>>> payment(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findPayment request = mandatoryRequest, sessionData, privilegeToCheck ",
        mandatoryRequest,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_PAYMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .paymentOutboundService.findAll(mandatoryRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findPayment response = findPayment-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
