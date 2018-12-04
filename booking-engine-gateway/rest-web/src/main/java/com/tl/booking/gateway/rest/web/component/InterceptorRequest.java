package com.tl.booking.gateway.rest.web.component;

import com.github.ooxi.phparser.SerializedPhpParser;
import com.tl.booking.common.libraries.JSONHelper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.common.rest.web.model.request.MandatoryRequestBuilder;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.api.CacheService;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.constant.fields.BaseMongoFields;
import com.tl.booking.gateway.entity.constant.fields.MandatoryFields;

import java.util.Map;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class InterceptorRequest extends HandlerInterceptorAdapter {

 /*
  *  Commented code line: 37 - 38
  *
  * We have commented this CacheService dependency injection code as we are not using Redis as part of this POC.
  * The main reason is we are not connecting to PHP Monolith application in this POC, hence we are not getting
  * session data which will be used in Redis to get stored session.
  *
  * < Its temporary changes. we can uncomment when we connect to PHP Monolith Application >
  *
  */
//   @Autowired
//   private CacheService cacheService;

 /*
  * Commented code line: 55 - 61 where we have hardcoded few values.
  *
  * As per industry best practices we should not use hardcoded values,
  * hence we have commented and taking the required parameter values from request headers.
  *
  * Right now we are taking required parameters values from the requests that are coming from React Core APP and injecting into MDC
  * so that we can use them wherever we want.
  *
  * < Its recommended to get parameters from request instead of using hard code values, hence its good have these changes >
  *
  * Note: If these values are common for all requests and specific to Gateway service, hence we can get these values from constants instead
  * of hardcoded in this place.
  */

//   private static final String STORE_ID = "TIKETCOM";
//   private static final String CHANNEL_ID = "WEB";
//   private static final String USERNAME = "guest";
//   private static final String SERVICE_ID = "gateway";

//   private static final String SESSION_NAME = "PHPSESSID";
//   private static final String KEY_PRIVILEGE = "priv";

  private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorRequest.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    /*
     * Code changes : line 81 - 86
     *
     * Here we are taking request param values from incoming request and injecting into MDC.
     *
     * These MDC values are being used in PromoCode Controller.
     *
     * < These changes are temporary(For this POC). You can remove if you want to use hardcoded/constants
     *    as you already injecting into MDC in the below code >
     *
     */
    
    MDC.put(BaseMongoFields.STORE_ID, request.getHeader(BaseMongoFields.STORE_ID));
    MDC.put(BaseMongoFields.CHANNEL_ID, request.getHeader(BaseMongoFields.CHANNEL_ID));
    MDC.put(BaseMongoFields.USERNAME, request.getHeader(BaseMongoFields.USERNAME));
    MDC.put(BaseMongoFields.REQUEST_ID, request.getHeader(BaseMongoFields.REQUEST_ID));
    MDC.put(BaseMongoFields.SERVICE_ID, request.getHeader(BaseMongoFields.SERVICE_ID));
    MDC.put(BaseMongoFields.PRIVILEGES, request.getHeader(BaseMongoFields.PRIVILEGES));

 /*
  *  Code commented line: 101 - 150
  *
  *  Here we have commented all authorization related code as we are not connecting to PHP Monolith App as part of this POC.
  *  As we are not receiving any information in Session we are bypassing authorization part.
  *
  *  And we are not generating random request id for each request, we are taking constant request id for each request as part of this POC
  *
  *  < These code changes are temporary(For this POC). Please remove this code once POC is done and move on with existing code>
  *
  *
  */

//     String session = this.validateSession(request);
//     if(session.equals("")){
//       return true;
//     }

//     LOGGER.info("InterceptorRequest preHandle : PHPREDIS_SESSION:{}", session);

//     String sessionData = this.cacheService.findCacheByKey("PHPREDIS_SESSION:" + session, String
//         .class);

//     LOGGER.info("InterceptorRequest preHandle : SESSION_DATA:{}", sessionData);
//     if(!isExistSessionData(sessionData)){
//       throw new BusinessLogicException(ResponseCode.NOT_AUTHORIZED.getCode(), ResponseCode
//           .NOT_AUTHORIZED.getMessage());
//     }
//     String sessionId = this.getSessionId(sessionData);
//     if(!isExistSessionId(sessionId)){
//       throw new BusinessLogicException(ResponseCode.NOT_AUTHORIZED.getCode(), ResponseCode
//           .NOT_AUTHORIZED.getMessage());
//     }
//     String authenticationData = this.cacheService.findCacheByKey(sessionId, String.class);
//     if(!isExistAuthenticationData(authenticationData)){
//       throw new BusinessLogicException(ResponseCode.NOT_AUTHORIZED.getCode(), ResponseCode
//           .NOT_AUTHORIZED.getMessage());
//     }
//     SerializedPhpParser phparser = new SerializedPhpParser(authenticationData);
//     String result = (String)phparser.parse();
//     Map<String,String> parsedJson = JSONHelper.convertJsonInStringToObject(result, Map.class);

//     MDC.put(BaseMongoFields.STORE_ID, STORE_ID);
//     MDC.put(BaseMongoFields.CHANNEL_ID, CHANNEL_ID);
//     MDC.put(BaseMongoFields.USERNAME, parsedJson.get("username"));
//     MDC.put(BaseMongoFields.BUSINESS_ID, parsedJson.get("business_id"));

//     Random rand = new Random();
//     String nextInt = STORE_ID + rand.nextInt(10000) + 1;
//     MDC.put(BaseMongoFields.REQUEST_ID, nextInt);
//     MDC.put(BaseMongoFields.SERVICE_ID, SERVICE_ID);
//     MDC.put(BaseMongoFields.PRIVILEGES, parsedJson.get(KEY_PRIVILEGE));

//     MandatoryRequest newMandatoryRequest = new MandatoryRequestBuilder()
//         .withStoreId(STORE_ID)
//         .withChannelId(CHANNEL_ID)
//         .withUsername(parsedJson.get("username"))
//         .withRequestId(nextInt)
//         .withServiceId(SERVICE_ID)
//         .build();

//     MDC.put(MandatoryFields.MANDATORY_REQUEST, newMandatoryRequest);
//     request.setAttribute(MandatoryFields.MANDATORY_REQUEST, newMandatoryRequest);

    return true;
  }


 /*
  * Code commented line :  167 - 218
  *
  * We have commented below code as we are not validating any existing and new session session data.
  *
  * As part of this POC we are not connecting to PHP Monolith application, hence we are not getting any session information.
  *
  * < These code changes are temporary( For POC). Please remove this code once POC is done and move on with existing code >
  *
  */

