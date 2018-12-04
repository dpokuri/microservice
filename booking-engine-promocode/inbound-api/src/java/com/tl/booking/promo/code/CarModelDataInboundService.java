package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface CarModelDataInboundService {

  void createCarModelData(final String message) throws Exception;
}
