package com.mckeydonelly.servletdungeoncrawler.engine.state;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.path.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class State {
    private static final Logger logger = LoggerFactory.getLogger(State.class);

    private final List<String> gameLog = new ArrayList<>();
    private final Map<Integer, NpcState> npcStateOnMap = new HashMap<>();
    //TODO для уникальных предметов на карте есть свой айди, но что делать с остальными? взятыми из сундука например?
    //TODO не добавлять сюда например? и проверять не нужно в таком случае? важно проверить лишь те, что на карте, чтобы их из карты убрать.
    private final Map<Integer, ItemState> itemStateOnMap = new HashMap<>();
    private final Map<Integer, CrateState> crateStateOnMap = new HashMap<>();
    private final Map<String, PathState> pathStateOnMap = new HashMap<>();

    public List<String> getGameLog() {
        return Collections.unmodifiableList(gameLog);
    }

    public void addGameLog(String message) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        logger.info("Adding new message to gameLog: {}", message);
        gameLog.add("[" + dateTime + "] " + message);
    }

    public void addPath(String pathMapId, boolean open) {
        pathStateOnMap.put(pathMapId, PathState.builder()
                .isOpen(open)
                .build());
    }

    public void addCrate(int crateMapId, boolean open) {
        crateStateOnMap.put(crateMapId, CrateState.builder()
                .isOpen(open)
                .build());
    }

    public void addNpc(int npcMapId, int currentHealth) {
        npcStateOnMap.put(npcMapId, NpcState.builder()
                .isDeath(currentHealth <= 0)
                .currentHealth(currentHealth)
                .build());
    }

    /**
     * Adds an object taken from a location so as not to display it at that location in the future
     *
     * @param itemMapId Unique itemId on map
     */
    public void addItem(int itemMapId) {
        itemStateOnMap.put(itemMapId, ItemState.builder()
                .isTaken(true)
                .build());
    }

    /**
     * Filters closed/open locations that have been open
     *
     * @param mapPathId Path id
     * @param path      current path
     * @return boolean
     */
    public boolean checkPath(String mapPathId, Path path) {
        if (pathStateOnMap.containsKey(mapPathId)) {
            return pathStateOnMap.get(mapPathId).isOpen();
        }
        return path.isOpen();
    }

    public boolean checkItem(int mapItemId) {
        return !itemStateOnMap.containsKey(mapItemId);
    }

    /**
     * Filters crates in location that have been open
     *
     * @param mapCrateId Crate id
     * @return boolean
     */
    public boolean checkCrate(int mapCrateId) {
        if (crateStateOnMap.containsKey(mapCrateId)) {
            return crateStateOnMap.get(mapCrateId).isOpen();
        }
        return false;
    }

    /**
     * Filters NPCs in locations that have been killed
     *
     * @param mapNpcId NPC id
     * @return boolean
     */
    public boolean checkNpc(int mapNpcId) {
        if (npcStateOnMap.containsKey(mapNpcId)) {
            return !npcStateOnMap.get(mapNpcId).isDeath();
        }
        return true;
    }
}
