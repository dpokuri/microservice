package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.HotelScrappingReportOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.HotelScrappingReportService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelScrappingReportServiceImpl implements HotelScrappingReportService {

  @Autowired
  private HotelScrappingReportOutboundService hotelScrappingReportOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> getPriceReportData(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String regionId,
      String checkInDate, String scrappingDate, String q, String sort, String method,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelScrappingReportOutboundService
            .getPriceReportData(mandatoryRequest, page, limit, regionId,
                checkInDate, scrappingDate, q, sort, method)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> getEmailReport(MandatoryRequest mandatoryRequest,
      String regionId, String q, String checkInDate, String scrappingDate, String email,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelScrappingReportOutboundService
            .getEmailReport(mandatoryRequest, regionId, q, checkInDate, scrappingDate, email))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<String>> exportReport(MandatoryRequest mandatoryRequest,
      String regionId, String q, String checkInDate, String scrappingDate,
      String privilegeToCheck, SessionData sessionData) {
    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelScrappingReportOutboundService
            .exportReport(mandatoryRequest, regionId, q, checkInDate, scrappingDate))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
