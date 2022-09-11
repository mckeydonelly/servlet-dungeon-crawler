package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationInfo {
    private Integer id;
    private String name;
}
