package com.mckeydonelly.servletdungeoncrawler.engine.gamestate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NpcState {
    private boolean isDeath;
    private int currentHealth;
}
