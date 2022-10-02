package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class QuestRepositoryInMemory implements QuestRepository {
    private final Map<String, Quest> quests = new ConcurrentHashMap<>();

    @Override
    public void save(Quest entity) {
        quests.put(entity.getId(), entity);
    }

    @Override
    public Quest findById(String id) {
        return quests.get(id);
    }

    @Override
    public Iterable<Quest> findAll() {
        return List.copyOf(quests.values());
    }

    @Override
    public int count() {
        return quests.size();
    }

    @Override
    public void delete(Quest entity) {
        quests.remove(entity.getId());
    }

    @Override
    public boolean exists(String id) {
        return quests.containsKey(id);
    }
}
