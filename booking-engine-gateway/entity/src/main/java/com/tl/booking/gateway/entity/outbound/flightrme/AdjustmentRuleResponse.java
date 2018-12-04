package com.tl.booking.gateway.entity.outbound.flightrme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.enums.AdjustmentRuleStatus;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class AdjustmentRuleResponse extends BaseMongoResponse {

  private static final long serialVersionUID = 1L;

  private String airline;

  private String origin;

  private String destination;

  private String originDestination;

  private String airlineClass;

  private String fareClass;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String departureStartDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String departureEndDate;

  private MarkupResponse markup;

  private DiscountResponse discount;

  private AdjustmentRuleStatus adjustmentRuleStatus;

  public String getAirline() {
    return airline;
  }

  public void setAirline(String airline) {
    this.airline = airline;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getOriginDestination() {
    return originDestination;
  }

  public void setOriginDestination(String originDestination) {
    this.originDestination = originDestination;
  }

  public String getAirlineClass() {
    return airlineClass;
  }

  public void setAirlineClass(String airlineClass) {
    this.airlineClass = airlineClass;
  }

  public String getFareClass() {
    return fareClass;
  }

  public void setFareClass(String fareClass) {
    this.fareClass = fareClass;
  }

  public String getDepartureStartDate() {
    return departureStartDate;
  }

  public void setDepartureStartDate(String departureStartDate) {
    this.departureStartDate = departureStartDate;
  }

  public String getDepartureEndDate() {
    return departureEndDate;
  }

  public void setDepartureEndDate(String departureEndDate) {
    this.departureEndDate = departureEndDate;
  }

  public MarkupResponse getMarkup() {
    return markup;
  }

  public void setMarkup(MarkupResponse markup) {
    this.markup = markup;
  }

  public DiscountResponse getDiscount() {
    return discount;
  }

  public void setDiscount(DiscountResponse discount) {
    this.discount = discount;
  }

  public AdjustmentRuleStatus getAdjustmentRuleStatus() {
    return adjustmentRuleStatus;
  }

  public void setAdjustmentRuleStatus(
      AdjustmentRuleStatus adjustmentRuleStatus) {
    this.adjustmentRuleStatus = adjustmentRuleStatus;
  }

  @Override
  public String toString() {
    return "AdjustmentRuleResponse{" +
        "airline='" + airline + '\'' +
        ", origin='" + origin + '\'' +
        ", destination='" + destination + '\'' +
        ", originDestination='" + originDestination + '\'' +
        ", airlineClass='" + airlineClass + '\'' +
        ", fareClass='" + fareClass + '\'' +
        ", departureStartDate='" + departureStartDate + '\'' +
        ", departureEndDate='" + departureEndDate + '\'' +
        ", markup=" + markup +
        ", discount=" + discount +
        ", adjustmentRuleStatus=" + adjustmentRuleStatus +
        '}' + super.toString();
  }
}
