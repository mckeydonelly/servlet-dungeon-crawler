package com.mckeydonelly.servletdungeoncrawler.engine.state;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ItemState {
    private boolean isTaken;
}
