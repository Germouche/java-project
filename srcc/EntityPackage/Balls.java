package EntityPackage;

import java.awt.Point;
import java.util.*;
import EventPackage.Event;

public class Balls extends Event{
    protected Point[] points;
    protected double[][] ballsVelocity;

    protected double[][] initialPos;
    protected double[][] initialVelocity;

    protected int[] spaceSize; // Dimensions de l'espace


    /**
     * Constructeur
     * @param numBalls
     * @param w Largeur de l'espace simulateur
     * @param h Hauteur de l'espace simulateur
     */
    public Balls(int numBalls, int w, int h){
        super(0);

        Random r = new Random();
        this.points = new Point[numBalls];
        this.initialPos = new double[numBalls][2];

        this.ballsVelocity = new double[numBalls][2];
        this.initialVelocity = new double[numBalls][2];

        this.spaceSize = new int[]{w, h};

        for(int i=0; i < numBalls; i++){
            this.initialPos[i] = new double[]{50 + r.nextInt(this.spaceSize[0]-50), 50 + r.nextInt(this.spaceSize[1]-50)};
            this.points[i] = new Point((int) initialPos[i][0], (int) initialPos[i][1]);

            this.initialVelocity[i] = new double[]{(double) (r.nextInt(21) - 10), (double) (r.nextInt(21) - 10)};
            this.ballsVelocity[i] = initialVelocity[i];
        }
    }


    public int size(){
        return this.points.length;
    }


    public double[] getPosition(int index){
        return new double[]{this.points[index].getX(), this.points[index].getY()};
    }


    public double[] getVelocity(int index){
        return this.ballsVelocity[index];
    }


    public int[] getSpaceSize(){
        return spaceSize;
    }


    public void setPosition(int index, double x, double y){
        points[index].setLocation((double) x, (double) y);
    }


    /**
     * Fait mouvoir les balles d'une position (dx, dy)
     * @param dx Variation de position en abscisse
     * @param dy Variation de position en ordonnée
     */
    public void translate(int dx, int dy){
        for(Point e : this.points){
            e.setLocation(e.getX() + dx, e.getY() + dy);
        }
    }


    public void reInit(){
        for(int i = 0; i < size(); i++){
            points[i].setLocation(initialPos[i][0], initialPos[i][1]);
            ballsVelocity[i][0] = initialVelocity[i][0];
            ballsVelocity[i][1] = initialVelocity[i][1];
        }
    }


    public String toString(){
        String str = "";
        for(int i=0; i < this.points.length; i++){
            str += String.format("Balle %d : (%f, %f)\n", i, this.points[i].getX(), this.points[i].getY());
        }

        return str;
    }


    public void execute(){
        System.out.println("Execute de balls");
        animateBalls();
    }


    /**
     * Anime les balles à vitesse constante (initialisée aléatoirement au début)
     */
    public final void animateBalls(){
        for(int i=0; i < size(); i++){
            setPosition(i, getPosition(i)[0] + getVelocity(i)[0], getPosition(i)[1] + getVelocity(i)[1]);

            // Gestion du hors cadre
            if(getPosition(i)[0] < 0 || getPosition(i)[0] > spaceSize[0]){
                getVelocity(i)[0] *= -1;
                setPosition(i, spaceSize[0] * ((getPosition(i)[0] < 0) ? 0 : 1), getPosition(i)[1]);
            }

            if(getPosition(i)[1] < 0 || getPosition(i)[1] > spaceSize[1]){
                getVelocity(i)[1] *= -1;
                setPosition(i, getPosition(i)[0], spaceSize[1] * ((getPosition(i)[1] < 0) ? 0 : 1));
            }
        }
    }
}
