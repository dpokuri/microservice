package com.tl.booking.gateway.service.api;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;

import io.reactivex.Single;
import java.util.List;

public interface PrivilegeService {
  Single<Boolean> checkAuthorized(String privilege, String privilegeToCheck);

  Single<List<PrivilegeResponse>> getAuthorizedPrivileges(MandatoryRequest mandatoryRequest, String
      privilegeToCheck);
}
