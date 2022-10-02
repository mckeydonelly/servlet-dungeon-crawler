package com.mckeydonelly.servletdungeoncrawler.engine.quest;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.Entity;
import lombok.Data;

@Data
public class Quest extends Entity {
    private String id;
    private String issuesById;
    private QuestType questType;
    private String targetId;
    private String info;
}
