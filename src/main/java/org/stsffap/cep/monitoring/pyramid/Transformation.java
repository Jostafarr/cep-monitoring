package org.stsffap.cep.monitoring.pyramid;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.stsffap.cep.monitoring.events.TemperatureAlert;

public abstract class Transformation {
    
    public abstract DataStream<Event> transform(DataStream<Event> dataStream);

}