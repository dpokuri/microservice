package com.tl.booking.promo.code.entity.constant.unit.test;

import com.tl.booking.promo.code.entity.constant.CacheKey;
import com.tl.booking.promo.code.entity.constant.enums.DataType;
import com.tl.booking.promo.code.entity.constant.enums.InputType;
import com.tl.booking.promo.code.entity.dao.Variable;
import com.tl.booking.promo.code.entity.dao.VariableBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class VariableTestVariable {

  public static String NAME = "nameaja";
  public static String REFERENCE_ID = "referenceId";
  public static String PARAM = "123456";
  public static String PARAM_2 = "1234567";
  public static String PARAM_3 = "1234568";
  public static String ID = "12345edfewfef";
  public static String INPUT_SOURCE = "input123";
  public static String DESCRIPTION = "input123";
  public static String ALLOW_ARITH_STRING = "test1, test2";

  public static Integer PAGE = 0;
  public static Integer SIZE = 10;

  public static String CACHE_KEY = CacheKey.VARIABLE + "-" + ID;

  public static List<String> ALLOWED_ARITH = Arrays.asList("test1", "test2");

  public static String PRODUCT_TYPE_DTO = ID;

  public static List<String> PRODUCT_TYPE_DTOS = Arrays.asList(PRODUCT_TYPE_DTO);

  public static Variable VARIABLE = new VariableBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withId(ID)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withInputData(new ArrayList<>())
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)
      .build();


  public static Variable VARIABLE_UPDATE_MOCK = new VariableBuilder()
      .withStoreId(CommonTestVariable.STORE_ID)
      .withId(ID)
      .withParam(PARAM_2)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withInputData(new ArrayList<>())
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)
      .build();

  public static Variable VARIABLE_REQUEST = new VariableBuilder()
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)

      .withInputData(new ArrayList<>())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withCreatedDate(new Date())
      .withUpdatedDate(new Date())
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .build();


  public static Variable VARIABLE_UPDATE_PARAM_OTHER = new VariableBuilder()
      .withId(ID)
      .withParam(PARAM_2)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)
      .build();

  public static List<Variable> VARIABLE_LIST = Arrays.asList(VARIABLE, VARIABLE);
  public static List<Variable> VARIABLE_MAP = Arrays.asList(VARIABLE);
  public static Variable VARIABLE_UPDATE_PARAM = new VariableBuilder()
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withProductTypes(PRODUCT_TYPE_DTOS)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)

      .withInputData(new ArrayList<>())
      .withStoreId(CommonTestVariable.STORE_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)

      .build();
  public static Variable VARIABLE_RESULT = new VariableBuilder()
      .withId(ID)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withProductTypes(PRODUCT_TYPE_DTOS)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)

      .withInputData(new ArrayList<>())
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .build();
  public static Variable VARIABLE_RESULT_CREATE = new VariableBuilder()
      .withId(ID)
      .withName(NAME)
      .withDescription(DESCRIPTION)
      .withProductTypes(PRODUCT_TYPE_DTOS)
      .withParam(PARAM)
      .withInputType(InputType.DROPDOWN)
      .withInputSource(INPUT_SOURCE)
      .withAllowedArithmetics(ALLOWED_ARITH)
      .withDataType(DataType.STRING)
      .withInputData(new ArrayList<>())
      .withStoreId(CommonTestVariable.STORE_ID)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUpdatedBy(CommonTestVariable.USERNAME)
      .withCreatedBy(CommonTestVariable.USERNAME)
      .withChannelId(CommonTestVariable.CHANNEL_ID)
      .withUsername(CommonTestVariable.USERNAME)
      .build();
  public static Page<Variable> VARIABLE_PAGE = new PageImpl<>(VARIABLE_LIST);
  public static List<Variable> VARIABLE_NULLS = Arrays.asList();
  public static Page<Variable> VARIABLE_PAGE_NULL = new PageImpl<>(VARIABLE_NULLS);
  public static String VARIABLE_JSON_BODY = ""
      + "{"
      + "\"allowedArithmetics\": [ \"test1\", \"test2\"], "
      + "\"dataType\": \"" + DataType.STRING.getName() + "\", "
      + "\"description\": \"" + DESCRIPTION + "\", "
      + "\"inputSource\": \"" + INPUT_SOURCE + "\", "
      + "\"inputType\": \"" + InputType.DROPDOWN.getValue() + "\", "
      + "\"inputData\": [], "
      + "\"param\": \"" + PARAM + "\","
      + "\"name\": \"" + NAME + "\","
      + "\"productTypes\": [\"" + ID + "\"]"
      + "}";
  public Map<String, Variable> VARIABLE_MAPS = this.variableMap(VARIABLE_LIST);

  private static Map<String, Variable> variableMap(List<Variable> VARIABLE_LIST) {
    Map<String, Variable> variableMap = new HashMap<>();

    for (Variable variable : VARIABLE_LIST) {
      variableMap.put(variable.getParam(), variable);
    }
    return variableMap;
  }

}
