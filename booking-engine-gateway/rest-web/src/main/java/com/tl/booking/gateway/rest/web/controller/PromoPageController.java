package com.tl.booking.gateway.rest.web.controller;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.service.api.PromoPageService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;

import io.swagger.annotations.Api;
import org.apache.log4j.MDC;
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
@RequestMapping(ApiPath.PROMO_PAGE)
@Api(value = "PromoPages")
public class PromoPageController {

  @Autowired
  private PromoPageService promoPageService;


  private String username = "username";
  private String businessId = "businessId";

  @GetMapping(path = "")
  public DeferredResult<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> findPromoPageFilterPaginated(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String categories,
      @RequestParam(required = false) PromoPageStatus status,
      @RequestParam(required = false) Integer precedence,
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam(required = false) PromoPageColumn columnSort,
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
    sessionData.setBusinessId((String)MDC.get(this.businessId));

    DeferredResult<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> deferred = new DeferredResult<>();
    this.promoPageService.findPromoPageFilterPaginated(
        mandatoryRequest,
        title,
        categories,
        status,
        precedence,
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
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> createPromoPage(
      @RequestBody PromoPageRequest promoPageRequest) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.createPromoPage(mandatoryRequest, promoPageRequest, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(value = "/{slug}")
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> updatePromoPage(
      @RequestBody PromoPageRequest promoPageRequest,
      @PathVariable String slug) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.updatePromoPage(mandatoryRequest, promoPageRequest, slug,(String) MDC
        .get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @DeleteMapping(value = "/{slug}")
  public DeferredResult<GatewayBaseResponse<Boolean>> deleteByStoreIdAndSlug(
      @PathVariable String slug) {

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

    this.promoPageService.deletePromoPage(mandatoryRequest, slug, (String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @GetMapping(path = "/{slug}")
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> findPromoPageBySlug(
      @PathVariable String slug) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.findPromoPageBySlug(mandatoryRequest, slug,(String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }


  @PutMapping(value = "/{slug}/pending")
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> updateStatusPendingPromoPageBySlug(

      @PathVariable String slug) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.updateStatusPendingPromoPage(mandatoryRequest, slug,(String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(value = "/{slug}/activated")
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> updateStatusActivatedPromoPageBySlug(

      @PathVariable String slug) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.updateStatusActivatedPromoPage(mandatoryRequest, slug,(String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(value = "/{slug}/inActivated")
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> updateStatusInActivatedPromoPageBySlug(

      @PathVariable String slug) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.updateStatusInActivatedPromoPage(mandatoryRequest, slug,(String) MDC
        .get(BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }

  @PutMapping(value = "/{slug}/rejected")
  public DeferredResult<GatewayBaseResponse<PromoPageResponse>> updateStatusRejectedPromoPageBySlug(

      @PathVariable String slug) {

    DeferredResult<GatewayBaseResponse<PromoPageResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get(this.username));

    this.promoPageService.updateStatusRejectedPromoPage(mandatoryRequest, slug,(String) MDC.get
        (BaseMongoFields.PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }
}
