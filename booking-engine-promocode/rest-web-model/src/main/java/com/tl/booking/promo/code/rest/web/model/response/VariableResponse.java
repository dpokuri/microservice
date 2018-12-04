package com.tl.booking.promo.code.rest.web.model.response;

import com.tl.booking.promo.code.entity.constant.enums.DataType;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.rest.web.model.BaseMongoResponse;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class VariableResponse extends BaseMongoResponse {

  private String name;

  private String description;

  private List<String> productTypes;

  private String param;

  private InputType inputType;

  private String inputSource;

  private List<InputDataResponse> inputData;

  private List<String> allowedArithmetics;

  private DataType dataType;

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

  public List<InputDataResponse> getInputData() {
    return inputData;
  }

  public void setInputData(
      List<InputDataResponse> inputData) {
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
    return "VariableResponse{" +
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
