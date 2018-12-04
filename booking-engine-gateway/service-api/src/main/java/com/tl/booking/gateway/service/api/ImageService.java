package com.tl.booking.gateway.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import io.reactivex.Single;

@FunctionalInterface
public interface ImageService {
  Single<GatewayBaseResponse<ImagesResponse>> createImages(
      MandatoryRequest mandatoryRequest,
      ImagesRequest imagesRequest,
      String privilegeToCheck,
      SessionData sessionData
  );
}
