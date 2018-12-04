package com.tl.booking.gateway.service.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;

import io.reactivex.Single;

public interface AdditionalDiscountService {

  Single<GatewayBaseResponse> createAdditionalDiscount(MandatoryRequest mandatoryRequest,
      AdditionalDiscountRequest additionalDiscountRequest, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> updateAdditionalDiscount(MandatoryRequest mandatoryRequest,
      AdditionalDiscountRequest additionalDiscountRequest, String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> deleteAdditionalDiscount(MandatoryRequest mandatoryRequest,
      String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> findAdditionalDiscountByHotel(MandatoryRequest mandatoryRequest,
      Integer hotelId, String type, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse> findAdditionalDiscountById(MandatoryRequest mandatoryRequest,
      String id, String privilegeToCheck, SessionData sessionData);

  Single<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> findAdditionalDiscount(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String type,
      String hotelId, String privilegeToCheck, SessionData sessionData);

}
