package com.mckeydonelly.servletdungeoncrawler.engine.objects.item;

import com.mckeydonelly.servletdungeoncrawler.engine.action.Use;
import lombok.Data;

import java.util.Map;

@Data
public class Item implements Use {
    private String id;
    private String name;
    private String imgPath;
    private ItemType type;
    private int weight;
    private Map<DamageType, Integer> damageTypeList;
    private Map<DefenseType, Integer> defenseTypeList;
    private int healAmount;

    @Override
    public int use() {
        return 0;
    }
}
