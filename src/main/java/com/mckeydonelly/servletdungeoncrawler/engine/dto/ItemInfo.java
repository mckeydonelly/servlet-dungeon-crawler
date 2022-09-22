package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemInfo {
    private String imgPath;
    private String name;
    private String type;
    private int weight;
    private int damage;
    private int magicDamage;
    private int defense;
    private int magicDefense;
    private int healAmount;
}
