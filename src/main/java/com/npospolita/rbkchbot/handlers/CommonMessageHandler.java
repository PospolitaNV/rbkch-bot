package com.npospolita.rbkchbot.handlers;

import com.npospolita.rbkchbot.service.ChatService;
import com.npospolita.rbkchbot.service.MembershipService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public abstract class CommonMessageHandler implements Handler {

    @Autowired
    ChatService chatService;

    @Autowired
    MembershipService membershipService;

    public boolean canHandle(Update update) {
        return update.message() != null
                && (chatService.isInWorkingChat(update.message().chat().id()) //todo is in PERSONAL MESSAGES!
                    || membershipService.isChatMember(update.message().from()))
                && StringUtils.hasText(update.message().text());

    }

}
