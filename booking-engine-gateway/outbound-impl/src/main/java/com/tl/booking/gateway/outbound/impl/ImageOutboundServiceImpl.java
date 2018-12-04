package com.tl.booking.gateway.outbound.impl;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.ImageOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.ImageEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class ImageOutboundServiceImpl implements ImageOutboundService {

  @Autowired
  private ImageEndPointService imageEndPointService;

  @Autowired
  private Tracer tracer;

  @Override
  public Single<GatewayBaseResponse<ImagesResponse>> createImages(
      MandatoryRequest mandatoryRequest,
      ImagesRequest imagesRequest) {


    return Single.<GatewayBaseResponse<ImagesResponse>>create(singleEmitter -> {
      try {
        
        Response<GatewayBaseResponse<ImagesResponse>> imageResponse = this
            .imageEndPointService.createImages
            (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), imagesRequest)
            .execute();
        GatewayBaseResponse<ImagesResponse> promoPage = imageResponse.body();

        singleEmitter.onSuccess(promoPage);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }
}
