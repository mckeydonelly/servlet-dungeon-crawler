package com.mckeydonelly.servletdungeoncrawler.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
    }

    @Test
    void should_return_exception_when_modify_inventory_list() {
        List<String> inventoryList = user.getInventory();

        assertThrows(UnsupportedOperationException.class, () -> inventoryList.add("testItemId"));
    }

    @Test
    void should_return_exception_when_modify_enemy_killed_list() {
        var enemyKilled = user.getEnemyKilled();

        assertThrows(UnsupportedOperationException.class, () -> enemyKilled.put("testEnemyId", 1));
    }

    @Test
    void should_return_exception_when_modify_equipped_items_list() {
        var equippedItems = user.getEquippedItems();

        assertThrows(UnsupportedOperationException.class, () -> equippedItems.add("testItemId"));
    }

    @Test
    void should_return_exception_when_modify_quests_list() {
        var quests = user.getQuests();

        assertThrows(UnsupportedOperationException.class, () -> quests.put("testQuestId", QuestStatus.ACTIVE));
    }

    @Test
    void should_return_true_false_if_user_have_item() {
        user.addItem("swordTest");

        assertTrue(user.isExistsItem("swordTest"));
        assertFalse(user.isExistsItem("something"));
    }

    @Test
    void should_return_true_false_if_user_have_quest() {
        user.addQuest("questTestId");

        assertTrue(user.isExistsQuest("questTestId"));
        assertFalse(user.isExistsQuest("something"));
    }

    @Test
    void should_return_true_false_if_user_kill_enemy() {
        user.addEnemyKilled("enemyId");

        assertTrue(user.isEnemyKilled("enemyId"));
        assertFalse(user.isEnemyKilled("something"));
    }

    @Test
    void should_change_quest_status_to_succeeded() {
        user.addQuest("questTestId");

        user.changeQuestStatus("questTestId", QuestStatus.SUCCEEDED);

        assertEquals(QuestStatus.SUCCEEDED, user.getQuests().get("questTestId"));
    }
}