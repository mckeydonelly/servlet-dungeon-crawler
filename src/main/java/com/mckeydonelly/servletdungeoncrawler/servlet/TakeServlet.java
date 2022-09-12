package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.gamestate.ItemState;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TakeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TakeServlet.class);
    private final SessionManager sessionManager;

    public TakeServlet(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        var user = sessionManager.validateUser(request);
        int itemMapId = Integer.parseInt(request.getParameter("id"));
        String itemId = request.getParameter("objectId");

        user.getInventory().add(itemId);
        user.getGameState().getItemStateOnMap().put(itemMapId, ItemState.builder()
                .isTaken(true)
                .build());

        response.getWriter().flush();
    }
}
