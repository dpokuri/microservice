package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelMappingRequest extends CommonModel {

  private String name;

  private String regionId;

  private ContactPerson contactPerson;

  private List<HotelMappingDetail> hotels;

  private int active;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public ContactPerson getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(ContactPerson contactPerson) {
    this.contactPerson = contactPerson;
  }

  public List<HotelMappingDetail> getHotels() {
    return hotels;
  }

  public void setHotels(
      List<HotelMappingDetail> hotels) {
    this.hotels = hotels;
  }

  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "HotelMappingRequest{" +
        "name='" + name + '\'' +
        ", regionId='" + regionId + '\'' +
        ", contactPerson=" + contactPerson +
        ", hotels=" + hotels +
        ", active=" + active +
        '}';
  }
}
