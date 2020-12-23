package com.npospolita.rbkchbot;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BotIntegrationTest extends DatabaseTestBase {

    @Autowired
    UpdateProcessService updateProcessService;

    @Autowired
    WorkingChatRepository workingChatRepository;

    @Autowired
    ChatService chatService;

    @MockBean
    TelegramApi telegramApi;

    @Test
    void chatServiceTest() {
        Update update = TestData.getAdminMessageUpdateWithCommand("/setup");

        assertFalse(chatService.isInWorkingChat(update.message().chat().id()));

        updateProcessService.process(update);
        assertTrue(chatService.isInWorkingChat(update.message().chat().id()));

        updateProcessService.process(update);
        assertTrue(chatService.isInWorkingChat(update.message().chat().id()));

        chatService.removeWorkingChat(update.message().chat().id());
        assertFalse(chatService.isInWorkingChat(update.message().chat().id()));

    }

    @Test
    void commandsAddRemoveTest() {
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/add_command-test-keknut"));
        verify(telegramApi, times(1)).addCommand(eq("test"), eq("keknut"));
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/remove_command test"));
        verify(telegramApi, times(1)).removeCommand(eq("test"));
    }

}
