package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer;

import lombok.Data;

@Data
public class Answer {
    private String label;
    private AnswerAvailability answerAvailability;
    private AnswerAction answerAction;
    private int successPhraseId;
    private int defaultPhraseId;
}
