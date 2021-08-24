package com.npospolita.rbkchbot.handlers;

import com.npospolita.rbkchbot.service.ChatService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NameChangedHandler implements Handler {

    @Autowired
    ChatService chatService;

    public boolean canHandle(Update update) {
        return update.message() != null
                && update.message().newChatTitle() != null
                && (chatService.isInWorkingChat(update.message().chat().id()));

    }

}
