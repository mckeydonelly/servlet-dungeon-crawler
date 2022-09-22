package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import lombok.Data;

@Data
public class AnswerAvailability {
    private AvailableType availableType;
    private boolean availableCondition;
    private String questId;
    private String itemId;

    public enum AvailableType {
        ALWAYS,
        QUEST,
        ITEM,
        ALL;
    }
}
