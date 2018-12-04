package com.tl.booking.gateway.service.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.hotel.InternalSubsidyOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.hotel.InternalSubsidyService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalSubsidyServiceImpl implements InternalSubsidyService {

  @Autowired
  private InternalSubsidyOutboundService internalSubsidyOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  @Override
  public Single<GatewayBaseResponse> createInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyRequest internalSubsidyRequest, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.internalSubsidyOutboundService.createInternalSubsidy(
            mandatoryRequest, internalSubsidyRequest)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse> updateInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyRequest internalSubsidyRequest, String id, String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.internalSubsidyOutboundService.updateInternalSubsidy(
            mandatoryRequest, internalSubsidyRequest, id
        )).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse> setActiveInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyActivateRequest internalSubsidyActivateRequest, String id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.internalSubsidyOutboundService.setActiveInternalSubsidy(
            mandatoryRequest, internalSubsidyActivateRequest, id
        )).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse> deleteInternalSubsidy(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.internalSubsidyOutboundService.deleteInternalSubsidy(
            mandatoryRequest, id
        )).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse> findInternalSubsidyByStoreIdAndId(MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck, SessionData sessionData) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(
            authorized -> this.internalSubsidyOutboundService.findInternalSubsidyByStoreIdAndId(
                mandatoryRequest, id
            )).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  public Single<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> findInternalSubsidyByStoreId(
      MandatoryRequest mandatoryRequest, Map<String, String> query,
      String privilegeToCheck, SessionData sessionData
  ) {

    return this.privilegeService.checkAuthorized(PrivilegeId.MM_MODULE, privilegeToCheck)
        .flatMap(authorized -> this.internalSubsidyOutboundService.findInternalSubsidyByStoreId(
            mandatoryRequest, query
        )).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

}
