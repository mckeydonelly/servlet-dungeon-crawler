package com.mckeydonelly.servletdungeoncrawler.settings;

public enum SettingsType {
    SAVED_USERS_FILE_PATH("app.game.savedUserConfigurationFile", "Saved users configuration file path"),
    MAP_CONFIG_FILE_PATH("app.game.mapConfigurationFile", "Map configuration file path"),
    CRATE_CONFIG_FILE_PATH("app.game.crateConfigurationFile", "Crates configuration file path"),
    NPC_CONFIG_FILE_PATH("app.game.npcConfigurationFile", "Units configuration file path"),
    QUEST_CONFIG_FILE_PATH("app.game.questConfigurationFile", "Quests configuration file path"),
    DIALOG_CONFIG_FILE_PATH("app.game.dialoguesConfigurationFile", "Dialogues configuration file path"),
    ITEM_CONFIG_FILE_PATH("app.game.itemConfigurationFile", "Items configuration file path"),
    SERVLET_INIT_PATH("app.servlet.init", "Initialization servlet path"),
    SERVLET_START_PATH("app.servlet.start", "Starting game servlet path"),
    SERVLET_ROOM_PATH("app.servlet.room", "Room game servlet path"),
    SERVLET_STATISTIC_PATH("app.servlet.user.statistic", "Statistic game servlet path"),
    SERVLET_MOVE_PATH("app.servlet.map.move", "Map move servlet path"),
    SERVLET_CRATE_ACTION_PATH("app.servlet.location.crateAction", "Open crate action servlet path"),
    SERVLET_TAKE_ACTION_PATH("app.servlet.location.takeAction", "Item on map servlet path"),
    SERVLET_UNIT_ATTACK_PATH("app.servlet.unit.attack", "Attack unit action servlet path"),
    SERVLET_UNIT_SPEAK_PATH("app.servlet.unit.speak", "Speak with unit action servlet path"),
    SERVLET_INVENTORY_PATH("app.servlet.user.inventory", "Open inventory servlet path"),
    SERVLET_QUESTS_PATH("app.servlet.user.quests", "Quest menu servlet path"),
    SERVLET_EQUIP_PATH("app.servlet.user.inventory.equip", "Equip item servlet path"),
    SERVLET_UNEQUIP_PATH("app.servlet.user.inventory.uneqip", "Unequip item servlet path");

    private final String typeCode;
    private final String description;

    SettingsType(String typeCode, String description) {
        this.typeCode = typeCode;
        this.description = description;
    }

    public String getTypeCode() {
        return typeCode;
    }
    public String getDescription() {
        return description;
    }
}
