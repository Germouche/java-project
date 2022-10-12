package TestPackage;

import SimulatorPackage.ConwaySimulator;
import gui.GUISimulator ;
import java.awt.Color ;

public class TestConwaySim {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);
        gui.setSimulable(new ConwaySimulator(gui, 20, 20));
    }
}
