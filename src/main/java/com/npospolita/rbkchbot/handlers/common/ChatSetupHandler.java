package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.handlers.AdminMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.service.ChatService;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@RequiredArgsConstructor
public class ChatSetupHandler extends AdminMessageHandler {

    private static final String COMMAND = "/setup";

    private final ChatService chatService;

    @Override
    public Result handle(Update update) {
        chatService.addWorkingChat(update.message().chat().id());
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
                && StringUtils.hasText(update.message().text())
                && update.message().text().startsWith(COMMAND);
    }
}
