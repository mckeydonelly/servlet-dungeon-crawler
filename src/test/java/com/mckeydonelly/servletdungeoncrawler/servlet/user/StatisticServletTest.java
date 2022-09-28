package com.mckeydonelly.servletdungeoncrawler.servlet.user;

import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    private User user;

    @Test
    void should_return_statistic_json() throws IOException, ServletException {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        Optional<User> optionalUser = Optional.of(user);
        when(sessionManager.getUser(request)).thenReturn(optionalUser);
        StatisticServlet statisticServlet = new StatisticServlet(sessionManager);
        PrintWriter printWriter = Mockito.spy(new PrintWriter(System.out));
        when(response.getWriter()).thenReturn(printWriter);

        statisticServlet.doGet(request, response);

        verify(printWriter).print(anyString());
        verify(printWriter).flush();
    }

    @Test
    void should_no_user_data_if_user_not_found() throws IOException, ServletException {
        when(sessionManager.getUser(request)).thenReturn(Optional.empty());
        StatisticServlet statisticServlet = new StatisticServlet(sessionManager);
        PrintWriter printWriter = Mockito.spy(new PrintWriter(System.out));
        when(response.getWriter()).thenReturn(printWriter);

        statisticServlet.doGet(request, response);

        verify(printWriter).print(anyString());
        verify(printWriter).flush();
    }
}