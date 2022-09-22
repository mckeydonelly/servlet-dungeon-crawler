package com.mckeydonelly.servletdungeoncrawler.engine.map;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.Location;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

@Setter
public class GameMap implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(GameMap.class);

    @Getter
    private String prologue;
    @Getter
    private String mapImgPath;
    private Map<Integer, Location> locationsOnMap;

    public Location fetchLocationById(Integer id) {
        try {
            return locationsOnMap.get(id);
        } catch (NullPointerException e) {
            logger.error("Didn't found location with id: {}", id);
            throw new IllegalArgumentException(e);
        }
    }
}

