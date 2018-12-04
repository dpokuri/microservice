package com.tl.booking.gateway.outbound.api.hotel;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyActivateRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyRequest;
import com.tl.booking.gateway.entity.outbound.Hotel.InternalSubsidyResponse;

import io.reactivex.Single;
import java.util.Map;

public interface InternalSubsidyOutboundService {

  Single<GatewayBaseResponse> createInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyRequest internalSubsidyRequest);

  Single<GatewayBaseResponse> updateInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyRequest internalSubsidyRequest, String id);

  Single<GatewayBaseResponse> setActiveInternalSubsidy(MandatoryRequest mandatoryRequest,
      InternalSubsidyActivateRequest internalSubsidyActivateRequest, String id);

  Single<GatewayBaseResponse> deleteInternalSubsidy(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse> findInternalSubsidyByStoreIdAndId(MandatoryRequest mandatoryRequest,
      String id);

  Single<GatewayBaseResponse<RestResponsePage<InternalSubsidyResponse>>> findInternalSubsidyByStoreId(
      MandatoryRequest mandatoryRequest, Map<String, String> query
  );
}
