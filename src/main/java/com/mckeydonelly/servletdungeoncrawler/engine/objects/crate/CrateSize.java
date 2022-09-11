package com.mckeydonelly.servletdungeoncrawler.engine.objects.crate;

public enum CrateSize {
    SMALL(1),
    MIDDLE(2),
    BIG(3);

    private final int maxItemInCrate;

    CrateSize(int maxItemInCrate) {
        this.maxItemInCrate = maxItemInCrate;
    }
}
