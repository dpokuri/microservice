package com.tl.booking.gateway.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;
import com.tl.booking.gateway.entity.outbound.SessionData;

import io.reactivex.Single;

public interface HolidayService {
  Single<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> findHolidayFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String startDate,
      String endDate,
      String lang,
      String content,
      Integer page,
      Integer size,
      HolidayColumn columnSort,
      SortDirection sortDirection,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<HolidayResponse>> createHoliday(
      MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<HolidayResponse>> updateHoliday(
      MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest,
      String id,
      String privilegeToCheck,
      SessionData sessionData
  );

  Single<GatewayBaseResponse<Boolean>> deleteHoliday(MandatoryRequest mandatoryRequest, String id, String privilegeToCheck,
      SessionData sessionData);

  Single<GatewayBaseResponse<HolidayResponse>> findHolidayById(MandatoryRequest mandatoryRequest, String
      id, String privilegeToCheck,
      SessionData sessionData);
}
