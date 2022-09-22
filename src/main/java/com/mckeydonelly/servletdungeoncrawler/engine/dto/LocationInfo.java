package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationInfo {
    private Integer id;
    private String name;
}
