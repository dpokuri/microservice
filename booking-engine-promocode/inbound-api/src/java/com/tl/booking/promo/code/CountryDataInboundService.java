package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface CountryDataInboundService {

  void createCountryData(final String message) throws Exception;
}
