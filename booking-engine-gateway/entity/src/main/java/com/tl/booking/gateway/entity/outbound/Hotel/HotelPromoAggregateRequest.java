package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class HotelPromoAggregateRequest extends CommonModel {

  @NotNull
  private Integer hotelId;

  @NotEmpty
  private List<Room> rooms;

  @NotBlank
  private String startDate;

  @NotBlank
  private String endDate;

  private List<Integer> travelDays;

  private List<Integer> bookingDays;

  @NotBlank
  private String periodeType;

  private Integer hMinStart;

  private Integer hMinEnd;

  private String bookingPeriodeStart;

  private String bookingPeriodeEnd;

  private String bookingTimeStart;

  private String bookingTimeEnd;

  private Integer minStay;

  private String promoTypeId;

  @Max(value = 100)
  private Double discountPercentage;

  private Integer discountAmount;

  private List<ValueAdded> valueAdded;

  @Valid
  private List<TimeTier> timeTiers;

  private String notes;

  @NotBlank
  private String cancellationPolicy;

  @NotNull
  private Integer priority;

  private List<String> platform;

  @NotNull
  private Integer isActive;

  public Integer getHotelId() {
    return hotelId;
  }

  public void setHotelId(Integer hotelId) {
    this.hotelId = hotelId;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void setRooms(List<Room> rooms) {
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

  public String getPromoTypeId() {
    return promoTypeId;
  }

  public void setPromoTypeId(String promoTypeId) {
    this.promoTypeId = promoTypeId;
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

  public List<TimeTier> getTimeTiers() {
    return timeTiers;
  }

  public void setTimeTiers(List<TimeTier> timeTiers) {
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
}
