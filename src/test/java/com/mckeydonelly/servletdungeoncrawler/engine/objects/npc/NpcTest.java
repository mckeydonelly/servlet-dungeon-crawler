package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DamageType;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DefenseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NpcTest {
    private Npc npc;

    @BeforeEach
    void setUp() {
        npc = new Npc();
        npc.setDamageTypes(new HashMap<>());
        npc.setDefenseTypes(new HashMap<>());
    }

    @Test
    void should_return_exception_when_modify_damage_type_list() {
        Map<DamageType, Integer> damageTypes = npc.getDamageTypes();

        assertThrows(UnsupportedOperationException.class, () -> damageTypes.put(DamageType.MAGIC, 23));
    }

    @Test
    void should_return_exception_when_modify_defense_type_list() {
        Map<DefenseType, Integer> defenseTypes = npc.getDefenseTypes();

        assertThrows(UnsupportedOperationException.class, () -> defenseTypes.put(DefenseType.MAGIC, 23));
    }
}
