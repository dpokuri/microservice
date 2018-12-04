package com.tl.booking.gateway.outbound.impl.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.libraries.utility.MandatoryRequestHelper;
import com.tl.booking.gateway.outbound.api.hotel.HotelAutoCompleteOutboundService;
import com.tl.booking.gateway.outbound.impl.configuration.HotelScrappingEndPointService;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;

import io.reactivex.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class HotelAutoCompleteOutboundServiceImpl implements HotelAutoCompleteOutboundService {

  @Autowired
  private HotelScrappingEndPointService hotelScrappingEndPointService;

  @Override
  public Single<GatewayBaseResponse<List<Map<String, String>>>> getAutoCompleteHotel(
      MandatoryRequest mandatoryRequest, String otaId, String q) {

    Map<String, String> queryParam = new HashMap<>();
    queryParam.put("otaId", otaId);
    queryParam.put("q", q);

    return Single.create(singleEmitter -> {
      try {
        Response<GatewayBaseResponse<List<Map<String, String>>>> autoCOmpleteResponse = this.hotelScrappingEndPointService
            .getAutoCompleteHotel
                (MandatoryRequestHelper.buildMandatoryRequestHeader(mandatoryRequest), queryParam).execute();
        GatewayBaseResponse<List<Map<String, String>>> success = autoCOmpleteResponse.body();

        singleEmitter.onSuccess(success);
      } catch (Exception e){
        singleEmitter.onError(e);
      }
    });
  }
}
