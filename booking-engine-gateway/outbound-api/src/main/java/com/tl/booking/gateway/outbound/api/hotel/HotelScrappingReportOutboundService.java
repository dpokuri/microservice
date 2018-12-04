package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

import io.reactivex.Single;

public interface HotelScrappingReportOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> getPriceReportData(MandatoryRequest mandatoryRequest, Integer page,
      Integer limit,
      String regionId,
      String checkInDate,
      String scrappingDate,
      String q,
      String sort,
      String method);

  Single<GatewayBaseResponse<Boolean>> getEmailReport(MandatoryRequest mandatoryRequest, String regionId,
      String q,
      String checkInDate,
      String scrappingDate,
      String email);

  Single<GatewayBaseResponse<String>> exportReport(MandatoryRequest mandatoryRequest, String regionId,
      String q,
      String checkInDate,
      String scrappingDate);
}
