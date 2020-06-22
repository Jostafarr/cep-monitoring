package org.stsffap.cep.monitoring.pyramid;

import org.apache.flink.streaming.api.graph.StreamGraph;

public class Hierachy {

    private Environment env;
    private StreamGraph graph;

    public Hierachy(Environment env){
        this.env = env;
    }

    public void execute() throws Exception {
        this.env.run();
    }
}