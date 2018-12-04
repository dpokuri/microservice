package com.tl.booking.gateway.entity.outbound;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PrivilegeResponse extends CommonModel implements Serializable {
  private String privilegeId;
  private String privilegeName;

  public String getPrivilegeId() {
    return privilegeId;
  }

  public void setPrivilegeId(String privilegeId) {
    this.privilegeId = privilegeId;
  }

  public String getPrivilegeName() {
    return privilegeName;
  }

  public void setPrivilegeName(String privilegeName) {
    this.privilegeName = privilegeName;
  }

  @Override
  public String toString() {
    return "PrivilegeResponse{" +
        "privilegeId='" + privilegeId + '\'' +
        ", privilegeName='" + privilegeName + '\'' +
        '}';
  }
}

