package com.npospolita.rbkchbot.handlers.admin;

import com.npospolita.rbkchbot.api.TelegramApi;
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
public class AddNewCommandHandler extends AdminMessageHandler {

    private static final String COMMAND = "/add_command";

    private final TelegramApi api;

    @Override
    public Result handle(Update update) {
        String text = update.message().text();
        String[] tokens = text.split("-");
        if (tokens.length != 3) {
            api.sendMessage(update, "Command usage: {/add_command}-{command}-{description}");
        } else {
            api.addCommand(tokens[1], tokens[2]);
            api.sendMessage(update, "Command added successfully.");
        }
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
                && StringUtils.hasText(update.message().text())
                && update.message().text().startsWith(COMMAND);
    }
}
