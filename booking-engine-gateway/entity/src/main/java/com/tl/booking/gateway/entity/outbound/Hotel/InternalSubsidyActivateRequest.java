package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class InternalSubsidyActivateRequest extends CommonModel {

  private Integer active;

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "InternalSubsidyActivateRequest{" +
        "active=" + active +
        '}';
  }
}
