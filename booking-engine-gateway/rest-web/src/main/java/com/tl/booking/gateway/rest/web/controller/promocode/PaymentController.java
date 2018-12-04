package com.tl.booking.gateway.rest.web.controller.promocode;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.service.api.promocode.PaymentService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentResponse;

import io.swagger.annotations.Api;
import java.util.List;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.PAYMENT)
@Api(value = "Payment")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  private String username = "username";

  @GetMapping(value = "/findAll")
  public DeferredResult<GatewayBaseResponse<List<PaymentResponse>>> findPayments() {

    DeferredResult<GatewayBaseResponse<List<PaymentResponse>>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.paymentService.payment(mandatoryRequest, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }



}
