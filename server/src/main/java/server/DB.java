package server;

import model.userData;

import java.util.HashMap;
import java.util.Map;

    public class DB {

        public static Map<String, model.userData> userDataMap = new HashMap<>();
        public static Map<String, model.authData> authDataMap = new HashMap<>();
        public static Map<Integer, model.gameData> gameDataMap = new HashMap<>();

}
