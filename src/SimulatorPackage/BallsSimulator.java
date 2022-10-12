package SimulatorPackage;

import java.awt.Color;

import EntityPackage.Balls;
import EventPackage.BallsEvent;
import gui.GUISimulator;
import gui.*;

public class BallsSimulator extends Simulator{
    // TODO : Mettre un numBalls par groupe
    private int numBalls; // Nombre de balles par groupe
    private Balls balls1;
    private Balls balls2;

    public BallsSimulator(int num, GUISimulator gui){
        super(gui);

        this.numBalls = num;

        this.balls1 = new Balls(this.numBalls, gui.getPanelWidth(), gui.getPanelHeight());
        this.balls2 = new Balls(this.numBalls, gui.getPanelWidth(), gui.getPanelHeight());

        printElements();
    }

    public void animate(){
        //balls.animateBalls();

        // 1 event par groupe de balle
        eManager.addEvent(new BallsEvent(balls1, eManager.getCurrentDate() + 1));
        eManager.addEvent(new BallsEvent(balls2, eManager.getCurrentDate() + 2));
    }

    public void printElements(){
        for(int i=0; i < this.numBalls; i++){
            this.gui.addGraphicalElement(new Oval((int) balls1.getPosition(i)[0], (int) balls1.getPosition(i)[1], Color.RED, Color.RED, 20));
            this.gui.addGraphicalElement(new Oval((int) balls2.getPosition(i)[0], (int) balls2.getPosition(i)[1], Color.YELLOW, Color.YELLOW, 20));
        }
    }

    public final void restart(){
        this.balls1.reInit();
        this.balls2.reInit();
    }
}
