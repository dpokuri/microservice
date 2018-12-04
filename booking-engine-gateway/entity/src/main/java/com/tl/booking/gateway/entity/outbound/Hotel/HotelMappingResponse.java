package com.tl.booking.gateway.entity.outbound.Hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import java.util.Map;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelMappingResponse extends CommonModel {
  private String id;

  private String name;

  private String regionId;

  private Map<String, String> region;

  private ContactPerson contactPerson;

  private List<HotelMappingDetail> hotels;

  private int active;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public Map<String, String> getRegion() {
    return region;
  }

  public void setRegion(Map<String, String> region) {
    this.region = region;
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
    return "HotelMappingResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", regionId='" + regionId + '\'' +
        ", region=" + region +
        ", contactPerson=" + contactPerson +
        ", hotels=" + hotels +
        ", active=" + active +
        '}';
  }
}
