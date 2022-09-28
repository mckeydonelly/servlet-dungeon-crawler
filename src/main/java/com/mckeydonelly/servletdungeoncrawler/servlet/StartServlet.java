package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.user.UserManager;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class StartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(StartServlet.class);
    private final UserManager userManager;

    public StartServlet(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        logger.info("Creating user...");
        userManager.createUser(request);

        logger.info("Redirect to start location");
        response.sendRedirect(request.getContextPath() + "/room");
    }
}
