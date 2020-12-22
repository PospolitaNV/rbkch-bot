package com.npospolita.rbkchbot.handlers;

import com.pengrad.telegrambot.model.Update;

public interface Handler {
    Result handle(Update update);

    boolean canHandle(Update update);
}
