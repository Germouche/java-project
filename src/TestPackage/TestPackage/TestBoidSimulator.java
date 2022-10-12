package TestPackage;

import SimulatorPackage.BoidSimulator;

import java.awt.Color;
import gui.*;

public class TestBoidSimulator {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 500, Color.BLACK);

        BoidSimulator simu = new BoidSimulator(20, gui);
        gui.setSimulable(simu);
    }
}
