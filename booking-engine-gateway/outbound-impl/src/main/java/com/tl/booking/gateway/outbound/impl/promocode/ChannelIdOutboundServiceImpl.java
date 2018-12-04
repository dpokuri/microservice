package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.ChannelIdOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.ChannelIdResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class ChannelIdOutboundServiceImpl implements ChannelIdOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  public Single<GatewayBaseResponse<List<ChannelIdResponse>>> findAll(MandatoryRequest mandatoryRequest)
  {
    return Single.<GatewayBaseResponse<List<ChannelIdResponse>>>create(singleEmitter -> {
      try {

        Response<GatewayBaseResponse<List<ChannelIdResponse>>> promoCodeResponse = this.promoCodeEndPointService
            .findAllChannelId
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<ChannelIdResponse>> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

}
