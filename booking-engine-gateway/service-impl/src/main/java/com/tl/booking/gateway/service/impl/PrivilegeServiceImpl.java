package com.tl.booking.gateway.service.impl;

import com.tl.booking.common.libraries.BeanMapper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.gateway.dao.api.PrivilegeRepository;
import com.tl.booking.gateway.libraries.exception.BusinessLogicException;
import com.tl.booking.gateway.service.api.PrivilegeService;
import com.tl.booking.gateway.entity.constant.enums.ResponseCode;
import com.tl.booking.gateway.entity.dao.Privilege;
import com.tl.booking.gateway.entity.outbound.PrivilegeResponse;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

  @Autowired
  private PrivilegeRepository privilegeRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

  @Override
  public Single<Boolean> checkAuthorized(String privilege, String privilegeToCheck) {

    LOGGER.info("createImages request = privilege {}, "
        + "privilegeToCheck {}", privilege,  privilegeToCheck);

    return Single.<Boolean>create(singleEmitter -> {

/*
 * Code commented line : 45 - 48
 *
 * Here we have commeted below code to bypass the privileges authorization part.
 *
 * < This code change is temporary(For this POC). Please remove this code once POC is done and move on with existing code >
 * */


//       if(!isExistPrivilege(privilegeToCheck) || !privilegeToCheck.contains(privilege)){
//         throw new BusinessLogicException(ResponseCode.NOT_AUTHORIZED.getCode(), ResponseCode
//             .NOT_AUTHORIZED.getMessage());
//       }
      singleEmitter.onSuccess(true);
    }).subscribeOn(Schedulers.io());
  }

  @Override
  public Single<List<PrivilegeResponse>> getAuthorizedPrivileges(MandatoryRequest mandatoryRequest,
      String privilegeToCheck) {

    LOGGER.info("getAuthorizedPrivileges request = mandatoryRequest {}, "
        + "privilegeToCheck {}", mandatoryRequest,  privilegeToCheck);

    return Single.<List<PrivilegeResponse>>create(singleEmitter -> {
      List<Privilege> privileges = this.privilegeRepository.findByStoreId(mandatoryRequest
          .getStoreId());
      String[] privilegeStrings = privilegeToCheck.split(",");
      List<Privilege> allowedPrivileges = new ArrayList<>();
      for(String privString : privilegeStrings){
        for(Privilege priv : privileges){
          if(privString.equals(priv.getPrivilegeId())){
            allowedPrivileges.add(priv);
            break;
          }
        }
      }

      LOGGER.info("getAuthorizedPrivileges response : result {} ", BeanMapper.mapAsList(allowedPrivileges, PrivilegeResponse.class));
      singleEmitter.onSuccess(BeanMapper.mapAsList(allowedPrivileges, PrivilegeResponse.class));
    }).subscribeOn(Schedulers.io());
  }

  private Boolean isExistPrivilege(String privilegeToCheck){
    return privilegeToCheck != null;
  }
}
