package com.tl.booking.gateway.entity.outbound.flightrme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class AdjustmentRuleRequest extends CommonModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @NotEmpty
  @NotEmpty
  private String airline;

  @NotNull
  @NotEmpty
  @NotEmpty
  private String origin;

  @NotNull
  @NotEmpty
  @NotEmpty
  private String destination;

  @NotNull
  @NotEmpty
  @NotEmpty
  private String originDestination;

  @NotNull
  @NotEmpty
  @NotEmpty
  private String airlineClass;

  @NotNull
  @NotEmpty
  @NotEmpty
  private String fareClass;

  @NotNull
  @NotEmpty
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String departureStartDate;

  @NotNull
  @NotEmpty
  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private String departureEndDate;

  @NotNull
  @Valid
  private MarkupRequest markup;

  @NotNull
  @Valid
  private DiscountRequest discount;

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

  public MarkupRequest getMarkup() {
    return markup;
  }

  public void setMarkup(MarkupRequest markup) {
    this.markup = markup;
  }

  public DiscountRequest getDiscount() {
    return discount;
  }

  public void setDiscount(DiscountRequest discount) {
    this.discount = discount;
  }

  @Override
  public String toString() {
    return "AdjustmentRuleRequest{" +
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
        '}' + super.toString();
  }
}
