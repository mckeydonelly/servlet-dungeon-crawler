package com.mckeydonelly.servletdungeoncrawler.user;

import com.mckeydonelly.servletdungeoncrawler.engine.gamestate.GameState;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
@Setter
public class User {
    private String id;
    private String name;
    private int health;
    private int maxHealth;
    private int currentLocationId;
    private int totalGames;
    @Builder.Default
    private List<String> inventory = new ArrayList<>();
    @Builder.Default
    private List<String> equippedItems = new ArrayList<>();
    @Builder.Default
    private Map<String, QuestStatus> quests = new HashMap<>();
    @Builder.Default
    private GameState gameState = new GameState();
    @Builder.Default
    private Map<String, Integer> enemyKilled = new HashMap<>();
}
