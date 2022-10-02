package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class QuestInfo {
    private String issuedBy;
    private String status;
    private String questType;
    private String targetName;
    private String info;
}
