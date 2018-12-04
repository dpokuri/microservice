package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.CardTypeResponse;

import io.reactivex.Single;
import java.util.List;

public interface CardTypeOutboundService {

  Single<GatewayBaseResponse<List<CardTypeResponse>>> findAll(MandatoryRequest mandatoryRequest);

}
