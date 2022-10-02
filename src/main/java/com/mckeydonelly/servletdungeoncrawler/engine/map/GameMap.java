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
        return locationsOnMap.get(id);
    }

    public boolean validateLocationId(Integer currentLocationId, Integer nextLocationId) {
        boolean result = false;
        if (nextLocationId > 0 && nextLocationId <= locationsOnMap.size()) {
            return result;
        }


        result = fetchLocationById(currentLocationId)
                .getPaths()
                .values()
                .stream()
                .anyMatch(path -> path.getLinkedLocationId() == nextLocationId);
        logger.info("Validating location id {}: {}", nextLocationId, result);
        return result;
    }
}

