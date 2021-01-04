package com.npospolita.rbkchbot;

import com.npospolita.rbkchbot.api.TelegramApi;
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

import static com.npospolita.rbkchbot.TestData.DEFAULT_CHAT_ID;
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


    @Test
    void membershipTrackingTest() {
        //can't process message outside chat
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", 123L));
        verify(chatService, times(0)).getTopicChatList();

        //enable in-chat processing
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/setup"));

        //processed in-chat message
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", DEFAULT_CHAT_ID));
        verify(chatService, times(1)).getTopicChatList();

        //processed outside-chat message cause of membership
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", 1234L));
        verify(chatService, times(2)).getTopicChatList();

        //user left chat
        updateProcessService.process(TestData.getLeftChatUpdateWithChat(DEFAULT_CHAT_ID));
        verify(membershipService, times(1)).removeMembership(any(), any());

        clearInvocations(chatService, membershipService);

        //can't process outside-chat message cause of membership
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", 1234L));
        verify(chatService, times(0)).getTopicChatList();

        //user join chat
        updateProcessService.process(TestData.getNewChatMemberUpdateWithChat(DEFAULT_CHAT_ID));
        verify(membershipService, times(1)).addMembership(any(), any());

        //processed outside-chat message cause of membership
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", 1234L));
        verify(chatService, times(1)).getTopicChatList();

        clearInvocations(chatService, membershipService);

        //disabled chat
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/disable"));

        //can't process outside-chat message cause of membership
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", 1234L));
        verify(chatService, times(0)).getTopicChatList();

        //can't process in-chat message cause of disabled chat
        updateProcessService.process(TestData.getSimpleMessageUpdateWithChatAndCommand("/chats", DEFAULT_CHAT_ID));
        verify(chatService, times(0)).getTopicChatList();
    }
}
