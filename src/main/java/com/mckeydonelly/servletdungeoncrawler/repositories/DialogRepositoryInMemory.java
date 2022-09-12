package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.Dialog;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class DialogRepositoryInMemory implements DialogRepository {
    public final Map<String, Dialog> dialogues = new ConcurrentHashMap<>();

    @Override
    public void save(Dialog entity) {
        dialogues.put(entity.getId(), entity);
    }

    @Override
    public Dialog findById(String id) {
        return dialogues.get(id);
    }

    @Override
    public Iterable<Dialog> findAll() {
        return List.copyOf(dialogues.values());
    }

    @Override
    public int count() {
        return dialogues.size();
    }

    @Override
    public void delete(Dialog entity) {
        dialogues.remove(entity.getId());
    }

    @Override
    public boolean exists(String id) {
        return dialogues.containsKey(id);
    }
}
