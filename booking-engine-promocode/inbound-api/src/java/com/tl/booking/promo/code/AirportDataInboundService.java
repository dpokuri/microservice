package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface AirportDataInboundService {

  void createAirportData(final String message) throws Exception;
}
