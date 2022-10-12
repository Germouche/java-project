package EventPackage;

import EntityPackage.Balls;

public class BallsEvent extends Event{
    private Balls balls;

    public BallsEvent(Balls balls, long date){
        super(date);

        this.balls = balls;
    }

    public void execute(){
        balls.animateBalls();
    }
}
