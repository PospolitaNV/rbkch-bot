package com.npospolita.rbkchbot;

import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BotIntegrationTest extends DatabaseTestBase {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    UpdateProcessService updateProcessService;

    @Autowired
    WorkingChatRepository workingChatRepository;

    @Autowired
    ChatService chatService;

    @Test
    void repositoryTest() {
        Update update = TestData.getAdminMessageUpdateWithCommand("/setup");

        assertFalse(chatService.isInWorkingChat(update.message().chat().id()));

        updateProcessService.process(update);
        assertTrue(chatService.isInWorkingChat(update.message().chat().id()));

        updateProcessService.process(update);
        assertTrue(chatService.isInWorkingChat(update.message().chat().id()));

        chatService.removeWorkingChat(update.message().chat().id());
        assertFalse(chatService.isInWorkingChat(update.message().chat().id()));

    }

}
