package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelPromoTypeDetail extends CommonModel {

  private Integer startDate;
  private Integer endDate;
  private Integer travelDays;
  private Integer bookingDays;
  private Integer periodType;
  private Integer hMinStart;
  private Integer hMinEnd;
  private Integer bookingPeriodStart;
  private Integer bookingPeriodEnd;
  private Integer bookingTimeStart;
  private Integer bookingTimeEnd;
  private Integer cancellationPolicy;
  private Integer priority;
  private Integer hotelId;
  private Integer roomId;
  private Integer platform;
  private Integer discountPercentage;
  private Integer discountAmount;
  private Integer promoTypeId;
  private Integer valueAdded;
  private Integer notes;
  private Integer minStay;
  private Integer active;

  public HotelPromoTypeDetail() {
    this.startDate = 0;
    this.endDate = 0;
    this.travelDays = 0;
    this.bookingDays = 0;
    this.periodType = 0;
    this.hMinStart = 0;
    this.hMinEnd = 0;
    this.bookingPeriodStart = 0;
    this.bookingPeriodEnd = 0;
    this.bookingTimeStart = 0;
    this.bookingTimeEnd = 0;
    this.cancellationPolicy = 0;
    this.priority = 0;
    this.hotelId = 0;
    this.roomId = 0;
    this.platform = 0;
    this.discountPercentage = 0;
    this.discountAmount = 0;
    this.promoTypeId = 0;
    this.valueAdded = 0;
    this.notes = 0;
    this.minStay = 0;
    this.active = 0;
  }

  public Integer getStartDate() {
    return startDate;
  }

  public void setStartDate(Integer startDate) {
    this.startDate = startDate;
  }

  public Integer getEndDate() {
    return endDate;
  }

  public void setEndDate(Integer endDate) {
    this.endDate = endDate;
  }

  public Integer getTravelDays() {
    return travelDays;
  }

  public void setTravelDays(Integer travelDays) {
    this.travelDays = travelDays;
  }

  public Integer getBookingDays() {
    return bookingDays;
  }

  public void setBookingDays(Integer bookingDays) {
    this.bookingDays = bookingDays;
  }

  public Integer getPeriodType() {
    return periodType;
  }

  public void setPeriodType(Integer periodType) {
    this.periodType = periodType;
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

  public Integer getBookingPeriodStart() {
    return bookingPeriodStart;
  }

  public void setBookingPeriodStart(Integer bookingPeriodStart) {
    this.bookingPeriodStart = bookingPeriodStart;
  }

  public Integer getBookingPeriodEnd() {
    return bookingPeriodEnd;
  }

  public void setBookingPeriodEnd(Integer bookingPeriodEnd) {
    this.bookingPeriodEnd = bookingPeriodEnd;
  }

  public Integer getBookingTimeStart() {
    return bookingTimeStart;
  }

  public void setBookingTimeStart(Integer bookingTimeStart) {
    this.bookingTimeStart = bookingTimeStart;
  }

  public Integer getBookingTimeEnd() {
    return bookingTimeEnd;
  }

  public void setBookingTimeEnd(Integer bookingTimeEnd) {
    this.bookingTimeEnd = bookingTimeEnd;
  }

  public Integer getCancellationPolicy() {
    return cancellationPolicy;
  }

  public void setCancellationPolicy(Integer cancellationPolicy) {
    this.cancellationPolicy = cancellationPolicy;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Integer getHotelId() {
    return hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }

  public Integer getRoomId() {
    return roomId;
  }

  public void setRoomId(Integer roomId) {
    this.roomId = roomId;
  }

  public Integer getPlatform() {
    return platform;
  }

  public void setPlatform(Integer platform) {
    this.platform = platform;
  }

  public Integer getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(Integer discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public Integer getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(Integer discountAmount) {
    this.discountAmount = discountAmount;
  }

  public Integer getPromoTypeId() {
    return promoTypeId;
  }

  public void setPromoTypeId(Integer promoTypeId) {
    this.promoTypeId = promoTypeId;
  }

  public Integer getValueAdded() {
    return valueAdded;
  }

  public void setValueAdded(Integer valueAdded) {
    this.valueAdded = valueAdded;
  }

  public Integer getNotes() {
    return notes;
  }

  public void setNotes(Integer notes) {
    this.notes = notes;
  }

  public Integer getMinStay() {
    return minStay;
  }

  public void setMinStay(Integer minStay) {
    this.minStay = minStay;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }

  @Override
  public String toString() {
    return "HotelPromoTypeDetail{" +
        "startDate=" + startDate +
        ", endDate=" + endDate +
        ", travelDays=" + travelDays +
        ", bookingDays=" + bookingDays +
        ", periodType=" + periodType +
        ", hMinStart=" + hMinStart +
        ", hMinEnd=" + hMinEnd +
        ", bookingPeriodStart=" + bookingPeriodStart +
        ", bookingPeriodEnd=" + bookingPeriodEnd +
        ", bookingTimeStart=" + bookingTimeStart +
        ", bookingTimeEnd=" + bookingTimeEnd +
        ", cancellationPolicy=" + cancellationPolicy +
        ", priority=" + priority +
        ", hotelId=" + hotelId +
        ", roomId=" + roomId +
        ", platform=" + platform +
        ", discountPercentage=" + discountPercentage +
        ", discountAmount=" + discountAmount +
        ", promoTypeId=" + promoTypeId +
        ", valueAdded=" + valueAdded +
        ", notes=" + notes +
        ", minStay=" + minStay +
        ", active=" + active +
        '}';
  }
}
