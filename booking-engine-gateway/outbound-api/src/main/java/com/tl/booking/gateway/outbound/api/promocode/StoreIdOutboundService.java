package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;

import io.reactivex.Single;
import java.util.List;

public interface StoreIdOutboundService {


  Single<GatewayBaseResponse<List<StoreIdResponse>>> findAll(MandatoryRequest mandatoryRequest);


}
