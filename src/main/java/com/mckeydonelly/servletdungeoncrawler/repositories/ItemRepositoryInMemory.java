package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class ItemRepositoryInMemory implements ItemRepository {
    private final Map<String, Item> items = new ConcurrentHashMap<>();

    @Override
    public void save(Item entity) {
        items.put(entity.getId(), entity);
    }

    @Override
    public Item findById(String id) {
        return items.get(id);
    }

    @Override
    public Iterable<Item> findAll() {
        return List.copyOf(items.values());
    }

    @Override
    public int count() {
        return items.size();
    }

    @Override
    public void delete(Item entity) {
        items.remove(entity.getId());
    }

    @Override
    public boolean exists(String id) {
        return items.containsKey(id);
    }
}
