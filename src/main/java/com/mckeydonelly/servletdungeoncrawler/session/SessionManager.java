package com.mckeydonelly.servletdungeoncrawler.session;

import com.mckeydonelly.servletdungeoncrawler.repositories.UserRepository;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.util.Objects.isNull;

public class SessionManager {
    public static final String USERID = "userid";
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private final UserRepository userRepository;

    public SessionManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(HttpServletRequest request) {
        HttpSession currentSession = request.getSession();
        String userId = (String) currentSession.getAttribute(USERID);

        if (isNull(userId)) {
            return Optional.empty();
        }

        return Optional.of(userRepository.findById(userId));
    }

    public User validateUser(HttpServletRequest request) {
        var user = getUser(request);

        if (user.isEmpty()) {
            request.getSession().invalidate();
            logger.error("Can't find user data in session or invalid");
            throw new IllegalStateException("Can't find user data in session or invalid");
        }
        return user.get();
    }
}
