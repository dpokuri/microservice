package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface CityDataInboundService {

  void createCityData(final String message) throws Exception;
}
