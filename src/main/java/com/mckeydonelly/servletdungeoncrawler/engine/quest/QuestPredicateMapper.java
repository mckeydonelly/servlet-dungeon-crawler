package com.mckeydonelly.servletdungeoncrawler.engine.quest;

import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.NpcRepository;
import com.mckeydonelly.servletdungeoncrawler.user.User;

import java.util.Objects;
import java.util.function.Predicate;

public class QuestPredicateMapper {
    private final NpcRepository npcRepository;
    private final ItemRepository itemRepository;

    public QuestPredicateMapper(NpcRepository npcRepository, ItemRepository itemRepository) {
        this.npcRepository = npcRepository;
        this.itemRepository = itemRepository;
    }

    public Predicate<User> map(Quest quest) {
        return switch (quest.getQuestType()) {
            case ENEMY_KILL -> user -> user.getEnemyKilled().containsValue(quest.getTargetId());
            case ITEM_BRING -> user -> user.getInventory().stream().anyMatch(item -> Objects.equals(itemRepository.findById(item), quest.getTargetId()));
        };
    }
}
