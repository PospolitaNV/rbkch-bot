package com.npospolita.rbkchbot.handlers.admin;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.MembershipService;
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
public class ChatDisableHandler extends AdminMessageHandler {

    private static final String COMMAND = "/disable";

    private final TelegramApi api;
    private final ChatService chatService;
    private final MembershipService membershipService;

    @Override
    public Result handle(Update update) {
        chatService.removeWorkingChat(update.message().chat().id());
        membershipService.removeAllMembership(update.message().chat().id());
        api.sendMessage(update, "Chat disabled.");
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
                && StringUtils.hasText(update.message().text())
                && update.message().text().startsWith(COMMAND);
    }
}
