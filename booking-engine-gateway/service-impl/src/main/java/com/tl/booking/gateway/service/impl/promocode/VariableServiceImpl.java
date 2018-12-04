package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.VariableOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.VariableService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.constant.enums.VariableColumn;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableFindAllResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VariableServiceImpl implements VariableService {

  @Autowired
  private VariableOutboundService variableOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(VariableServiceImpl.class);


  public Single<GatewayBaseResponse<VariableResponse>> findVariableById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("findVariableById Request mandatoryRequest, id, privilegeToCheck, sessionData", mandatoryRequest, id, privilegeToCheck, sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_VARIABLE, privilegeToCheck)
        .flatMap
            (authorized ->
                this.variableOutboundService.findVariableById(mandatoryRequest, id))
        .flatMap
            (restResponseCodeGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
                  restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("findVariableById response = result", restResponseCodeGatewayBaseResponse);
                  return restResponseCodeGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<VariableResponse>>> findVariableFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String param,
      String inputType,
      Integer page,
      Integer size,
      VariableColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findVariableFilterPaginated request = findPromoPageFilterPaginated-mandatoryRequest, param, inputType, page, size, columnSort, sortDirection, privilegeToCheck, sessionData",
        mandatoryRequest,
        page,
        inputType,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_VARIABLE, privilegeToCheck)
        .flatMap(authorized
            ->
            this
                .variableOutboundService.findVariableFilterPaginated(mandatoryRequest,
                param, inputType, page, size,
                columnSort, sortDirection, privilegeToCheck))
        .flatMap(restResponseCodeGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
              restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findVariableFilterPaginated response = findVariableFilterPaginated-result ",
                  restResponseCodeGatewayBaseResponse);

              return restResponseCodeGatewayBaseResponse;
            }))
        .subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<VariableResponse>> createVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("createVariable request = mandatoryRequest, promoPageRequest, privilegeToCheck, sessionData ",
        mandatoryRequest,
        promoCodeRequest,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_VARIABLE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .variableOutboundService.createVariable(mandatoryRequest, promoCodeRequest))
        .flatMap
            (restResponsePageGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponsePageGatewayBaseResponse.setPrivileges(privileges);
                  restResponsePageGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("createVariable response = result", restResponsePageGatewayBaseResponse);
                  return restResponsePageGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<VariableResponse>> updateVariable(
      MandatoryRequest mandatoryRequest,
      VariableRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateVariable request = mandatoryRequest, promoCodeRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        promoCodeRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_VARIABLE, privilegeToCheck)
        .flatMap
            (authorized ->
                this.variableOutboundService
                    .updateVariable(mandatoryRequest, promoCodeRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateVariable response = result ", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteVariable(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("deleteVariable request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_VARIABLE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .variableOutboundService.deleteVariable(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("deleteVariable response = deleteVariable-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<List<VariableFindAllResponse>>> findAllVariable(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findAllVariable request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_ALL_VARIABLE, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .variableOutboundService.findAllVariable(mandatoryRequest))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findAllVariable response = findAllVariable-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
