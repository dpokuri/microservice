package com.tl.booking.gateway.rest.web.controller.flightrme;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.flightrme.AdjustmentRuleService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleColumn;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleRequest;
import com.tl.booking.gateway.entity.outbound.flightrme.AdjustmentRuleResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@RequestMapping(ApiPath.ADJUSTMENT_RULE)
@Api(value = "AdjustmentRule")
public class AdjustmentRuleController {

//  @Autowired
//  private AdjustmentRuleService adjustmentRuleService;

  private String username = "username";
//
//  @GetMapping(path = "/{id}")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> findAdjustmentRuleById(
//      @PathVariable String id) {
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//    this.adjustmentRuleService.findAdjustmentRuleById(mandatoryRequest, id, (String) MDC.get(BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//
//  }
//
//  @GetMapping(path = "")
//  public DeferredResult<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> findAdjustmentRuleFilterPaginated(
//      @RequestParam(required = false) String airline,
//      @RequestParam(required = false) String origin,
//      @RequestParam(required = false) String destination,
//      @RequestParam(required = false) AdjustmentRuleStatus adjustmentRuleStatus,
//      @RequestParam Integer page,
//      @RequestParam Integer size,
//      @RequestParam(required = false) AdjustmentRuleColumn columnSort,
//      @RequestParam(required = false) SortDirection sortDirection) {
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    DeferredResult<GatewayBaseResponse<RestResponsePage<AdjustmentRuleResponse>>> deferred = new DeferredResult<>();
//    this.adjustmentRuleService.findAdjustmentRuleFilterPaginated(
//        mandatoryRequest,
//        airline,
//        origin,
//        destination,
//        adjustmentRuleStatus,
//        page,
//        size,
//        columnSort,
//        sortDirection,
//        (String) MDC.get(BaseMongoFields.PRIVILEGES),
//        sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//  @PostMapping(path = "")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> createAdjustmentRule(
//      @RequestBody AdjustmentRuleRequest adjustmentRuleRequest) {
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String) MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.createAdjustmentRule(mandatoryRequest, adjustmentRuleRequest, (String) MDC.get
//        (BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//  @PutMapping(value = "/{id}")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> updateAdjustmentRule(
//      @RequestBody AdjustmentRuleRequest adjustmentRuleRequest,
//      @PathVariable String id) {
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.updateAdjustmentRule(mandatoryRequest, adjustmentRuleRequest, id,(String) MDC
//        .get(BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//  @DeleteMapping(value = "/{id}")
//  public DeferredResult<GatewayBaseResponse<Boolean>> deleteById(
//      @PathVariable String id) {
//
//    DeferredResult<GatewayBaseResponse<Boolean>> deferred = new DeferredResult<>();
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.deleteAdjustmentRule(mandatoryRequest, id, (String) MDC.get
//        (BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//  @PutMapping(path = "/{id}/unActivate")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusUnActivedAdjustmentRuleById(
//      @PathVariable String id) {
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.updateStatusUnActivatedAdjustmentRule(mandatoryRequest, id, (String) MDC.get
//        (BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//  @PutMapping(path = "/{id}/activate")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusActivedAdjustmentRuleById(
//      @PathVariable String id) {
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.updateStatusActivatedAdjustmentRule(mandatoryRequest, id, (String) MDC.get
//        (BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//
//  @PutMapping(path = "/{id}/pending")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusPendingAdjustmentRuleById(
//      @PathVariable String id) {
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.updateStatusPendingAdjustmentRule(mandatoryRequest, id, (String) MDC.get
//        (BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }
//
//  @PutMapping(path = "/{id}/rejected")
//  public DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> updateStatusRejectedAdjustmentRuleById(
//      @PathVariable String id) {
//
//    DeferredResult<GatewayBaseResponse<AdjustmentRuleResponse>> deferred = new DeferredResult<>();
//
//    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
//        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
//        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
//        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
//        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
//        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
//        .build();
//
//    SessionData sessionData = new SessionData();
//    sessionData.setUsername((String)MDC.get(this.username));
//
//    this.adjustmentRuleService.updateStatusRejectedAdjustmentRule(mandatoryRequest, id, (String) MDC.get
//        (BaseMongoFields.PRIVILEGES), sessionData)
//        .subscribe(deferred::setResult, deferred::setErrorResult);
//    return deferred;
//  }


}
