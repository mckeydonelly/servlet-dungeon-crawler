package com.mckeydonelly.servletdungeoncrawler.servlet.user.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mckeydonelly.servletdungeoncrawler.engine.dto.ItemInfo;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DamageType;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.DefenseType;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.session.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(InventoryServlet.class);
    private final ObjectMapper jsonConverter = new ObjectMapper();
    private final SessionManager sessionManager;
    private final Repository<Item, String> itemRepository;

    public InventoryServlet(SessionManager sessionManager, Repository<Item, String> itemRepository) {
        this.sessionManager = sessionManager;
        this.itemRepository = itemRepository;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Incoming request to {}: {}", request.getRequestURL(), request.getQueryString());
        var user = sessionManager.validateUser(request);

        response.setContentType("application/json; charset=utf-8");
        var writer = response.getWriter();
        String inventoryJson = jsonConverter.writeValueAsString(prepareItemInfo(user));
        logger.info("Response: {}", inventoryJson);
        writer.print(inventoryJson);
        writer.flush();
    }

    private List<ItemInfo> prepareItemInfo(User user) {
        logger.info("Preparing ItemInfo...");
        return user.getInventory()
                .stream()
                .map(itemRepository::findById)
                .map(item -> ItemInfo.builder()
                        .imgPath(item.getImgPath())
                        .name(item.getName())
                        .type(item.getType().toString().toLowerCase())
                        .weight(item.getWeight())
                        .damage(item.getDamageTypeList().get(DamageType.NORMAL))
                        .magicDamage(item.getDamageTypeList().get(DamageType.MAGIC))
                        .defense(item.getDefenseTypeList().get(DefenseType.NORMAL))
                        .magicDefense(item.getDefenseTypeList().get(DefenseType.MAGIC))
                        .healAmount(item.getHealAmount())
                        .build())
                .collect(Collectors.toList());
    }
}
