package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PathInfo {
    private LocationInfo locationInfo;
    private boolean isOpened;
}
