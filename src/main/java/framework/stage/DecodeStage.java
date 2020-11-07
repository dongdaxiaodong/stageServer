package framework.stage;

import framework.utils.Event;
import framework.utils.httpUtils.RequestType;

import java.util.ArrayList;

public class DecodeStage extends AbstractStage{
    public DecodeStage(){
        StageMap.getInstance().stageMap.put("decode",this);
    }

    @Override
    public void doJob(ArrayList<Event> elist) {
        Runnable task = new HandleThread(elist);
        pool.execute(task);
    }
    private RequestType parseHttpType(String type){
        switch (type) {
            case "GET": return RequestType.GET;
            case "POST": return RequestType.POST;
            case "PUT": return RequestType.PUT;
            case "DELETE": return RequestType.DELETE;
            case "CONNECT": return RequestType.CONNECT;
            case "OPTIONS": return RequestType.OPTIONS;
            case "HEAD": return RequestType.HEAD;
            case "TRACE": return RequestType.TRACE;
        }
        return RequestType.ERROR;
    }
    class HandleThread implements Runnable {
        ArrayList<Event> elist;
        public HandleThread(ArrayList<Event> elist){
            this.elist = elist;
        }

        @Override
        public void run() {
            try {
                System.out.println("in decode stage");
                Event e;
                for(int i=0;i<BatchSize;i++){
                    e = elist.get(i);
                    if(e.type == Event.Type.Decode){
                        String str = e.Packet;
                        Event event = new Event(e.key,Event.Type.ReadResponse);
                        String[] lines = str.split("\\r?\\n");
                        String[] params = lines[0].split(" ");
                        event.requestType = parseHttpType(params[0]);
                        event.Packet = params[1];
                        StageMap.getInstance().stageMap.get("app").Enqueue(event);
                    }
                }
            }
            catch (Exception e){

            }
        }
    }
}
