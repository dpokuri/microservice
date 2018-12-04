package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface AirlineDataInboundService {

  void createAirlineData(final String message) throws Exception;
}
