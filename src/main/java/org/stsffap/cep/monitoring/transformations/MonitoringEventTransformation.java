package org.stsffap.cep.monitoring.transformations;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.SourceTransformation;
import org.stsffap.cep.monitoring.pyramid.Transformation;
import org.stsffap.cep.monitoring.sources.MonitoringEventSource;

public class MonitoringEventTransformation extends SourceTransformation {

    private static final int MAX_RACK_ID = 10;
    private static final long PAUSE = 100;
    private static final double TEMPERATURE_RATIO = 0.5;
    private static final double POWER_STD = 10;
    private static final double POWER_MEAN = 100;
    private static final double TEMP_STD = 20;
    private static final double TEMP_MEAN = 80;

   @Override 
    public DataStream<Event> transform(StreamExecutionEnvironment env) {
        
       
        return env
        .addSource(new MonitoringEventSource(
                MAX_RACK_ID,
                PAUSE,
                TEMPERATURE_RATIO,
                POWER_STD,
                POWER_MEAN,
                TEMP_STD,
                TEMP_MEAN))
        .assignTimestampsAndWatermarks(new IngestionTimeExtractor<>());
    }
    
}