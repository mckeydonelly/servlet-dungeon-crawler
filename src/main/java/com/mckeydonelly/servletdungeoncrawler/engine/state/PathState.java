package com.mckeydonelly.servletdungeoncrawler.engine.state;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PathState {
    private boolean isOpen;
}
