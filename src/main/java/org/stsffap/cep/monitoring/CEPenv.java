package org.stsffap.cep.monitoring;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.stsffap.cep.monitoring.pyramid.Environment;

public class CEPenv extends Environment {
    
    
    @Override 
    public void run() throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.execute("CEP monitoring job");
    }
}