package com.tl.booking.gateway.outbound.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.enums.HolidayColumn;
import com.tl.booking.gateway.entity.constant.enums.SortDirection;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.HolidayRequest;
import com.tl.booking.gateway.entity.outbound.HolidayResponse;
import com.tl.booking.gateway.entity.outbound.RestResponsePage;

import io.reactivex.Single;
import org.springframework.stereotype.Service;

@Service
public interface HolidayOutboundService {

  Single<GatewayBaseResponse<RestResponsePage<HolidayResponse>>> findHolidayFilterPaginated(
      MandatoryRequest mandatoryRequest,
      String startDate,
      String endDate,
      String lang,
      String content,
      Integer page,
      Integer size,
      HolidayColumn columnSort,
      SortDirection sortDirection
  );

  Single<GatewayBaseResponse<HolidayResponse>> createHoliday(MandatoryRequest mandatoryRequest, HolidayRequest holidayRequest);

  Single<GatewayBaseResponse<HolidayResponse>> updateHoliday(MandatoryRequest mandatoryRequest,
      HolidayRequest holidayRequest, String id);

  Single<GatewayBaseResponse<Boolean>> deleteHoliday(MandatoryRequest mandatoryRequest, String id);

  Single<GatewayBaseResponse<HolidayResponse>> findHolidayById(MandatoryRequest mandatoryRequest, String id);
}
