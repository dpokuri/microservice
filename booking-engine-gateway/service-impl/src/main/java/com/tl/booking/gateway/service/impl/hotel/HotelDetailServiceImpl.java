package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.HotelDetailOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.HotelDetailService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelDetailServiceImpl implements HotelDetailService {

  @Autowired
  private PrivilegeService privilegeService;

  @Autowired
  private HotelDetailOutboundService hotelDetailOutboundService;

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelRoomList(
      MandatoryRequest mandatoryRequest, String hotelId, String privilegeToCheck, SessionData sessionData) {
    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelDetailOutboundService.getHotelRoomList(mandatoryRequest, hotelId))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);
              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getHotelPolicy(
      MandatoryRequest mandatoryRequest, String privilegeToCheck, SessionData sessionData) {
    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .hotelDetailOutboundService.getHotelPolicy(mandatoryRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);
              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }
}
