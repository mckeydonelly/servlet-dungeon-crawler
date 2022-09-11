package com.mckeydonelly.servletdungeoncrawler.engine.gamestate;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.path.Path;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO Double check logic
@Data
public class GameState {
    private final List<String> gameLog;
    private final Map<Integer, NpcState> npcStateOnMap;
    private Map<Integer, ItemState> itemStateOnMap;
    private Map<Integer, CrateState> crateStateOnMap;
    private Map<Integer, PathState> pathStateOnMap;

    public GameState() {
        this.gameLog = new ArrayList<>();
        this.npcStateOnMap = new HashMap<>();
        this.itemStateOnMap = new HashMap<>();
        this.crateStateOnMap = new HashMap<>();
        this.pathStateOnMap = new HashMap<>();
    }

    public void addGameLog(String message) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        gameLog.add("[" + dateTime + "] " + message);
    }

    public boolean checkPath(int mapPathId, Path path) {
        if (pathStateOnMap.containsKey(mapPathId)) {
            return pathStateOnMap.get(mapPathId).isOpen();
        }
        return path.isOpen();
    }

    public boolean checkItem(int mapItemId) {
        return !itemStateOnMap.containsKey(mapItemId);
    }

    public boolean checkCrate(int mapCrateId) {
        if(crateStateOnMap.containsKey(mapCrateId)) {
            return crateStateOnMap.get(mapCrateId).isOpen();
        }
        return false;
    }

    public boolean checkNpc(int mapNpcId) {
        if(npcStateOnMap.containsKey(mapNpcId)) {
            return !npcStateOnMap.get(mapNpcId).isDeath();
        }
        return true;
    }
}
