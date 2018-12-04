package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.PromoCodeOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.PromoCodeService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCodeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.PromoCodeResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {

  @Autowired
  private PromoCodeOutboundService promoCodeOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCodeServiceImpl.class);


  public Single<GatewayBaseResponse<PromoCodeResponse>> findPromoCodeById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("findPromoCodeById Request mandatoryRequest, id, privilegeToCheck, sessionData", mandatoryRequest, id, privilegeToCheck, sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_PROMO_CODE, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoCodeOutboundService.findPromoCodeById(mandatoryRequest, id))
        .flatMap
            (restResponseCodeGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
                  restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("findPromoCodeById response = "
                      + " result", restResponseCodeGatewayBaseResponse);
                  return restResponseCodeGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoCodeResponse>>> findPromoCodeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String code,
      String campaignId,
      Integer page,
      Integer size,
      PromoCodeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findPromoCodeFilterPaginated request =  findPromoPageFilterPaginated-mandatoryRequest, code, campaignId, page, size, columnSort, sortDirection, privilegeToCheck, sessionData",
        mandatoryRequest,
        code,
        campaignId,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CODE, privilegeToCheck)
        .flatMap(authorized
            ->
            this
                .promoCodeOutboundService.findPromoCodeFilterPaginated(mandatoryRequest,
                code, campaignId, page, size,
                columnSort, sortDirection, privilegeToCheck))
        .flatMap(restResponseCodeGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
              restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findPromoCodeFilterPaginated response = "
                      + "findPromoCodeFilterPaginated-result ",
                  restResponseCodeGatewayBaseResponse);

              return restResponseCodeGatewayBaseResponse;
            }))
        .subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> createPromoCode(
      MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("createPromoCode request = mandatoryRequest, promoPageRequest, privilegeToCheck, sessionData ",
        mandatoryRequest,
        promoCodeRequest,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_CODE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.createPromoCode(mandatoryRequest, promoCodeRequest))
        .flatMap
            (restResponsePageGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponsePageGatewayBaseResponse.setPrivileges(privileges);
                  restResponsePageGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("createPromoCode response = result", restResponsePageGatewayBaseResponse);
                  return restResponsePageGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> updatePromoCode(
      MandatoryRequest mandatoryRequest,
      PromoCodeRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updatePromoCode request = mandatoryRequest, promoCodeRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        promoCodeRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_CODE, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoCodeOutboundService
                    .updatePromoCode(mandatoryRequest, promoCodeRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updatePromoCode response = "
                  + "result ", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoCode(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("deletePromoCode request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_CODE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.deletePromoCode(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("deletePromoPage response = "
                  + "deletePromoPage-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> updateStatusUnActivatePromoCode(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusUnActivatePromoCode request = "
            + "mandatoryRequest, "
            + "id, "
            + "sessionData, "
            + "privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_CODE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.updateStatusUnActivatePromoCode(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusUnActivatePromoCode response = "
                  + "result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCodeResponse>> updateStatusActivatePromoCode(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusActivatePromoCode request = "
            + "mandatoryRequest, "
            + "id, "
            + "sessionData, "
            + "privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_CODE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoCodeOutboundService.updateStatusActivatePromoCode(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusActivatePromoCode response = "
                  + "result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


}
