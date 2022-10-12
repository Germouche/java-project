package TestPackage;

import SimulatorPackage.SchellingSimulator;
import gui.GUISimulator ;
import java.awt.Color ;

public class TestSchellingSim {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);
        gui.setSimulable(new SchellingSimulator(gui, 50, 50));
    }
}
