package org.stsffap.cep.monitoring.pyramid;



public abstract class Event {

    protected int eventID;

    public Event(int eventID) {
        this.eventID = eventID;
    }

    public int getEventID(){
        return eventID;
    }

    public void setEventID(int eventID){
        this.eventID =  eventID;
    }

	@Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            Event event = (Event) obj;
            return event.canEquals(this) && eventID == event.eventID;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return eventID;
    }

    public boolean canEquals(Object obj) {
        return obj instanceof Event;
    }
}


    /* private MonitoringEvent monitoringEvent;
    private PowerEvent powerEvent;
    private TemperatureAlert temperatureAlert;
    private TemperatureEvent temperatureEvent;
    private TemperatureWarning temperatureWarning;

    public Event(MonitoringEvent monitoringEvent ){
       
        this.monitoringEvent = monitoringEvent;
    }
    
    public Event(PowerEvent powerEvent ){
        
        this.powerEvent = powerEvent;
    }

    public Event(TemperatureAlert temperatureAlert ){
       
        this.temperatureAlert = temperatureAlert;
    }

    public Event(TemperatureEvent temperatureEvent){
       
        this.temperatureEvent = temperatureEvent;
    }

    public Event(TemperatureWarning temperatureWarning){
        
        this.temperatureWarning = temperatureWarning;
    }
 */

