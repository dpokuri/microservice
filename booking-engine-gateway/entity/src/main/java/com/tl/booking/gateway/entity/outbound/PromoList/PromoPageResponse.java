package com.tl.booking.gateway.entity.outbound.PromoList;

import com.tl.booking.gateway.entity.constant.enums.PromoPageStatus;
import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoPageResponse extends BaseMongoResponse {

  private static final long serialVersionUID = 1L;

  private List<String> platform;
  private List<TitleDescriptionResponse> title;
  private List<PhotoResponse> photos;
  private Integer precedence;
  private PromoPageStatus status;
  private List<String> categoryIds;
  private String slug;
  private List<PromoPagePeriodResponse> periods;
  private String promoCode;
  private Integer promoCodeCount;
  private List<String> allowedActions;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public List<String> getPlatform() {
    return platform;
  }

  public void setPlatform(List<String> platform) {
    this.platform = platform;
  }

  public List<TitleDescriptionResponse> getTitle() {
    return title;
  }

  public void setTitle(
      List<TitleDescriptionResponse> title) {
    this.title = title;
  }

  public List<PhotoResponse> getPhotos() {
    return photos;
  }

  public void setPhotos(
      List<PhotoResponse> photos) {
    this.photos = photos;
  }

  public Integer getPrecedence() {
    return precedence;
  }

  public void setPrecedence(Integer precedence) {
    this.precedence = precedence;
  }

  public PromoPageStatus getStatus() {
    return status;
  }

  public void setStatus(PromoPageStatus status) {
    this.status = status;
  }

  public List<String> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<String> categoryIds) {
    this.categoryIds = categoryIds;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public List<PromoPagePeriodResponse> getPeriods() {
    return periods;
  }

  public void setPeriods(
      List<PromoPagePeriodResponse> periods) {
    this.periods = periods;
  }

  public String getPromoCode() {
    return promoCode;
  }

  public void setPromoCode(String promoCode) {
    this.promoCode = promoCode;
  }

  public Integer getPromoCodeCount() {
    return promoCodeCount;
  }

  public void setPromoCodeCount(Integer promoCodeCount) {
    this.promoCodeCount = promoCodeCount;
  }

  public List<String> getAllowedActions() {
    return allowedActions;
  }

  public void setAllowedActions(List<String> allowedActions) {
    this.allowedActions = allowedActions;
  }

  @Override
  public String toString() {
    return "PromoPageResponse{" +
        "platform=" + platform +
        ", title=" + title +
        ", photos=" + photos +
        ", precedence=" + precedence +
        ", status=" + status +
        ", categoryIds=" + categoryIds +
        ", slug='" + slug + '\'' +
        ", periods=" + periods +
        ", promocode='" + promoCode + '\'' +
        ", promoCodeCount=" + promoCodeCount +
        ", allowedActions=" + allowedActions +
        '}';
  }
}
