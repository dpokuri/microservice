package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

import io.reactivex.Single;

public interface HotelScrappingReportService {

  Single<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> getPriceReportData(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String regionId,
      String checkInDate,
      String scrappingDate,
      String q,
      String sort,
      String method,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<Boolean>> getEmailReport(MandatoryRequest mandatoryRequest, String regionId,
      String q,
      String checkInDate,
      String scrappingDate,
      String email,
      String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<String>> exportReport(MandatoryRequest mandatoryRequest, String regionId,
      String q,
      String checkInDate,
      String scrappingDate,
      String privilegeToCheck, SessionData sessionData);

}
