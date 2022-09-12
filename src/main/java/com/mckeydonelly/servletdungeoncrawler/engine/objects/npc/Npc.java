package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DamageType;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DefenseType;
import lombok.Data;

import java.util.Map;

@Data
public class Npc {
    private String id;
    private String name;
    private String imgPath;
    private int health;
    private int maxHealth;
    private Map<DamageType, Integer> damageTypeList;
    private Map<DefenseType, Integer> defenseTypeList;
    private String dialogId;
}
