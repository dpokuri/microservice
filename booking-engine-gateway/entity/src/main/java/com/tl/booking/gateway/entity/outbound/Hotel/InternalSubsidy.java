package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class InternalSubsidy extends CommonModel {

  private String name;
  private LabelPromo label;
  private String tierType;
  private List<PriceTier> priceTiers;
  private List<StarTier> starTiers;
  private Image img;
  private String imgUrl;
  private Long travelDateStart;
  private Long travelDateEnd;
  private List<Integer> travelDays;
  private Long bookingDateStart;
  private Long bookingDateEnd;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LabelPromo getLabel() {
    return label;
  }

  public void setLabel(LabelPromo label) {
    this.label = label;
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

  public Image getImg() {
    return img;
  }

  public void setImg(Image img) {
    this.img = img;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public Long getTravelDateStart() {
    return travelDateStart;
  }

  public void setTravelDateStart(Long travelDateStart) {
    this.travelDateStart = travelDateStart;
  }

  public Long getTravelDateEnd() {
    return travelDateEnd;
  }

  public void setTravelDateEnd(Long travelDateEnd) {
    this.travelDateEnd = travelDateEnd;
  }

  public List<Integer> getTravelDays() {
    return travelDays;
  }

  public void setTravelDays(List<Integer> travelDays) {
    this.travelDays = travelDays;
  }

  public Long getBookingDateStart() {
    return bookingDateStart;
  }

  public void setBookingDateStart(Long bookingDateStart) {
    this.bookingDateStart = bookingDateStart;
  }

  public Long getBookingDateEnd() {
    return bookingDateEnd;
  }

  public void setBookingDateEnd(Long bookingDateEnd) {
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
    return "InternalSubsidy{" +
        "name='" + name + '\'' +
        ", Label=" + label +
        ", tierType='" + tierType + '\'' +
        ", priceTiers=" + priceTiers +
        ", starTiers=" + starTiers +
        ", img=" + img +
        ", imgUrl='" + imgUrl + '\'' +
        ", travelDateStart=" + travelDateStart +
        ", travelDateEnd=" + travelDateEnd +
        ", travelDays=" + travelDays +
        ", bookingDateStart=" + bookingDateStart +
        ", bookingDateEnd=" + bookingDateEnd +
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
