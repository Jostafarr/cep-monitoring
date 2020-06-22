package org.stsffap.cep.monitoring.Level;

import java.util.ArrayList;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.stsffap.cep.monitoring.pyramid.BaseLevel;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.Transformation;
import org.stsffap.cep.monitoring.transformations.MonitoringEventTransformation;
import org.stsffap.cep.monitoring.events.MonitoringEvent;

public class FirstLevel extends BaseLevel {


    private ArrayList<DataStream<Event>> level = new ArrayList<DataStream<Event>>();
       
    

    @Override
    public void build() {
        this.level.add(new MonitoringEventTransformation().transform(env));

    }
    
}