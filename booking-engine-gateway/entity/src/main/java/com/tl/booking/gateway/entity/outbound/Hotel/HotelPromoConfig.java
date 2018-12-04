package com.tl.booking.gateway.entity.outbound.Hotel;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelPromoConfig {

  private Integer hotelId;

  private Integer minDiscountHotelTonight;

  private String hotelName;

  private String hotelNameAlias;

  private Country country;

  private Province province;

  private City city;

  private Area area;

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    setHotelNameAlias(hotelName.toLowerCase());

    this.hotelName = hotelName;
  }

  public String getHotelNameAlias() {
    return hotelNameAlias;
  }

  public void setHotelNameAlias(String hotelNameAlias) {
    this.hotelNameAlias = hotelNameAlias;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Province getProvince() {
    return province;
  }

  public void setProvince(Province province) {
    this.province = province;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public Integer getHotelId() {
    return hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }

  public Integer getMinDiscountHotelTonight() {
    return minDiscountHotelTonight;
  }

  public void setMinDiscountHotelTonight(Integer minDiscountHotelTonight) {
    this.minDiscountHotelTonight = minDiscountHotelTonight;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return "HotelPromoConfig{" +
        "hotelId=" + hotelId +
        ", minDiscountHotelTonight=" + minDiscountHotelTonight +
        ", hotelName='" + hotelName + '\'' +
        ", hotelNameAlias='" + hotelNameAlias + '\'' +
        ", country=" + country +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        '}';
  }
}
