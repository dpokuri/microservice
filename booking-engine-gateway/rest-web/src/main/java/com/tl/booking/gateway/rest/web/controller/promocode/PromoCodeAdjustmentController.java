package com.tl.booking.gateway.rest.web.controller.promocode;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.service.api.promocode.PromoCodeAdjustmentService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;

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
@RequestMapping(ApiPath.PROMO_ADJUSTMENT)
@Api(value = "PromoCodeAdjustment")
public class PromoCodeAdjustmentController {

  @Autowired
  private PromoCodeAdjustmentService promoCodeAdjustmentService;

  private String username = "username";

  @ApiOperation(value = "Find promo code adjustment by Id", notes = "Get Document of PromoCodeAdjustmentAdjustment By Id")
  @GetMapping(path = "/{id}")
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> findPromoCodeAdjustmentById(
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

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();
    this.promoCodeAdjustmentService.findPromoCodeAdjustmentById(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;

  }

  @GetMapping(path = "")
  public DeferredResult<BaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Boolean isPromoCodeAdjustmentCombine,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) PromoCodeAdjustmentColumn columnSort,
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

    DeferredResult<BaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> deferred = new DeferredResult<>();
    this.promoCodeAdjustmentService.findPromoCodeAdjustmentFilterPaginated(
        mandatoryRequest,
        name,
        isPromoCodeAdjustmentCombine,
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
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(
      @Valid @RequestBody PromoCodeAdjustmentRequest promoCodeRequest) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.createPromoCodeAdjustment(mandatoryRequest, promoCodeRequest, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }


  @PutMapping(path = "/{id}")
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(
      @Valid @RequestBody PromoCodeAdjustmentRequest promoCodeRequest,
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.updatePromoCodeAdjustment(mandatoryRequest, promoCodeRequest, id,(String) MDC
        .get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @DeleteMapping(path = "/{id}")
  public DeferredResult<BaseResponse<Boolean>> deleteById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse<Boolean>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.deletePromoCodeAdjustment(mandatoryRequest, id, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(path = "/{id}/unActivate")
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusUnActivedPromoCodeAdjustmentById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.updateStatusUnActivatePromoCodeAdjustment(mandatoryRequest, id, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(path = "/{id}/activate")
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivedPromoCodeAdjustmentById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.updateStatusActivatePromoCodeAdjustment(mandatoryRequest, id, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(path = "/{id}/pending")
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeAdjustmentById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.updateStatusPendingPromoCodeAdjustment(mandatoryRequest, id, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(path = "/{id}/rejected")
  public DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustmentById(
      @NotBlank(message = "id must be not blank")
      @NotEmpty(message = "id must be not empty")
      @PathVariable String id) {

    DeferredResult<BaseResponse<PromoCodeAdjustmentResponse>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.updateStatusRejectedPromoCodeAdjustment(mandatoryRequest, id, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @GetMapping(path = ApiPath.FIND_ACTIVATE)
  public DeferredResult<BaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentActivate() {

    DeferredResult<BaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoCodeAdjustmentService.findPromoCodeAdjustmentActivate(mandatoryRequest, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }


}
