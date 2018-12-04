package com.tl.booking.gateway.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.PromoCategoryOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.PromoCategoryService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.PromoCategoryColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryRequest;
import com.tl.booking.gateway.entity.outbound.PromoList.PromoCategoryResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoCategoryServiceImpl implements PromoCategoryService {

  @Autowired
  private PromoCategoryOutboundService promoCategoryOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(PromoCategoryServiceImpl.class);

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<PromoCategoryResponse>>> findPromoCategoryFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String category,
      Integer page,
      Integer size,
      PromoCategoryColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findPromoCategoryFilterPaginated request = "
            + "findPromoCategoryFilterPaginated-mandatoryRequest {}, "
            + "category {}, "
            + "page {}, "
            + "size {}, "
            + "columnSort {}, "
            + "sortDirection {}, "
            + "findPromoCategoryFilterPaginated-privilegeToCheck {}", mandatoryRequest,
        category,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
        this
        .promoCategoryOutboundService.findPromoCategoryFilterPaginated(mandatoryRequest,
            category, page, size,
            columnSort, sortDirection)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);

          LOGGER.info("findPromoCategoryFilterPaginated response : "
                  + "findPromoCategoryFilterPaginated-result {}, ",
              restResponsePageGatewayBaseResponse);

          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<PromoCategoryResponse>> createPromoCategory(MandatoryRequest mandatoryRequest,
      PromoCategoryRequest promoCategoryRequest, String privilegeToCheck, SessionData sessionData) {
    LOGGER.info("createPromoCategory request = "
            + "createPromoCategory-mandatoryRequest {}, "
            + "promoCategoryRequest {}, "
            + "createPromoCategory-privilegeToCheck {}", mandatoryRequest,
        promoCategoryRequest,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
        this
        .promoCategoryOutboundService.createPromoCategory(mandatoryRequest, promoCategoryRequest)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);

          LOGGER.info("createPromoCategory response : "
                  + "createPromoCategory-result {} ", restResponsePageGatewayBaseResponse);

          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCategoryResponse>> updatePromoCategory(
      MandatoryRequest mandatoryRequest,
      PromoCategoryRequest promoCategoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updatePromoCategory request = "
            + "updatePromoCategory-mandatoryRequest {}, "
            + "promoCategoryRequest {}, "
            + "id {}, "
            + "updatePromoCategory-privilegeToCheck {}", mandatoryRequest,
        promoCategoryRequest, id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
        this.promoCategoryOutboundService.updatePromoCategory(mandatoryRequest, promoCategoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          LOGGER.info("updatePromoCategory response : "
                  + "updatePromoCategory-result {}, ", restResponsePageGatewayBaseResponse);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deletePromoCategory(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("deletePromoCategory request = "
            + "deletePromoCategory-mandatoryRequest {}, "
            + "deletePromoCategory-privilegeToCheck {}", mandatoryRequest,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
        this
        .promoCategoryOutboundService.deletePromoCategory(mandatoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          LOGGER.info("deletePromoCategory response : "
                  + "deletePromoCategory-result {}", restResponsePageGatewayBaseResponse);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<PromoCategoryResponse>> findPromoCategoryById(MandatoryRequest
      mandatoryRequest, String
      id, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("findPromoCategoryById request = "
            + "findPromoCategoryById-mandatoryRequest {}, "
            + "id {}, "
            + "findPromoCategoryById-privilegeToCheck {}", mandatoryRequest,id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
        this
        .promoCategoryOutboundService.findPromoCategoryById(mandatoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);

          LOGGER.info("findPromoCategoryById response : "
                  + "findPromoCategoryById-result {}", restResponsePageGatewayBaseResponse);

          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getCategories(MandatoryRequest
      mandatoryRequest, String lang, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("getCategories request = "
            + "getCategories-mandatoryRequest {}, "
            + "lang {}, "
            + "getCategories-privilegeToCheck {}", mandatoryRequest,lang,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
            this
                .promoCategoryOutboundService.getCategories(mandatoryRequest, lang)).flatMap
        (restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);

          LOGGER.info("getCategories response : "
                  + "getCategories-result {}", restResponsePageGatewayBaseResponse);

          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<Map<String, Object>>>> getListCategories(MandatoryRequest
      mandatoryRequest, String lang, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("getListCategories request = "
            + "getListCategories-mandatoryRequest {}, "
            + "lang {}, "
            + "getListCategories-privilegeToCheck {}", mandatoryRequest,lang,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PROMO_CATEGORY, privilegeToCheck).flatMap
        (authorized ->
            this
                .promoCategoryOutboundService.getListCategories(mandatoryRequest, lang)).flatMap
        (restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);

          LOGGER.info("getListCategories response : "
                  + "getListCategories-result {}", restResponsePageGatewayBaseResponse);

          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
