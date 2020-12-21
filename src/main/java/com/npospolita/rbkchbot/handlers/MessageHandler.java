package com.npospolita.rbkchbot.handlers;

import com.pengrad.telegrambot.model.Update;

public interface MessageHandler extends Handler {
    Result handle(Update update);

    default boolean canHandle(Update update) {
        return update.message() != null;
    }
}
