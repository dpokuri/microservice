package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.ProductTypeOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.ProductTypeService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.ProductTypeColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.ProductTypeResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

  @Autowired
  private ProductTypeOutboundService productTypeOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeServiceImpl.class);


  public Single<GatewayBaseResponse<ProductTypeResponse>> findProductTypeById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("findProductTypeById Request mandatoryRequest, id, privilegeToCheck, sessionData", mandatoryRequest, id, privilegeToCheck, sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_PRODUCT_TYPE, privilegeToCheck)
        .flatMap
            (authorized ->
                this.productTypeOutboundService.findProductTypeById(mandatoryRequest, id))
        .flatMap
            (restResponseCodeGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
                  restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("findProductTypeById response = "
                      + " result", restResponseCodeGatewayBaseResponse);
                  return restResponseCodeGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<ProductTypeResponse>>> findProductTypeFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      Integer page,
      Integer size,
      ProductTypeColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findProductTypeFilterPaginated request = param, page, size, columnSort, sortDirection, privilegeToCheck, sessionData",
        mandatoryRequest,
        param,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_PRODUCT_TYPE, privilegeToCheck)
        .flatMap(authorized
            ->
            this
                .productTypeOutboundService.findProductTypeFilterPaginated(mandatoryRequest,
                param, page, size,
                columnSort, sortDirection, privilegeToCheck))
        .flatMap(restResponseCodeGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
              restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findProductTypeFilterPaginated response = findProductTypeFilterPaginated-result ",
                  restResponseCodeGatewayBaseResponse);

              return restResponseCodeGatewayBaseResponse;
            }))
        .subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<ProductTypeResponse>> createProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("createProductType request = mandatoryRequest, promoPageRequest, privilegeToCheck, sessionData ",
        mandatoryRequest,
        promoCodeRequest,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_PRODUCT_TYPE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .productTypeOutboundService.createProductType(mandatoryRequest, promoCodeRequest))
        .flatMap
            (restResponsePageGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponsePageGatewayBaseResponse.setPrivileges(privileges);
                  restResponsePageGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("createProductType response = result", restResponsePageGatewayBaseResponse);
                  return restResponsePageGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<ProductTypeResponse>> updateProductType(
      MandatoryRequest mandatoryRequest,
      ProductTypeRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateProductType request = mandatoryRequest, promoCodeRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        promoCodeRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_PRODUCT_TYPE, privilegeToCheck)
        .flatMap
            (authorized ->
                this.productTypeOutboundService
                    .updateProductType(mandatoryRequest, promoCodeRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateProductType response = result ", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteProductType(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("deleteProductType request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_PRODUCT_TYPE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .productTypeOutboundService.deleteProductType(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("deleteProductType response = deleteProductType-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<ProductTypeResponse>>> findProductTypes(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findProductType request = mandatoryRequest, sessionData, privilegeToCheck ",
        mandatoryRequest,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_PRODUCT_TYPE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .productTypeOutboundService.findProductTypes(mandatoryRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findProductType response = findProductType-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
