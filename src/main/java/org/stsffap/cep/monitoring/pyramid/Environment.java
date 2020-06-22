package org.stsffap.cep.monitoring.pyramid;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public abstract class Environment  {
    public abstract void run ()throws Exception;
}