package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.phrase;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.Answer;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.AnswerAvailability;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import lombok.Data;

import java.util.Map;
import java.util.stream.Collectors;

//TODO Quest check phrase? Должна быть фраза которая проверяет что она квестовая и не высвечивается пока квест не взян
//TODO А после по ней проверяется что-то в инвентаре или убийство какого-то босса на карте?
//TODO Нужна логика по работе с квестами: взятие квеста/триггер выполнения квеста/проверка квеста/успех и действие при выполнении
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
