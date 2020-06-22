package org.stsffap.cep.monitoring.operators;

import java.util.Map;
import java.util.List;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.stsffap.cep.monitoring.events.MonitoringEvent;
import org.stsffap.cep.monitoring.events.TemperatureEvent;
import org.stsffap.cep.monitoring.events.TemperatureWarning;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.OperatorTransformation;
import org.stsffap.cep.monitoring.pyramid.Transformation;

public class TemperatureWarningFunction /* extends OperatorTransformation */{
    
    
   
        static double TEMPERATURE_THRESHOLD = 100;
        // Warning pattern: Two consecutive temperature events whose temperature is higher than the given threshold
        // appearing within a time interval of 10 seconds
      public static final  Pattern<Event, ?> warningPattern = Pattern.<Event>begin("first")
                .subtype(MonitoringEvent.class).subtype(TemperatureEvent.class)
                .where(new IterativeCondition<TemperatureEvent>() {
                    private static final long serialVersionUID = -6301755149429716724L;

                    @Override
                    public boolean filter(TemperatureEvent value, Context<TemperatureEvent> ctx) throws Exception {
                         return value.getTemperature() >= TEMPERATURE_THRESHOLD;
                    }
                })
                .next("second")
                .subtype(MonitoringEvent.class).subtype(TemperatureEvent.class)
                .where(new IterativeCondition<TemperatureEvent>() {
                    private static final long serialVersionUID = 2392863109523984059L;

                    @Override
                    public boolean filter(TemperatureEvent value, Context<TemperatureEvent> ctx) throws Exception {
                        return value.getTemperature() >= TEMPERATURE_THRESHOLD;
                    }
                })
                .within(Time.seconds(10));
                
      public static PatternProcessFunction<Event, Event> warningPatternProcessFunction(){

        return new PatternProcessFunction<Event, Event>(){
           
            @Override
            public void processMatch(Map<String, List<Event>> match, Context ctx, Collector<Event> out) throws Exception{
                
                    TemperatureEvent first = (TemperatureEvent) match.get("first").get(0);
                    TemperatureEvent second = (TemperatureEvent) match.get("second").get(0);
    
                    out.collect( new TemperatureWarning(first.getEventID(), first.getRackID(), (first.getTemperature() + second.getTemperature()) / 2));
            }
            };
        };
                
                
                /* KeyedStream<Event, String> keyedStream = inputEventStream.keyBy(new KeySelector<Event, String>() {
                   

                    @Override
                    public String getKey(Event value) throws Exception {
                        int a =  ((MonitoringEvent) value).getRackID();
                        return String.valueOf(a);
                    }
                  }); */
      /*   // Create a pattern stream from our warning pattern
        PatternStream<Event> tempPatternStream = CEP.pattern(
                keyedStream,
                warningPattern);
        
                return tempPatternStream.select(
                    (Map<String, List<Event>> pattern) -> {
                        TemperatureEvent first = (TemperatureEvent) pattern.get("first").get(0);
                        TemperatureEvent second = (TemperatureEvent) pattern.get("second").get(0);
        
                        return new TemperatureWarning(first.getEventID(), first.getRackID(), (first.getTemperature() + second.getTemperature()) / 2);
                    }
                ); */
}
