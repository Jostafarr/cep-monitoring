/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.stsffap.cep.monitoring;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternFlatSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.IngestionTimeExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.stsffap.cep.monitoring.sources.MonitoringEventSource;
import org.stsffap.cep.monitoring.transformations.MonitoringEventTransformation;
import org.stsffap.cep.monitoring.transformations.TemperatureAlertTransformation;
import org.stsffap.cep.monitoring.transformations.TemperatureWarningTransformation;
import org.stsffap.cep.monitoring.events.MonitoringEvent;
import org.stsffap.cep.monitoring.events.TemperatureEvent;
import org.stsffap.cep.monitoring.events.TemperatureAlert;
import org.stsffap.cep.monitoring.events.TemperatureWarning;
import org.stsffap.cep.monitoring.pyramid.Event;
import org.stsffap.cep.monitoring.pyramid.Transformation;

import java.util.List;
import java.util.Map;

/**
 * CEP example monitoring program
 *
 * This example program generates a stream of monitoring events which are analyzed using Flink's CEP library.
 * The input event stream consists of temperature and power events from a set of racks. The goal is to detect
 * when a rack is about to overheat. In order to do that, we create a CEP pattern which generates a
 * TemperatureWarning whenever it sees two consecutive temperature events in a given time interval whose temperatures
 * are higher than a given threshold value. A warning itself is not critical but if we see two warning for the same rack
 * whose temperatures are rising, we want to generate an alert. This is achieved by defining another CEP pattern which
 * analyzes the stream of generated temperature warnings.
 */
public class CEPMonitoring {
    

    private static final int MAX_RACK_ID = 10;
    private static final long PAUSE = 100;
    private static final double TEMPERATURE_RATIO = 0.5;
    private static final double POWER_STD = 10;
    private static final double POWER_MEAN = 100;
    private static final double TEMP_STD = 20;
    private static final double TEMP_MEAN = 80;

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Use ingestion time => TimeCharacteristic == EventTime + IngestionTimeExtractor
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Transformation transform0 = new MonitoringEventTransformation();
        Transformation transform1 = new TemperatureWarningTransformation();
        Transformation transform2 = new TemperatureAlertTransformation();
        
        
        
        // Input stream of monitoring events
      

        DataStream<Event> alerts = ((TemperatureAlertTransformation) transform2)
                .transform(((TemperatureWarningTransformation) transform1)
                        .transform(
                ((MonitoringEventTransformation) transform0).transform(env)));
        
       
    
        // Print the warning and alert events to stdout
       //() warnings.print();
        alerts.print();

        env.execute("CEP monitoring job");
    }
}
