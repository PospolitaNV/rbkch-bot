package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.TestData;
import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.UpdateProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RetraHandlerTest {

    @Autowired
    UpdateProcessService updateProcessService;

    @SpyBean
    RetraHandler handler;

    @Autowired
    ChatService chatService;

    @BeforeEach
    public void init() {
        chatService.addWorkingChat(-23123123123123L);
    }

    @Test
    void processCommandStartedWithRetra() {
        updateProcessService.process(TestData.getSimpleMessageUpdateWithCommand("/retra"));
        updateProcessService.process(TestData.getSimpleMessageUpdate());

        verify(handler, times(1)).handle(any());
        verify(handler, times(2)).canHandle(any());
    }

}