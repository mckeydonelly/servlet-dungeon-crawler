package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestInfo {
    private String issuedBy;
    private String status;
    private String questType;
    private String targetName;
    private String info;
}
