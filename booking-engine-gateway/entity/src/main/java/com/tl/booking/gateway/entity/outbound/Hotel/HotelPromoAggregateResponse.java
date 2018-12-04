package com.tl.booking.gateway.entity.outbound.Hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelPromoAggregateResponse extends CommonModel {

  private String id;

  private Integer hotelId;

  private List<Object> rooms;

  private String startDate;

  private String endDate;

  private List<Integer> travelDays;

  private List<Integer> bookingDays;

  private String periodeType;

  private Integer hMinStart;

  private Integer hMinEnd;

  private String bookingPeriodeStart;

  private String bookingPeriodeEnd;

  private String bookingTimeStart;

  private String bookingTimeEnd;

  private Integer minStay;

  private HotelPromoTypeIdNameResponse promoType;

  private Double discountPercentage;

  private Integer discountAmount;

  private List<ValueAdded> valueAdded;

  private List<Object> timeTiers;

  private String notes;

  private String cancellationPolicy;

  private Integer priority;

  private List<String> platform;

  private Integer isActive;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getHotelId() {
    return hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }

  public List<Object> getRooms() {
    return rooms;
  }

  public void setRooms(List<Object> rooms) {
    this.rooms = rooms;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public List<Integer> getTravelDays() {
    return travelDays;
  }

  public void setTravelDays(List<Integer> travelDays) {
    this.travelDays = travelDays;
  }

  public List<Integer> getBookingDays() {
    return bookingDays;
  }

  public void setBookingDays(List<Integer> bookingDays) {
    this.bookingDays = bookingDays;
  }

  public String getPeriodeType() {
    return periodeType;
  }

  public void setPeriodeType(String periodeType) {
    this.periodeType = periodeType;
  }

  public Integer gethMinStart() {
    return hMinStart;
  }

  public void sethMinStart(Integer hMinStart) {
    this.hMinStart = hMinStart;
  }

  public Integer gethMinEnd() {
    return hMinEnd;
  }

  public void sethMinEnd(Integer hMinEnd) {
    this.hMinEnd = hMinEnd;
  }

  public String getBookingPeriodeStart() {
    return bookingPeriodeStart;
  }

  public void setBookingPeriodeStart(String bookingPeriodeStart) {
    this.bookingPeriodeStart = bookingPeriodeStart;
  }

  public String getBookingPeriodeEnd() {
    return bookingPeriodeEnd;
  }

  public void setBookingPeriodeEnd(String bookingPeriodeEnd) {
    this.bookingPeriodeEnd = bookingPeriodeEnd;
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

  public Integer getMinStay() {
    return minStay;
  }

  public void setMinStay(Integer minStay) {
    this.minStay = minStay;
  }

  public HotelPromoTypeIdNameResponse getPromoType() {
    return promoType;
  }

  public void setPromoType(
      HotelPromoTypeIdNameResponse promoType) {
    this.promoType = promoType;
  }

  public Double getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(Double discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public Integer getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(Integer discountAmount) {
    this.discountAmount = discountAmount;
  }

  public List<ValueAdded> getValueAdded() {
    return valueAdded;
  }

  public void setValueAdded(
      List<ValueAdded> valueAdded) {
    this.valueAdded = valueAdded;
  }

  public List<Object> getTimeTiers() {
    return timeTiers;
  }

  public void setTimeTiers(List<Object> timeTiers) {
    this.timeTiers = timeTiers;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getCancellationPolicy() {
    return cancellationPolicy;
  }

  public void setCancellationPolicy(String cancellationPolicy) {
    this.cancellationPolicy = cancellationPolicy;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public List<String> getPlatform() {
    return platform;
  }

  public void setPlatform(List<String> platform) {
    this.platform = platform;
  }

  public Integer getIsActive() {
    return isActive;
  }

  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
  }

  @Override
  public String toString() {
    return "HotelPromoAggregateResponse{" +
        "id='" + id + '\'' +
        ", hotelId=" + hotelId +
        ", rooms=" + rooms +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", travelDays=" + travelDays +
        ", bookingDays=" + bookingDays +
        ", periodeType='" + periodeType + '\'' +
        ", hMinStart=" + hMinStart +
        ", hMinEnd=" + hMinEnd +
        ", bookingPeriodeStart='" + bookingPeriodeStart + '\'' +
        ", bookingPeriodeEnd='" + bookingPeriodeEnd + '\'' +
        ", bookingTimeStart='" + bookingTimeStart + '\'' +
        ", bookingTimeEnd='" + bookingTimeEnd + '\'' +
        ", minStay=" + minStay +
        ", promoType=" + promoType +
        ", discountPercentage=" + discountPercentage +
        ", discountAmount=" + discountAmount +
        ", valueAdded=" + valueAdded +
        ", timeTiers=" + timeTiers +
        ", notes='" + notes + '\'' +
        ", cancellationPolicy='" + cancellationPolicy + '\'' +
        ", priority=" + priority +
        ", platform=" + platform +
        ", isActive=" + isActive +
        '}';
  }
}
