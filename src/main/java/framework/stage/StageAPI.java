package framework.stage;

import framework.utils.Event;

import java.util.ArrayList;

public interface StageAPI {
    public void Enqueue(Event e);
    public void doJob(ArrayList<Event> elist);
}
