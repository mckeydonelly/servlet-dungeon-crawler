package com.mckeydonelly.servletdungeoncrawler.user;

import com.mckeydonelly.servletdungeoncrawler.engine.state.State;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Builder
@Getter
public class User extends Entity {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private String id;
    private String name;
    private int health;
    private int maxHealth;
    @Setter
    private int currentLocationId;
    private int totalGames;
    @Builder.Default
    private State state = new State();
    @Builder.Default
    private List<String> inventory = new ArrayList<>();
    @Builder.Default
    private List<String> equippedItems = new ArrayList<>();
    @Builder.Default
    private Map<String, QuestStatus> quests = new HashMap<>();
    @Builder.Default
    private Map<String, Integer> enemyKilled = new HashMap<>();

    public List<String> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    public Map<String, Integer> getEnemyKilled() {
        return Collections.unmodifiableMap(enemyKilled);
    }

    public List<String> getEquippedItems() {
        return Collections.unmodifiableList(equippedItems);
    }

    public Map<String, QuestStatus> getQuests() {
        return Collections.unmodifiableMap(quests);
    }

    public boolean isExistsItem(String itemId) {
        return inventory.contains(itemId);
    }

    public boolean isExistsQuest(String questId) {
        return quests.containsKey(questId);
    }

    public boolean isEnemyKilled(String enemyId) {
        return enemyKilled.containsKey(enemyId);
    }

    public void addEnemyKilled(String enemyId) {
        enemyKilled.put(enemyId, enemyKilled.getOrDefault(enemyId, 0) + 1);
    }

    public void addItem(String itemId) {
        inventory.add(itemId);
    }

    public void addQuest(String questId) {
        quests.putIfAbsent(questId, QuestStatus.ACTIVE);
    }

    public void changeQuestStatus(String questId, QuestStatus questStatus) {
        logger.info("User change quest {} status to {}", questId, questStatus);
        quests.put(questId, questStatus);
    }
}
