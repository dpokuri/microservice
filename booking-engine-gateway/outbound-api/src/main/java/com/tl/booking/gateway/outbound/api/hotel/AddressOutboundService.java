package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;

import io.reactivex.Single;
import java.util.List;

public interface AddressOutboundService {

  Single<GatewayBaseResponse<List<Address>>> getCountry(MandatoryRequest mandatoryRequest);

  Single<GatewayBaseResponse<List<Address>>> getProvince(MandatoryRequest mandatoryRequest,
      String countryId);

  Single<GatewayBaseResponse<List<Address>>> getCity(MandatoryRequest mandatoryRequest, String provinceId);

  Single<GatewayBaseResponse<List<Address>>> getArea(MandatoryRequest mandatoryRequest, String cityId);
}
