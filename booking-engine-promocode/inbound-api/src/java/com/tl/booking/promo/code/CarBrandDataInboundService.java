package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface CarBrandDataInboundService {

  void createCarBrandData(final String message) throws Exception;
}
