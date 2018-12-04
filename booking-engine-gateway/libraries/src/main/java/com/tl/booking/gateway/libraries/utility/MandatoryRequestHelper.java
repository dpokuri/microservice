package com.tl.booking.gateway.libraries.utility;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;

import java.util.HashMap;
import java.util.Map;

public class MandatoryRequestHelper {

  private MandatoryRequestHelper(){}

  public static Map<String,String> buildMandatoryRequestHeader(MandatoryRequest mandatoryRequest){
    Map<String,String> returnedMandatoryRequest = new HashMap<>();
    returnedMandatoryRequest.put(BaseMongoFields.STORE_ID, mandatoryRequest.getStoreId());
    returnedMandatoryRequest.put(BaseMongoFields.CHANNEL_ID, mandatoryRequest.getChannelId());
    returnedMandatoryRequest.put(BaseMongoFields.SERVICE_ID, mandatoryRequest.getServiceId());
    returnedMandatoryRequest.put(BaseMongoFields.REQUEST_ID, mandatoryRequest.getRequestId());
    returnedMandatoryRequest.put(BaseMongoFields.USERNAME, mandatoryRequest.getUsername());
    return returnedMandatoryRequest;
  }

  /* Code Added line: 28 - 38
   *
   * Here we are adding 'x-b3-traceid' and 'X-B3-Flags' to Request headers
   *
   * <This change is temporary as we need to finalize the best way to integrate sleuth traces with stack driver >
   */
  public static Map<String,String> buildMandatoryRequestHeader(MandatoryRequest mandatoryRequest, String traceId){
    Map<String,String> returnedMandatoryRequest = new HashMap<>();
    returnedMandatoryRequest.put(BaseMongoFields.STORE_ID, mandatoryRequest.getStoreId());
    returnedMandatoryRequest.put(BaseMongoFields.CHANNEL_ID, mandatoryRequest.getChannelId());
    returnedMandatoryRequest.put(BaseMongoFields.SERVICE_ID, mandatoryRequest.getServiceId());
    returnedMandatoryRequest.put(BaseMongoFields.REQUEST_ID, mandatoryRequest.getRequestId());
    returnedMandatoryRequest.put(BaseMongoFields.USERNAME, mandatoryRequest.getUsername());
    returnedMandatoryRequest.put("x-b3-traceid", traceId);
    returnedMandatoryRequest.put("X-B3-Flags", "1");
    return returnedMandatoryRequest;
  }

}
