package com.mckeydonelly.servletdungeoncrawler.servlet.user;

import com.mckeydonelly.servletdungeoncrawler.engine.dto.AnswerInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.dto.DialogInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.Dialog;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.AnswerActionManager;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.Answer;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
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
    private final Repository<Npc, String> npcRepository;
    private final Repository<Dialog, String> dialogRepository;

    public SpeakServlet(SessionManager sessionManager,
                        AnswerActionManager answerActionManager,
                        Repository<Npc, String> npcRepository,
                        Repository<Dialog, String> dialogRepository) {
        this.sessionManager = sessionManager;
        this.answerActionManager = answerActionManager;
        this.npcRepository = npcRepository;
        this.dialogRepository = dialogRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());

        Object npcIdTmp = request.getParameter("npcId");
        String npcId = null;
        if(Objects.isNull(npcIdTmp)) {
            logger.error("npcId is empty");
            throw new IllegalArgumentException("npcId is empty");
        } else {
            npcId = (String) npcIdTmp;
        }
        Npc npc = npcRepository.findById(npcId);
        Dialog npcDialog = dialogRepository.findById(npc.getDialogId());

        var user = sessionManager.validateUser(request);

        int phraseId = 0;
        try {
            phraseId = Integer.parseInt(request.getParameter("phraseId"));
        } catch (NumberFormatException e) {
            logger.error("phraseId may have only digits");
            throw new IllegalArgumentException(e);
        }

        Object answerId = request.getParameter("answerId");
        int nextPhraseId = DEFAULT_PHRASE_ID;
        if(Objects.nonNull(answerId)) {
            Answer answer = null;
            try {
                answer = npcDialog.getAnswerById(phraseId, Integer.parseInt(answerId.toString()), user);
            } catch (NumberFormatException e) {
                logger.error("answerId may have only digits");
                throw new IllegalArgumentException(e);
            }
            nextPhraseId = answerActionManager.answerAction(user, answer);
        }
        DialogInfo dialogInfo = prepareDialogInfo(user, nextPhraseId, npcId, npc, npcDialog);

        request.setAttribute("name", npc.getName());
        request.setAttribute("dialogInfo", dialogInfo);

        logger.info("Response: npcName={}, dialogInfo={}", npc.getName(), dialogInfo);
        request.getServletContext().getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/dialog.jsp")
                .forward(request, response);
    }

    private static DialogInfo prepareDialogInfo(User user, int phraseId, String npcId, Npc npc, Dialog npcDialog) {
        logger.info("Preparing DialogInfo...");
        return DialogInfo.builder()
                .npcId(npcId)
                .phraseId(phraseId)
                .npcName(npc.getName())
                .imgPath(npc.getImgPath())
                .greeting(npcDialog.getGreeting())
                .phrase(npcDialog.getPhraseLabel(phraseId))
                .answers(npcDialog.getAvailableAnswers(phraseId, user)
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
