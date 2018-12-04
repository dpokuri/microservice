package com.tl.booking.gateway.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.HolidayOutboundService;
import com.tl.booking.gateway.service.api.HolidayService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService{

  @Autowired
  private HolidayOutboundService holidayOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(HolidayServiceImpl.class);

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> findHolidayFilterPaginated
      (MandatoryRequest
      mandatoryRequest,
      String startDate, String endDate, String lang, String content, Integer page, Integer size,
      HolidayColumn columnSort, SortDirection sortDirection, String privilegeToCheck,
          SessionData sessionData) {

    LOGGER.info("findHolidayFilterPaginated request = statDate {}, endDate {}, lang {}, content {},"
            + " page {}, size {}, columnSort {}, sortDirection {}, "
            + "findHolidayFilterPaginated-privilegeToCheck {}",
        startDate, endDate, lang, content, page, size, columnSort, sortDirection, privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, privilegeToCheck).flatMap(authorized -> this
        .holidayOutboundService.findHolidayFilterPaginated(mandatoryRequest,
        startDate, endDate, lang, content, page, size,
        columnSort, sortDirection)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HolidayResponse>> createHoliday(MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest, String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("findHolidayFilterPaginated request = mandatoryRequest {}, holidayRequest {}, "
            + "createHoliday-privilegeToCheck {}",
        mandatoryRequest, holidayRequest, privilegeToCheck);
    return this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, privilegeToCheck).flatMap(authorized -> this
        .holidayOutboundService.createHoliday(mandatoryRequest, holidayRequest)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HolidayResponse>> updateHoliday(MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest, String id, String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("updateHoliday request = mandatoryRequest {}, holidayRequest {}, id {}, "
        + "updateHoliday-privilegeToCheck {}", mandatoryRequest, holidayRequest, id,
        privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, privilegeToCheck).flatMap(authorized ->
        this.holidayOutboundService.updateHoliday(mandatoryRequest, holidayRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteHoliday(MandatoryRequest mandatoryRequest, String id,
      String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("deleteHoliday request = mandatoryRequest {}, id {}, "
        + "deleteHoliday-privilegeToCheck {}", mandatoryRequest, id, privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, privilegeToCheck).flatMap(authorized -> this
        .holidayOutboundService.deleteHoliday(mandatoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HolidayResponse>> findHolidayById(MandatoryRequest mandatoryRequest, String
      id, String privilegeToCheck,
      SessionData sessionData) {

    LOGGER.info("findHolidayById request = mandatoryRequest {}, id {}, "
        + "findHolidayById-privilegeToCheck {}", mandatoryRequest, id, privilegeToCheck);

    return this.privilegeService.checkAuthorized(PrivilegeId.HOLIDAY, privilegeToCheck).flatMap(authorized -> this
        .holidayOutboundService.findHolidayById(mandatoryRequest, id)).flatMap(restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);
          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
