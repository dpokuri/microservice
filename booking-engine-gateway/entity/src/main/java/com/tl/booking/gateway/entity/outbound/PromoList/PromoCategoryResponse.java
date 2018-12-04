package com.tl.booking.gateway.entity.outbound.PromoList;

import java.util.List;

import com.tl.booking.gateway.entity.outbound.BaseMongoResponse;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class PromoCategoryResponse extends BaseMongoResponse {

  private String parentCategoryId;

  private List<CategoryNameResponse> name;

  private List<CategoryDescriptionResponse> description;

  private Integer precedence;

  public String getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(String parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public List<CategoryNameResponse> getName() {
    return name;
  }

  public void setName(
      List<CategoryNameResponse> name) {
    this.name = name;
  }

  public List<CategoryDescriptionResponse> getDescription() {
    return description;
  }

  public void setDescription(
      List<CategoryDescriptionResponse> description) {
    this.description = description;
  }

  public Integer getPrecedence() {
    return precedence;
  }

  public void setPrecedence(Integer precedence) {
    this.precedence = precedence;
  }

  @Override
  public String toString() {
    return "PromoCategoryResponse{" +
        "parentCategoryId='" + parentCategoryId + '\'' +
        ", name=" + name +
        ", description=" + description +
        ", precedence=" + precedence +
        '}';
  }
}
