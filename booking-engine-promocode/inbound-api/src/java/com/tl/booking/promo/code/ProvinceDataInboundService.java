package com.tl.booking.promo.code;

import org.springframework.stereotype.Service;

@Service
public interface ProvinceDataInboundService {

  void createProvinceData(final String message) throws Exception;
}
