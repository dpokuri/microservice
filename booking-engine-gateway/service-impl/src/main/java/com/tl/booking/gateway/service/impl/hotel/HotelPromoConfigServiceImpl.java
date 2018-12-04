package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.HotelPromoConfigOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.HotelPromoConfigService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigFindAllParams;
import com.tl.booking.gateway.entity.outbound.Hotel.HotelPromoConfigRequest;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelPromoConfigServiceImpl implements HotelPromoConfigService {

  private final HotelPromoConfigOutboundService hotelPromoConfigOutboundService;

  private final PrivilegeService privilegeService;

  @Autowired
  public HotelPromoConfigServiceImpl(
      HotelPromoConfigOutboundService hotelPromoConfigOutboundService,
      PrivilegeService privilegeService) {
    this.hotelPromoConfigOutboundService = hotelPromoConfigOutboundService;
    this.privilegeService = privilegeService;
  }

  @Override
  public Single<GatewayBaseResponse<Object>> create(
      MandatoryRequest mandatoryRequest, HotelPromoConfigRequest hotelPromoConfigRequest,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoConfigOutboundService
            .create(mandatoryRequest, hotelPromoConfigRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Object>> update(MandatoryRequest mandatoryRequest, String id,
      HotelPromoConfigRequest hotelPromoConfigRequest, String privilegeToCheck,
      SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoConfigOutboundService
            .update(mandatoryRequest, id, hotelPromoConfigRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);
              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Object>> delete(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoConfigOutboundService
            .delete(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);
              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Object>> getOne(MandatoryRequest mandatoryRequest, Integer id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoConfigOutboundService
            .getOne(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);
              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Object>> getAll(MandatoryRequest mandatoryRequest,
      HotelPromoConfigFindAllParams hotelPromoConfigFindAllParams, String privilegeToCheck,
      SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelPromoConfigOutboundService
            .getAll(mandatoryRequest, hotelPromoConfigFindAllParams))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);
              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }
}
