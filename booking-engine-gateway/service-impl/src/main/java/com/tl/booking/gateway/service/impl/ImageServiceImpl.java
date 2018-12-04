package com.tl.booking.gateway.service.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.outbound.api.ImageOutboundService;
import com.tl.booking.gateway.service.api.ImageService;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.entity.constant.PrivilegeId;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageOutboundService imageOutboundService;

  @Autowired
  private PrivilegeService privilegeService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

  @Override
  public Single<GatewayBaseResponse<ImagesResponse>> createImages(MandatoryRequest mandatoryRequest,
      ImagesRequest imagesRequest, String privilegeToCheck, SessionData sessionData) {

    LOGGER.info("createImages request = mandatoryRequest {}, imagesRequest {}, "
        + "privilegeToCheck {}", mandatoryRequest, imagesRequest, privilegeToCheck);

    return this.privilegeService
        .checkAuthorized(PrivilegeId.CREATE_IMAGES, privilegeToCheck)
        .flatMap
        (authorized -> this.imageOutboundService
            .createImages(mandatoryRequest, imagesRequest)).flatMap
        (restResponsePageGatewayBaseResponse -> this
        .privilegeService.getAuthorizedPrivileges(mandatoryRequest, privilegeToCheck)
        .map(privileges -> {
          restResponsePageGatewayBaseResponse.setPrivileges(privileges);
          restResponsePageGatewayBaseResponse.setSessionData(sessionData);

          LOGGER.info("createImages response : result {}", restResponsePageGatewayBaseResponse);

          return restResponsePageGatewayBaseResponse;
        })).subscribeOn(Schedulers.io());
  }
}
