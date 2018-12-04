package com.tl.booking.gateway.rest.web.controller.promocode;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.service.api.promocode.BusinessLogicResponseService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.BusinessLogicResponseColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BusinessLogicResponseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.MDC;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.BUSINESS_LOGIC)
@Api(value = "BusinessLogicResponse")
public class BusinessLogicResponseController {

  @Autowired
  private BusinessLogicResponseService businessLogicResponseService;

  private String username = "username";

  @ApiOperation(value = "Find businessLogicResponse by Id", notes = "Get Document of BusinessLogicResponse By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<GatewayBaseResponse<BusinessLogicResponseResponse>> findBusinessLogicResponseById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    DeferredResult<GatewayBaseResponse<BusinessLogicResponseResponse>> deferred = new DeferredResult<>();
    this.businessLogicResponseService.findBusinessLogicResponseById(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;

  }

  @GetMapping(path = "")
  public DeferredResult<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> findBusinessLogicResponseFilterPaginated(
      @RequestParam(required = false) String responseCode,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) BusinessLogicResponseColumn columnSort,
      @RequestParam(required = false) SortDirection sortDirection) {

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    DeferredResult<GatewayBaseResponse<RestResponsePage<BusinessLogicResponseResponse>>> deferred = new DeferredResult<>();
    this.businessLogicResponseService.findBusinessLogicResponseFilterPaginated(
        mandatoryRequest,
        responseCode,
        page,
        size,
        columnSort,
        sortDirection,
        (String) MDC.get(BaseMongoFields.PRIVILEGES),
        sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PostMapping(path = "")
  public DeferredResult<GatewayBaseResponse<BusinessLogicResponseResponse>> createBusinessLogicResponse(
      @Valid @RequestBody BusinessLogicResponseRequest promoCodeRequest) {

    DeferredResult<GatewayBaseResponse<BusinessLogicResponseResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.businessLogicResponseService.createBusinessLogicResponse(mandatoryRequest, promoCodeRequest, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }


  @PutMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse<BusinessLogicResponseResponse>> updateBusinessLogicResponse(
      @Valid @RequestBody BusinessLogicResponseRequest promoCodeRequest,
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<GatewayBaseResponse<BusinessLogicResponseResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.businessLogicResponseService.updateBusinessLogicResponse(mandatoryRequest, promoCodeRequest, id,(String) MDC
        .get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @DeleteMapping(value = "/{id}")
  public DeferredResult<GatewayBaseResponse<Boolean>> deleteById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<GatewayBaseResponse<Boolean>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.businessLogicResponseService.deleteBusinessLogicResponse(mandatoryRequest, id, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }


  @GetMapping(value = "/findAll")
  public DeferredResult<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> findBusinessLogicResponses() {

    DeferredResult<GatewayBaseResponse<List<BusinessLogicResponseResponse>>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.businessLogicResponseService.findBusinessLogicResponses(mandatoryRequest, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }



}
