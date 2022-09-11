package com.mckeydonelly.servletdungeoncrawler.engine.objects.crate;

import com.mckeydonelly.servletdungeoncrawler.engine.action.Open;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import lombok.Data;

@Data
public class Crate implements Open {
    private String id;
    private String name;
    private String imgPathClosed;
    private String imgPathOpen;
    private CrateSize crateSize;
    private CrateType crateType;
    private String keyId;

    @Override
    public Item open() {
        return null;
    }
}
