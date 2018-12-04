package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.InternalSubsidyOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelSubsidyEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

import io.reactivex.Single;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class InternalSubsidyOutboundServiceImpl implements InternalSubsidyOutboundService {

  @Autowired
  private HotelSubsidyEndPointService hotelSubsidyEndPointService;

  @Override
  public Single<GatewayBaseResponse> createInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyRequest internalSubsidyRequest) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> internalSubsidyResponse = this.hotelSubsidyEndPointService
            .createInternalSubsidy
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    internalSubsidyRequest).execute();

        GatewayBaseResponse success = internalSubsidyResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> updateInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyRequest internalSubsidyRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> internalSubsidyResponse = this.hotelSubsidyEndPointService
            .updateInternalSubsidy(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                internalSubsidyRequest, id).execute();

        GatewayBaseResponse success = internalSubsidyResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> setActiveInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyActivateRequest internalSubsidyActivateRequest, String id) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> internalSubsidyResponse = this.hotelSubsidyEndPointService
            .setActiveInternalSubsidy(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                internalSubsidyActivateRequest, id).execute();

        GatewayBaseResponse success = internalSubsidyResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> deleteInternalSubsidy(MandatoryRequest mandatoryRequest, String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> internalSubsidyResponse = this.hotelSubsidyEndPointService
            .deleteInternalSubsidy(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                id).execute();

        GatewayBaseResponse success = internalSubsidyResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse> findInternalSubsidyByStoreIdAndId(MandatoryRequest mandatoryRequest,
      String id) {
    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse> internalSubsidyResponse = this.hotelSubsidyEndPointService
            .findInternalSubsidyByStoreIdAndId(
                MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                id).execute();

        GatewayBaseResponse success = internalSubsidyResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> findInternalSubsidyByStoreId(
      MandatoryRequest mandatoryRequest, Map<String, String> query
  ) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> internalSubsidyResponse =
            this.hotelSubsidyEndPointService
                .findInternalSubsidyByStoreId(
                    MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest),
                    query).execute();

        GatewayBaseResponse success = internalSubsidyResponse.body();
        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
