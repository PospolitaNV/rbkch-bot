package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.TestBase;
import com.npospolita.rbkchbot.TestData;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ChatSetupHandlerTest extends TestBase {

    @Autowired
    UpdateProcessService updateProcessService;

    @SpyBean
    ChatSetupHandler handler;

    @Test
    void processOnlyAdminCommandStartedWithSetup() {
        updateProcessService.process(TestData.getAdminMessageUpdateWithCommand("/setup"));
        verify(handler, times(1)).handle(any());

        updateProcessService.process(TestData.getSimpleMessageUpdate());
        verify(handler, times(1)).handle(any());

    }

}