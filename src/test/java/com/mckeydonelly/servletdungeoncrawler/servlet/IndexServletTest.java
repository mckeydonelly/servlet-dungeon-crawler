package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private GameMap gameMap;

    @Test
    void should_set_data_and_redirect() throws ServletException, IOException {
        User user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        Optional<User> optionalUser = Optional.of(user);
        when(sessionManager.getUser(request)).thenReturn(optionalUser);
        when(gameMap.getPrologue()).thenReturn("TestPrologue");
        ServletContext servletContext = Mockito.spy(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        RequestDispatcher requestDispatcher = Mockito.spy(RequestDispatcher.class);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        HttpSession session = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        IndexServlet indexServlet = Mockito.spy(new IndexServlet(sessionManager, gameMap));

        indexServlet.doGet(request, response);

        verify(session, times(2)).setAttribute(anyString(), any());
        verify(servletContext).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }
}