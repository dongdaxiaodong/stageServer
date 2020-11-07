package framework.stage;

import framework.utils.Event;
import framework.utils.httpUtils.Dispatcher;
import framework.utils.httpUtils.Response;
import framework.utils.httpUtils.RestfulParser;

import java.util.ArrayList;
import java.util.HashMap;

public class AppStage extends AbstractStage{
    public AppStage(){
        StageMap.getInstance().stageMap.put("app",this);
    }

    @Override
    public void doJob(ArrayList<Event> elist) {
        Runnable task = new HandleThread(elist);
        pool.execute(task);
    }
    class HandleThread implements Runnable {
        ArrayList<Event> elist;
        public HandleThread(ArrayList<Event> elist){
            this.elist = elist;
        }
        @Override
        public void run(){
            try {
                System.out.println("in app stage");
                Event e;
                for(int i=0;i<BatchSize;i++){
                    e = elist.get(i);
                    if(e.type == Event.Type.ReadResponse){
                        System.out.println("APP Read "+ e.Packet);
                        HashMap<String,String> params = RestfulParser.parse(e.Packet);
                        System.out.println(params.toString());
                        Event event = new Event(e.key,Event.Type.Encode);
                        Response response = Dispatcher.dispatch(e.requestType,params);
                        event.responseType = response.responseType;
                        event.Packet = response.content;
                        StageMap.getInstance().stageMap.get("encode").Enqueue(event);
                    }else if(e.type == Event.Type.WriteResponse){
                        System.out.println("Write Done");
                    }
                }
            }catch (Exception e){

            }
        }
    }
}
