package com.mckeydonelly.servletdungeoncrawler.servlet.map;

import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.engine.map.location.Location;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private GameMap gameMap;
    @Mock
    private Location location;
    private User user;

    @Test
    void should_return_exception_when_illegal_parameter_format() {
        String illegalNextLocationId = "abc";

        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        MoveServlet moveServlet = new MoveServlet(sessionManager, gameMap);
        when(request.getParameter("nextLocationId")).thenReturn(illegalNextLocationId);

        assertThrows(IllegalArgumentException.class, () -> moveServlet.doPost(request, response));
    }

    @Test
    void should_set_next_location_to_user_and_send_redirect() throws IOException {
        int nextLocationIdNumber = 33;
        String nextLocationId = "33";
        String locationName = "TestLoc";

        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        MoveServlet moveServlet = new MoveServlet(sessionManager, gameMap);
        MoveServlet spyMoveServlet = Mockito.spy(moveServlet);
        when(request.getParameter("nextLocationId")).thenReturn(nextLocationId);
        when(gameMap.fetchLocationById(nextLocationIdNumber)).thenReturn(location);
        when(location.getName()).thenReturn(locationName);

        spyMoveServlet.doPost(request, response);

        assertEquals(nextLocationIdNumber, user.getCurrentLocationId());
        verify(response).sendRedirect(anyString());
    }

}