package com.mckeydonelly.servletdungeoncrawler.engine.objects.crate;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.Entity;
import lombok.Data;

@Data
public class Crate extends Entity {
    private String id;
    private String name;
    private String imgPathClosed;
    private String imgPathOpen;
    private CrateSize crateSize;
    private CrateType crateType;
    private String keyId;
}
