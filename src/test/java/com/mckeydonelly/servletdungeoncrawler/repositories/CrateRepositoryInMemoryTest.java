package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.crate.Crate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrateRepositoryInMemoryTest {
    private Repository<Crate, String> crateRepository;

    @BeforeEach
    void setUp() {
        crateRepository = new CrateRepositoryInMemory();
    }

    @Test
    void should_save_entity() {
        Crate crate = new Crate();
        crate.setId("crateTest");

        crateRepository.save(crate);

        assertTrue(crateRepository.exists(crate.getId()));
    }

    @Test
    void should_find_crate_by_id() {
        Crate crate = new Crate();
        crate.setId("crateTest");
        crateRepository.save(crate);

        Crate actual = crateRepository.findById(crate.getId());

        assertEquals(crate, actual);
    }

    @Test
    void should_return_all_crates() {
        Crate crate = new Crate();
        crate.setId("crateTest");
        crateRepository.save(crate);

        Iterable<Crate> crates = crateRepository.findAll();

        Crate actual = crates.iterator().next();

        assertEquals(crate, actual);
    }

    @Test
    void should_return_count() {
        Crate crate = new Crate();
        crate.setId("crateTest");

        crateRepository.save(crate);

        assertEquals(1, crateRepository.count());
    }

    @Test
    void should_delete_crate() {
        Crate crate = new Crate();
        crate.setId("crateTest");

        crateRepository.save(crate);
        crateRepository.delete(crate);

        assertFalse(crateRepository.exists(crate.getId()));
    }

    @Test
    void should_exists_crate_by_id() {
        Crate crate = new Crate();
        crate.setId("crateTest");

        crateRepository.save(crate);

        assertTrue(crateRepository.exists(crate.getId()));
    }
}
