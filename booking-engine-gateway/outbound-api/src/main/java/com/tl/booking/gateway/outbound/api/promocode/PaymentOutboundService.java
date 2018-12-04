package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.PaymentResponse;

import io.reactivex.Single;
import java.util.List;

public interface PaymentOutboundService {


  Single<GatewayBaseResponse<List<PaymentResponse>>> findAll(MandatoryRequest mandatoryRequest);

}
