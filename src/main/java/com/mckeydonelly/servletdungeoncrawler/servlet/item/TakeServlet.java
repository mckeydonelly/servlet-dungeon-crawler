package com.mckeydonelly.servletdungeoncrawler.servlet.item;

import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.isNull;

public class TakeServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TakeServlet.class);
    private final SessionManager sessionManager;

    public TakeServlet(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        var user = sessionManager.validateUser(request);

        int itemMapId = 0;
        try {
            itemMapId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("id may have only digits");
            throw new IllegalArgumentException(e);
        }
        Object itemIdTmp = request.getParameter("objectId");
        if(isNull(itemIdTmp)) {
            logger.error("Missing parameter: objectId");
            throw new IllegalArgumentException("Missing parameter: objectId");
        }
        String itemId = (String) itemIdTmp;

        user.addItem(itemId);
        user.getState().addItem(itemMapId);
        logger.info("User add to inventory item: {}", itemId);

        response.getWriter().flush();
    }
}
