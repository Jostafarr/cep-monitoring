package org.stsffap.cep.monitoring.transformations;

import java.util.Map;
import java.util.List;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.stsffap.cep.monitoring.events.TemperatureAlert;
import org.stsffap.cep.monitoring.events.TemperatureWarning;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.Transformation;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;

public class TemperatureAlertTransformation extends Transformation {

    public DataStream<Event> transform(DataStream<Event> warnings) {
        // Alert pattern: Two consecutive temperature warnings appearing within a time
        // interval of 20 seconds
        Pattern<Event, ?> alertPattern = Pattern.<Event>begin("first").subtype(TemperatureWarning.class).next("second").subtype(TemperatureWarning.class)
                .within(Time.seconds(20));

                KeyedStream<Event, String> keyedStream = warnings.keyBy(new KeySelector<Event, String>() {
            

                    @Override
                    public String getKey(Event value) throws Exception {
                        int a =  ((TemperatureWarning) value).getRackID();
                        return String.valueOf(a);
                    }
                    });        
                // Create a pattern stream from our alert pattern
                PatternStream<Event> alertPatternStream = CEP.pattern(keyedStream, alertPattern);

        return alertPatternStream
                .flatSelect((Map<String, List<Event>> pattern, Collector<Event> out) -> {
                    TemperatureWarning first = (TemperatureWarning) pattern.get("first").get(0);
                    TemperatureWarning second = (TemperatureWarning) pattern.get("second").get(0);

                    if (first.getAverageTemperature() < second.getAverageTemperature()) {
                        TemperatureAlert event =  new TemperatureAlert(first.getEventID(), first.getRackID());
                        out.collect(event);
                    }
                }, TypeInformation.of(Event.class));
    }

   
    }

  
   

  

 
