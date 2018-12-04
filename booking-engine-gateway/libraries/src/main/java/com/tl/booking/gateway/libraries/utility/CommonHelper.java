package com.tl.booking.gateway.libraries.utility;

import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.outbound.SessionData;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.MDC;
import org.springframework.data.domain.Sort.Direction;

public class CommonHelper {
  private CommonHelper(){}

  public static Direction convertToSortDirection(String dir){
    Direction direction;

    switch (dir){
      case "desc":
      case "descending":
        direction = Direction.DESC;
        break;
      case "asc":
      case "ascending":
      default:
        direction = Direction.ASC;
        break;
    }

    return direction;
  }

  public static Map<String, String> convertMandatoryRequestToMap(MandatoryRequest mandatoryRequest){
    Map<String, String> headerMap = new HashMap<>();

    headerMap.put("storeId", mandatoryRequest.getStoreId());
    headerMap.put("channelId", mandatoryRequest.getChannelId());
    headerMap.put("requestId", mandatoryRequest.getRequestId());
    headerMap.put("serviceId", mandatoryRequest.getServiceId());
    headerMap.put("username", mandatoryRequest.getUsername());

    return headerMap;
  }

  public static SessionData getSessionData(){
    SessionData sessionData = new SessionData();
    sessionData.setUsername((String) MDC.get(BaseMongoFields.USERNAME));
    sessionData.setBusinessId((String)MDC.get(BaseMongoFields.BUSINESS_ID));

    return sessionData;
  }
}
