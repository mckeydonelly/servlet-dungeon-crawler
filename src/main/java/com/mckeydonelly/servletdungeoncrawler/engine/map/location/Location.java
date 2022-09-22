package com.mckeydonelly.servletdungeoncrawler.engine.map.location;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.path.Path;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Setter
public class Location {
    private int id;
    @Getter
    private String name;
    private Map<Integer, Path> paths;
    private Map<Integer, String> crateOnLocationList;
    private Map<Integer, String> npcOnLocationList;
    private Map<Integer, String> itemOnLocationList;

    public Map<Integer, Path> getPaths() {
        return Collections.unmodifiableMap(paths);
    }

    public Map<Integer, String> getCrateOnLocationList() {
        return Collections.unmodifiableMap(crateOnLocationList);
    }

    public Map<Integer, String> getNpcOnLocationList() {
        return Collections.unmodifiableMap(npcOnLocationList);
    }

    public Map<Integer, String> getItemOnLocationList() {
        return Collections.unmodifiableMap(itemOnLocationList);
    }
}
