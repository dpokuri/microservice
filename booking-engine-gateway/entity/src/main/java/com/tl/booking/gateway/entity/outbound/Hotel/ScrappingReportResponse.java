package com.tl.booking.gateway.entity.outbound.Hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScrappingReportResponse extends CommonModel {

  private String hotelId;

  private String hotelName;

  private PicRegionDetail region;

  private List<ScrappingReportDetail> priceList;

  private ContactPerson contactPerson;

  private String lastScrappingDate;

  public String getLastScrappingDate() {
    return lastScrappingDate;
  }

  public void setLastScrappingDate(String lastScrappingDate) {
    this.lastScrappingDate = lastScrappingDate;
  }

  public ContactPerson getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(ContactPerson contactPerson) {
    this.contactPerson = contactPerson;
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

  public PicRegionDetail getRegion() {
    return region;
  }

  public void setRegion(PicRegionDetail region) {
    this.region = region;
  }

  public List<ScrappingReportDetail> getPriceList() {
    return priceList;
  }

  public void setPriceList(
      List<ScrappingReportDetail> priceList) {
    this.priceList = priceList;
  }

  @Override
  public String toString() {
    return "ScrappingReportResponse{" +
        "hotelId='" + hotelId + '\'' +
        ", hotelName='" + hotelName + '\'' +
        ", region=" + region +
        ", priceList=" + priceList +
        ", contactPerson=" + contactPerson +
        ", lastScrappingDate=" + lastScrappingDate +
        '}';
  }
}
