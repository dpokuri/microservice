package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelScrappingReportOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.ScrappingReportResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class HotelScrappingReportOutboundServiceImpl implements
    HotelScrappingReportOutboundService {

  @Autowired
  private HotelScrappingEndPointService hotelScrappingEndPointService;

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> getPriceReportData(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String regionId,
      String checkInDate, String scrappingDate, String q, String sort, String method) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("page", page.toString());
    queryParam.put("limit", limit.toString());
    queryParam.put("regionId", regionId);
    queryParam.put("checkInDate", checkInDate);
    queryParam.put("scrappingDate", scrappingDate);
    queryParam.put("q", q);
    queryParam.put("sort", sort);
    queryParam.put("method", method);

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>>> scrappingReportResponse = this
            .hotelScrappingEndPointService
            .getPriceReportData
                (
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    queryParam)
            .execute();
        GatewayBaseResponse<RestResponsePage<ScrappingReportResponse>> success = scrappingReportResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<Boolean>> getEmailReport(MandatoryRequest mandatoryRequest,
      String regionId, String q, String checkInDate, String scrappingDate, String email) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("regionId", regionId);
    queryParam.put("checkInDate", checkInDate);
    queryParam.put("scrappingDate", scrappingDate);
    queryParam.put("q", q);
    queryParam.put("email", email);

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<Boolean>> regionMappingResponse = this.hotelScrappingEndPointService
            .getEmailReport
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), queryParam).execute();
        GatewayBaseResponse<Boolean> success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<String>> exportReport(MandatoryRequest mandatoryRequest,
      String regionId, String q, String checkInDate, String scrappingDate) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("regionId", regionId);
    queryParam.put("q", q);
    queryParam.put("checkInDate", checkInDate);
    queryParam.put("scrappingDate", scrappingDate);

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<String>> regionMappingResponse = this.hotelScrappingEndPointService
            .exportReport
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), queryParam).execute();
        GatewayBaseResponse<String> success = regionMappingResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }
}
