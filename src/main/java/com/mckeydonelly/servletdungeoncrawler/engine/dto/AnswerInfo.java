package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AnswerInfo {
    private int id;
    private String label;
}
