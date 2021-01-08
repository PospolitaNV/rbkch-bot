package com.npospolita.rbkchbot.deprecated.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeftChatHandler implements Handler {

    public static final String LEFT_CHAT_LINK = "https://www.youtube.com/watch?v=xfT645b6l0s";
    private final TelegramApi api;

    @Override
    public boolean canHandle(Update update) {
        return update.message().leftChatMember() != null;
    }

    @Override
    public void handle(Update update) {
        api.sendMessageWithPreview(update, LEFT_CHAT_LINK);
    }
}
