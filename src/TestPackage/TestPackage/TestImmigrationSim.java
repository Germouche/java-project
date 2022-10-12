package TestPackage;

import SimulatorPackage.ImmigrationSimulator;
import gui.GUISimulator ;
import java.awt.Color ;

public class TestImmigrationSim {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);
        gui.setSimulable(new ImmigrationSimulator(gui, 20, 20));
    }
}
