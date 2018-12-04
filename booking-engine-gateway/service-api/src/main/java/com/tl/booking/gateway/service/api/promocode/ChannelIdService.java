package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.ChannelIdResponse;

import io.reactivex.Single;
import java.util.List;

public interface ChannelIdService {

  Single<GatewayBaseResponse<List<ChannelIdResponse>>> findAllChannelId(
      MandatoryRequest mandatoryRequest,
      String privilegeToCheck,
      SessionData sessionData
  );



}