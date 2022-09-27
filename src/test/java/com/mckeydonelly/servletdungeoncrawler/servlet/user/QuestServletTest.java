package com.mckeydonelly.servletdungeoncrawler.servlet.user;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.servlet.user.inventory.InventoryServlet;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private Repository<Quest, String> questRepository;
    @Mock
    private Repository<Item, String> itemRepository;
    @Mock
    private Repository<Npc, String> npcRepository;
    private User user;

    @Test
    void should_return_json_inventory_list() throws IOException, ServletException {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        QuestServlet questServlet = new QuestServlet(sessionManager,
                questRepository,
                itemRepository,
                npcRepository);
        PrintWriter printWriter = Mockito.spy(new PrintWriter(System.out));
        when(response.getWriter()).thenReturn(printWriter);

        questServlet.doGet(request, response);

        verify(printWriter).print(anyString());
        verify(printWriter).flush();
    }
}