package com.mckeydonelly.servletdungeoncrawler.engine.map.location;

import com.mckeydonelly.servletdungeoncrawler.engine.map.location.path.Path;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Location {
    private int id;
    private String name;
    private Map<Integer, Path> paths;
    private Map<Integer, String> crateOnLocationList;
    private Map<Integer, String> npcOnLocationList;
    private Map<Integer, String> itemOnLocationList;
}
