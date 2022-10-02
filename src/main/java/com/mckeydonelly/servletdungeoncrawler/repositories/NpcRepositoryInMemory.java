package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class NpcRepositoryInMemory implements NpcRepository {
    private final Map<String, Npc> npcs = new ConcurrentHashMap<>();

    @Override
    public void save(Npc entity) {
        npcs.put(entity.getId(), entity);
    }

    @Override
    public Npc findById(String id) {
        return npcs.get(id);
    }

    @Override
    public Iterable<Npc> findAll() {
        return List.copyOf(npcs.values());
    }

    @Override
    public int count() {
        return npcs.size();
    }

    @Override
    public void delete(Npc entity) {
        npcs.remove(entity.getId());
    }

    @Override
    public boolean exists(String id) {
        return npcs.containsKey(id);
    }
}
