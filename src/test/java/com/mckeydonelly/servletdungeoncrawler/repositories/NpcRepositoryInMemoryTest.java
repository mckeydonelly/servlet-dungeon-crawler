package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.Npc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NpcRepositoryInMemoryTest {
    private Repository<Npc, String> npcRepository;

    @BeforeEach
    void setUp() {
        npcRepository = new NpcRepositoryInMemory();
    }

    @Test
    void should_save_entity() {
        Npc npc = new Npc();
        npc.setId("npcTest");

        npcRepository.save(npc);

        assertTrue(npcRepository.exists(npc.getId()));
    }

    @Test
    void should_find_npc_by_id() {
        Npc npc = new Npc();
        npc.setId("npcTest");
        npcRepository.save(npc);

        Npc actual = npcRepository.findById(npc.getId());

        assertEquals(npc, actual);
    }

    @Test
    void should_return_all_npcs() {
        Npc npc = new Npc();
        npc.setId("npcTest");
        npcRepository.save(npc);

        Iterable<Npc> npcs = npcRepository.findAll();

        Npc actual = npcs.iterator().next();

        assertEquals(npc, actual);
    }

    @Test
    void should_return_count() {
        Npc npc = new Npc();
        npc.setId("npcTest");

        npcRepository.save(npc);

        assertEquals(1, npcRepository.count());
    }

    @Test
    void should_delete_npc() {
        Npc npc = new Npc();
        npc.setId("npcTest");

        npcRepository.save(npc);
        npcRepository.delete(npc);

        assertFalse(npcRepository.exists(npc.getId()));
    }

    @Test
    void should_exists_npc_by_id() {
        Npc npc = new Npc();
        npc.setId("npcTest");

        npcRepository.save(npc);

        assertTrue(npcRepository.exists(npc.getId()));
    }
}
