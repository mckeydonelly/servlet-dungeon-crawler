package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer;

import lombok.Data;

@Data
public class AnswerAction {
    private AnswerActionType answerActionType;
    private String questId;
    private String itemId;
}
