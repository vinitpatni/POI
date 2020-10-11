package com.tomtom.poi.reporting;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.tomtom.poi.metric.POIGraphiteMetric;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static com.tomtom.poi.Constants.*;

public class POIMonitoringConfiguration {

    private final static MetricRegistry metricRegistry = new MetricRegistry();

    public static POIMonitoringConfiguration registerGraphiteReporter(){
        Graphite graphite = new Graphite(new InetSocketAddress(GRAPHITE_HOST, GRAPHITE_PORT));
        GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry).prefixedWith(APP_NAME).build(graphite);
        reporter.start(REPORTING_DELAY_IN_SEC, TimeUnit.SECONDS);
        return new POIMonitoringConfiguration();
    }

    public Timer.Context startTimer(POIGraphiteMetric metric) {
        Timer timer = this.metricRegistry.timer(MetricRegistry.name(metric.getClassName(), new String[]{metric.getName(), TIMER_METRIC}));
        return timer.time();
    }

    public void markMeter(POIGraphiteMetric metric) {
        Meter meter = this.metricRegistry.meter(MetricRegistry.name(metric.getClassName(), new String[]{metric.getName(), METER_METRIC}));
        meter.mark();
    }
}
