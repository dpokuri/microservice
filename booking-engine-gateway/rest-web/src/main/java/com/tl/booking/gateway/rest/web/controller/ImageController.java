package com.tl.booking.gateway.rest.web.controller;


import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.common.rest.web.model.response.BaseResponse;
import com.tl.booking.gateway.service.api.ImageService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import io.swagger.annotations.Api;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(ApiPath.IMAGES)
@Api(value = "Images")
public class ImageController {

  @Autowired
  private ImageService imageService;

  /*
   * Code changes line : 42-47, 52-56
   *
   * Here we added @RequestHeaders for parameters to accept values from request instead of MDC.
   *
   * As part of this POC we don't have facility to test image uploading feature through Admin Dashboard(React Core) app,
   * hence we are using rest client where we are passing storeId, channelId, requestId, serviceId, userName and privileges as part of request headers.
   *
   * < These changes are temporary(For POC). Please remove this code once POC is done and move on with existing code >
   */

  @Async
  @PostMapping(path = "")
  public DeferredResult<GatewayBaseResponse<ImagesResponse>> createImages(
      @RequestHeader(value="storeId") String storeId,
      @RequestHeader(value="channelId") String channelId,
      @RequestHeader(value="requestId") String requestId,
      @RequestHeader(value="serviceId") String serviceId,
      @RequestHeader(value="userName") String userName,
      @RequestHeader(value="privileges") String privileges,
      @RequestBody ImagesRequest imagesRequest) {

    DeferredResult<GatewayBaseResponse<ImagesResponse>> deferred = new DeferredResult<>();
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId(storeId)
        .withChannelId(channelId)
        .withServiceId(requestId)
        .withRequestId(serviceId)
        .withUsername(userName)
        .build();
       

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    // Here we are taking privileges from request header instead of MDC.

    // < This code change is temporary(For POC). Please remove this code once POC is done and move on with existing code >

    this.imageService.createImages(mandatoryRequest, imagesRequest, privileges, sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);
    return deferred;
  }
}
