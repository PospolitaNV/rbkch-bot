package com.npospolita.rbkchbot.handlers;


import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Value;

public abstract class AdminMessageHandler implements Handler {
    @Value("${admin.id}")
    Integer adminId;

    @Override
    public boolean canHandle(Update update) {
        return update.message() != null
                && update.message().from().id().equals(adminId);
    }
}
