package com.tl.booking.gateway.entity.outbound;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class SessionData extends CommonModel implements Serializable {
  private String username;
  private String businessId;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getBusinessId() {
    return businessId;
  }

  public void setBusinessId(String businessId) {
    this.businessId = businessId;
  }

  @Override
  public String toString() {
    return "SessionData{" +
        "username='" + username + '\'' +
        ", businessId='" + businessId + '\'' +
        '}';
  }
}
