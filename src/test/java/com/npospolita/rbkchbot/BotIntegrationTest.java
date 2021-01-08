package com.npospolita.rbkchbot;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.controller.WebHookReceiver;
import com.npospolita.rbkchbot.domain.constant.AdminCommand;
import com.npospolita.rbkchbot.domain.constant.UserCommand;
import com.npospolita.rbkchbot.repo.MemberRepository;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.MembershipService;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BotIntegrationTest extends DatabaseTestBase {

    @Autowired
    UpdateProcessService updateProcessService;

    @Autowired
    WorkingChatRepository workingChatRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WebHookReceiver webHookReceiver;

    @SpyBean
    ChatService chatService;

    @SpyBean
    MembershipService membershipService;

    @MockBean
    TelegramApi telegramApi;

    @BeforeEach
    public void clear() {
        workingChatRepository.deleteAll();
    }

    @Test
    void chatServiceTest() {
        Update update = TestData.builder()
                .command(AdminCommand.ENABLE_CHAT_SUPPORT)
                .user(TestData.User.ADMIN)
                .build()
                .toUpdate();

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
        updateProcessService.process(TestData.builder()
                .command(AdminCommand.COMMAND_ADD)
                .text("test-keknut")
                .user(TestData.User.ADMIN)
                .build()
                .toUpdate());
        verify(telegramApi, times(1)).addCommand(eq("test"), eq("keknut"));

        updateProcessService.process(TestData.builder()
                .command(AdminCommand.COMMAND_REMOVE)
                .text("test")
                .user(TestData.User.ADMIN)
                .build()
                .toUpdate());
        verify(telegramApi, times(1)).removeCommand(eq("test"));
    }


    @Test
    void membershipTrackingTest() {
        //can't process message in personal chat
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.OTHER)
                .build()
                .toUpdate());
        verify(chatService, times(0)).getTopicChatList();

        //enable in-chat processing
        enableChat(TestData.ChatType.WORKING, "working");

        //processed in-chat message (added membership)
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.WORKING)
                .build()
                .toUpdate());
        verify(chatService, times(1)).getTopicChatList();

        //processed in personal-chat message cause of membership
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.PERSONAL)
                .build()
                .toUpdate());
        verify(chatService, times(2)).getTopicChatList();

        //user left chat
        updateProcessService.process(TestData.builder()
                .action(TestData.Action.LEFT_CHAT)
                .chatType(TestData.ChatType.WORKING)
                .build()
                .toUpdate());
        verify(membershipService, times(1)).removeMembership(any(), any());

        clearInvocations(chatService, membershipService);

        //can't process outside-chat message cause of membership
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.PERSONAL)
                .build()
                .toUpdate());
        verify(chatService, times(0)).getTopicChatList();

        //user join chat
        updateProcessService.process(TestData.builder()
                .action(TestData.Action.JOINED_CHAT)
                .chatType(TestData.ChatType.WORKING)
                .build()
                .toUpdate());
        verify(membershipService, times(1)).addMembership(any(), any());

        //processed outside-chat message cause of membership
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.PERSONAL)
                .build()
                .toUpdate());
        verify(chatService, times(1)).getTopicChatList();

        clearInvocations(chatService, membershipService);

        //disabled chat
        updateProcessService.process(TestData.builder()
                .command(AdminCommand.DISABLE_CHAT_SUPPORT)
                .chatType(TestData.ChatType.WORKING)
                .user(TestData.User.ADMIN)
                .build()
                .toUpdate());

        //can't process outside-chat message cause of membership
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.PERSONAL)
                .build()
                .toUpdate());
        verify(chatService, times(0)).getTopicChatList();

        //can't process in-chat message cause of disabled chat
        updateProcessService.process(TestData.builder()
                .command(UserCommand.CHAT_LIST)
                .chatType(TestData.ChatType.WORKING)
                .build()
                .toUpdate());
        verify(chatService, times(0)).getTopicChatList();
    }

    //todo separate tests ?
    @Test
    void notifyHandlerTest() {
        enableChat(TestData.ChatType.WORKING, "working");
        enableChat(TestData.ChatType.WORKING_2, "working_2");

        updateProcessService.process(TestData.builder()
                .command(AdminCommand.NOTIFY)
                .chatType(TestData.ChatType.WORKING)
                .user(TestData.User.ADMIN)
                .text("working,working_2-test message")
                .build()
                .toUpdate());

        verify(telegramApi, times(1))
                .sendMessage(eq(TestData.ChatType.WORKING.getChatId()), eq("test message"));
        verify(telegramApi, times(1))
                .sendMessage(eq(TestData.ChatType.WORKING_2.getChatId()), eq("test message"));

        //only tagged chats are in broadcast
        updateProcessService.process(TestData.builder()
                .command(AdminCommand.NOTIFY)
                .chatType(TestData.ChatType.WORKING)
                .user(TestData.User.ADMIN)
                .text("working-test message2")
                .build()
                .toUpdate());

        verify(telegramApi, times(1))
                .sendMessage(eq(TestData.ChatType.WORKING.getChatId()), eq("test message2"));
        verify(telegramApi, times(0))
                .sendMessage(eq(TestData.ChatType.WORKING_2.getChatId()), eq("test message2"));

        //disabled chat is not in broadcast
        disableChat(TestData.ChatType.WORKING_2);

        updateProcessService.process(TestData.builder()
                .command(AdminCommand.NOTIFY)
                .chatType(TestData.ChatType.WORKING)
                .user(TestData.User.ADMIN)
                .text("working,working_2-test message3")
                .build()
                .toUpdate());

        verify(telegramApi, times(1))
                .sendMessage(eq(TestData.ChatType.WORKING.getChatId()), eq("test message3"));
        verify(telegramApi, times(0))
                .sendMessage(eq(TestData.ChatType.WORKING_2.getChatId()), eq("test message3"));
    }

    private void enableChat(TestData.ChatType chatType, String tag) {
        updateProcessService.process(TestData.builder()
                .command(AdminCommand.ENABLE_CHAT_SUPPORT)
                .chatType(chatType)
                .user(TestData.User.ADMIN)
                .text(tag)
                .build()
                .toUpdate());
    }

    private void disableChat(TestData.ChatType chatType) {
        updateProcessService.process(TestData.builder()
                .command(AdminCommand.ENABLE_CHAT_SUPPORT)
                .chatType(chatType)
                .user(TestData.User.ADMIN)
                .build()
                .toUpdate());
    }
}
