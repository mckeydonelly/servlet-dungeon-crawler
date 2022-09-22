package com.mckeydonelly.servletdungeoncrawler.engine.map.location;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.path.Path;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.crate.Crate;
import com.mckeydonelly.servletdungeoncrawler.engine.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setPaths(new HashMap<>());
        location.setCrateOnLocationList(new HashMap<>());
        location.setNpcOnLocationList(new HashMap<>());
        location.setItemOnLocationList(new HashMap<>());
    }

    @Test
    void should_return_exception_when_modify_paths() {
        Map<Integer, Path> paths = location.getPaths();

        assertThrows(UnsupportedOperationException.class, () -> paths.put(1, new Path()));
    }

    @Test
    void should_return_exception_when_modify_crates() {
        Map<Integer, String> crateOnLocationList = location.getCrateOnLocationList();

        assertThrows(UnsupportedOperationException.class, () -> crateOnLocationList.put(1, "Test"));
    }

    @Test
    void should_return_exception_when_modify_npcs() {
        Map<Integer, String> npcOnLocationList = location.getNpcOnLocationList();

        assertThrows(UnsupportedOperationException.class, () -> npcOnLocationList.put(1, "Test"));
    }

    @Test
    void should_return_exception_when_modify_items() {
        Map<Integer, String> itemOnLocationList = location.getItemOnLocationList();

        assertThrows(UnsupportedOperationException.class, () -> itemOnLocationList.put(1, "Test"));
    }
}
