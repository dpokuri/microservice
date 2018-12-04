package com.tl.booking.gateway.entity.outbound.PromoCode;

import com.tl.booking.common.entity.CommonModel;
import com.tl.booking.gateway.entity.constant.enums.DataType;
import com.tl.booking.gateway.entity.constant.enums.InputType;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@GeneratePojoBuilder
public class VariableRequest extends CommonModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  @NotEmpty
  @NotNull
  private String name;

  @NotBlank
  @NotEmpty
  @NotNull
  private String description;

  @Valid
  @NotNull
  private List<String> productTypes;


  @NotBlank
  @NotEmpty
  @NotNull
  private String param;

  @Valid
  @NotNull
  private InputType inputType;

  @NotNull
  private String inputSource;

  @NotNull
  private List<InputDataRequest> inputData;

  @Valid
  @NotNull
  private List<String> allowedArithmetics;

  @Valid
  @NotNull
  private DataType dataType;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getProductTypes() {
    return productTypes;
  }

  public void setProductTypes(List<String> productTypes) {
    this.productTypes = productTypes;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public InputType getInputType() {
    return inputType;
  }

  public void setInputType(InputType inputType) {
    this.inputType = inputType;
  }

  public String getInputSource() {
    return inputSource;
  }

  public void setInputSource(String inputSource) {
    this.inputSource = inputSource;
  }

  public List<InputDataRequest> getInputData() {
    return inputData;
  }

  public void setInputData(
      List<InputDataRequest> inputData) {
    this.inputData = inputData;
  }

  public List<String> getAllowedArithmetics() {
    return allowedArithmetics;
  }

  public void setAllowedArithmetics(List<String> allowedArithmetics) {
    this.allowedArithmetics = allowedArithmetics;
  }

  public DataType getDataType() {
    return dataType;
  }

  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  @Override
  public String toString() {
    return "VariableRequest{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", productTypes=" + productTypes +
        ", param='" + param + '\'' +
        ", inputType=" + inputType +
        ", inputSource='" + inputSource + '\'' +
        ", inputData=" + inputData +
        ", allowedArithmetics=" + allowedArithmetics +
        ", dataType=" + dataType +
        '}';
  }
}
