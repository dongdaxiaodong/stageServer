package framework.utils.httpUtils;

import controller.AddController;
import controller.PowController;

import java.io.IOException;
import java.util.HashMap;

import static framework.utils.httpUtils.ResponseType.HTML;
import static framework.utils.httpUtils.ResponseType.JSON;

public class Dispatcher {
    public static Response dispatch(RequestType type, HashMap<String,String> params) throws IOException {
        if(type == RequestType.GET){
            try {
                switch (params.get("path")){
                    case "/pow":
                        return new Response(JSON, PowController.getInstance().Pow(params.get("base"),params.get("index")));
                    case "/add":
                        return new Response(JSON, AddController.getInstance().Add(params.get("left"),params.get("right")));
                    default:
                        return new Response(HTML,StaticPage.getInstance().get("404.html"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
                catch (NullPointerException p){
                return new Response(HTML,StaticPage.getInstance().get("400.html"));
                }
        }else{
            return new Response(HTML,StaticPage.getInstance().get("405.html"));
        }
        return null;
    }
}
