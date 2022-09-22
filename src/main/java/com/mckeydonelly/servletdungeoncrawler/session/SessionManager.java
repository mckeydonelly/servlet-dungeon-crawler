package com.mckeydonelly.servletdungeoncrawler.session;

import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
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
    private final Repository<User, String> userRepository;

    public SessionManager(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validate and get user from session
     *
     * @param request HttpServletRequest
     * @return User
     */
    public User validateUser(HttpServletRequest request) {
        logger.info("Validating user");
        var user = getUser(request);

        if (user.isEmpty()) {
            request.getSession().invalidate();
            logger.error("Can't find user data in session or invalid");
            throw new IllegalStateException("Can't find user data in session or invalid");
        }
        return user.get();
    }


    /**
     * Get optional user for cases with we may have data without user
     *
     * @param request HttpServletRequest
     * @return Optional<User>
     */
    public Optional<User> getUser(HttpServletRequest request) {
        logger.info("Getting user from request");
        HttpSession currentSession = request.getSession();
        String userId = (String) currentSession.getAttribute(USERID);

        if (isNull(userId)) {
            logger.info("User not found in session");
            return Optional.empty();
        }

        return Optional.of(userRepository.findById(userId));
    }
}
