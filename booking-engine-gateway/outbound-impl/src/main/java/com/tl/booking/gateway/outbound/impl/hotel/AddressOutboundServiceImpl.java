package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.AddressOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelSubsidyEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;

import io.reactivex.Single;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class AddressOutboundServiceImpl implements AddressOutboundService {

  private final HotelSubsidyEndPointService hotelSubsidyEndPointService;

  @Autowired
  public AddressOutboundServiceImpl(HotelSubsidyEndPointService hotelSubsidyEndPointService) {
    this.hotelSubsidyEndPointService = hotelSubsidyEndPointService;
  }

  @Override
  public Single<GatewayBaseResponse<List<Address>>> getCountry(
      MandatoryRequest mandatoryRequest) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Address>>>
            addressResponse = hotelSubsidyEndPointService.callGetCountry(
            MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest)).execute();

        GatewayBaseResponse<List<Address>> success = addressResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        e.printStackTrace();
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<List<Address>>> getProvince(
      MandatoryRequest mandatoryRequest, String countryId) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Address>>>
            addressResponse = hotelSubsidyEndPointService.callGetProvince(
            MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), countryId)
            .execute();

        GatewayBaseResponse<List<Address>> success = addressResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<List<Address>>> getCity(
      MandatoryRequest mandatoryRequest, String provinceId) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Address>>>
            addressResponse = hotelSubsidyEndPointService.callGetCity(
            MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), provinceId)
            .execute();

        GatewayBaseResponse<List<Address>> success = addressResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }

  @Override
  public Single<GatewayBaseResponse<List<Address>>> getArea(
      MandatoryRequest mandatoryRequest, String cityId) {

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Address>>>
            addressResponse = hotelSubsidyEndPointService.callGetArea(
            MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), cityId)
            .execute();

        GatewayBaseResponse<List<Address>> success = addressResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    });
  }
}
