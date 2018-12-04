package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface HotelDataInboundService {

  void createHotelData(final String message) throws Exception;
}
