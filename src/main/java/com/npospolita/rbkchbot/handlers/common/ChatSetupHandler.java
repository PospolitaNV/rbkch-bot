package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.handlers.AdminMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
public class ChatSetupHandler extends AdminMessageHandler {

    private static final String COMMAND = "/setup";

    @Override
    public Result handle(Update update) {
        log.info("I'm in chat setup handler!");
        return Result.CONTINUE;
    }

    public boolean canHandle(Update update) {
        return super.canHandle(update) && update.message().text().startsWith(COMMAND);
    }
}
