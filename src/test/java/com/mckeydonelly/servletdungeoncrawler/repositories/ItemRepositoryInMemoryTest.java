package com.mckeydonelly.servletdungeoncrawler.repositories;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryInMemoryTest {
    private Repository<Item, String> itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository = new ItemRepositoryInMemory();
    }

    @Test
    void should_save_entity() {
        Item item = new Item();
        item.setId("itemTest");

        itemRepository.save(item);

        assertTrue(itemRepository.exists(item.getId()));
    }

    @Test
    void should_find_item_by_id() {
        Item item = new Item();
        item.setId("itemTest");
        itemRepository.save(item);

        Item actual = itemRepository.findById(item.getId());

        assertEquals(item, actual);
    }

    @Test
    void should_return_all_items() {
        Item item = new Item();
        item.setId("itemTest");
        itemRepository.save(item);

        Iterable<Item> items = itemRepository.findAll();

        Item actual = items.iterator().next();

        assertEquals(item, actual);
    }

    @Test
    void should_return_count() {
        Item item = new Item();
        item.setId("itemTest");

        itemRepository.save(item);

        assertEquals(1, itemRepository.count());
    }

    @Test
    void should_delete_item() {
        Item item = new Item();
        item.setId("itemTest");

        itemRepository.save(item);
        itemRepository.delete(item);

        assertFalse(itemRepository.exists(item.getId()));
    }

    @Test
    void should_exists_item_by_id() {
        Item item = new Item();
        item.setId("itemTest");

        itemRepository.save(item);

        assertTrue(itemRepository.exists(item.getId()));
    }
}
