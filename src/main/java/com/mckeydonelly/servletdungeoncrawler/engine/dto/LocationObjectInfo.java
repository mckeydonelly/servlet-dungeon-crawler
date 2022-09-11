package com.mckeydonelly.servletdungeoncrawler.engine.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationObjectInfo {
    private int id;
    private String objectId;
    private String name;
    private String imgPath;
    private ObjectType type;
    private boolean isActive;

    public enum ObjectType {
        CRATE,
        ITEM;
    }
}
