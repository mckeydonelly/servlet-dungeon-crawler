package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.user.User;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Setter
@Getter
class Phrase {
    private static final Logger logger = LoggerFactory.getLogger(Phrase.class);

    private int id;
    private String label;
    private Map<Integer, Answer> answers;

    protected Map<Integer, Answer> fetchAvailableAnswers(User user) {
        logger.info("Fetch available answers");
        return Collections.unmodifiableMap(answers
                .entrySet()
                .stream()
                .filter(answer -> checkAnswerAvailable(user, answer.getValue().getAnswerAvailability()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private boolean checkAnswerAvailable(User user, AnswerAvailability answerAvailability) {
        String itemId = answerAvailability.getItemId();
        String questId = answerAvailability.getQuestId();
        boolean availableCondition = answerAvailability.isAvailableCondition();

        return switch (answerAvailability.getAvailableType()) {
            case ALWAYS -> true;
            case QUEST -> availableCondition == user.isExistsQuest(questId);
            case ITEM -> availableCondition == user.isExistsItem(itemId);
            case ALL -> availableCondition == (user.isExistsItem(itemId) && user.isExistsQuest(questId));
        };
    }
}
