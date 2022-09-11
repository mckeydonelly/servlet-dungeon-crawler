package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.crate.Crate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class CrateRepositoryInMemory implements CrateRepository {
    private final Map<String, Crate> crates = new ConcurrentHashMap<>();

    @Override
    public void save(Crate entity) {
        crates.put(entity.getId(), entity);
    }

    @Override
    public Crate findById(String id) {
        return crates.get(id);
    }

    @Override
    public Iterable<Crate> findAll() {
        return List.copyOf(crates.values());
    }

    @Override
    public int count() {
        return crates.size();
    }

    @Override
    public void delete(Crate entity) {
        crates.remove(entity.getId());
    }

    @Override
    public boolean exists(String id) {
        return crates.containsKey(id);
    }
}
