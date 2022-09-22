package com.mckeydonelly.servletdungeoncrawler.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
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
        String statisticJson = jsonConverter.writeValueAsString(setStatistic(request));
        logger.info("Response: {}", statisticJson);
        writer.print(statisticJson);
        writer.flush();
    }

    private Map<String, String> setStatistic(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();

        var userOptional = sessionManager.getUser(request);

        if(userOptional.isEmpty()) {
            result.put("User", "No user data");
            return result;
        }
        User user = userOptional.get();

        result.put("totalGames", String.valueOf(user.getTotalGames()));
        result.put("enemyKilledInThisGame", String.valueOf(user.getEnemyKilled().size()));
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
