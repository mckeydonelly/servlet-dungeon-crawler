package com.mckeydonelly.servletdungeoncrawler.session;

import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.repositories.UserRepositoryInMemory;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionManagerTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;
    private SessionManager sessionManager;
    private Repository<User, String> userRepository;
    private User user;

    @BeforeEach
    void setUp() {
    }

    @Test
    void should_return_user() {
        userRepository = new UserRepositoryInMemory();
        sessionManager = new SessionManager(userRepository);
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        userRepository.save(user);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userid")).thenReturn("Test");

        User actual = sessionManager.validateUser(request);

        assertEquals(user, actual);
    }

    @Test
    void should_return_illegal_state_exception() {
        userRepository = new UserRepositoryInMemory();
        sessionManager = new SessionManager(userRepository);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userid")).thenReturn("TestWithoutAdd");

        assertThrows(IllegalStateException.class, () -> sessionManager.validateUser(request));
    }

    @Test
    void should_return_optional_user() {
        userRepository = new UserRepositoryInMemory();
        sessionManager = new SessionManager(userRepository);
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        userRepository.save(user);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userid")).thenReturn("Test");

        Optional<User> actual = sessionManager.getUser(request);

        assertEquals(user, actual.get());
    }

    @Test
    void should_return_empty_optional_user() {
        userRepository = new UserRepositoryInMemory();
        sessionManager = new SessionManager(userRepository);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userid")).thenReturn("TestWithoutAdd");

        Optional<User> actual = sessionManager.getUser(request);

        assertTrue(actual.isEmpty());
    }
}
