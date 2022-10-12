package EventPackage;

public abstract class Event implements Comparable<Event> {
    protected long date;

    public Event(long date){
        this.date = date;
    }

    public long getDate(){
        return date;
    }

    @Override
    public int compareTo(Event e){
        return e.getDate() > this.date ? 0 : 1;
    }

    @Override
    public String toString(){
        return String.format("Event date %d", date);
    }

    public abstract void execute();

}
