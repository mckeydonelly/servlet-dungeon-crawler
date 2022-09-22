package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DialogInfo {
    private int phraseId;
    private String npcId;
    private String npcName;
    private String imgPath;
    private String greeting;
    private String phrase;
    private List<AnswerInfo> answers;
}
