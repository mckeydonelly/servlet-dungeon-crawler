package com.mckeydonelly.servletdungeoncrawler.engine.state;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.path.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {
    private State state;

    @BeforeEach
    void setUp() {
        state = new State();
    }

    @Test
    void should_return_exception_when_modify_gameLog() {
        List<String> gameLog = state.getGameLog();

        assertThrows(UnsupportedOperationException.class, () -> gameLog.add("Test"));
    }

    @Test
    void should_add_new_game_log() {
        String expected = "Test log";

        state.addGameLog(expected);
        String actual = state.getGameLog().get(state.getGameLog().size() - 1);
        assertTrue(actual.endsWith(expected));
    }

    @Test
    void should_add_and_return_new_path_state() {
        Path path = new Path();
        path.setId(1);
        path.setLinkedLocationId(3);
        path.setOpenItemId(null);
        path.setOpen(false);

        state.addPath("1-3", true);

        assertTrue(state.checkPath("1-3", path));
        assertFalse(state.checkPath("3-3", path));
    }

    @Test
    void should_add_and_return_item_state() {
        state.addItem(34);
        assertFalse(state.checkItem(34));
        assertTrue(state.checkItem(35));
    }

    @Test
    void should_add_and_return_crate_state() {
        state.addCrate(34, true);
        assertTrue(state.checkCrate(34));
        assertFalse(state.checkCrate(35));
    }

    @Test
    void should_add_and_return_npc_state() {
        state.addNpc(34, 122);
        state.addNpc(35, 0);
        state.addNpc(36, -10);
        assertTrue(state.checkNpc(34));
        assertFalse(state.checkNpc(35));
        assertFalse(state.checkNpc(36));
        assertTrue(state.checkNpc(37));
    }
}
