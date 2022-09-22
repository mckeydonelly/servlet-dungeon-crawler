package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.item.Item;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.QuestType;
import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.Repository;
import com.mckeydonelly.servletdungeoncrawler.user.QuestStatus;
import com.mckeydonelly.servletdungeoncrawler.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnswerActionManager {
    private static final Logger logger = LoggerFactory.getLogger(AnswerActionManager.class);

    private final Repository<Quest, String> questRepository;
    private final Repository<Item, String> itemRepository;

    public AnswerActionManager(Repository<Quest, String> questRepository, Repository<Item, String> itemRepository) {
        this.questRepository = questRepository;
        this.itemRepository = itemRepository;
    }

    public int answerAction(User user, Answer answer) {
        logger.info("Make answer action");
        AnswerAction answerAction = answer.getAnswerAction();
        boolean isSuccess = switch (answerAction.getAnswerActionType()) {
            case TAKE_ITEM -> takeItem(user, answerAction.getItemId());
            case TAKE_QUEST -> takeQuest(user, answerAction.getQuestId());
            case CHECK_QUEST -> checkQuest(user, answerAction);
            case NONE -> true;
        };

        if (isSuccess) {
            return answer.getSuccessPhraseId();
        }
        return answer.getDefaultPhraseId();
    }

    private boolean takeItem(User user, String itemId) {
        logger.info("User add to inventory item: {}", itemId);
        user.addItem(itemId);
        return true;
    }

    private boolean takeQuest(User user, String questId) {
        logger.info("User add to quests quest: {}", questId);
        user.addQuest(questId);
        return true;
    }

    private boolean checkQuest(User user, AnswerAction answerAction) {
        logger.info("Check quest {} status", answerAction.getQuestId());
        Quest quest = questRepository.findById(answerAction.getQuestId());
        logger.info("Quest details: {}", quest);

        boolean isSuccess = false;
        if (quest.getQuestType() == QuestType.ENEMY_KILL) {
            isSuccess = user.isEnemyKilled(quest.getTargetId());
        } else if (quest.getQuestType() == QuestType.ITEM_BRING) {
            isSuccess = user.isExistsItem(itemRepository.findById(quest.getTargetId()).getId());
        }

        if (isSuccess) {
            user.changeQuestStatus(answerAction.getQuestId(), QuestStatus.SUCCEEDED);
        }

        return isSuccess;
    }
}
