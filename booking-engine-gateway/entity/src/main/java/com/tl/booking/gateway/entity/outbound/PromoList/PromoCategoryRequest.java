package com.tl.booking.gateway.entity.outbound.PromoList;

import com.tl.booking.common.entity.CommonModel;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class PromoCategoryRequest extends CommonModel implements Serializable {

  private String parentCategoryId;

  @NotNull
  @NotEmpty
  private List<CategoryNameRequest> name;

  @NotNull
  @NotEmpty
  private List<CategoryDescriptionRequest> description;

  @NotNull
  @NotEmpty
  private Integer precedence;

  public String getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(String parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public List<CategoryNameRequest> getName() {
    return name;
  }

  public void setName(
      List<CategoryNameRequest> name) {
    this.name = name;
  }

  public List<CategoryDescriptionRequest> getDescription() {
    return description;
  }

  public void setDescription(
      List<CategoryDescriptionRequest> description) {
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
    return "PromoCategoryRequest{" +
        "parentCategoryId='" + parentCategoryId + '\'' +
        ", name=" + name +
        ", description=" + description +
        ", precedence=" + precedence +
        '}';
  }
}
