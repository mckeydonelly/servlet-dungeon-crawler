package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.Dialog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DialogRepositoryInMemoryTest {
    private Repository<Dialog, String> dialogRepository;

    @BeforeEach
    void setUp() {
        dialogRepository = new DialogRepositoryInMemory();
    }

    @Test
    void should_save_entity() {
        Dialog dialog = new Dialog();
        dialog.setId("dialogTest");

        dialogRepository.save(dialog);

        assertTrue(dialogRepository.exists(dialog.getId()));
    }

    @Test
    void should_find_dialog_by_id() {
        Dialog dialog = new Dialog();
        dialog.setId("dialogTest");
        dialogRepository.save(dialog);

        Dialog actual = dialogRepository.findById(dialog.getId());

        assertEquals(dialog, actual);
    }

    @Test
    void should_return_all_dialogs() {
        Dialog dialog = new Dialog();
        dialog.setId("dialogTest");
        dialogRepository.save(dialog);

        Iterable<Dialog> dialogs = dialogRepository.findAll();

        Dialog actual = dialogs.iterator().next();

        assertEquals(dialog, actual);
    }

    @Test
    void should_return_count() {
        Dialog dialog = new Dialog();
        dialog.setId("dialogTest");

        dialogRepository.save(dialog);

        assertEquals(1, dialogRepository.count());
    }

    @Test
    void should_delete_dialog() {
        Dialog dialog = new Dialog();
        dialog.setId("dialogTest");

        dialogRepository.save(dialog);
        dialogRepository.delete(dialog);

        assertFalse(dialogRepository.exists(dialog.getId()));
    }

    @Test
    void should_exists_dialog_by_id() {
        Dialog dialog = new Dialog();
        dialog.setId("dialogTest");

        dialogRepository.save(dialog);

        assertTrue(dialogRepository.exists(dialog.getId()));
    }
}
