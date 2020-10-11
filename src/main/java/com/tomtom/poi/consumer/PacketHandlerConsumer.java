package com.tomtom.poi.consumer;

import com.codahale.metrics.Timer;
import com.tomtom.poi.Constants;
import com.tomtom.poi.exception.PacketHandlerConsumerException;
import com.tomtom.poi.payload.DataPayload;
import com.tomtom.poi.reporting.POIMonitoringConfiguration;
import com.tomtom.poi.validations.RuleValidator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.tomtom.poi.Constants.NO_OF_PACKET_HANDLER_EVENT_PROCESSED;

/**
 * This kafka consumer which will keep on polling kafka broker
 * and fetch records from kafka to consume. It will run configured
 * rule validators on the data payload and publish on downstream
 * if none of the rule validator failed and all rule validators passes for data packet
 */
public class PacketHandlerConsumer {

    private final KafkaProducer<Object, Object> kafkaProducer;
    private final List<RuleValidator> ruleValidatorList;
    private final String kafkaTopicName;
    private final POIMonitoringConfiguration poiMonitoringConfiguration;
    private static final Logger logger = LoggerFactory.getLogger(PacketHandlerConsumer.class);


    public PacketHandlerConsumer(Map<String, Object> kafkaProducerConfig, List<RuleValidator> categoryValidators, POIMonitoringConfiguration poiMonitoringConfiguration) {
        kafkaProducer = new KafkaProducer<>(kafkaProducerConfig);
        kafkaTopicName = (String) kafkaProducerConfig.get("topic");
        this.ruleValidatorList = categoryValidators;
        this.poiMonitoringConfiguration = poiMonitoringConfiguration;
    }

    public void process(ConsumerRecord consumerRecord) throws PacketHandlerConsumerException {
        logger.debug("Received Kafka payload");
        Timer.Context context = null;
        try {
            context = poiMonitoringConfiguration.startTimer(Constants.TIME_TO_PROCESS_PACKET_HANDLER_EVENT);
            DataPayload dataPayload = (DataPayload) consumerRecord.value();
            boolean ruleValidationFailed = false;
            for (RuleValidator ruleValidator : ruleValidatorList) {
                if (ruleValidator.validate(dataPayload)) {
                    logger.info("Validator pass for id : {}", ruleValidator.id());
                } else {
                    logger.info("Validator failed for id : {}", ruleValidator.id());
                    ruleValidationFailed = true;
                    break;
                }
            }
            //
            if(!ruleValidationFailed){
                logger.info("Producing kafka message on downstream topic {}", kafkaTopicName);
                kafkaProducer.send(new ProducerRecord<>(kafkaTopicName,dataPayload));
            }

        }catch (Exception ex){
            logger.error("Error while processing msg : {}", ex.getMessage());
            throw new PacketHandlerConsumerException(ex);
        }finally {
            context.stop();
            poiMonitoringConfiguration.markMeter(NO_OF_PACKET_HANDLER_EVENT_PROCESSED);
        }
        logger.debug("Processed Kafka payload successfully");

    }
}
