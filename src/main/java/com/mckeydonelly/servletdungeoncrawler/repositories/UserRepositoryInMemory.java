package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.user.User;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Setter
public class UserRepositoryInMemory implements UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void save(User entity) {
        users.put(entity.getId(), entity);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }

    @Override
    public Iterable<User> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public int count() {
        return users.size();
    }

    @Override
    public void delete(User entity) {
        users.remove(entity.getId());
    }

    @Override
    public boolean exists(String id) {
        return users.containsKey(id);
    }
}
