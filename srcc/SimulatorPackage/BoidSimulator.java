package SimulatorPackage;
import java.awt.Color;

import EntityPackage.Boids;
import EventPackage.BoidsEvent;
import gui.GUISimulator;
import gui.*;

public class BoidSimulator extends Simulator{
    private int numBoids;
    private Boids boids1;
    private Boids boids2;

    private static Color TRANSP = new Color(0, 0, 0, 0);

    public BoidSimulator(int num, GUISimulator gui){
        super(gui);

        this.numBoids = num;

        this.boids1 = new Boids(this.numBoids, gui.getWidth(), gui.getHeight(), 1);
        this.boids2 = new Boids(this.numBoids, gui.getWidth(), gui.getHeight(), 1);

        this.boids1.addExternBoids(this.boids2);
        this.boids2.addExternBoids(this.boids1);

        printElements();
    }

    public void restart(){
        this.boids1.reInit();
        this.boids2.reInit();
    }

    public void animate(){
        //boids.animateBoids();

        eManager.addEvent(new BoidsEvent(boids1, eManager.getCurrentDate() + 1));
        eManager.addEvent(new BoidsEvent(boids2, eManager.getCurrentDate() + 2));
    }

    @Override
    public void printElements(){
        for(int i=0; i < this.numBoids; i++){
            double[] aim1 = boids1.getOrientation(i);

            gui.addGraphicalElement(new Oval((int) boids1.getPosition(i)[0], (int) boids1.getPosition(i)[1], Color.YELLOW, TRANSP, boids1.getRadius()*2)); // FOV portée
            gui.addGraphicalElement(new Rectangle((int) (boids1.getPosition(i)[0] + aim1[0]*boids1.getRadius()), (int) (boids1.getPosition(i)[1] + aim1[1]*boids1.getRadius()), Color.YELLOW, Color.YELLOW, 5)); // Orientation
            gui.addGraphicalElement(new Rectangle((int) boids1.getPosition(i)[0], (int) boids1.getPosition(i)[1], Color.GREEN, Color.GREEN, 5)); // Boid

            double[] aim2 = boids2.getOrientation(i);

            gui.addGraphicalElement(new Oval((int) boids2.getPosition(i)[0], (int) boids2.getPosition(i)[1], Color.RED, TRANSP, boids2.getRadius()*2)); // FOV portée
            gui.addGraphicalElement(new Rectangle((int) (boids2.getPosition(i)[0] + aim2[0]*boids2.getRadius()), (int) (boids2.getPosition(i)[1] + aim2[1]*boids2.getRadius()), Color.RED, Color.RED, 5)); // Orientation
            gui.addGraphicalElement(new Rectangle((int) boids2.getPosition(i)[0], (int) boids2.getPosition(i)[1], Color.GREEN, Color.GREEN, 5)); // Boid
        }
    }
}
