package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryInMemoryTest {
    private Repository<User, String> userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryInMemory();
    }

    @Test
    void should_save_entity() {
        User user = User.builder()
                .id("userTest")
                .build();
        userRepository.save(user);

        assertTrue(userRepository.exists(user.getId()));
    }

    @Test
    void should_find_user_by_id() {
        User user = User.builder()
                .id("userTest")
                .build();
        userRepository.save(user);

        User actual = userRepository.findById(user.getId());

        assertEquals(user, actual);
    }

    @Test
    void should_return_all_users() {
        User user = User.builder()
                .id("userTest")
                .build();
        userRepository.save(user);

        Iterable<User> users = userRepository.findAll();

        User actual = users.iterator().next();

        assertEquals(user, actual);
    }

    @Test
    void should_return_count() {
        User user = User.builder()
                .id("userTest")
                .build();

        userRepository.save(user);

        assertEquals(1, userRepository.count());
    }

    @Test
    void should_delete_user() {
        User user = User.builder()
                .id("userTest")
                .build();

        userRepository.save(user);
        userRepository.delete(user);

        assertFalse(userRepository.exists(user.getId()));
    }

    @Test
    void should_exists_user_by_id() {
        User user = User.builder()
                .id("userTest")
                .build();

        userRepository.save(user);

        assertTrue(userRepository.exists(user.getId()));
    }
}
