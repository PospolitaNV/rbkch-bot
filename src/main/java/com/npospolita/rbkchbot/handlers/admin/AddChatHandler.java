package com.npospolita.rbkchbot.handlers.admin;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.service.ChatService;
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
public class AddChatHandler extends AdminMessageHandler {

    private static final String COMMAND = "/add";

    private final TelegramApi api;
    private final ChatService chatService;

    @Override
    public Result handle(Update update) {
        String[] split = update.message().text().split("-");
        if (split.length != 3) {
            api.sendMessage(update, "Command usage: {/add}-{description}-{link}");
        } else {
            chatService.addTopicChat(split[1], split[2]);
            api.sendMessage(update, "Chat added successfully.");
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
