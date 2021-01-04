package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.constant.UserCommand;
import com.npospolita.rbkchbot.handlers.CommonMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order
@Component
@RequiredArgsConstructor
public class AboutHandler extends CommonMessageHandler {

    private static final UserCommand command = UserCommand.ABOUT;

    public static final String ABOUT = "author: PospolitaNV\nFeel free to contribute: https://github.com/PospolitaNV/rbkch-bot";

    private final TelegramApi telegramApi;

    @Override
    public Result handle(Update update) {
        telegramApi.sendMessage(update, ABOUT);
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update) && update.message().text().startsWith(command.getCommand());
    }
}
