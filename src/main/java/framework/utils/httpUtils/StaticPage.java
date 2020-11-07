package framework.utils.httpUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class StaticPage {
    public HashMap<String,String> pageMap = new HashMap<>();
    private StaticPage(){};
    private static class SingletonInner {
        private static StaticPage staticPage = new StaticPage();
    }
    public static StaticPage getInstance(){
        return SingletonInner.staticPage;
    }
    public String get(String name) throws IOException {
        if(pageMap.containsKey(name)){
            return pageMap.get(name);
        }else{
            String res  = getFile(name);
            if(!res.isEmpty()){
                pageMap.put(name,res);
                return res;
            }else{
                return get("404.html");
            }
        }
    }

    public String getFile(String fileName) throws IOException {
        fileName = this.getClass().getClassLoader().getResource(fileName).getFile();
        final Path path = Paths.get(fileName);
        String contents = new String(Files.readAllBytes(path));
        return contents;
    }
}
