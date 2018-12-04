package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.fields.HotelPromoAggregateFields;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class FindAllPromoParam extends CommonModel {

  private Integer page = 0;
  private Integer limit = 20;

  @NotNull
  private String hotelId;

  private String roomId = "";
  private String startDate = "";
  private String endDate = "";

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
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

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public Map<String, String> getFindAllPromoParamAsMap() {
    Map<String, String> params = new HashMap<>();
    params.put(HotelPromoAggregateFields.PAGE, getPage().toString());
    params.put(HotelPromoAggregateFields.LIMIT, getLimit().toString());
    params.put(HotelPromoAggregateFields.HOTEL_ID, getHotelId());
    params.put(HotelPromoAggregateFields.ROOM_ID, getRoomId());
    params.put(HotelPromoAggregateFields.START_DATE, getStartDate());
    params.put(HotelPromoAggregateFields.END_DATE, getEndDate());

    return params;
  }
}
