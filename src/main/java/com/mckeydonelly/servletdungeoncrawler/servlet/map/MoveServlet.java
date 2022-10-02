package com.mckeydonelly.servletdungeoncrawler.servlet.map;

import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MoveServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MoveServlet.class);

    private final SessionManager sessionManager;
    private final GameMap gameMap;

    public MoveServlet(SessionManager sessionManager, GameMap gameMap) {
        this.sessionManager = sessionManager;
        this.gameMap = gameMap;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        var user = sessionManager.validateUser(request);

        int nextLocationId;
        try {
            nextLocationId = Integer.parseInt(request.getParameter("nextLocationId"));
        } catch (NumberFormatException e) {
            logger.error("nextLocationId may have only digits");
            throw new IllegalArgumentException(e);
        }
        user.getState().addGameLog("You move to location: " + gameMap.fetchLocationById(nextLocationId).getName());
        if (!gameMap.validateLocationId(user.getCurrentLocationId(), nextLocationId)) {
            logger.error("Incorrect next location id: {}", nextLocationId);
            throw new IllegalArgumentException("Incorrect next location id");
        }
        user.setCurrentLocationId(nextLocationId);

        logger.info("User move to location: name={}, id={}", gameMap.fetchLocationById(nextLocationId).getName(), nextLocationId);
        response.sendRedirect(request.getContextPath() + "/room");
    }
}
