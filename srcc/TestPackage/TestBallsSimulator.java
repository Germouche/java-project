package TestPackage;

import SimulatorPackage.BallsSimulator;

import java.awt.Color;
import gui.*;

public class TestBallsSimulator {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(500, 500, Color.BLACK);

        BallsSimulator simu = new BallsSimulator(5, gui);
        gui.setSimulable(simu);
    }
}
