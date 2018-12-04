package com.tl.booking.gateway.outbound.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import io.reactivex.Single;
import org.springframework.stereotype.Service;

@Service
public interface GatewayOutboundService {

  Single<Object> forwardRequestGetAll(MandatoryRequest mandatoryRequest, String endPoint);

  Single<Object> forwardRequestGet(MandatoryRequest mandatoryRequest, String endPoint, String param);
  Single<Object> forwardRequestPost(MandatoryRequest mandatoryRequest, String endPoint, Object
      object);
  Single<Object> forwardRequestPut(MandatoryRequest mandatoryRequest, String endPoint, String param,
      Object object);

  Single<Object> forwardRequestDelete(MandatoryRequest mandatoryRequest, String endPoint, String
      param);
}
