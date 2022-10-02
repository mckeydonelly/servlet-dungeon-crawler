package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.Entity;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DamageType;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DefenseType;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Setter
public class Npc extends Entity {
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String imgPath;
    @Getter
    private int health;
    @Getter
    private int maxHealth;
    private Map<DamageType, Integer> damageTypes;
    private Map<DefenseType, Integer> defenseTypes;
    @Getter
    private String dialogId;

    public Map<DamageType, Integer> getDamageTypes() {
        return Collections.unmodifiableMap(damageTypes);
    }

    public Map<DefenseType, Integer> getDefenseTypes() {
        return Collections.unmodifiableMap(defenseTypes);
    }

}
