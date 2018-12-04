package com.tl.booking.gateway.entity.outbound.PromoList;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

@GeneratePojoBuilder
public class PromoPageWithoutSlugRequest extends CommonModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  private List<String> platform;

  @Valid
  private List<TitleDescriptionRequest> title;

  @Valid
  @NotNull
  private List<PhotoRequest> photos;

  @NotNull
  private Integer precedence;

  @NotNull
  private List<String> categoryIds;

  @Valid
  private List<PromoPagePeriodRequest> periods;

  @NotBlank
  private String promoCode;

  @NotNull
  private Integer promoCodeCount;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public List<String> getPlatform() {
    return platform;
  }

  public void setPlatform(List<String> platform) {
    this.platform = platform;
  }

  public List<TitleDescriptionRequest> getTitle() {
    return title;
  }

  public void setTitle(
      List<TitleDescriptionRequest> title) {
    this.title = title;
  }

  public List<PhotoRequest> getPhotos() {
    return photos;
  }

  public void setPhotos(
      List<PhotoRequest> photos) {
    this.photos = photos;
  }

  public Integer getPrecedence() {
    return precedence;
  }

  public void setPrecedence(Integer precedence) {
    this.precedence = precedence;
  }

  public List<String> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<String> categoryIds) {
    this.categoryIds = categoryIds;
  }

  public List<PromoPagePeriodRequest> getPeriods() {
    return periods;
  }

  public void setPeriods(
      List<PromoPagePeriodRequest> periods) {
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

  @Override
  public String toString() {
    return "PromoPageWithoutSlugRequest{" +
        "platform=" + platform +
        ", title=" + title +
        ", photos=" + photos +
        ", precedence=" + precedence +
        ", categoryIds=" + categoryIds +
        ", periods=" + periods +
        ", promocode='" + promoCode + '\'' +
        ", promoCodeCount=" + promoCodeCount +
        '}';
  }
}
