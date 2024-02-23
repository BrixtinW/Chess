package server;

import model.userData;

import java.util.HashMap;
import java.util.Map;
import model.*;

    public class DB {

        public static Map<String, model.userData> userDataMap = new HashMap<>();
        public static Map<String, model.authData> authDataMap = new HashMap<>();
        public static Map<Integer, model.gameData> gameDataMap = new HashMap<>();

        static {
            userDataMap.put("Billy", new model.userData("Billy", "Bob", "Joe"));
            authDataMap.put("password", new authData("password", "NoMoreSnuggles"));
        }


}
