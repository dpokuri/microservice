package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.HotelPromoAggregateOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.HotelPromoAggregateService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.FindAllPromoParam;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoAggregateResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelPromoAggregateServiceImpl implements HotelPromoAggregateService {

  private final HotelPromoAggregateOutboundService hotelPromoAggregateOutboundService;

  private final PrivilegeService privilegeService;

  @Autowired
  public HotelPromoAggregateServiceImpl(
      HotelPromoAggregateOutboundService hotelPromoAggregateOutboundService,
      PrivilegeService privilegeService) {
    this.hotelPromoAggregateOutboundService = hotelPromoAggregateOutboundService;
    this.privilegeService = privilegeService;
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<HotelPromoAggregateResponse>>>
  findAllHotelPromoByHotelIdAndRoomIdAndDate(MandatoryRequest mandatoryRequest,
      FindAllPromoParam findAllPromoParam, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoAggregateOutboundService
            .findAllHotelPromoByHotelIdAndRoomIdAndDate(mandatoryRequest, findAllPromoParam))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoAggregateResponse>> findHotelPromoById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoAggregateOutboundService
            .findHotelPromoById(mandatoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoAggregateResponse>> createHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, HotelPromoAggregateRequest hotelPromoAggregateRequest,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoAggregateOutboundService
            .createHotelPromoAggregate(mandatoryRequest, hotelPromoAggregateRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HotelPromoAggregateResponse>> updateHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id,
      HotelPromoAggregateRequest hotelPromoAggregateRequest, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoAggregateOutboundService
            .updateHotelPromoAggregate(mandatoryRequest, id, hotelPromoAggregateRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> deleteHotelPromoAggregate(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoAggregateOutboundService
            .deleteHotelPromoAggregate(mandatoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

}
