package com.npospolita.rbkchbot.handlers.admin;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.constant.AdminCommand;
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
public class ChatEnableHandler extends AdminMessageHandler {

    private static final AdminCommand command = AdminCommand.ENABLE_CHAT_SUPPORT;

    private final TelegramApi api;
    private final ChatService chatService;

    @Override
    public Result handle(Update update) {
        String[] tokens = update.message().text().split("-");
        if (tokens.length != 2) {
            api.sendMessage(update, command.getUsage());
        } else {
            chatService.addWorkingChat(update.message().chat().id(), tokens[1], update.message().chat().title());
            api.sendMessage(update, command.getResponse());
        }
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
                && StringUtils.hasText(update.message().text())
                && update.message().text().startsWith(command.getCommand());
    }
}
