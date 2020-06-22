package org.stsffap.cep.monitoring.pyramid;

import java.util.ArrayList;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public abstract  class Level {
    protected ArrayList<DataStream<Event>> level;
    
    
    public abstract void build();

    public ArrayList<DataStream<Event>> getLevel(){
        return level;
    }

    public int size(){
        return level.size();
    }
    
}