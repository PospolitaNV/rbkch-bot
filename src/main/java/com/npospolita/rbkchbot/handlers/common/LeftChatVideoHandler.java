package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.LeftChatHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeftChatVideoHandler extends LeftChatHandler {

    private final TelegramApi telegramApi;

    public static final String LEFT_CHAT_LINK = "https://www.youtube.com/watch?v=xfT645b6l0s";

    @Override
    public Result handle(Update update) {
        telegramApi.sendMessageWithPreview(update, LEFT_CHAT_LINK);
        return Result.STOP;
    }

}
