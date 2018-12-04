package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class ScrappingReportDetail extends CommonModel {

  private String otaId;
  private String otaName;
  private Integer price;
  private String roomName;

  public String getOtaId() {
    return otaId;
  }

  public void setOtaId(String otaId) {
    this.otaId = otaId;
  }

  public String getOtaName() {
    return otaName;
  }

  public void setOtaName(String otaName) {
    this.otaName = otaName;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  @Override
  public String toString() {
    return "ScrappingReportDetail{" +
        "otaId='" + otaId + '\'' +
        ", otaName='" + otaName + '\'' +
        ", price=" + price +
        ", roomName='" + roomName + '\'' +
        '}';
  }
}
