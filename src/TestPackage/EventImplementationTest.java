package TestPackage;

import EventPackage.Event;

public class EventImplementationTest extends Event {
    public EventImplementationTest(long date){
        super(date);
    }

    public void execute(){
        System.out.println(String.format("[Event] Execution evenement prévue à date %d", date));
    }
}
