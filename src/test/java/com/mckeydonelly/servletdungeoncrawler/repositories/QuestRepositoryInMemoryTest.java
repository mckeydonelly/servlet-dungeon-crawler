package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestRepositoryInMemoryTest {
    private Repository<Quest, String> questRepository;

    @BeforeEach
    void setUp() {
        questRepository = new QuestRepositoryInMemory();
    }

    @Test
    void should_save_entity() {
        Quest quest = new Quest();
        quest.setId("questTest");

        questRepository.save(quest);

        assertTrue(questRepository.exists(quest.getId()));
    }

    @Test
    void should_find_quest_by_id() {
        Quest quest = new Quest();
        quest.setId("questTest");
        questRepository.save(quest);

        Quest actual = questRepository.findById(quest.getId());

        assertEquals(quest, actual);
    }

    @Test
    void should_return_all_quests() {
        Quest quest = new Quest();
        quest.setId("questTest");
        questRepository.save(quest);

        Iterable<Quest> quests = questRepository.findAll();

        Quest actual = quests.iterator().next();

        assertEquals(quest, actual);
    }

    @Test
    void should_return_count() {
        Quest quest = new Quest();
        quest.setId("questTest");

        questRepository.save(quest);

        assertEquals(1, questRepository.count());
    }

    @Test
    void should_delete_quest() {
        Quest quest = new Quest();
        quest.setId("questTest");

        questRepository.save(quest);
        questRepository.delete(quest);

        assertFalse(questRepository.exists(quest.getId()));
    }

    @Test
    void should_exists_quest_by_id() {
        Quest quest = new Quest();
        quest.setId("questTest");

        questRepository.save(quest);

        assertTrue(questRepository.exists(quest.getId()));
    }
}
