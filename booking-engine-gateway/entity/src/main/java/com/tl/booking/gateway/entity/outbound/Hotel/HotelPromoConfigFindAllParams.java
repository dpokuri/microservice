package com.tl.booking.gateway.entity.outbound.Hotel;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.DefaultValues;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class HotelPromoConfigFindAllParams extends CommonModel {

  private Integer page = DefaultValues.INT_PAGE;

  private Integer limit = DefaultValues.PAGE_SIZE;

  private String q;

  private String countryId;

  private Integer provinceId;

  private Integer cityId;

  private Integer areaId;

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

  public String getQ() {
    return q;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public Integer getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(Integer provinceId) {
    this.provinceId = provinceId;
  }

  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(Integer cityId) {
    this.cityId = cityId;
  }

  public Integer getAreaId() {
    return areaId;
  }

  public void setAreaId(Integer areaId) {
    this.areaId = areaId;
  }

  @Override
  public String toString() {
    return "HotelPromoConfigFindAllParams{" +
        "page=" + page +
        ", limit=" + limit +
        ", q='" + q + '\'' +
        ", countryId='" + countryId + '\'' +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        '}';
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
