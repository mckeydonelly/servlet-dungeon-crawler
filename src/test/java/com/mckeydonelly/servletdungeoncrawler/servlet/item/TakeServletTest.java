package com.mckeydonelly.servletdungeoncrawler.servlet.item;

import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TakeServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    private TakeServlet takeServlet;
    private User user;

    @Test
    void should_add_item_to_user_inventory() throws IOException, ServletException {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        takeServlet = new TakeServlet(sessionManager);
        PrintWriter printWriter = new PrintWriter(System.out);
        when(response.getWriter()).thenReturn(printWriter);
        when(request.getParameter("id")).thenReturn("34");
        when(request.getParameter("objectId")).thenReturn("testSword");

        takeServlet.doGet(request, response);

        assertTrue(user.isExistsItem("testSword"));
        assertFalse(user.getState().checkItem(34));
    }

    @Test
    void should_return_exception_if_missing_object_id() {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        takeServlet = new TakeServlet(sessionManager);
        when(request.getParameter("id")).thenReturn("34");

        assertThrows(IllegalArgumentException.class, () -> takeServlet.doGet(request, response));
    }

    @Test
    void should_return_exception_if_id_not_number() {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        takeServlet = new TakeServlet(sessionManager);
        when(request.getParameter("id")).thenReturn("abc");

        assertThrows(IllegalArgumentException.class, () -> takeServlet.doGet(request, response));
    }
}