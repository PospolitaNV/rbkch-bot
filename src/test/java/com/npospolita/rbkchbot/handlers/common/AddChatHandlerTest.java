package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.DatabaseTestBase;
import com.npospolita.rbkchbot.TestData;
import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.WorkingChat;
import com.npospolita.rbkchbot.handlers.admin.AddChatHandler;
import com.npospolita.rbkchbot.repo.TopicChatRepository;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AddChatHandlerTest extends DatabaseTestBase {

    @Autowired
    UpdateProcessService updateProcessService;

    @SpyBean
    AddChatHandler handler;

    @Autowired
    ChatService chatService;

    @Autowired
    WorkingChatRepository workingChatRepository;

    @Autowired
    TopicChatRepository topicChatRepository;

    @MockBean
    TelegramApi api;

    @BeforeEach
    public void init() {
        workingChatRepository.save(new WorkingChat(-23123123123123L, "test"));
    }

    @Test
    void addChatHandler() {
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/add_chat-description-link"));
        updateProcessService.process(TestData.getSimpleMessageUpdateWithCommand("/chats"));

        verify(handler, times(1)).handle(any());
        verify(handler, times(2)).canHandle(any());
        verify(api, times(1)).sendMessage(any(), eq("[description](link)\n"));
    }
}