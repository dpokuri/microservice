package com.tl.booking.gateway.rest.web.controller.promocode;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.promocode.VariableSourceService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.log4j.MDC;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.VARIABLE_SOURCE)
@Api(value = "VariableSource")
public class VariableSourceController {

  @Autowired
  private VariableSourceService variableSourceService;

  private String username = "username";

  @GetMapping(value = "/{sourceType}")
  public DeferredResult<GatewayBaseResponse<List<VariableSourceResponse>>> findAllVariableSource(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String sourceType,
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @NotNull
      @RequestParam(value = "key", required = false) String key) {

    DeferredResult<GatewayBaseResponse<List<VariableSourceResponse>>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.variableSourceService.findAllVariableSource(mandatoryRequest, sourceType, key, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }


}
