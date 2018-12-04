package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.StoreIdOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.StoreIdResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class StoreIdOutboundServiceImpl implements StoreIdOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  public Single<GatewayBaseResponse<List<StoreIdResponse>>> findAll(MandatoryRequest mandatoryRequest)
  {
    return Single.<GatewayBaseResponse<List<StoreIdResponse>>>create(singleEmitter -> {
      try {

        Response<GatewayBaseResponse<List<StoreIdResponse>>> promoCodeResponse = this.promoCodeEndPointService
            .findAllStoreId
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest))
            .execute();
        GatewayBaseResponse<List<StoreIdResponse>> success = promoCodeResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

}
