package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.user.User;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Setter
public class Dialog {
    private static final Logger logger = LoggerFactory.getLogger(Dialog.class);

    @Getter
    private String id;
    @Getter
    private String greeting;
    private Map<Integer, Phrase> phrases;

    public Answer getAnswerById(int currentPhraseId, int answerId, User user) {
        try {
            return phrases.get(currentPhraseId).fetchAvailableAnswers(user).get(answerId);
        } catch (NullPointerException e) {
            logger.error("Didn't found answer by id: currentPhraseId={}, answerId={}", currentPhraseId, answerId);
            throw new IllegalArgumentException(e);
        }
    }

    public String getPhraseLabel(int phraseId) {
        try {
            return phrases.get(phraseId).getLabel();
        } catch (NullPointerException e) {
            logger.error("Didn't found phrase by id={}", phraseId);
            throw new IllegalArgumentException(e);
        }
    }

    public Map<Integer, Answer> getAvailableAnswers(int phraseId, User user) {
        try {
            return phrases.get(phraseId).fetchAvailableAnswers(user);
        } catch (NullPointerException e) {
            logger.error("Didn't found phrase by id={}", phraseId);
            throw new IllegalArgumentException(e);
        }
    }
}
