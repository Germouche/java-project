package TestPackage;

import EventPackage.EventManager;

public class TestEventManager {
    public static void main(String[] args){
        EventManager eManager = new EventManager();

        // Test
        EventImplementationTest e1 = new EventImplementationTest(0);
        EventImplementationTest e2 = new EventImplementationTest(2);
        EventImplementationTest e3 = new EventImplementationTest(10);

        eManager.addEvent(e1);
        eManager.addEvent(e2);
        eManager.addEvent(e3);

        for(int i = 0; i < 20; i++){
            eManager.next();
        }
    }
}
