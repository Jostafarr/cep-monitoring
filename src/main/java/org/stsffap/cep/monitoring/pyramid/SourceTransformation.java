package org.stsffap.cep.monitoring.pyramid;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public abstract class SourceTransformation extends Transformation {
    
    public abstract DataStream<Event> transform(StreamExecutionEnvironment env);
}