package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserHealthInfo {
    private int health;
    private int maxHealth;
    private int percentOfMax;
}
