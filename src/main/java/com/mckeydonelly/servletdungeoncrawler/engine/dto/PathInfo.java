package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PathInfo {
    private LocationInfo locationInfo;
    private boolean isOpened;
}
