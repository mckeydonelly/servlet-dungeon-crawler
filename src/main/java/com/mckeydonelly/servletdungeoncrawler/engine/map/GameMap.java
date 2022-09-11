package com.mckeydonelly.servletdungeoncrawler.engine.map;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.Location;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class GameMap implements Serializable {
    private String prologue;
    private Map<Integer, Location> locationsOnMap;

    public Location fetchLocationById(Integer id) {
        return locationsOnMap.get(id);
    }
}

