package com.mckeydonelly.servletdungeoncrawler.servlet.user;

import com.mckeydonelly.servletdungeoncrawler.engine.dto.DialogInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.*;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Test
    void should_exception_when_answerId_is_illegal_format() {
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
        when(request.getParameter("phraseId")).thenReturn("33");
        when(request.getParameter("answerId")).thenReturn("abc");

        assertThrows(IllegalArgumentException.class, () -> speakServlet.doGet(request, response));
    }

    @Test
    void should_return_parameters() throws ServletException, IOException {
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
        SpeakServlet speakServlet = new SpeakServlet(sessionManager,
                answerActionManager,
                npcRepository,
                dialogRepository);
        SpeakServlet spySpeakServlet = Mockito.spy(speakServlet);
        when(request.getParameter("npcId")).thenReturn("33");
        Npc npc = Mockito.mock(Npc.class);
        when(npc.getName()).thenReturn("TestNpc");
        when(npc.getDialogId()).thenReturn("abc");
        when(npcRepository.findById("33")).thenReturn(npc);
        when(request.getParameter("phraseId")).thenReturn("33");
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.ALWAYS);
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        Dialog dialog = new Dialog();
        dialog.setPhrases(phrases);
        when(dialogRepository.findById("abc")).thenReturn(dialog);
        ServletContext servletContext = Mockito.spy(ServletContext.class);
        when(request.getServletContext()).thenReturn(servletContext);
        RequestDispatcher requestDispatcher = Mockito.spy(RequestDispatcher.class);
        when(servletContext.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        spySpeakServlet.doGet(request, response);

        verify(request, times(2)).setAttribute(anyString(), any());
        verify(servletContext).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }
}