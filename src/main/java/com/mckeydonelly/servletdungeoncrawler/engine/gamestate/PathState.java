package com.mckeydonelly.servletdungeoncrawler.engine.gamestate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PathState {
    private boolean isOpen;
}
