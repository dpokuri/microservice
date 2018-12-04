package com.tl.booking.gateway.outbound.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import io.reactivex.Single;

@FunctionalInterface
public interface ImageOutboundService {

  Single<GatewayBaseResponse<ImagesResponse>> createImages(
      MandatoryRequest mandatoryRequest,
      ImagesRequest imagesRequest);
}
