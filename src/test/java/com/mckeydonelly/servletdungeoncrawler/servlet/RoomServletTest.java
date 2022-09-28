package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.engine.map.location.Location;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.crate.Crate;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoomServletTest {
    @Mock
    private SessionManager sessionManager;
    @Mock
    private Repository<Item, String> itemRepository;
    @Mock
    private Repository<Crate, String> crateRepository;
    @Mock
    private Repository<Npc, String> npcRepository;
    @Mock
    private GameMap gameMap;
    private User user;

    @Test
    void should_set_data_and_redirect() throws ServletException, IOException {
        HttpServletRequest request = Mockito.spy(HttpServletRequest.class);
        HttpServletResponse response = Mockito.spy(HttpServletResponse.class);
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        Location location = new Location();
        location.setPaths(new HashMap<>());
        location.setItemOnLocationList(new HashMap<>());
        location.setCrateOnLocationList(new HashMap<>());
        location.setNpcOnLocationList(new HashMap<>());
        when(gameMap.fetchLocationById(1)).thenReturn(location);
        ServletContext servletContext = Mockito.spy(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        RequestDispatcher requestDispatcher = Mockito.spy(RequestDispatcher.class);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        RoomServlet roomServlet = Mockito.spy(new RoomServlet(
                sessionManager,
                itemRepository,
                crateRepository,
                npcRepository,
                gameMap
        ));

        roomServlet.doGet(request, response);

        verify(request, times(7)).setAttribute(anyString(), any());
        verify(servletContext).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }
}