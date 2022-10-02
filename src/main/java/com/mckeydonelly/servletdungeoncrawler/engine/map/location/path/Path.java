package com.mckeydonelly.servletdungeoncrawler.engine.map.location.path;

import lombok.Data;

@Data
public class Path {
    private Integer id;
    private int linkedLocationId;
    private boolean isOpen;
    private String openItemId;
}
