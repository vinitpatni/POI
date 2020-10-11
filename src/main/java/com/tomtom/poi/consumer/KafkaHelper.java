package com.tomtom.poi.consumer;

import com.tomtom.poi.payload.DataPayload;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KafkaHelper {
    public static ConsumerRecord msg(){
        return new ConsumerRecord<>("mock-topic",1,1l,null, new DataPayload());
    }
}
