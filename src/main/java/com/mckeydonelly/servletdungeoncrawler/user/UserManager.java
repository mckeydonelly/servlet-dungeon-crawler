package com.mckeydonelly.servletdungeoncrawler.user;

import com.google.common.hash.Hashing;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class UserManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private static final String USERID = "userid";
    private static final String REQUEST_USER_NAME = "user_name";
    private static final int START_HEALTH = 150;
    private static final int START_LOCATION = 1;
    private final Repository<User, String> userRepository;
    private final SessionManager sessionManager;

    public UserManager(Repository<User, String> userRepository, SessionManager sessionManager) {
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    public void createUser(HttpServletRequest request) {
        String userName = request.getParameter(REQUEST_USER_NAME);
        logger.info("Creating user with name: {}", userName);
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
        logger.info("User created: {}", user);

        HttpSession currentSession = request.getSession();
        currentSession.setAttribute(USERID, userId);

        user.getState().addGameLog("Your adventure is begin!");

        request.getServletContext().setAttribute("user", user);
    }

    private String calculateUserId(String data) {
        return Hashing.sha256()
                .hashString(data, StandardCharsets.UTF_8)
                .toString();
    }
}
