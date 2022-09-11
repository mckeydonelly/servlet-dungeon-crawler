package com.mckeydonelly.servletdungeoncrawler.user;

import com.google.common.hash.Hashing;
import com.mckeydonelly.servletdungeoncrawler.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    //TODO starting health to config? or may be to start page with name?
    //TODO move to config?
    private static final String USERID = "userid";
    private static final String REQUEST_USER_NAME = "user_name";
    private static final int START_HEALTH = 150;
    private static final int START_LOCATION = 1;
    private final UserRepository userRepository;
    private final SessionManager sessionManager;

    public UserManager(UserRepository userRepository, SessionManager sessionManager) {
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    public void createUser(HttpServletRequest request) {
        String userName = request.getParameter(REQUEST_USER_NAME);
        var oldUser = sessionManager.getUser(request);
        String userId = calculateUserId(userName);

        var user = User.builder()
                .id(userId)
                .name(userName)
                .currentLocationId(START_LOCATION)
                .health(START_HEALTH)
                .maxHealth(START_HEALTH)
                .totalGames(oldUser.map(old -> old.getTotalGames() + 1).orElse(1))
                .build();

        userRepository.save(user);

        HttpSession currentSession = request.getSession();
        currentSession.setAttribute(USERID, userId);

        user.getGameState().addGameLog("Your adventure is begin!");

        request.getServletContext().setAttribute("user", user);
    }

    private String calculateUserId(String data) {
        return Hashing.sha256()
                .hashString(data, StandardCharsets.UTF_8)
                .toString();
    }
}
