package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelMappingDetail extends CommonModel {
  private String otaId;

  private Map<String, String> ota;

  private String hotelId;

  private String hotelName;

  private String location;

  public String getOtaId() {
    return otaId;
  }

  public void setOtaId(String otaId) {
    this.otaId = otaId;
  }

  public Map<String, String> getOta() {
    return ota;
  }

  public void setOta(Map<String, String> ota) {
    this.ota = ota;
  }

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "HotelMappingDetail{" +
        "otaId='" + otaId + '\'' +
        ", ota=" + ota +
        ", hotelId='" + hotelId + '\'' +
        ", hotelName='" + hotelName + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}
