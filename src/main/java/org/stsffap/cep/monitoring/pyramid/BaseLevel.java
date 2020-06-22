package org.stsffap.cep.monitoring.pyramid;

import java.util.ArrayList;

import org.apache.flink.api.java.tuple.Tuple2;

public abstract class BaseLevel extends Level {
    
private ArrayList<Tuple2< SourceTransformation, Event>> level;

}