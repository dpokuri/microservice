package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface CarVendorDataInboundService {

  void createCarVendorData(final String message) throws Exception;
}
