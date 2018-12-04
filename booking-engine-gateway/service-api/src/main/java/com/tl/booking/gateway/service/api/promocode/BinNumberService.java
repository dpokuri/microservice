package com.tl.booking.gateway.service.api.promocode;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.BinNumberColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberRequest;
import com.tl.booking.gateway.entity.outbound.PromoCode.BinNumberResponse;

import io.reactivex.Single;
import java.util.List;

public interface BinNumberService {

  Single<GatewayBaseResponse<BinNumberResponse>> findBinNumberById(
      MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);


  Single<GatewayBaseResponse<RestResponsePage<BinNumberResponse>>> findBinNumberFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String binNumber,
      Integer page,
      Integer size,
      BinNumberColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<BinNumberResponse>> createBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest binNumberRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<BinNumberResponse>> updateBinNumber(
      MandatoryRequest mandatoryRequest,
      BinNumberRequest binNumberRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteBinNumber(
      MandatoryRequest mandatoryRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<List<String>>> binNumber(
      MandatoryRequest mandatoryRequest,
      String binNumber,
      String bankId,
      String cardTypeId,
      String privilegeToCheck,
      SessionData sessionData
  );



}
