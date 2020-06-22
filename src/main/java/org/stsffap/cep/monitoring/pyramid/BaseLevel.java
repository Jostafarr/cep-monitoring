package org.stsffap.cep.monitoring.pyramid;

import java.util.ArrayList;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public abstract class BaseLevel extends Level {
    
    protected StreamExecutionEnvironment env;

}