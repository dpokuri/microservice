package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.HotelOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.HotelService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByAddressParam;
import com.tl.booking.gateway.entity.outbound.Hotel.FindHotelByHotelIdParam;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

  private final HotelOutboundService hotelOutboundService;

  private final PrivilegeService privilegeService;

  @Autowired
  public HotelServiceImpl(
      HotelOutboundService hotelOutboundService,
      PrivilegeService privilegeService) {
    this.hotelOutboundService = hotelOutboundService;
    this.privilegeService = privilegeService;
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelByAddress(
      MandatoryRequest mandatoryRequest, FindHotelByAddressParam findHotelByAddressParam,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelOutboundService
            .findHotelByAddress(mandatoryRequest, findHotelByAddressParam)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, String>>>> findHotelNameByHotelIds(
      MandatoryRequest mandatoryRequest, FindHotelByHotelIdParam findHotelByHotelIdParam, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.hotelOutboundService
            .findHotelNameByHotelIds(mandatoryRequest, findHotelByHotelIdParam)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
