package com.tl.booking.gateway.outbound.impl.configuration;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.Image.ImagesRequest;
import com.tl.booking.gateway.entity.outbound.Image.ImagesResponse;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ImageEndPointService {
   @POST("/tix-image/imagesFromUrl")
  Call<GatewayBaseResponse<ImagesResponse>> createImages(@HeaderMap Map<String, String> header,
      @Body ImagesRequest imagesRequest);
}
