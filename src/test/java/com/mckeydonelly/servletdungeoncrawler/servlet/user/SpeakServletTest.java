package com.mckeydonelly.servletdungeoncrawler.servlet.user;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.AnswerActionManager;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.Dialog;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.servlet.item.TakeServlet;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpeakServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private AnswerActionManager answerActionManager;
    @Mock
    private Repository<Npc, String> npcRepository;
    @Mock
    private Repository<Dialog, String> dialogRepository;
    private User user;

    @Test
    void should_exception_when_empty_npcId() {
        SpeakServlet speakServlet = new SpeakServlet(sessionManager,
                answerActionManager,
                npcRepository,
                dialogRepository);

        assertThrows(IllegalArgumentException.class, () -> speakServlet.doGet(request, response));
    }

    @Test
    void should_exception_when_phraseId_is_illegal_format() {
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
        when(sessionManager.validateUser(request)).thenReturn(user);
        SpeakServlet speakServlet = new SpeakServlet(sessionManager,
                answerActionManager,
                npcRepository,
                dialogRepository);
        when(request.getParameter("npcId")).thenReturn("33");
        Npc npc = Mockito.mock(Npc.class);
        when(npc.getDialogId()).thenReturn("abc");
        when(npcRepository.findById("33")).thenReturn(npc);
        when(dialogRepository.findById("abc")).thenReturn(new Dialog());
        when(request.getParameter("phraseId")).thenReturn("abc");

        assertThrows(IllegalArgumentException.class, () -> speakServlet.doGet(request, response));
    }
}