//   private String validateSession(HttpServletRequest request){
//     if(!request.getRequestURL().toString().split("/")[3].equals("swagger-resources")){
//       Cookie[] cookies = request.getCookies();
//       if (cookies != null) {
//         String cookieString = "";
//         for(Cookie cookie : cookies){
//           if(cookie.getName().equals(SESSION_NAME)){
//             cookieString = cookie.getValue();

//             try{
//               String[] cookieStrings = cookieString.split("~");
//               cookieString = cookieStrings[cookieStrings.length - 1];
//             } catch (Exception e){
//             }
//             LOGGER.info("Cookies {}", cookieString);
//             break;
//           }
//         }

//         if(cookieString.equals("")){
//           throw new BusinessLogicException(ResponseCode.NOT_AUTHORIZED.getCode(), ResponseCode
//               .NOT_AUTHORIZED.getMessage());
//         }
//         return cookieString;

//       } else {
//         throw new BusinessLogicException(ResponseCode.NOT_AUTHORIZED.getCode(), ResponseCode
//             .NOT_AUTHORIZED.getMessage());
//       }
//     }
//     return "";
//   }

//   private Boolean isExistSessionData(String sessionData){
//     return sessionData != null;
//   }

//   private Boolean isExistSessionId(String sessionId){
//     return sessionId != null;
//   }

//   private Boolean isExistAuthenticationData(String authenticationData){
//     return authenticationData != null;
//   }

//   private String getSessionId(String sessionData){
//     String sessionId = sessionData.split(";")[0];
//     Integer index = sessionId.indexOf(":\"");
//     sessionId = sessionId.substring(index + 2, index + 42);

//     return sessionId;
//   }
}
