package com.npospolita.rbkchbot.handlers.admin;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.constant.AdminCommand;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Order
@Component
@RequiredArgsConstructor
public class AvailableCommandsHandler extends AdminMessageHandler {

    private static final AdminCommand command = AdminCommand.GET_COMMANDS;

    private final TelegramApi api;

    @Override
    public Result handle(Update update) {
        api.sendMessage(update, getAvailableComands());
        return Result.STOP;
    }

    private String getAvailableComands() {
        StringBuilder stringBuilder = new StringBuilder();
        for (AdminCommand value : AdminCommand.values()) {
            stringBuilder
                    .append(value.getUsage())
                    .append(",\n")
                    .append(value.getDescription())
                    .append("\n\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
                && StringUtils.hasText(update.message().text())
                && update.message().text().startsWith(command.getCommand());
    }
}
