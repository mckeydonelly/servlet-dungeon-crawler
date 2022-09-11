package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mckeydonelly.servletdungeoncrawler.engine.map.GameMap;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.AnswerActionManager;
import com.mckeydonelly.servletdungeoncrawler.repositories.*;
import com.mckeydonelly.servletdungeoncrawler.settings.SettingsType;
import com.mckeydonelly.servletdungeoncrawler.user.SessionManager;
import com.mckeydonelly.servletdungeoncrawler.user.UserManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class AppConfigurationServlet implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigurationServlet.class);
    private static final String APP_SETTINGS_PATH = "app.properties";
    private static final ObjectMapper mapper = new ObjectMapper();
    private Properties appSettings;
    private UserRepository userRepository;
    private CrateRepository crateRepository;
    private DialogRepository dialogRepository;
    private NpcRepository npcRepository;
    private QuestRepository questRepository;
    private ItemRepository itemRepository;
    private SessionManager sessionManager;
    private UserManager userManager;
    private AnswerActionManager answerActionManager;
    private GameMap gameMap;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //TODO move to settings package?
        //TODO move jsp paths to config?
        logger.info("Start application");

        appSettings = initProperties();

        gameMap = initializeJson(appSettings.getProperty(SettingsType.MAP_CONFIG_FILE_PATH.getTypeCode()), GameMap.class);

        //TODO Initialize saved users from system directory? Not in resources
        //userRepository = initializeJson(appSettings.getProperty(SettingsType.SERVLET_SAVED_USERS_FILE_PATH.getTypeCode()), UserRepositoryInMemory.class);
        userRepository = new UserRepositoryInMemory();

        crateRepository = initializeJson(appSettings.getProperty(SettingsType.CRATE_CONFIG_FILE_PATH.getTypeCode()), CrateRepositoryInMemory.class);
        dialogRepository = initializeJson(appSettings.getProperty(SettingsType.DIALOG_CONFIG_FILE_PATH.getTypeCode()), DialogRepositoryInMemory.class);
        npcRepository = initializeJson(appSettings.getProperty(SettingsType.NPC_CONFIG_FILE_PATH.getTypeCode()), NpcRepositoryInMemory.class);
        questRepository = initializeJson(appSettings.getProperty(SettingsType.QUEST_CONFIG_FILE_PATH.getTypeCode()), QuestRepositoryInMemory.class);
        itemRepository = initializeJson(appSettings.getProperty(SettingsType.ITEM_CONFIG_FILE_PATH.getTypeCode()), ItemRepositoryInMemory.class);

        sessionManager = new SessionManager(userRepository);
        userManager = new UserManager(userRepository, sessionManager);
        answerActionManager = new AnswerActionManager(questRepository, itemRepository);

        ServletContext context = sce.getServletContext();
        context.addServlet("IndexServlet", new IndexServlet(sessionManager, gameMap))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_INIT_PATH.getTypeCode()));
        context.addServlet("StartServlet", new StartServlet(userManager))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_START_PATH.getTypeCode()));
        context.addServlet("RoomServlet", new RoomServlet(sessionManager,
                        itemRepository,
                        crateRepository,
                        npcRepository,
                        gameMap))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_ROOM_PATH.getTypeCode()));
        context.addServlet("MoveServlet", new MoveServlet(sessionManager, gameMap))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_MOVE_PATH.getTypeCode()));
        context.addServlet("TakeServlet", new TakeServlet(sessionManager))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_TAKE_ACTION_PATH.getTypeCode()));
        context.addServlet("InventoryServlet", new InventoryServlet(sessionManager, itemRepository))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_INVENTORY_PATH.getTypeCode()));
        context.addServlet("SpeakServlet", new SpeakServlet(sessionManager,
                        answerActionManager,
                        npcRepository,
                        dialogRepository,
                        questRepository))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_UNIT_SPEAK_PATH.getTypeCode()));
        context.addServlet("StatisticServlet", new StatisticServlet(sessionManager))
                .addMapping(appSettings.getProperty(SettingsType.SERVLET_STATISTIC_PATH.getTypeCode()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }

    private Properties initProperties() {
        logger.info("Initialization app...");
        Properties properties = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(APP_SETTINGS_PATH)) {
            properties.load(inputStream);

            //TODO make more beautiful
            logger.info("Starting parameters:");
            properties.entrySet()
                    .forEach(setting -> logger.info(String.valueOf(setting)));
        } catch (IOException e) {
            logger.error("Can't find properties file by path: {}", APP_SETTINGS_PATH);
            throw new RuntimeException(e);
        }
        return properties;
    }

    private <T> T initializeJson(String filePath, Class<T> destinationClass) {
        logger.info("Initialize {} from file {}", destinationClass.getName(), filePath);
        T returnObject = null;
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            returnObject = mapper.readValue(in, destinationClass);
        } catch (Exception e) {
            logger.error("Error with read quest file {}", filePath, e);
            throw new RuntimeException(e);
        }

        return returnObject;
    }
}
