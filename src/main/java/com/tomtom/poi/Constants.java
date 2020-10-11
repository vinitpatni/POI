package com.tomtom.poi;

import com.tomtom.poi.consumer.PacketHandlerConsumer;
import com.tomtom.poi.metric.POIGraphiteMetric;

public class Constants {
    public static final String APP_NAME = "POI";
    public static final String KAFA_DOWNSTREAM_TOPIC = "packet.downstream.topic";
    public static final String CONFNAME_PACKAGE_HANDLER_SERVICE = "package.handler.service";
    public static final String CONFNAME_KAFKA_PRODUCER_CONFIG = "producer.configs";
    public static final String CONFNAME_VALIDATOR = "validator";
    public static final String HANDLER_CLASS = "handler.class";

    //Reporting
    public static final String GRAPHITE_HOST = "localhost";
    public static final int GRAPHITE_PORT = 1234;
    public static final int REPORTING_DELAY_IN_SEC = 5;
    public static final String TIMER_METRIC = "-Time";
    public static final String METER_METRIC = "-Meter";
    public static final String COUNTER_METRIC = "-Counter";

    //metric
    public static POIGraphiteMetric TIME_TO_PROCESS_PACKET_HANDLER_EVENT = new POIGraphiteMetric(
            PacketHandlerConsumer
                    .class.getSimpleName(), "TIME_TO_PROCESS_PACKET_HANDLER_EVENT");

    public static POIGraphiteMetric NO_OF_PACKET_HANDLER_EVENT_PROCESSED = new POIGraphiteMetric(
            PacketHandlerConsumer
                    .class.getSimpleName(), "NO_OF_PACKET_HANDLER_EVENT_PROCESSED");


}
