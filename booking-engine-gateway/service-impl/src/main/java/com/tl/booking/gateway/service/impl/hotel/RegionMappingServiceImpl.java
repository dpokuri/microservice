package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.RegionMappingOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.RegionMappingService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.RegionMappingResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionMappingServiceImpl implements RegionMappingService {

  @Autowired
  private RegionMappingOutboundService regionMappingOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<RegionMappingResponse>>> findRegionMappingsByStoreId(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String q, String sort,
      String method, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .regionMappingOutboundService
            .findRegionMappingsByStoreId(mandatoryRequest, page, limit, q,
                sort, method)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<RegionMappingResponse>> findRegionMappingByStoreIdAndId(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .regionMappingOutboundService.findRegionMappingByStoreIdAndId(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> createRegionMapping(MandatoryRequest mandatoryRequest,
      RegionMappingRequest regionMappingRequest, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .regionMappingOutboundService
            .createRegionMapping(mandatoryRequest, regionMappingRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> updateRegionMapping(MandatoryRequest mandatoryRequest, String id,
      RegionMappingRequest regionMappingRequest, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this

            .regionMappingOutboundService
            .updateRegionMapping(mandatoryRequest, id, regionMappingRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse> deleteRegionMapping(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this
            .regionMappingOutboundService.deleteRegionMapping(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
