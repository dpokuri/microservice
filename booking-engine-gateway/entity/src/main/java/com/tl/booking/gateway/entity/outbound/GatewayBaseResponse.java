package com.tl.booking.gateway.entity.outbound;

import com.tl.booking.common.rest.web.model.response.BaseResponse;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class GatewayBaseResponse<T> extends BaseResponse<T> {
  private SessionData sessionData;

  private List<PrivilegeResponse> privileges;

  public SessionData getSessionData() {
    return sessionData;
  }

  public void setSessionData(SessionData sessionData) {
    this.sessionData = sessionData;
  }

  public List<PrivilegeResponse> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(
      List<PrivilegeResponse> privileges) {
    this.privileges = privileges;
  }

  @Override
  public String toString() {
    return "GatewayBaseResponse{" +
        "sessionData=" + sessionData +
        ", privileges=" + privileges +
        '}';
  }
}
