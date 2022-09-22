package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.QuestType;
import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepositoryInMemory;
import com.mckeydonelly.servletdungeoncrawler.repositories.QuestRepositoryInMemory;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.user.QuestStatus;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class AnswerActionManagerTest {
    private Repository<Quest, String> questRepository;
    private Repository<Item, String> itemRepository;
    private AnswerActionManager answerActionManager;
    private User user;

    @BeforeEach
    void setUp() {
        questRepository = new QuestRepositoryInMemory();
        itemRepository = new ItemRepositoryInMemory();
        answerActionManager = new AnswerActionManager(questRepository, itemRepository);
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
    void should_return_success_phrase_id() {
        Answer answer = new Answer();
        answer.setSuccessPhraseId(1);
        answer.setDefaultPhraseId(2);
        AnswerAction answerAction = new AnswerAction();
        answerAction.setAnswerActionType(AnswerActionType.TAKE_ITEM);
        answerAction.setItemId("sword123");
        answer.setAnswerAction(answerAction);

        int actual = answerActionManager.answerAction(user, answer);

        assertEquals(1, actual);
    }

    @Test
    void should_return_default_phrase() {
        Answer answer = new Answer();
        answer.setSuccessPhraseId(1);
        answer.setDefaultPhraseId(2);

        AnswerAction answerAction = new AnswerAction();
        answerAction.setAnswerActionType(AnswerActionType.CHECK_QUEST);
        answerAction.setQuestId("TestQuest");
        answer.setAnswerAction(answerAction);

        Quest quest = new Quest();
        quest.setId("TestQuest");
        quest.setTargetId("something123");
        quest.setQuestType(QuestType.ITEM_BRING);
        questRepository.save(quest);

        Item item = new Item();
        item.setId("something123");
        itemRepository.save(item);

        user.addItem("something1234");

        int actual = answerActionManager.answerAction(user, answer);

        assertEquals(2, actual);
    }

    @Test
    void should_add_item_to_user_inventory() {
        Answer answer = new Answer();
        answer.setSuccessPhraseId(1);
        answer.setDefaultPhraseId(2);
        AnswerAction answerAction = new AnswerAction();
        answerAction.setAnswerActionType(AnswerActionType.TAKE_ITEM);
        answerAction.setItemId("sword123");
        answer.setAnswerAction(answerAction);

        answerActionManager.answerAction(user, answer);

        assertTrue(user.isExistsItem("sword123"));
    }

    @Test
    void should_add_quest_to_user_quests() {
        Answer answer = new Answer();
        answer.setSuccessPhraseId(1);
        answer.setDefaultPhraseId(2);
        AnswerAction answerAction = new AnswerAction();
        answerAction.setAnswerActionType(AnswerActionType.TAKE_QUEST);
        answerAction.setQuestId("quest123");
        answer.setAnswerAction(answerAction);

        answerActionManager.answerAction(user, answer);

        assertTrue(user.isExistsQuest("quest123"));
    }

    @Test
    void should_change_quest_item_bring_to_success() {
        Answer answer = new Answer();
        answer.setSuccessPhraseId(1);
        answer.setDefaultPhraseId(2);

        AnswerAction answerAction = new AnswerAction();
        answerAction.setAnswerActionType(AnswerActionType.CHECK_QUEST);
        answerAction.setQuestId("TestQuest");
        answer.setAnswerAction(answerAction);

        Quest quest = new Quest();
        quest.setId("TestQuest");
        quest.setTargetId("something123");
        quest.setQuestType(QuestType.ITEM_BRING);
        questRepository.save(quest);

        Item item = new Item();
        item.setId("something123");
        itemRepository.save(item);

        user.addQuest(quest.getId());
        user.addItem("something123");

        answerActionManager.answerAction(user, answer);

        assertEquals(QuestStatus.SUCCEEDED, user.getQuests().get(quest.getId()));
    }

    @Test
    void should_change_quest_enemy_kill_to_success() {
        Answer answer = new Answer();
        answer.setSuccessPhraseId(1);
        answer.setDefaultPhraseId(2);

        AnswerAction answerAction = new AnswerAction();
        answerAction.setAnswerActionType(AnswerActionType.CHECK_QUEST);
        answerAction.setQuestId("TestQuest");
        answer.setAnswerAction(answerAction);

        Quest quest = new Quest();
        quest.setId("TestQuest");
        quest.setTargetId("something123");
        quest.setQuestType(QuestType.ENEMY_KILL);
        questRepository.save(quest);

        user.addQuest(quest.getId());
        user.addEnemyKilled("something123");

        answerActionManager.answerAction(user, answer);

        assertEquals(QuestStatus.SUCCEEDED, user.getQuests().get(quest.getId()));
    }
}
