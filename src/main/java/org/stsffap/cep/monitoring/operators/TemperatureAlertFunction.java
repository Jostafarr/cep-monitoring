package org.stsffap.cep.monitoring.operators;

import java.util.Map;
import java.util.List;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternFlatSelectFunction;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.stsffap.cep.monitoring.events.TemperatureAlert;
import org.stsffap.cep.monitoring.events.TemperatureWarning;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.OperatorTransformation;
import org.stsffap.cep.monitoring.pyramid.Transformation;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.functions.PatternProcessFunction;
public class TemperatureAlertFunction /* extends OperatorTransformation */ {

    // Alert pattern: Two consecutive temperature warnings appearing within a time
    // interval of 20 seconds
    public static final Pattern<Event, ?> alertPattern = Pattern.<Event>begin("first").subtype(TemperatureWarning.class)
            .next("second").subtype(TemperatureWarning.class).within(Time.seconds(20));

    
     
    
    // Create a pattern stream from our alert pattern
    // PatternStream<Event> alertPatternStream = CEP.pattern(keyedStream,
    // alertPattern);



    public static PatternProcessFunction<Event, Event> alertPatternProcessFunction() {
        return new PatternProcessFunction<Event, Event>(){
           
            @Override
            public void processMatch(Map<String, List<Event>> match, Context ctx, Collector<Event> out) throws Exception{
                TemperatureWarning first = (TemperatureWarning) match.get("first").get(0);
                TemperatureWarning second = (TemperatureWarning) match.get("second").get(0);
    
                if (first.getAverageTemperature() < second.getAverageTemperature()) {
                    TemperatureAlert event =  new TemperatureAlert(first.getEventID(), first.getRackID());
                    out.collect(event);
                    
                }
            }
            };
        };



        /* KeyedStream<Event, String> keyedStream = warnings.keyBy(new
        KeySelector<Event, String>(){
 
        @Override public String getKey(Event value) throws Exception { int a =
        ((TemperatureWarning) value).getRackID(); return String.valueOf(a); } });

        DataStream<Event> input =  CEP.pattern(keyedStream, alertPattern).process(alertPatternProcessFunction()); */
    
    /* public static PatternFlatSelectFunction<Event,Event> alertPatternfunction(DataStream<Event> warnings) {
         {
        
       


        DataStream<Event> result = input.flatSelect((Map<String, List<Event>> pattern, Collector<Event> out) -> {
            TemperatureWarning first = (TemperatureWarning) pattern.get("first").get(0);
            TemperatureWarning second = (TemperatureWarning) pattern.get("second").get(0);

            if (first.getAverageTemperature() < second.getAverageTemperature()) {
                TemperatureAlert event =  new TemperatureAlert(first.getEventID(), first.getRackID());
                out.collect(event);
                
            }
        }, TypeInformation.of(Event.class));
            
                }
                 */
                


   /*  @Override
    public DataStream<Event> transform(DataStream<Event> warnings) {
      

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
 */
  

   
    }

  
   

  

 
