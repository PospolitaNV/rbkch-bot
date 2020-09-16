package com.npospolita.rbkchbot.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TolikOnVacationHandler implements Handler {

    private final TelegramApi api;

    @Value("${tolik.id}")
    Integer tolikId;


    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        return message.from().id().equals(tolikId)
                && Math.random() < 0.2;
    }

    @Override
    public void handle(Update update) {
        api.sendMessage(update, "#толикуходивотпуск");
    }

}
