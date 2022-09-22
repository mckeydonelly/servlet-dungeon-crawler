package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.dto.*;
import com.mckeydonelly.servletdungeoncrawler.engine.state.State;
import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.crate.Crate;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RoomServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RoomServlet.class);
    private final SessionManager sessionManager;
    private final Repository<Item, String> itemRepository;
    private final Repository<Crate, String> crateRepository;
    private final Repository<Npc, String> npcRepository;
    private final GameMap gameMap;

    public RoomServlet(SessionManager sessionManager,
                       Repository<Item, String> itemRepository,
                       Repository<Crate, String> crateRepository,
                       Repository<Npc, String> npcRepository, GameMap gameMap) {
        this.sessionManager = sessionManager;
        this.itemRepository = itemRepository;
        this.crateRepository = crateRepository;
        this.npcRepository = npcRepository;
        this.gameMap = gameMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        var user = sessionManager.validateUser(request);

        List<PathInfo> paths = preparePathsInfo(user);
        UserHealthInfo userHealthInfo = prepareUserHealthInfo(user);
        List<LocationObjectInfo> objectsOnLoc = prepareLocationObjectsInfo(user);
        List<NpcInfo> npcs = prepareNpcInfo(user);
        String locationName = gameMap.fetchLocationById(user.getCurrentLocationId()).getName();

        request.setAttribute("mapImgPath", gameMap.getMapImgPath());
        request.setAttribute("paths", paths);
        request.setAttribute("userHealthInfo", userHealthInfo);
        request.setAttribute("objects", objectsOnLoc);
        request.setAttribute("npcs", npcs);
        request.setAttribute("locationName", locationName);
        request.setAttribute("gameLog", user.getState().getGameLog());

        logger.info("Response: mapImgPath={}, paths={}, userHealthInfo={}, objectsOnLoc={}, npcs={}, locationName={}, gameLogs=...",
                gameMap.getMapImgPath(),
                paths,
                userHealthInfo,
                objectsOnLoc,
                npcs,
                locationName);
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/game.jsp")
                .forward(request, response);
    }

    private List<PathInfo> preparePathsInfo(User user) {
        logger.info("Preparing PathsInfo...");
        State state = user.getState();
        return gameMap.fetchLocationById(user.getCurrentLocationId())
                .getPaths()
                .entrySet()
                .stream()
                .map(entry -> PathInfo.builder()
                        .isOpened(state.checkPath(user.getCurrentLocationId()+ "-" + entry.getKey(), entry.getValue()))
                        .locationInfo(LocationInfo.builder()
                                .id(entry.getValue().getLinkedLocationId())
                                .name(gameMap.fetchLocationById(entry.getValue().getLinkedLocationId()).getName())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    private static UserHealthInfo prepareUserHealthInfo(User user) {
        logger.info("Preparing UserHealthInfo...");
        return UserHealthInfo.builder()
                .health(user.getHealth())
                .maxHealth(user.getMaxHealth())
                .percentOfMax(user.getHealth() * 100 / user.getMaxHealth())
                .build();
    }

    private List<LocationObjectInfo> prepareLocationObjectsInfo(User user) {
        logger.info("Preparing LocationObjectsInfo...");
        State state = user.getState();

        Map<Integer, Item> itemOnLoc = getItemsOnLoc(user, state);
        Map<Integer, Crate> crateOnLoc = getCratesOnLoc(user);

        List<LocationObjectInfo> objectsOnLoc = new ArrayList<>();

        objectsOnLoc.addAll(itemOnLoc.entrySet()
                .stream()
                .map(entry -> LocationObjectInfo.builder()
                        .id(entry.getKey())
                        .objectId(entry.getValue().getId())
                        .type(LocationObjectInfo.ObjectType.ITEM)
                        .name(entry.getValue().getName())
                        .imgPath(entry.getValue().getImgPath())
                        .isActive(true)
                        .build())
                .collect(Collectors.toList()));

        objectsOnLoc.addAll(crateOnLoc.entrySet()
                .stream()
                .map(entry -> LocationObjectInfo.builder()
                        .id(entry.getKey())
                        .objectId(entry.getValue().getId())
                        .type(LocationObjectInfo.ObjectType.CRATE)
                        .name(entry.getValue().getName())
                        .imgPath(entry.getValue().getImgPathClosed())
                        .isActive(!state.checkCrate(entry.getKey()))
                        .build())
                .collect(Collectors.toList()));
        return objectsOnLoc;
    }

    private Map<Integer, Crate> getCratesOnLoc(User user) {
        logger.info("Preparing crates on location");
        return gameMap.fetchLocationById(user.getCurrentLocationId())
                .getCrateOnLocationList()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> crateRepository.findById(entry.getValue())));
    }

    private Map<Integer, Item> getItemsOnLoc(User user, State state) {
        logger.info("Preparing items on location");
        return gameMap.fetchLocationById(user.getCurrentLocationId())
                .getItemOnLocationList()
                .entrySet()
                .stream()
                .filter(entry -> state.checkItem(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> itemRepository.findById(entry.getValue())));
    }

    private List<NpcInfo> prepareNpcInfo(User user) {
        logger.info("Preparing NpcInfo...");
        State state = user.getState();

        return gameMap.fetchLocationById(user.getCurrentLocationId())
                .getNpcOnLocationList()
                .entrySet()
                .stream()
                .filter(entry -> state.checkNpc(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> npcRepository.findById(entry.getValue())
                ))
                .entrySet()
                .stream()
                .map(entry -> NpcInfo.builder()
                        .mapId(entry.getKey())
                        .npcId(entry.getValue().getId())
                        .name(entry.getValue().getName())
                        .imgPath(entry.getValue().getImgPath())
                        .build())
                .collect(Collectors.toList());
    }
}
