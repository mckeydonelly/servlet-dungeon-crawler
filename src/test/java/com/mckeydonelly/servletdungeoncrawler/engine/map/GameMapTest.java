package com.mckeydonelly.servletdungeoncrawler.engine.map;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {
    private GameMap gameMap;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap();
    }

    @Test
    void should_return_location_by_id() {
        Map<Integer, Location> locationsOnMap = new HashMap<>();
        Location expected = new Location();
        expected.setId(1);
        expected.setName("expected");
        locationsOnMap.put(1, expected);

        gameMap.setLocationsOnMap(locationsOnMap);

        assertEquals(expected.getName(), gameMap.fetchLocationById(1).getName());
    }

    @Test
    void should_return_exception_when_fetch_location_by_illegal_id() {
        assertThrows(IllegalArgumentException.class, () -> gameMap.fetchLocationById(34));
    }
}
