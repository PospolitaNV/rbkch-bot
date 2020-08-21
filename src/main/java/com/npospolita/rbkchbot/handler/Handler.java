package com.npospolita.rbkchbot.handler;

import com.pengrad.telegrambot.model.Update;

public interface Handler {

    boolean canHandle(Update update);

    void handle(Update update);
}
