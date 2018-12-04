package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.PromoCodeAdjustmentOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.PromoCodeAdjustmentService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeAdjustmentColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentDropdownResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeAdjustmentResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoCodeAdjustmentServiceImpl implements PromoCodeAdjustmentService {

  @Autowired
  private PromoCodeAdjustmentOutboundService promoCodeOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCodeAdjustmentServiceImpl.class);


  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> findPromoCodeAdjustmentById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("findPromoCodeAdjustmentById Request mandatoryRequest, id, privilegeToCheck, sessionData", mandatoryRequest, id, privilegeToCheck, sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoCodeOutboundService.findPromoCodeAdjustmentById(mandatoryRequest, id))
        .flatMap
            (restResponseCodeGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
                  restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("findPromoCodeAdjustmentById response = "
                      + " result", restResponseCodeGatewayBaseResponse);
                  return restResponseCodeGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoCodeAdjustmentResponse>>> findPromoCodeAdjustmentFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String name,
      Boolean isPromoCodeCombine,
      Integer page,
      Integer size,
      PromoCodeAdjustmentColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findPromoCodeAdjustmentFilterPaginated request = findPromoPageFilterPaginated-mandatoryRequest, code, campaignId, page, size, columnSort, sortDirection, privilegeToCheck, sessionData",
        mandatoryRequest,
        name,
        isPromoCodeCombine,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap(authorized
            ->
            this
                .promoCodeOutboundService.findPromoCodeAdjustmentFilterPaginated(mandatoryRequest,
                name, isPromoCodeCombine, page, size,
                columnSort, sortDirection, privilegeToCheck))
        .flatMap(restResponseCodeGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
              restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findPromoCodeAdjustmentFilterPaginated response = findPromoCodeAdjustmentFilterPaginated-result ",
                  restResponseCodeGatewayBaseResponse);

              return restResponseCodeGatewayBaseResponse;
            }))
        .subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> createPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("createPromoCodeAdjustment request = mandatoryRequest, promoPageRequest, privilegeToCheck, sessionData ",
        mandatoryRequest,
        promoCodeRequest,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.createPromoCodeAdjustment(mandatoryRequest, promoCodeRequest))
        .flatMap
            (restResponsePageGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponsePageGatewayBaseResponse.setPrivileges(privileges);
                  restResponsePageGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("createPromoCodeAdjustment response = result", restResponsePageGatewayBaseResponse);
                  return restResponsePageGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      PromoCodeAdjustmentRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updatePromoCodeAdjustment request = mandatoryRequest, promoCodeRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        promoCodeRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoCodeOutboundService
                    .updatePromoCodeAdjustment(mandatoryRequest, promoCodeRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updatePromoCodeAdjustment response = result ", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("deletePromoCodeAdjustment request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.deletePromoCodeAdjustment(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("deletePromoPage response = deletePromoPage-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusUnActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusUnActivatePromoCodeAdjustment request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.updateStatusUnActivatePromoCodeAdjustment(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusUnActivatePromoCodeAdjustment response = result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusActivatePromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusActivatePromoCodeAdjustment request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.updateStatusActivatePromoCodeAdjustment(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusActivatePromoCodeAdjustment response = "
                  + "result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusPendingPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusPendingPromoCodeAdjustment request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.PENDING_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.updateStatusPendingPromoCodeAdjustment(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusPendingPromoCodeAdjustment response = result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoCodeAdjustmentResponse>> updateStatusRejectedPromoCodeAdjustment(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusRejectedPromoCodeAdjustment request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.REJECTED_PROMO_ADJUSTMENT, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.updateStatusRejectedPromoCodeAdjustment(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusRejectedPromoCodeAdjustment response = result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<List<PromoCodeAdjustmentDropdownResponse>>> findPromoCodeAdjustmentActivate(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findPromoCodeAdjustmentActivate request = mandatoryRequest, sessionData, privilegeToCheck ",
        mandatoryRequest,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_PROMO_ADJUSTMENT_ACTIVATE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.findPromoCodeAdjustmentActivate(mandatoryRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findPromoCodeAdjustmentActivate response = result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
