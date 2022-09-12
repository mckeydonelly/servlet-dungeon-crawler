package com.mckeydonelly.servletdungeoncrawler.engine.quest;

import lombok.Data;

@Data
public class Quest {
    private String id;
    private String issuesById;
    private QuestType questType;
    private String targetId;
    private String info;
}
