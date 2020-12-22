package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.DatabaseTestBase;
import com.npospolita.rbkchbot.TestData;
import com.npospolita.rbkchbot.handlers.admin.ChatSetupHandler;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ChatSetupHandlerTest extends DatabaseTestBase {

    @Autowired
    UpdateProcessService updateProcessService;

    @SpyBean
    ChatSetupHandler handler;

    @Autowired
    WorkingChatRepository workingChatRepository;

    @Test
    void processOnlyAdminCommandStartedWithSetup() {
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/setup test"));
        updateProcessService.process(TestData.getSimpleMessageUpdateWithCommand("/setup lol"));

        verify(handler, times(1)).handle(any());
        verify(handler, times(2)).canHandle(any());

        workingChatRepository.findById(-23123123123123L).get().getTag().equals("test");
    }

}