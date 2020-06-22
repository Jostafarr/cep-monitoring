package org.stsffap.cep.monitoring.transformations;

import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.stsffap.cep.monitoring.operators.TemperatureAlertFunction;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.OperatorTransformation;
import org.stsffap.cep.monitoring.events.TemperatureWarning;

public class TemperatureAlertTransformation extends OperatorTransformation {

    @Override
    public DataStream<Event> transform(DataStream<Event> dataStream) {
       
       
        KeyedStream<Event, String> keyedStream = dataStream.keyBy(new
        KeySelector<Event, String>(){
 
        @Override public String getKey(Event value) throws Exception { int a =
        ((TemperatureWarning) value).getRackID(); return String.valueOf(a); } });
        
       return   CEP.pattern(keyedStream, TemperatureAlertFunction.alertPattern).process(TemperatureAlertFunction.alertPatternProcessFunction());
    }

    
    
}