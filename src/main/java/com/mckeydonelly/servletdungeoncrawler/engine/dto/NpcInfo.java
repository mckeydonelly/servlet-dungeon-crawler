package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NpcInfo {
    private int mapId;
    private String npcId;
    private String name;
    private String imgPath;
}
