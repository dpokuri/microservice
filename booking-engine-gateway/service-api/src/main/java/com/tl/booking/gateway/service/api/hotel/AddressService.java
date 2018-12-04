package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.Address;

import io.reactivex.Single;
import java.util.List;

public interface AddressService {

  Single<GatewayBaseResponse<List<Address>>> getCountry(
      MandatoryRequest mandatoryRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<List<Address>>> getProvince(
      MandatoryRequest mandatoryRequest, String countryId, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<List<Address>>> getCity(
      MandatoryRequest mandatoryRequest, String provinceId, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<List<Address>>> getArea(
      MandatoryRequest mandatoryRequest, String cityId, String privilegeToCheck, SessionData sessionData);
}
