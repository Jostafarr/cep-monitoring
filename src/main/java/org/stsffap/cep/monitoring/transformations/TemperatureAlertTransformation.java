package org.stsffap.cep.monitoring.transformations;

import java.util.Map;
import java.util.List;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.stsffap.cep.monitoring.events.TemperatureAlert;
import org.stsffap.cep.monitoring.events.TemperatureWarning;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.typeinfo.TypeInformation;

public class TemperatureAlertTransformation {
    
    public static DataStream<TemperatureAlert> transform(DataStream<TemperatureWarning> warnings){
          // Alert pattern: Two consecutive temperature warnings appearing within a time interval of 20 seconds
          Pattern<TemperatureWarning, ?> alertPattern = Pattern.<TemperatureWarning>begin("first")
          .next("second")
          .within(Time.seconds(20));

            // Create a pattern stream from our alert pattern
        PatternStream<TemperatureWarning> alertPatternStream = CEP.pattern(
          warnings.keyBy("rackID"),
          alertPattern);


          return alertPatternStream.flatSelect(
            (Map<String, List<TemperatureWarning>> pattern, Collector<TemperatureAlert> out) -> {
                TemperatureWarning first = pattern.get("first").get(0);
                TemperatureWarning second = pattern.get("second").get(0);

                if (first.getAverageTemperature() < second.getAverageTemperature()) {
                    out.collect(new TemperatureAlert(first.getRackID()));
                }
            },
            TypeInformation.of(TemperatureAlert.class));
    }
}