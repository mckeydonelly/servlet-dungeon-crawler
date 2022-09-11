package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.engine.dto.AnswerInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.dto.DialogInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.Dialog;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.AnswerActionManager;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.Answer;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.AnswerAction;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.AnswerActionType;
import com.mckeydonelly.servletdungeoncrawler.repositories.DialogRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.NpcRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.QuestRepository;
import com.mckeydonelly.servletdungeoncrawler.user.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class SpeakServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SpeakServlet.class);
    public static final int DEFAULT_PHRASE_ID = 1;
    private final SessionManager sessionManager;
    private final AnswerActionManager answerActionManager;
    private final NpcRepository npcRepository;
    private final DialogRepository dialogRepository;
    private final QuestRepository questRepository;

    public SpeakServlet(SessionManager sessionManager,
                        AnswerActionManager answerActionManager,
                        NpcRepository npcRepository,
                        DialogRepository dialogRepository,
                        QuestRepository questRepository) {
        this.sessionManager = sessionManager;
        this.answerActionManager = answerActionManager;
        this.npcRepository = npcRepository;
        this.dialogRepository = dialogRepository;
        this.questRepository = questRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());

        var user = sessionManager.validateUser(request);
        int phraseId = Integer.parseInt(request.getParameter("phraseId"));
        String npcId = request.getParameter("npcId");

        Npc npc = npcRepository.findById(npcId);
        Dialog npcDialog = dialogRepository.findById(npc.getDialogId());

        Object answerId = request.getParameter("answerId");
        int nextPhraseId = DEFAULT_PHRASE_ID;
        if(Objects.nonNull(answerId)) {
            Answer answer = npcDialog.getPhrases().get(phraseId).getAnswers().get(Integer.parseInt(answerId.toString()));
            nextPhraseId = answerActionManager.answerAction(user, answer);
        }
        DialogInfo dialogInfo = prepareDialogInfo(user, nextPhraseId, npcId, npc, npcDialog);

        request.setAttribute("name", npc.getName());
        request.setAttribute("dialogInfo", dialogInfo);

        getServletContext().getRequestDispatcher("/WEB-INF/jsp/dialog.jsp")
                .forward(request, response);
    }

    private static DialogInfo prepareDialogInfo(User user, int phraseId, String npcId, Npc npc, Dialog npcDialog) {
        return DialogInfo.builder()
                .npcId(npcId)
                .phraseId(phraseId)
                .npcName(npc.getName())
                .imgPath(npc.getImgPath())
                .greeting(npcDialog.getGreeting())
                .phrase(npcDialog.getPhrases().get(phraseId).getLabel())
                .answers(npcDialog.getPhrases()
                        .get(phraseId)
                        .fetchAvailableAnswers(user)
                        .entrySet()
                        .stream()
                        .map(entry -> AnswerInfo.builder()
                                .id(entry.getKey())
                                .label(entry.getValue().getLabel())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
