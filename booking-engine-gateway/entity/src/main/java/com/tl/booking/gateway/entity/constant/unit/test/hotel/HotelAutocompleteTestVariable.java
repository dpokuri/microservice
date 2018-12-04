package com.tl.booking.gateway.entity.constant.unit.test.hotel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tl.booking.gateway.entity.outbound.GatewayBaseResponse;
import com.tl.booking.gateway.entity.outbound.GatewayBaseResponseBuilder;

public class HotelAutocompleteTestVariable {

  public static String otaID = "1";
  public static String q = "test";

  private static Map<String, String> getData(){
    Map<String, String> data = new HashMap<>();

    data.put("id", "1");
    data.put("name", "Aston Rasuna Jakarta");
    data.put("location", "Jakarta");

    return data;
  }

  public static Map<String, String> DATA = getData();


  public static GatewayBaseResponse<List<Map<String, String>>> AUTOCOMPLETE_RETURN =
      new GatewayBaseResponseBuilder<List<Map<String, String>>>()
          .withCode("SUCCESS")
          .withMessage("")
          .withErrors(null)
          .withData(Arrays.asList(DATA))
          .build();

}
