package org.stsffap.cep.monitoring.Level;

import java.util.ArrayList;

import org.apache.flink.api.java.tuple.Tuple2;
import org.stsffap.cep.monitoring.pyramid.BaseLevel;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.Transformation;

public class FirstLevel extends BaseLevel {


    private ArrayList<Tuple2< Transformation, Event>> level = new ArrayList<Tuple2< Transformation, Event>>();    


    @Override
    public void build() {
        // TODO Auto-generated method stub

    }
    
}