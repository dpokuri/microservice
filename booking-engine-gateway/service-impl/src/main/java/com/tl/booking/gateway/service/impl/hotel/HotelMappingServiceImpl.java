package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.HotelMappingOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.HotelMappingService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelMappingResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelMappingServiceImpl implements HotelMappingService {

  @Autowired
  private HotelMappingOutboundService hotelMappingOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<HotelMappingResponse>>> findHotelMappingsByStoreId(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String q, String regionId,
      String sort, String method, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelMappingOutboundService
            .findHotelMappingsByStoreId(mandatoryRequest, page, limit, q, regionId, sort, method))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HotelMappingResponse>> findHotelMappingByStoreIdAndId(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelMappingOutboundService.findHotelMappingByStoreIdAndId(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> createHotelMapping(MandatoryRequest mandatoryRequest,
      HotelMappingRequest hotelMappingRequest, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelMappingOutboundService.createHotelMapping(mandatoryRequest, hotelMappingRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> updateHotelMapping(MandatoryRequest mandatoryRequest, String id,
      HotelMappingRequest hotelMappingRequest, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelMappingOutboundService
            .updateHotelMapping(mandatoryRequest, id, hotelMappingRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> deleteHotelMapping(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelMappingOutboundService.deleteHotelMapping(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
