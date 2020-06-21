package org.stsffap.cep.monitoring.transformations;

import java.util.Map;
import java.util.List;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.stsffap.cep.monitoring.events.MonitoringEvent;
import org.stsffap.cep.monitoring.events.TemperatureEvent;
import org.stsffap.cep.monitoring.events.TemperatureWarning;

public class TemperatureWarningTransformation {
    public static DataStream<TemperatureWarning> transform(DataStream<MonitoringEvent> inputEventStream, double TEMPERATURE_THRESHOLD){
        
        // Warning pattern: Two consecutive temperature events whose temperature is higher than the given threshold
        // appearing within a time interval of 10 seconds
        Pattern<MonitoringEvent, ?> warningPattern = Pattern.<MonitoringEvent>begin("first")
                .subtype(TemperatureEvent.class)
                .where(new IterativeCondition<TemperatureEvent>() {
                    private static final long serialVersionUID = -6301755149429716724L;

                    @Override
                    public boolean filter(TemperatureEvent value, Context<TemperatureEvent> ctx) throws Exception {
                         return value.getTemperature() >= TEMPERATURE_THRESHOLD;
                    }
                })
                .next("second")
                .subtype(TemperatureEvent.class)
                .where(new IterativeCondition<TemperatureEvent>() {
                    private static final long serialVersionUID = 2392863109523984059L;

                    @Override
                    public boolean filter(TemperatureEvent value, Context<TemperatureEvent> ctx) throws Exception {
                        return value.getTemperature() >= TEMPERATURE_THRESHOLD;
                    }
                })
                .within(Time.seconds(10));

        // Create a pattern stream from our warning pattern
        PatternStream<MonitoringEvent> tempPatternStream = CEP.pattern(
                inputEventStream.keyBy("rackID"),
                warningPattern);
        
                return tempPatternStream.select(
                    (Map<String, List<MonitoringEvent>> pattern) -> {
                        TemperatureEvent first = (TemperatureEvent) pattern.get("first").get(0);
                        TemperatureEvent second = (TemperatureEvent) pattern.get("second").get(0);
        
                        return new TemperatureWarning(first.getRackID(), (first.getTemperature() + second.getTemperature()) / 2);
                    }
                );
    }
}