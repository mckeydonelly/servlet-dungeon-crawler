package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.phrase;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.Answer;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.AnswerAvailability;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import lombok.Data;

import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Phrase {
    private int id;
    private String label;
    private Map<Integer, Answer> answers;

    public Map<Integer, Answer> fetchAvailableAnswers(User user) {
        return answers
                .entrySet()
                .stream()
                .filter(answer -> checkAnswerAvailable(user, answer.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean checkAnswerAvailable(User user, Answer answer) {
        if(AnswerAvailability.AvailableType.ALWAYS.equals(answer.getAnswerAvailability().getAvailableType())) {
            return true;
        }

        if(AnswerAvailability.AvailableType.ALL.equals(answer.getAnswerAvailability().getAvailableType())) {
            if(answer.getAnswerAvailability().isAvailableCondition()) {
                return user.getInventory().contains(answer.getAnswerAvailability().getItemId()) &&
                        user.getQuests().containsKey(answer.getAnswerAvailability().getQuestId());
            }
            return !(user.getInventory().contains(answer.getAnswerAvailability().getItemId()) &&
                    user.getQuests().containsKey(answer.getAnswerAvailability().getQuestId()));
        } else {
            if (answer.getAnswerAvailability().isAvailableCondition()) {
                return user.getInventory().contains(answer.getAnswerAvailability().getItemId()) ||
                        user.getQuests().containsKey(answer.getAnswerAvailability().getQuestId());
            }
            return !(user.getInventory().contains(answer.getAnswerAvailability().getItemId()) ||
                    user.getQuests().containsKey(answer.getAnswerAvailability().getQuestId()));
        }
    }
 }
