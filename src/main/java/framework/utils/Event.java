package framework.utils;

import framework.utils.httpUtils.RequestType;
import framework.utils.httpUtils.ResponseType;

import java.nio.channels.SelectionKey;

public class Event{
    public enum Type {Read,Write,ReadResponse,WriteResponse,Flush,Decode,Encode}
    public Type type;
    public RequestType requestType;
    public ResponseType responseType;
    public SelectionKey key;
    public String Packet;
    public Event(SelectionKey key,Type type){
        this.type = type;
        this.key = key;
    }
}