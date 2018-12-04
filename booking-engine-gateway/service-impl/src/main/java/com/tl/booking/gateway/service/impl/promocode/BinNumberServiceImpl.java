package com.tl.booking.gateway.service.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.promocode.BinNumberOutboundService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.service.api.promocode.BinNumberService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BinNumberServiceImpl implements BinNumberService {

  @Autowired
  private BinNumberOutboundService binNumberOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(BinNumberServiceImpl.class);


  public Single<GatewayBaseResponse<BinNumberResponse>> findBinNumberById(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("findBinNumberById Request mandatoryRequest, id, privilegeToCheck, sessionData", mandatoryRequest, id, privilegeToCheck, sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_BY_ID_BIN_NUMBER, privilegeToCheck)
        .flatMap
            (authorized ->
                this.binNumberOutboundService.findBinNumberById(mandatoryRequest, id))
        .flatMap
            (restResponseCodeGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
                  restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("findBinNumberById response = result", restResponseCodeGatewayBaseResponse);
                  return restResponseCodeGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> findBinNumberFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String binNumber,
      Integer page,
      Integer size,
      BinNumberColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("findBinNumberFilterPaginated request = findPromoPageFilterPaginated-mandatoryRequest, binNumber, page, size, columnSort, sortDirection, privilegeToCheck, sessionData",
        mandatoryRequest,
        binNumber,
        page,
        size,
        columnSort,
        sortDirection,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.GET_BIN_NUMBER, privilegeToCheck)
        .flatMap(authorized
            ->
            this
                .binNumberOutboundService.findBinNumberFilterPaginated(mandatoryRequest,
                binNumber, page, size,
                columnSort, sortDirection, privilegeToCheck))
        .flatMap(restResponseCodeGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponseCodeGatewayBaseResponse.setPrivileges(privileges);
              restResponseCodeGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("findBinNumberFilterPaginated response = findBinNumberFilterPaginated-result ",
                  restResponseCodeGatewayBaseResponse);

              return restResponseCodeGatewayBaseResponse;
            }))
        .subscribeOn(Schedulers.io());
  }


  @Override
  public Single<GatewayBaseResponse<BinNumberResponse>> createBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest promoCodeRequest,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("createBinNumber request = mandatoryRequest, promoPageRequest, privilegeToCheck, sessionData ",
        mandatoryRequest,
        promoCodeRequest,
        privilegeToCheck,
        sessionData);

    return this.privilegeService.checkAuthorized(PrivilegeId.CREATE_BIN_NUMBER, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .binNumberOutboundService.createBinNumber(mandatoryRequest, promoCodeRequest))
        .flatMap
            (restResponsePageGatewayBaseResponse -> this
                .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
                .map(privileges -> {
                  restResponsePageGatewayBaseResponse.setPrivileges(privileges);
                  restResponsePageGatewayBaseResponse.setSessionData(sessionData);

                  LOGGER.info("createBinNumber response = result", restResponsePageGatewayBaseResponse);
                  return restResponsePageGatewayBaseResponse;
                })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<BinNumberResponse>> updateBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest promoCodeRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("updateBinNumber request = mandatoryRequest, promoCodeRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        promoCodeRequest,
        id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.UPDATE_BIN_NUMBER, privilegeToCheck)
        .flatMap
            (authorized ->
                this.binNumberOutboundService
                    .updateBinNumber(mandatoryRequest, promoCodeRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("updateBinNumber response = result ", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteBinNumber(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  ) {

    LOGGER.info("deleteBinNumber request = mandatoryRequest, id, sessionData, privilegeToCheck ",
        mandatoryRequest,
        id,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.DELETE_BIN_NUMBER, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .binNumberOutboundService.deleteBinNumber(mandatoryRequest, id))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("deleteBinNumber response = deleteBinNumber-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }


  public Single<GatewayBaseResponse<List<String>>> binNumber(
      MandatoryRequest mandatoryRequest,
      String binNumber,
      String bankId,
      String cardTypeId,
      String privilegeToCheck,
      SessionData sessionData
  )
  {

    LOGGER.info("binNumber request = mandatoryRequest, String binNumber, bankId, cardTypeId, privilegeToCheck, sessionData ",
        mandatoryRequest,
        binNumber,
        bankId,
        cardTypeId,
        sessionData,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.FIND_BIN_NUMBER, privilegeToCheck)
        .flatMap
            (authorized ->
                this
                    .binNumberOutboundService.binNumber(mandatoryRequest, binNumber, bankId, cardTypeId))
        .flatMap(restResponsePageGatewayBaseResponse -> this
            .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
            .map(privileges -> {
              restResponsePageGatewayBaseResponse.setPrivileges(privileges);
              restResponsePageGatewayBaseResponse.setSessionData(sessionData);

              LOGGER.info("binNumber response = binNumber-result", restResponsePageGatewayBaseResponse);

              return restResponsePageGatewayBaseResponse;
            })).subscribeOn(Schedulers.io());
  }

}
