package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatisticServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(StatisticServlet.class);
    private final ObjectMapper jsonConverter = new ObjectMapper();
    private final SessionManager sessionManager;

    public StatisticServlet(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());

        response.setContentType("application/json; charset=utf-8");
        var writer = response.getWriter();
        writer.print(jsonConverter.writeValueAsString(setStatistic(request)));
        writer.flush();
    }

    private Map<String, String> setStatistic(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();

        var user = sessionManager.getUser(request);

        if(user.isEmpty()) {
            result.put("User", "No user data");
            return result;
        }

        result.put("totalGames", String.valueOf(user.get().getTotalGames()));
        result.put("enemyKilledInThisGame", String.valueOf(user.get().getEnemyKilled().size()));
        result.put("ip", getUserIp(request));

        return result;
    }

    private String getUserIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
