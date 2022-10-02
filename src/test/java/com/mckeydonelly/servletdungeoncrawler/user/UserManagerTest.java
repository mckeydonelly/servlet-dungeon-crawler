package com.mckeydonelly.servletdungeoncrawler.user;

import com.google.common.hash.Hashing;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.repositories.UserRepositoryInMemory;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserManagerTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;
    @Mock
    ServletContext servletContext;
    private Repository<User, String> userRepository;
    private SessionManager sessionManager;
    private UserManager userManager;

    @Test
    void should_create_new_user() {
        userRepository = new UserRepositoryInMemory();
        sessionManager = new SessionManager(userRepository);
        userManager = new UserManager(userRepository, sessionManager);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("user_name")).thenReturn("Test");
        when(request.getServletContext()).thenReturn(servletContext);

        userManager.createUser(request);

        String expectedUserId = Hashing.sha256()
                .hashString("Test", StandardCharsets.UTF_8)
                .toString();
        assertTrue(userRepository.exists(expectedUserId));
    }

    @Test
    void should_create_new_user_with_total_game_from_old() {
        userRepository = new UserRepositoryInMemory();
        sessionManager = new SessionManager(userRepository);
        userManager = new UserManager(userRepository, sessionManager);
        User oldUser = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(1)
                .build();
        userRepository.save(oldUser);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("user_name")).thenReturn("Test");
        when(request.getServletContext()).thenReturn(servletContext);
        when(session.getAttribute("userid")).thenReturn("Test");

        userManager.createUser(request);

        String expectedUserId = Hashing.sha256()
                .hashString("Test", StandardCharsets.UTF_8)
                .toString();
        assertTrue(userRepository.exists(expectedUserId));
    }
}