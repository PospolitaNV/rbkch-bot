package com.npospolita.rbkchbot.handlers;

import com.npospolita.rbkchbot.service.ChatService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public abstract class CommonMessageHandler implements Handler {

    @Autowired
    ChatService chatService;

    //todo add user service
    public boolean canHandle(Update update) {
        return update.message() != null
                && chatService.isInWorkingChat(update.message().chat().id())
                && StringUtils.hasText(update.message().text());

    }

}
