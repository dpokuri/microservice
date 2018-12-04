package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.AdditionalDiscountResponse;

import io.reactivex.Single;

public interface AdditionalDiscountOutboundService {

  Single<GatewayBaseResponse> createAdditionalDiscount(MandatoryRequest mandatoryRequest,
      AdditionalDiscountRequest additionalDiscountRequest);

  Single<GatewayBaseResponse> updateAdditionalDiscount(MandatoryRequest mandatoryRequest,
      AdditionalDiscountRequest additionalDiscountRequest, String id);

  Single<GatewayBaseResponse> deleteAdditionalDiscount(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse> findAdditionalDiscountByHotel(MandatoryRequest mandatoryRequest,
      Integer hotelId, String type);

  Single<GatewayBaseResponse> findAdditionalDiscountById(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse<RestResponsePage<AdditionalDiscountResponse>>> findAdditionalDiscount(
      MandatoryRequest mandatoryRequest, Integer page, Integer limit, String type, String hotelId);

}
