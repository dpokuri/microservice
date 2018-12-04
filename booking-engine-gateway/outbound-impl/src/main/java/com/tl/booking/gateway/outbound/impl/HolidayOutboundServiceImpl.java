package com.tl.booking.gateway.outbound.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.HolidayOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.CalendarEndPointService;
import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class HolidayOutboundServiceImpl implements
    HolidayOutboundService {

  @Autowired
  private CalendarEndPointService gatewayEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> findHolidayFilterPaginated(MandatoryRequest mandatoryRequest,
      String startDate, String endDate, String lang, String content, Integer page, Integer size,
      HolidayColumn columnSort, SortDirection sortDirection) {

    Map<String, String> queryParam = new HashMap<>();
    if(isExistStartDate(startDate)){
      queryParam.put("startDate", startDate);
    }
    if(isExistEndDate(endDate)){
      queryParam.put("endDate", endDate);
    }
    if(isExistLang(lang)) {
      queryParam.put("lang", lang);
    }
    if(isExistContent(content)) {
      queryParam.put("content", content);
    }
    queryParam.put("page", page.toString());
    queryParam.put("size", size.toString());
    if(isExistColumnSort(columnSort)){
      queryParam.put("columnSort", columnSort.getName());
    }
    if(isExistSortDirection(sortDirection)){
      queryParam.put("sortDirection", sortDirection.getName());
    }


    return Single.<GatewayBaseResponse<RestResponsePage<HolidayResponse>>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> holidayResponse = this
            .gatewayEndPointService
            .findHolidayFilterPaginated
                (
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();
        GatewayBaseResponse<RestResponsePage<HolidayResponse>> holidayPage = holidayResponse.body();

        singleEmitter.onSuccess(holidayPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  private boolean isExistStartDate(String startDate) {
    return startDate != null;
  }

  private boolean isExistEndDate(String endDate) {
    return endDate != null;
  }

  @Override
  public Single<GatewayBaseResponse<HolidayResponse>> createHoliday(MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest) {
    return Single.<GatewayBaseResponse<HolidayResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<HolidayResponse>> holidayResponse = this.gatewayEndPointService
            .createHoliday(MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), holidayRequest)
            .execute();
        GatewayBaseResponse<HolidayResponse> holidayPage = holidayResponse.body();

        singleEmitter.onSuccess(holidayPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HolidayResponse>> updateHoliday(MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest, String id) {
    return Single.<GatewayBaseResponse<HolidayResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<HolidayResponse>> holidayResponse = this.gatewayEndPointService
            .updateHoliday
            (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), holidayRequest, id).execute();
        GatewayBaseResponse<HolidayResponse> holidayPage = holidayResponse.body();

        singleEmitter.onSuccess(holidayPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> deleteHoliday(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<Boolean>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> holidayResponse = this.gatewayEndPointService
            .deleteHoliday
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<Boolean> success = holidayResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<GatewayBaseResponse<HolidayResponse>> findHolidayById(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.<GatewayBaseResponse<HolidayResponse>>create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<HolidayResponse>> holidayResponse = this.gatewayEndPointService
            .findHolidayById
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), id).execute();
        GatewayBaseResponse<HolidayResponse> success = holidayResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }


  private boolean isExistSortDirection(SortDirection sortDirection) {
    return sortDirection != null;
  }

  private boolean isExistContent(String content) {
    return content != null;
  }

  private boolean isExistLang(String lang) {
    return lang != null;
  }

  private boolean isExistColumnSort(HolidayColumn columnSort) {
    return columnSort != null;
  }
}
