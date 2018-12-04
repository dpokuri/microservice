package com.tl.booking.promo.code;

import com.tl.booking.promo.code.entity.constant.KafkaKey;
import com.tl.booking.promo.code.entity.constant.KafkaTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendMessageOutboundServiceImpl implements SendMessageOutboundService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void sendMessage(String message) {
    kafkaTemplate.send(KafkaTopic.AIRPORT, KafkaKey.AIRPORT_CREATE, message);
  }
}
