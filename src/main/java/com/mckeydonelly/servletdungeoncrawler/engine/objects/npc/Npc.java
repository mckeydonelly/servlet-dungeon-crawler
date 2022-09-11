package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc;

import com.mckeydonelly.servletdungeoncrawler.engine.action.Attack;
import com.mckeydonelly.servletdungeoncrawler.engine.action.Speak;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DamageType;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DefenseType;
import lombok.Data;

import java.util.Map;

@Data
public class Npc implements Attack, Speak {
    private String id;
    private String name;
    private String imgPath;
    private int health;
    private int maxHealth;
    private Map<DamageType, Integer> damageTypeList;
    private Map<DefenseType, Integer> defenseTypeList;
    private String dialogId;

    @Override
    public int attack() {
        return 0;
    }

    @Override
    public String speak() {
        return null;
    }
}
