package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class LabelPromo extends CommonModel {

  private String en;
  private String id;

  public LabelPromo() {
  }

  public LabelPromo(String en, String id) {
    this.en = en;
    this.id = id;
  }

  public String getEn() {
    return en;
  }

  public void setEn(String en) {
    this.en = en;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "LabelPromo{" +
        "en='" + en + '\'' +
        ", id='" + id + '\'' +
        '}';
  }
}
