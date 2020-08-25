package com.npospolita.rbkchbot.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeftChatHandler implements Handler {

    private final TelegramApi api;

    @Override
    public boolean canHandle(Update update) {
        return update.message().leftChatMember() != null;
    }

    @Override
    public void handle(Update update) {
        api.sendMessage(update, "https://www.youtube.com/watch?v=xfT645b6l0s");
    }
}
