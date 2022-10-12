package EventPackage;

import java.util.PriorityQueue;

public class EventManager {
    private long currentDate;
    private PriorityQueue<Event> eventQueue;

    public EventManager(){
        this.currentDate = 0;
        this.eventQueue = new PriorityQueue<Event>();
    }


    public void addEvent(Event e){
        eventQueue.add(e);
    }


    public long getCurrentDate(){
        return currentDate;
    }

    /**
     * Passage à l'instant de temps suivant
     */
    public void next(){
        currentDate++;

        System.out.println(String.format("[EventManager]Passage au temps %d", currentDate));

        while(!isFinished() && eventQueue.peek().getDate() <= currentDate){
            eventQueue.poll().execute();
        }
    }


    /**
     * Retourne un booléen sur l'état vide ou non de la queue
     * @return Booléen d'état de la queue
     */
    public boolean isFinished(){
        return eventQueue.isEmpty();
    }


    public void restart(){
        eventQueue.clear();
    }
}
