package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IndexServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(IndexServlet.class);

    private final SessionManager sessionManager;
    private final GameMap gameMap;

    public IndexServlet(SessionManager sessionManager, GameMap gameMap) {
        this.sessionManager = sessionManager;
        this.gameMap = gameMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        logger.info("Validating user...");
        var user = sessionManager.getUser(request);

        HttpSession session = request.getSession();

        user.ifPresent(value -> session.setAttribute("userName", value.getName()));
        session.setAttribute("questProlog", gameMap.getPrologue());

        logger.info("Redirect to index page");
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/index.jsp")
                .forward(request, response);
    }
}
