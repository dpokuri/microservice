package com.tl.booking.gateway.outbound.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;

import io.reactivex.Single;
import java.util.List;

public interface BinNumberOutboundService {


  Single<GatewayBaseResponse<BinNumberResponse>> findBinNumberById(
      MandatoryRequest mandatoryRequest,
      String id);


  Single<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> findBinNumberFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String binNumber,
      Integer page,
      Integer size,
      BinNumberColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck
  );


  Single<GatewayBaseResponse<BinNumberResponse>> createBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest promoCodeRequest);

  Single<GatewayBaseResponse<BinNumberResponse>> updateBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest promoCodeRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deleteBinNumber(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<List<String>>> binNumber(MandatoryRequest mandatoryRequest,
      String binNumber, String bankId, String cardTypeId);


}
