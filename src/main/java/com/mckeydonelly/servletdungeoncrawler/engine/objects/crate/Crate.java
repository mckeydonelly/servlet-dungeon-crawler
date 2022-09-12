package com.mckeydonelly.servletdungeoncrawler.engine.objects.crate;

import lombok.Data;

@Data
public class Crate {
    private String id;
    private String name;
    private String imgPathClosed;
    private String imgPathOpen;
    private CrateSize crateSize;
    private CrateType crateType;
    private String keyId;
}
