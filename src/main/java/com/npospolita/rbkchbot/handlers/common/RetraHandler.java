package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.constant.UserCommand;
import com.npospolita.rbkchbot.handlers.CommonMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order
@Component
@RequiredArgsConstructor
public class RetraHandler extends CommonMessageHandler {

    private static final UserCommand command = UserCommand.RETRA;

    private final TelegramApi telegramApi;

    @Value("${rbkch.retra}")
    private String retraInfo;

    @Override
    public Result handle(Update update) {
        telegramApi.sendMessage(update, retraInfo);
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update) && update.message().text().startsWith(command.getCommand());
    }
}
