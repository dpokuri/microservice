package com.tl.booking.promo.code;

import com.tl.booking.common.libraries.JSONHelper;
import com.tl.booking.common.rest.web.model.request.MandatoryRequest;
import com.tl.booking.promo.code.entity.constant.KafkaTopic;
import com.tl.booking.promo.code.entity.constant.VariableSourceTable;
import com.tl.booking.promo.code.entity.dao.VariableSource;
import com.tl.booking.promo.code.entity.inbound.KafkaInboundData;
import com.tl.booking.promo.code.libraries.configuration.KafkaProperties;
import com.tl.booking.promo.code.service.api.VariableSourceService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventDataInboundServiceImpl implements EventDataInboundService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventDataInboundServiceImpl.class);

  @Autowired
  KafkaProperties kafkaProperties;

  @Autowired
  VariableSourceService variableSourceService;

  @Override
  @KafkaListener(topics = KafkaTopic.EVENT)
  public void createEventData(String message) throws Exception {
    MandatoryRequest mandatoryRequest = new MandatoryRequest();
    mandatoryRequest.setUsername("system");
    mandatoryRequest.setServiceId("com.tl.booking.promo.code");
    mandatoryRequest.setRequestId(kafkaProperties.getGroupId());
    mandatoryRequest.setChannelId("DESKTOP");
    mandatoryRequest.setStoreId("TIKETCOM");

    LOGGER.info("createEventData request message {}, mandatoryRequest {}", message,
        mandatoryRequest);
    List<VariableSource> variableSources = this.mapDataToEvent(message, mandatoryRequest);

    this.variableSourceService.updateVariableSources(mandatoryRequest, variableSources).subscribe();
    LOGGER.info("createEventData response variableSources {}", variableSources);
  }

  private List<VariableSource> mapDataToEvent(String message, MandatoryRequest
      mandatoryRequest) throws IOException {
    List<VariableSource> variableSources = new ArrayList<>();
    for (Map<String, String> data : JSONHelper.convertJsonInStringToObject(message,
        KafkaInboundData.class).getData
        ()) {
      VariableSource variableSource = new VariableSource();
      variableSource.setSourceType(VariableSourceTable.EVENT);
      variableSource.setValueId(data.get("businessId"));
      variableSource.setValueName(data.get("businessName"));
      variableSource.setValueSearch(data.get("businessName").toLowerCase());
      variableSource.setStoreId(mandatoryRequest.getStoreId());
      variableSources.add(variableSource);
    }

    return variableSources;
  }
}
