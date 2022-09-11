package com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog;

import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.Answer;
import com.mckeydonelly.servletdungeoncrawler.engine.objects.npc.dialog.answer.AnswerAction;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.Quest;
import com.mckeydonelly.servletdungeoncrawler.engine.quest.QuestType;
import com.mckeydonelly.servletdungeoncrawler.repositories.ItemRepository;
import com.mckeydonelly.servletdungeoncrawler.repositories.QuestRepository;
import com.mckeydonelly.servletdungeoncrawler.user.QuestStatus;
import com.mckeydonelly.servletdungeoncrawler.user.User;

public class AnswerActionManager {
    private final QuestRepository questRepository;
    private final ItemRepository itemRepository;

    public AnswerActionManager(QuestRepository questRepository, ItemRepository itemRepository) {
        this.questRepository = questRepository;
        this.itemRepository = itemRepository;
    }

    public int answerAction(User user, Answer answer) {
        AnswerAction answerAction = answer.getAnswerAction();
        boolean isSuccess = switch (answerAction.getAnswerActionType()) {
            case TAKE_ITEM -> takeItem(user, answerAction);
            case TAKE_QUEST -> takeQuest(user, answerAction);
            case CHECK_QUEST -> checkQuest(user, answerAction);
            case NONE -> true;
        };

        if(isSuccess) {
            return answer.getSuccessPhraseId();
        }
        return answer.getDefaultPhraseId();
    }

    private boolean takeItem(User user, AnswerAction answerAction) {
        return user.getInventory().add(answerAction.getItemId());
    }

    private boolean takeQuest(User user, AnswerAction answerAction) {
        user.getQuests().putIfAbsent(answerAction.getQuestId(), QuestStatus.ACTIVE);
        return true;
    }

    private boolean checkQuest(User user, AnswerAction answerAction) {
        Quest quest = questRepository.findById(answerAction.getQuestId());

        if(quest.getQuestType() == QuestType.ENEMY_KILL) {
            return user.getEnemyKilled().containsKey(quest.getTargetId());
        } else {
            return user.getInventory().contains(itemRepository.findById(quest.getTargetId()).getId());
        }

    }
}
