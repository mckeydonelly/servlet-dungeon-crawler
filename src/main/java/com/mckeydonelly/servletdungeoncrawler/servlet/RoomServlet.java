package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.dto.*;
import com.mckeydonelly.servletdungeoncrawler.engine.gamestate.GameState;
import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.crate.Crate;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.repositories.CrateRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.NpcRepository;
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
    private final ItemRepository itemRepository;
    private final CrateRepository crateRepository;
    private final NpcRepository npcRepository;
    private final GameMap gameMap;

    public RoomServlet(SessionManager sessionManager, ItemRepository itemRepository, CrateRepository crateRepository, NpcRepository npcRepository, GameMap gameMap) {
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

        request.setAttribute("paths", paths);
        request.setAttribute("userHealthInfo", userHealthInfo);
        request.setAttribute("objects", objectsOnLoc);
        request.setAttribute("npcs", npcs);
        request.setAttribute("locationName", locationName);
        request.setAttribute("gameLog", user.getGameState().getGameLog());

        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/game.jsp")
                .forward(request, response);
    }

    private List<PathInfo> preparePathsInfo(User user) {
        GameState gameState = user.getGameState();
        return gameMap.fetchLocationById(user.getCurrentLocationId())
                .getPaths()
                .entrySet()
                .stream()
                .map(entry -> PathInfo.builder()
                        .isOpened(gameState.checkPath(entry.getKey(), entry.getValue()))
                        .locationInfo(LocationInfo.builder()
                                .id(entry.getValue().getLinkedLocationId())
                                .name(gameMap.fetchLocationById(entry.getValue().getLinkedLocationId()).getName())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    private static UserHealthInfo prepareUserHealthInfo(User user) {
        return UserHealthInfo.builder()
                .health(user.getHealth())
                .maxHealth(user.getMaxHealth())
                .percentOfMax(user.getHealth() * 100 / user.getMaxHealth())
                .build();
    }

    private List<LocationObjectInfo> prepareLocationObjectsInfo(User user) {
        GameState gameState = user.getGameState();

        Map<Integer, Item> itemOnLoc = gameMap.fetchLocationById(user.getCurrentLocationId())
                .getItemOnLocationList()
                .entrySet()
                .stream()
                .filter(entry -> gameState.checkItem(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> itemRepository.findById(entry.getValue())));
        Map<Integer, Crate> crateOnLoc = gameMap.fetchLocationById(user.getCurrentLocationId())
                .getCrateOnLocationList()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> crateRepository.findById(entry.getValue())));
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
                        .isActive(!gameState.checkCrate(entry.getKey()))
                        .build())
                .collect(Collectors.toList()));
        return objectsOnLoc;
    }

    private List<NpcInfo> prepareNpcInfo(User user) {
        GameState gameState = user.getGameState();

        return gameMap.fetchLocationById(user.getCurrentLocationId())
                .getNpcOnLocationList()
                .entrySet()
                .stream()
                .filter(entry -> gameState.checkNpc(entry.getKey()))
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
