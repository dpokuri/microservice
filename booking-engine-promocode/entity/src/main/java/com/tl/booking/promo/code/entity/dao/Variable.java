package com.tl.booking.promo.code.entity.dao;

import com.tl.booking.promo.code.entity.InputData;
import com.tl.booking.promo.code.entity.constant.CollectionName;
import com.tl.booking.promo.code.entity.constant.enums.DataType;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.entity.constant.fields.VariableFields;
import com.tl.booking.promo.code.entity.dao.common.BaseMongo;
import java.util.List;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@GeneratePojoBuilder
@Document(collection = CollectionName.VARIABLE)
public class Variable extends BaseMongo {

  @Field(value = VariableFields.PARAM)
  private String param;

  @Field(value = VariableFields.NAME)
  private String name;

  @Field(value = VariableFields.DESCRIPTION)
  private String description;

  @Field(value = VariableFields.PRODUCT_TYPES)
  private List<String> productTypes;

  @Field(value = VariableFields.INPUT_TYPE)
  private InputType inputType;

  @Field(value = VariableFields.INPUT_SOURCE)
  private String inputSource;

  @Field(value = VariableFields.INPUT_DATA)
  private List<InputData> inputData;

  @Field(value = VariableFields.ALLOWED_ARITHMETICS)
  private List<String> allowedArithmetics;

  @Field(value = VariableFields.DATA_TYPE)
  private DataType dataType;

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
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

  public List<InputData> getInputData() {
    return inputData;
  }

  public void setInputData(List<InputData> inputData) {
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
    return "Variable{" +
        "param='" + param + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", productTypes=" + productTypes +
        ", inputType=" + inputType +
        ", inputSource='" + inputSource + '\'' +
        ", inputData=" + inputData +
        ", allowedArithmetics=" + allowedArithmetics +
        ", dataType=" + dataType +
        '}';
  }
}
