package com.mckeydonelly.servletdungeoncrawler.servlet.user.inventory;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.ItemType;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private Repository<Item, String> itemRepository;
    private User user;

    @Test
    void should_return_json_inventory_list() throws IOException {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        InventoryServlet inventoryServlet = new InventoryServlet(sessionManager, itemRepository);
        PrintWriter printWriter = Mockito.spy(new PrintWriter(System.out));
        when(response.getWriter()).thenReturn(printWriter);

        inventoryServlet.doGet(request, response);

        verify(printWriter).print(anyString());
        verify(printWriter).flush();
    }
}