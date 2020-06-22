package org.stsffap.cep.monitoring.pyramid;

import org.apache.flink.streaming.api.datastream.DataStream;

public abstract class OperatorTransformation extends Transformation {
    
    public abstract DataStream<Event> transform(DataStream<Event> dataStream);
}