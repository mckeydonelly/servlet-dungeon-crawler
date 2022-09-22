package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DialogTest {
    private Dialog dialog;
    private User user;

    @BeforeEach
    void setUp() {
        dialog = new Dialog();
        user = User.builder()
                .id("Test")
                .name("Test")
                .health(150)
                .maxHealth(150)
                .currentLocationId(1)
                .totalGames(0)
                .build();
    }

    @Test
    void should_return_exception_when_get_answer_by_illegal_id() {
        assertThrows(IllegalArgumentException.class, () -> dialog.getAnswerById(33, 33, user));
    }

    @Test
    void should_return_exception_when_get_phrase_label_by_illegal_id() {
        assertThrows(IllegalArgumentException.class, () -> dialog.getPhraseLabel(33));
    }

    @Test
    void should_return_exception_when_get_available_answer_by_illegal_id() {
        assertThrows(IllegalArgumentException.class, () -> dialog.getAvailableAnswers(33, user));
    }

    @Test
    void should_return_answer_by_id_for_always_condition() {
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
        dialog.setPhrases(phrases);

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_answer_by_id_for_quest_condition_true() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.QUEST);
        answerAvailability.setAvailableCondition(true);
        answerAvailability.setQuestId("something123");
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);
        user.addQuest("something123");

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_answer_by_id_for_quest_condition_false() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.QUEST);
        answerAvailability.setAvailableCondition(false);
        answerAvailability.setQuestId("something123");
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_answer_by_id_for_item_condition_true() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.ITEM);
        answerAvailability.setAvailableCondition(true);
        answerAvailability.setItemId("something123");
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);
        user.addItem("something123");

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_answer_by_id_for_item_condition_false() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.ITEM);
        answerAvailability.setAvailableCondition(false);
        answerAvailability.setItemId("something123");
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_answer_by_id_for_all_condition_true() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.ALL);
        answerAvailability.setAvailableCondition(true);
        answerAvailability.setItemId("something123");
        answerAvailability.setQuestId("something123");
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);
        user.addItem("something123");
        user.addQuest("something123");

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_answer_by_id_for_all_condition_false() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer = new Answer();
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.ALL);
        answerAvailability.setAvailableCondition(false);
        answerAvailability.setItemId("something123");
        answerAvailability.setQuestId("something123");
        answer.setAnswerAvailability(answerAvailability);
        answers.put(1, answer);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);

        Answer actual = dialog.getAnswerById(1, 1, user);

        assertEquals(answer, actual);
    }

    @Test
    void should_return_phrase_label_by_id() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        phrase.setLabel("Test");
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);

        String actual = dialog.getPhraseLabel(1);

        assertEquals("Test", actual);
    }

    @Test
    void should_return_available_answers_list_by_phrase_id() {
        Map<Integer, Phrase> phrases = new HashMap<>();
        Phrase phrase = new Phrase();
        Map<Integer, Answer> answers = new HashMap<>();
        Answer answer1 = new Answer();
        answer1.setLabel("answer1");
        AnswerAvailability answerAvailability = new AnswerAvailability();
        answerAvailability.setAvailableType(AnswerAvailability.AvailableType.ALWAYS);
        answer1.setAnswerAvailability(answerAvailability);
        answers.put(1, answer1);
        Answer answer2 = new Answer();
        answer2.setLabel("answer2");
        answer2.setAnswerAvailability(answerAvailability);
        answers.put(2, answer2);
        Answer answer3 = new Answer();
        answer3.setLabel("answer3");
        answer3.setAnswerAvailability(answerAvailability);
        answers.put(3, answer3);
        phrase.setAnswers(answers);
        phrases.put(1, phrase);
        dialog.setPhrases(phrases);

        Map<Integer, Answer> answerMap = dialog.getAvailableAnswers(1, user);

        assertEquals(answers, answerMap);
    }
}
