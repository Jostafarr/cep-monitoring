package org.stsffap.cep.monitoring.pyramid;

import org.apache.flink.streaming.api.graph.StreamGraph;
import org.apache.flink.streaming.api.datastream.DataStream;
import  org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class Hierachy {

    private StreamExecutionEnvironment env;
    private StreamGraph graph;


    public void addLevel(Level level){
        for(int i = 0; i < level.size(); i++){
           env.addOperator( level
                            .getLevel()
                            .get(i)
                            .getTransformation());

        }
    }

    /* public Hierachy(StreamExecutionEnvironment env){
        this.env = ) env;
        this.graph =  new StreamGraph( env);
    }
 */
   /*  public void execute() throws Exception {
        this.env.run();
    } */
}