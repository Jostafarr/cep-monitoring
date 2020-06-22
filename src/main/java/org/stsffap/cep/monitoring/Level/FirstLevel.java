package org.stsffap.cep.monitoring.Level;

import java.util.ArrayList;

import org.apache.flink.api.java.tuple.Tuple2;
import org.stsffap.cep.monitoring.pyramid.BaseLevel;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.Transformation;
import org.stsffap.cep.monitoring.transformations.MonitoringEventTransformation;
import org.stsffap.cep.monitoring.events.MonitoringEvent;

public class FirstLevel extends BaseLevel {


    private ArrayList<Tuple2< Transformation, Event>> level = new ArrayList<Tuple2< Transformation, Event>>();


	private int b= 1;


	private int a = 1;    


    Tuple2<MonitoringEventTransformation, MonitoringEvent> monitoringEvent = new Tuple2<MonitoringEventTransformation, MonitoringEvent>(new MonitoringEventTransformation(), new MonitoringEvent( a ,  b ) ) ;
    level.add(monitoringEvent);

    @Override
    public void build() {
        // TODO Auto-generated method stub

    }
    
}