package com.tl.booking.gateway.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.PromoPageOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.PromoPageService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoPageColumn;
import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoPageResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoPageServiceImpl implements PromoPageService {

  @Autowired
  private PromoPageOutboundService promoPageOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoPageServiceImpl.class);

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoPageResponse>>> findPromoPageFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String title,
      String categories,
      PromoPageStatus status,
      Integer precedence,
      Integer page,
      Integer size,
      PromoPageColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findPromoPageFilterPaginated request = "
            + "findPromoPageFilterPaginated-mandatoryRequest {}, "
            + "title {}, "
            + "categories {}, "
            + "status {}, "
            + "precedence {}, "
            + "page {}, "
            + "size {}, "
            + "columnSort {}, "
            + "sortDirection {}, "
            + "findPromoPageFilterPaginated-privilegeToCheck {}",
        mandatoryRequest,
        title,
        categories,
        status,
        precedence,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_LIST, privilegeToCheck)
        .flatMap(authorized
            ->
            this
                .promoPageOutboundService.findPromoPageFilterPaginated(mandatoryRequest,
                title, categories, status, precedence, page, size,
                columnSort, sortDirection, privilegeToCheck))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findPromoPageFilterPaginated response = "
                      + "findPromoPageFilterPaginated-result {} ",
                  restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            }))
        .subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> createPromoPage(
      MandatoryRequest mandatoryRequest,
      PromoPageRequest promoPageRequest, String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("createPromoPage request = "
            + "createPromoPage-mandatoryRequest {}, "
            + "promoPageRequest {}, "
            + "createPromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        promoPageRequest,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_LIST, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoPageOutboundService.createPromoPage(mandatoryRequest, promoPageRequest))
        .flatMap
            (restResponsePageGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponsePageGatewayBaseResponse.setPrivileges(privileges);
                  restResponsePageGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("createPromoPage response = "
                      + "createPromoPage-result {}", restResponsePageGatewayBaseResponse);
                  return restResponsePageGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updatePromoPage(
      MandatoryRequest mandatoryRequest,
      PromoPageRequest promoPageRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updatePromoPage request = "
            + "updatePromoPage-mandatoryRequest {}, "
            + "promoPageRequest {}, "
            + "updatePromoPage-slug {}, "
            + "updatePromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        promoPageRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_LIST, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoPageOutboundService
                    .updatePromoPage(mandatoryRequest, promoPageRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updatePromoPage response = "
                  + "updatePromoPage-result {} ", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoPage(MandatoryRequest mandatoryRequest,
      String id, String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("deletePromoPage request = "
            + "deletePromoPage-mandatoryRequest {}, "
            + "deletePromoPage-slug {}, "
            + "deletePromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_LIST, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoPageOutboundService.deletePromoPage(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("deletePromoPage response = "
                  + "deletePromoPage-result {}", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> findPromoPageBySlug(
      MandatoryRequest mandatoryRequest, String
      id, String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("findPromoPageBySlug request = "
            + "findPromoPageBySlug-mandatoryRequest {}, "
            + "findPromoPageBySlug-slug {}, "
            + "findPromoPageBySlug-privilegeToCheck {} ",
        mandatoryRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_LIST, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .promoPageOutboundService.findPromoPageBySlug(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findPromoPageBySlug response = findPromoPageBySlug-result {}",
                  restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusPendingPromoPage(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusPendingPromoPage request = "
            + "updateStatusPendingPromoPage-mandatoryRequest {}, "
            + "updateStatusPendingPromoPage-slug {}, "
            + "updateStatusPendingPromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        id,
        privilegeToCheck);

    return this.privilegeService
        .checkAuthorized(PrivilegeId.REQUEST_APPROVAL_PROMO_LIST, privilegeToCheck).flatMap
            (authorized ->
                this.promoPageOutboundService.updateStatusPendingPromoPage(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusPendingPromoPage response = "
                  + "updateStatusPendingPromoPage-result {}", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusActivatedPromoPage(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusActivatedPromoPage request = "
            + "updateStatusActivatedPromoPage-mandatoryRequest {}, "
            + "updateStatusActivatedPromoPage-slug {}, "
            + "updateStatusActivatedPromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_LIST, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoPageOutboundService
                    .updateStatusActivatedPromoPage(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusActivatedPromoPage response = "
                      + "updateStatusActivatedPromoPage-result {}",
                  restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusInActivatedPromoPage(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusInActivatedPromoPage request = "
            + "updateStatusInActivatedPromoPage-mandatoryRequest {}, "
            + "updateStatusInActivatedPromoPage-slug {}, "
            + "updateStatusInActivatedPromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        id,
        privilegeToCheck);

    return this.privilegeService
        .checkAuthorized(PrivilegeId.UNACTIVATE_PROMO_LIST, privilegeToCheck).flatMap
            (authorized ->
                this.promoPageOutboundService
                    .updateStatusInActivatedPromoPage(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusInActivatedPromoPage response = "
                      + "updateStatusInActivatedPromoPage-result {}",
                  restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoPageResponse>> updateStatusRejectedPromoPage(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateStatusRejectedPromoPage request = "
            + "updateStatusRejectedPromoPage-mandatoryRequest {}, "
            + "updateStatusRejectedPromoPage-slug {}, "
            + "updateStatusRejectedPromoPage-privilegeToCheck {} ",
        mandatoryRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.ACTIVATE_PROMO_LIST, privilegeToCheck)
        .flatMap
            (authorized ->
                this.promoPageOutboundService.updateStatusRejectedPromoPage(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateStatusRejectedPromoPage response = "
                  + "updateStatusRejectedPromoPage-result {}", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
