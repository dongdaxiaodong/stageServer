package framework.stage;

import java.util.HashMap;

public class StageMap {
    public HashMap<String,StageAPI> stageMap = new HashMap<>();
    private StageMap(){};
    private static class SingletonInner {
        private static StageMap stageMap = new StageMap();
    }
    public static StageMap getInstance(){
        return SingletonInner.stageMap;
    }
}
