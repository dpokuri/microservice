package com.tl.booking.gateway.outbound.impl.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.promocode.VariableSourceOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.PromoCodeEndPointService;
import com.tl.booking.gateway.entity.constant.fields.VariableSourceFields;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.PromoCode.VariableSourceResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class VariableSourceOutboundServiceImpl implements VariableSourceOutboundService {

  @Autowired
  private PromoCodeEndPointService promoCodeEndPointService;

  public Single<GatewayBaseResponse<List<VariableSourceResponse>>> findAll(MandatoryRequest mandatoryRequest,
      String sourceType, String key)
  {
    return Single.<GatewayBaseResponse<List<VariableSourceResponse>>>create(singleEmitter -> {
      try {

        Map<String, String> queryParam = new HashMap<>();

        if (isNotNullString(key)) {
          queryParam.put(VariableSourceFields.KEY, key);
        }


        Response<GatewayBaseResponse<List<VariableSourceResponse>>> response = this.promoCodeEndPointService
            .findVariableSource
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), sourceType, queryParam)
            .execute();
        GatewayBaseResponse<List<VariableSourceResponse>> success = response.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e) {
        singleEmitter.onError(e);
      }
    }).subscribeOn(Schedulers.io());
  }

  private Boolean isNotNullString(String string) {
    return string != null;
  }

}
