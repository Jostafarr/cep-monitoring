package org.stsffap.cep.monitoring.pyramid;

public class Hierachy {
    
    private Environment env;

    public Hierachy(Environment env){
        this.env = env;
    }

    public void execute() throws Exception {
        this.env.execute();
    }
}