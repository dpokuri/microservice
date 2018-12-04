package com.tl.booking.gateway.rest.web.controller.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.service.api.hotel.HotelScrappingReportService;
import com.tl.booking.gateway.entity.constant.ApiPath;
import com.tl.booking.gateway.entity.constant.DefaultValues;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

import io.swagger.annotations.Api;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(ApiPath.REPORT)
@Api(value = "Hotel - Report")
public class HotelScrappingReportController {

  @Autowired
  private HotelScrappingReportService hotelScrappingReportService;

  private static final String MDC_PRIVILEGES = "privileges";

  @GetMapping
  public DeferredResult<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> getPriceReportData(
      @RequestParam(value = "page", defaultValue = DefaultValues.PAGE) Integer page,
      @RequestParam(value = "limit", defaultValue = DefaultValues.PAGE_LIMIT) Integer limit,
      @RequestParam(value = "regionId", defaultValue = "", required = false)String regionId,
      @RequestParam(value = "checkInDate", defaultValue = "", required = false) String checkInDate,
      @RequestParam(value = "scrappingDate", defaultValue = "", required = false) String scrappingDate,
      @RequestParam(value = "q", defaultValue = "", required = false) String q,
      @RequestParam(value = "sort", defaultValue = DefaultValues.SORT_BY) String sort,
      @RequestParam(value = "method", defaultValue = DefaultValues.SORT_METHOD) String method){

    DeferredResult<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String) org.apache.log4j.MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String) org.apache.log4j.MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String) org.apache.log4j.MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String) org.apache.log4j.MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String) org.apache.log4j.MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelScrappingReportService
        .getPriceReportData(mandatoryRequest, page,
            limit,
            regionId,
            checkInDate,
            scrappingDate,

            q,
            sort,
            method, (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = "/send-email")
  public DeferredResult<GatewayBaseResponse<Boolean>> getEmailReport(
      @RequestParam(value = "regionId", defaultValue = "", required = false) String regionId,
      @RequestParam(value = "q", defaultValue = "", required = false) String q,
      @RequestParam(value = "checkInDate", defaultValue = "", required = false) String checkInDate,
      @RequestParam(value = "scrappingDate", defaultValue = "", required = false) String scrappingDate,
      @RequestParam(value = "email")String email){

    DeferredResult<GatewayBaseResponse<Boolean>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelScrappingReportService
        .getEmailReport(mandatoryRequest,
            regionId,
            checkInDate,
            scrappingDate,
            q,
            email,
            (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

  @GetMapping(value = "/export")
  public DeferredResult<GatewayBaseResponse<String>> exportReport(
      @RequestParam(value = "regionId", defaultValue = "", required = false) String regionId,
      @RequestParam(value = "q", defaultValue = "", required = false) String q,
      @RequestParam(value = "checkInDate", defaultValue = "", required = false) String checkInDate,
      @RequestParam(value = "scrappingDate", defaultValue = "", required = false) String scrappingDate){

    DeferredResult<GatewayBaseResponse<String>> deferred = new DeferredResult<>();

    SessionData sessionData = new SessionData();
    sessionData.setUsername((String)MDC.get("username"));

    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withStoreId((String)MDC.get(BaseMongoFields.STORE_ID))
        .withChannelId((String)MDC.get(BaseMongoFields.CHANNEL_ID))
        .withServiceId((String)MDC.get(BaseMongoFields.SERVICE_ID))
        .withRequestId((String)MDC.get(BaseMongoFields.REQUEST_ID))
        .withUsername((String)MDC.get(BaseMongoFields.USERNAME))
        .build();

    this.hotelScrappingReportService
        .exportReport(mandatoryRequest,
            regionId,
            q,
            checkInDate,
            scrappingDate,
            (String)MDC.get(MDC_PRIVILEGES), sessionData)
        .subscribe(deferred::setResult, deferred::setErrorResult);

    return deferred;
  }

}
