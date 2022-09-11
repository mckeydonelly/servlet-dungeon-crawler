package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserHealthInfo {
    private int health;
    private int maxHealth;
    private int percentOfMax;
}
