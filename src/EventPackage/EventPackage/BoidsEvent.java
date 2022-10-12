package EventPackage;

import EntityPackage.Boids;

public class BoidsEvent extends Event {
    private Boids boids;

    public BoidsEvent(Boids boids, long date){
        super(date);

        this.boids = boids;
    }

    public void execute(){
        boids.animateBoids();
    }
}
