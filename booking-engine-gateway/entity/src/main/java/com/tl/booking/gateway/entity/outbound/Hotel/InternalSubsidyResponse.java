package com.tl.booking.gateway.entity.outbound.Hotel;

import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class InternalSubsidyResponse {

  private String id;
  private String name;
  private LabelPromo Label;
  private String tierType;
  private List<PriceTier> priceTiers;
  private List<StarTier> starTiers;
  private String imgUrl;
  private String travelDateStart;
  private String travelDateEnd;
  private List<Integer> travelDays;
  private String bookingDateStart;
  private String bookingDateEnd;
  private String bookingTimeStart;
  private String bookingTimeEnd;
  private List<Integer> bookingDays;
  private List<String> blackOutDate;
  private Integer pinHotel;
  private Address country;
  private Address province;
  private Address city;
  private Address area;
  private String listType;
  private List<Integer> hotelId;
  private List<String> platform;
  private Integer priority;
  private Integer active;

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

  public LabelPromo getLabel() {
    return Label;
  }

  public void setLabel(LabelPromo label) {
    Label = label;
  }

  public String getTierType() {
    return tierType;
  }

  public void setTierType(String tierType) {
    this.tierType = tierType;
  }

  public List<PriceTier> getPriceTiers() {
    return priceTiers;
  }

  public void setPriceTiers(
      List<PriceTier> priceTiers) {
    this.priceTiers = priceTiers;
  }

  public List<StarTier> getStarTiers() {
    return starTiers;
  }

  public void setStarTiers(List<StarTier> starTiers) {
    this.starTiers = starTiers;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getTravelDateStart() {
    return travelDateStart;
  }

  public void setTravelDateStart(String travelDateStart) {
    this.travelDateStart = travelDateStart;
  }

  public String getTravelDateEnd() {
    return travelDateEnd;
  }

  public void setTravelDateEnd(String travelDateEnd) {
    this.travelDateEnd = travelDateEnd;
  }

  public List<Integer> getTravelDays() {
    return travelDays;
  }

  public void setTravelDays(List<Integer> travelDays) {
    this.travelDays = travelDays;
  }

  public String getBookingDateStart() {
    return bookingDateStart;
  }

  public void setBookingDateStart(String bookingDateStart) {
    this.bookingDateStart = bookingDateStart;
  }

  public String getBookingDateEnd() {
    return bookingDateEnd;
  }

  public void setBookingDateEnd(String bookingDateEnd) {
    this.bookingDateEnd = bookingDateEnd;
  }

  public String getBookingTimeStart() {
    return bookingTimeStart;
  }

  public void setBookingTimeStart(String bookingTimeStart) {
    this.bookingTimeStart = bookingTimeStart;
  }

  public String getBookingTimeEnd() {
    return bookingTimeEnd;
  }

  public void setBookingTimeEnd(String bookingTimeEnd) {
    this.bookingTimeEnd = bookingTimeEnd;
  }

  public List<Integer> getBookingDays() {
    return bookingDays;
  }

  public void setBookingDays(List<Integer> bookingDays) {
    this.bookingDays = bookingDays;
  }

  public List<String> getBlackOutDate() {
    return blackOutDate;
  }

  public void setBlackOutDate(List<String> blackOutDate) {
    this.blackOutDate = blackOutDate;
  }

  public Integer getPinHotel() {
    return pinHotel;
  }

  public void setPinHotel(Integer pinHotel) {
    this.pinHotel = pinHotel;
  }

  public Address getCountry() {
    return country;
  }

  public void setCountry(Address country) {
    this.country = country;
  }

  public Address getProvince() {
    return province;
  }

  public void setProvince(Address province) {
    this.province = province;
  }

  public Address getCity() {
    return city;
  }

  public void setCity(Address city) {
    this.city = city;
  }

  public Address getArea() {
    return area;
  }

  public void setArea(Address area) {
    this.area = area;
  }

  public String getListType() {
    return listType;
  }

  public void setListType(String listType) {
    this.listType = listType;
  }

  public List<Integer> getHotelId() {
    return hotelId;
  }

  public void setHotelId(List<Integer> hotelId) {
    this.hotelId = hotelId;
  }

  public List<String> getPlatform() {
    return platform;
  }

  public void setPlatform(List<String> platform) {
    this.platform = platform;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "InternalSubsidyResponse{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", Label=" + Label +
        ", tierType='" + tierType + '\'' +
        ", priceTiers=" + priceTiers +
        ", starTiers=" + starTiers +
        ", imgUrl='" + imgUrl + '\'' +
        ", travelDateStart='" + travelDateStart + '\'' +
        ", travelDateEnd='" + travelDateEnd + '\'' +
        ", travelDays=" + travelDays +
        ", bookingDateStart='" + bookingDateStart + '\'' +
        ", bookingDateEnd='" + bookingDateEnd + '\'' +
        ", bookingTimeStart='" + bookingTimeStart + '\'' +
        ", bookingTimeEnd='" + bookingTimeEnd + '\'' +
        ", bookingDays=" + bookingDays +
        ", blackOutDate=" + blackOutDate +
        ", pinHotel=" + pinHotel +
        ", country=" + country +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", listType='" + listType + '\'' +
        ", hotelId=" + hotelId +
        ", platform=" + platform +
        ", priority=" + priority +
        ", active=" + active +
        '}';
  }
}
