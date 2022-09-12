package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mckeydonelly.servletdungeoncrawler.engine.dto.QuestInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.QuestType;
import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.NpcRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.QuestRepository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class QuestServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(QuestServlet.class);
    private final ObjectMapper jsonConverter = new ObjectMapper();
    private final SessionManager sessionManager;
    private final QuestRepository questRepository;
    private final ItemRepository itemRepository;
    private final NpcRepository npcRepository;

    public QuestServlet(SessionManager sessionManager, QuestRepository questRepository, ItemRepository itemRepository, NpcRepository npcRepository) {
        this.sessionManager = sessionManager;
        this.questRepository = questRepository;
        this.itemRepository = itemRepository;
        this.npcRepository = npcRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        var user = sessionManager.validateUser(request);

        response.setContentType("application/json; charset=utf-8");
        var writer = response.getWriter();
        writer.print(jsonConverter.writeValueAsString(prepareQuestInfo(user)));
        writer.flush();
    }

    private List<QuestInfo> prepareQuestInfo(User user) {
        return user.getQuests()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        questEntry -> questRepository.findById(questEntry.getKey()),
                        questEntry -> questEntry.getValue().toString()
                ))
                .entrySet()
                .stream()
                .map(entry -> QuestInfo.builder()
                        .issuedBy(entry.getKey().getIssuesById())
                        .status(entry.getValue())
                        .questType(entry.getKey().getQuestType().toString())
                        .targetName(parseTargetId(entry.getKey()))
                        .info(entry.getKey().getInfo())
                        .build())
                .collect(Collectors.toList());
    }

    private String parseTargetId(Quest quest) {
        QuestType questType = quest.getQuestType();
        String targetId = quest.getTargetId();

        return switch (questType) {
            case ITEM_BRING -> itemRepository.findById(targetId).getName();
            case ENEMY_KILL -> npcRepository.findById(targetId).getName();
            case BOTH -> "Bring " + itemRepository.findById(targetId).getName() + " \n" + "Kill " + npcRepository.findById(targetId).getName();
        };
    }
}
